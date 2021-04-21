package model;

import java.sql.*;
import java.util.ArrayList;

public class User {

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String RegisterUser(String name, String email, String pass) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			PreparedStatement preparedStmt = con.prepareStatement("select email from user where email=?");
			preparedStmt.setString(1, email);
			ResultSet rs = preparedStmt.executeQuery();

			if (rs.next()) {
				return "Email already exist..!";
			} else {
				// create a prepared statement
				String query = " insert into user (Id,Name,Email,Password)" + " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt2 = con.prepareStatement(query);
				// binding values
				System.out.println(name + email + pass);
				preparedStmt2.setInt(1, 0);
				preparedStmt2.setString(2, name);
				preparedStmt2.setString(3, email);
				preparedStmt2.setString(4, pass);
				// execute the statement
				preparedStmt2.execute();
				con.close();
				output = "Successfully Registered..!";
			}

		} catch (Exception e) {
			output = "Error while inserting the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readAllUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>ID</th><th>Name</th>" + "<th>Email</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from User";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String ID = Integer.toString(rs.getInt("Id"));
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				// Add into the html table
				output += "<tr><td>" + ID + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + email + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='Item.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='<itemData><itemID>" + ID + "</itemID></itemData>'>" + "</form></td></tr>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateUser(String ID, String name, String email, String pass) {
		String output = "";
		//System.out.println(ID + name + email + pass);
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE user SET Name=?,Email=?,Password=? WHERE Id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, pass);
			//System.out.println(name + email + pass);
			preparedStmt.setInt(4, Integer.parseInt(ID));
			// execute the statement
			int rs = preparedStmt.executeUpdate();

			if (rs>0){
				output = "User details updated successfully";
			}
			else {
				output = "User details update Failed";
			}
			con.close();
			
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
