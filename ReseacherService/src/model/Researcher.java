package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Researcher {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/avinda", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return con;
	}

	public String insertResearcher(String reId, String reName, String proName, String proVal, String reDesp, String reDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into researcher(`reId`, `reName`, `proName`, `proVal`, `reDesp`, `reDate`)" + " values ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, reName);
			preparedStmt.setString(3, proName);
			preparedStmt.setString(4, proVal);
			preparedStmt.setString(5, reDesp);
			preparedStmt.setString(6, reDate);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Researcher.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readResearchers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Research ID</th><th>Research Name</th><th>Product Name</th><th>Product Value</th><th>Description</th><th>Date</th></tr>";
			String query = "select * from researcher";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String rid = Integer.toString(rs.getInt("reId"));
				String rname = rs.getString("reName");
				String proName = rs.getString("proName");
				String proVal = rs.getString("proVal");
				String reDesp = rs.getString("reDesp");
				String reDate = rs.getString("reDate");

				output += "<tr><td>" + rid + "</td>";
				output += "<td>" + rname + "</td>";
				output += "<td>" + proName + "</td>";
				output += "<td>" + proVal + "</td>";
				output += "<td>" + reDesp + "</td>";
				output += "<td>" + reDate + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Researchers.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateResearcher(String reId, String reName, String proName, String proVal, String reDesp, String reDate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE researcher SET reName=?,proName=?,proVal=?,reDesp=?,reDate=?" + "WHERE reId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, reName);
			preparedStmt.setString(2, proName);
			preparedStmt.setString(3, proVal);
			preparedStmt.setString(4, reDesp);
			preparedStmt.setString(5, reDate);
			preparedStmt.setInt(6, Integer.parseInt(reId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the researcher ";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteResearcher(String reId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from researcher where reId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(reId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the researcher.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
