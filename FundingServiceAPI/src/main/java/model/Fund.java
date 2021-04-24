package model;

import java.sql.*;

public class Fund {

	//Connect to the DB
	private Connection connect()
	{

		Connection con = null;


		try
		{
			Class.forName("com.mysql.jdbc.Driver");

	        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/funddb", "root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	//Method Insert a fund
	public String insertFund(String rID, String rName, String fAmount,String subject, String desc)
	{
		String output = "";
		try
		{
			//Connection
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting a fund."; 
		    }
			
			// create a prepared statement
			String query = " insert into funds(resID, resName, fAmount, subject, description)"
					       + " values (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, rID);
			preparedStmt.setString(2, rName);
			preparedStmt.setDouble(3, Double.parseDouble(fAmount));
			preparedStmt.setString(4, subject);
			preparedStmt.setString(5, desc);
			
			// execute the statement

			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting the fund.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//Method to read funds
	public String readFunds()
	{
		String output = "";
		try
		{
			//Connection
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Researcher ID</th><th>Researcher Name</th>" +
					"<th>Funding Amount</th>" +
					"<th>Subject</th>" +
					"<th>Description</th>" +
					"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from funds";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
						
			// iterate through the rows in the result set
			while (rs.next())
			{
				String fundID = Integer.toString(rs.getInt("fundID"));
				String rID = rs.getString("resID");
				String rName = rs.getString("resName");
				String fAmount = Double.toString(rs.getDouble("fAmount"));
				String subject = rs.getString("subject");
				String desc = rs.getString("description");
				
				
				// Add into the html table
				output += "<tr><td>" + rID + "</td>";
				output += "<td>" + rName + "</td>";
				output += "<td>" + fAmount + "</td>";
				output += "<td>" + subject + "</td>";
				output += "<td>" + desc + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'"
						   +"class='btn btn-secondary'></td>"
								+ "<td><form method='post' action='funds.jsp'>"
								+ "<input name='btnRemove' type='submit' value='Remove'"
								+"class='btn btn-danger'>"
										+ "<input name='fundID' type='hidden' value='" + fundID
										+ "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//Method to update a fund
	public String updateFund(String fundID, String rID, String rName, String fAmount,String subject, String desc)
	{
		String output = "";
		try
		{
			//Connection
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating???"; 
			}
			
			// create a prepared statement
			String query = "UPDATE funds SET resID=?,resName=?,fAmount=?,subject=?,description=?"
					        + "WHERE fundID=?";
			
		    PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, rID);
			preparedStmt.setString(2, rName);
			preparedStmt.setDouble(3, Double.parseDouble(fAmount));
			preparedStmt.setString(4, subject);
			preparedStmt.setString(5, desc);
			preparedStmt.setInt(6, Integer.parseInt(fundID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the Fund???";
			System.err.println(e.getMessage());
		}
		
		return output;
		
		
	}

	//Method to Delete a fund
	public String deleteFund(String fundID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from funds where fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the Funf???";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
}