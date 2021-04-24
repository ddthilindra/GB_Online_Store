package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/onlinestore", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return con;
	}

	public String insertPayment(String pyId, String pName, String cName, String pyDate,String quantity, String amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payments(`pyId`,`pName`, `cName`,`pyDate`,`quantity`,`amount`)" + " values (?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pName);
			preparedStmt.setString(3, cName);
			preparedStmt.setString(4, pyDate);
			preparedStmt.setString(5, quantity);
			preparedStmt.setDouble(6,Double.parseDouble(amount));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment ID</th><th>Product Name</th><th>Customer Name</th><th>Date</th><th>Quantity</th><th>Amount</th></tr>";
			String query = "select * from payments";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String pyId = Integer.toString(rs.getInt("pyId"));
				String pName = rs.getString("pName");
				String cName = rs.getString("cName");
				String pyDate = rs.getString("pyDate");
				String quantity = rs.getString("quantity");
				String amount = Double.toString(rs.getDouble("amount"));

				output += "<tr><td>" + pyId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + cName + "</td>";
				output += "<td>" + pyDate + "</td>";
				output += "<td>" + quantity + "</td>";
				output += "<td>" + amount + "</td>";

			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String pyId, String pName, String cName, String pyDate, String quantity, String amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payments SET pName=?,cName=?,pyDate=?,quantity=?,amount=? WHERE pyId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, pName);
			preparedStmt.setString(2, cName);
			preparedStmt.setString(3, pyDate);
			preparedStmt.setString(4, quantity);
			preparedStmt.setDouble(5,Double.parseDouble(amount) );
			preparedStmt.setInt(6, Integer.parseInt(pyId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(String pyId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from payments where pyId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pyId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
