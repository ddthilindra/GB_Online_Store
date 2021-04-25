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

	// User registration method (POST)
	public String RegisterUser(String uName, String uEmail, String uAddress, String uPhone, String uDob, String uPass) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			String query = "SELECT email FROM user WHERE email=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, uEmail);
			ResultSet rs = preparedStmt.executeQuery();

			if (rs.next()) {
				return "This email address is already being used..!";
			} else {

				// create a prepared statement
				String query2 = " INSERT INTO user (Id,Name,Email,Address,PhoneNo,Dob,Password)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2);
				// binding values
				// System.out.println(name + email + pass);
				preparedStmt2.setInt(1, 0);
				preparedStmt2.setString(2, uName);
				preparedStmt2.setString(3, uEmail);
				preparedStmt2.setString(4, uAddress);
				preparedStmt2.setString(5, uPhone);
				preparedStmt2.setString(6, uDob);
				preparedStmt2.setString(7, uPass);
				// execute the statement
				preparedStmt2.execute();
				con.close();
				output = "<h2>User has been Successfully Registered..!</h2>";
			}

		} catch (Exception e) {
			output = "Error while registering the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Read all user information of the table (GET)
	public String readAllUsers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<h2>Registerd users in the System</h2><br><table border='1'><tr><th>ID</th><th>Name</th>"
					+ "<th>Email</th>" + "<th>Address</th>" + "<th>Phon No</th>" + "<th>DOB</th>" + "<th>User Role</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "SELECT * FROM User";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String ID = Integer.toString(rs.getInt("Id"));
				String NAME = rs.getString("Name");
				String EMAIL = rs.getString("Email");
				String ADDRESS = rs.getString("Address");
				String PHNONE = rs.getString("PhoneNo");
				String DOB = rs.getString("Dob");
				String ROLE = rs.getString("Role");
				// Add into the html table
				output += "<tr><td>" + ID + "</td>";
				output += "<td>" + NAME + "</td>";
				output += "<td>" + EMAIL + "</td>";
				output += "<td>" + ADDRESS + "</td>";
				output += "<td>" + PHNONE + "</td>";
				output += "<td>" + DOB + "</td>";
				output += "<td>" + ROLE + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='Item.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + ID + "'>" + "</form></td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the users.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Update user information (PUT)
	public String updateUser(String id, String uName, String uEmail, String uAddress, String uPhone, String uDob,
			String uPass) {
		String output = "";
		// System.out.println(ID + name + email + pass);
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE user SET Name=?,Email=?,Address=?,PhoneNo=?,Dob=?,Password=? WHERE Id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, uName);
			preparedStmt.setString(2, uEmail);
			preparedStmt.setString(3, uAddress);
			preparedStmt.setString(4, uPhone);
			preparedStmt.setString(5, uDob);
			preparedStmt.setString(6, uPass);
			preparedStmt.setInt(7, Integer.parseInt(id));
			// execute the statement
			int rs = preparedStmt.executeUpdate();

			if (rs > 0) {
				output = "<h2>User details has been updated successfully..!</h2>";
			} else {
				output = "User details update Failed..!";
			}
			con.close();

		} catch (Exception e) {
			output = "Error while updating the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Delete user method (DELETE)
	public String deleteUser(String id) {
		System.out.println(id);
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "DELETE FROM user WHERE Id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			int rs = preparedStmt.executeUpdate();

			if (rs > 0) {
				output = "<h2>User has been deleted successfully</h2>";
			} else {
				output = "User delete Failed";
			}
			con.close();

		} catch (Exception e) {
			output = "Error while deleting the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// This method use for user login. return user, id, name and email.
	// It can be use store the session variables. Before store we have split.
	public String[] loginUser(String email, String pwd) {

		String[] output = null;

		try {
			Connection con = connect();
			// System.out.println("called"+email+pwd);
			if (con == null) {
				output[0] = "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " select * from user where Email=? and Password=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, pwd);
			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();
			
			
			//System.out.println(ROLE);
			if (rs.next()) {
				String ROLE = rs.getString("Role");
				if(ROLE.equals("Customer")) {
					String[] strs = { "You are Customer!<br>","(Set these varible to the session)<br>",rs.getString("Id"), rs.getString("Name"), rs.getString("Email") };
					output = strs;
				}
				else {
					String[] strs = { "You are Admin!<br>","(Set these varible to the session)<br>",rs.getString("Id"), rs.getString("Name"), rs.getString("Email") };
					output = strs;
				}
				
			} else {
				output = null;
			}
			con.close();

		} catch (Exception e) {
			output[0] = "Error while Login the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Reset user password (PUT)
	public String resetPassUser(String uEmail, String uPass) {
		String output = "";
		// System.out.println(ID + name + email + pass);
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE user SET Password=? WHERE Email=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, uPass);
			preparedStmt.setString(2, uEmail);
			// execute the statement
			int rs = preparedStmt.executeUpdate();

			if (rs > 0) {
				output = "<h2>User password has been changed successfully..!</h2>";
			} else {
				output = "User password update Failed..!";
			}
			con.close();

		} catch (Exception e) {
			output = "Error while updating the user password.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
