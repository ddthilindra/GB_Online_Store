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
}
