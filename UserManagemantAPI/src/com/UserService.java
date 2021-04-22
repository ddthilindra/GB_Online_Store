package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.User;

@Path("/User")
public class UserService {

	User userObj = new User();

	// This API can use for add a new user information to DB
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String RegisterUser(@FormParam("Name") String username, @FormParam("Email") String email,@FormParam("Address") String address,@FormParam("PhoneNo") String phoneno,@FormParam("Dob") String dob,@FormParam("Password") String password) {
		//System.out.println("Api called");
		String output = userObj.RegisterUser(username, email,address,phoneno,dob, password);
		return output;
	}
	
	// This API can use for get the all information of users from DB
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readAllUsers() {
		//System.out.println("Api called");
		return userObj.readAllUsers();
	}
	
	// This API can use for update the all user information in DB
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(@FormParam("Id") String id,@FormParam("Name") String username, @FormParam("Email") String email,@FormParam("Address") String address,@FormParam("PhoneNo") String phoneno,@FormParam("Dob") String dob,@FormParam("Password") String password) {
		//System.out.println("Api called");
		String output = userObj.updateUser(id, username, email,address,phoneno,dob, password);
		return output;
	}
	
	// This API can use for delete the all user information in DB
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(@FormParam("Id") String id) {
		//System.out.println("Api called");
		//System.out.println(uID);
		String output = userObj.deleteUser(id);		
		return output;
	}
	
	// This API can use for store user id, name and email to session variables
	@POST
	@Path("/login") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String LoginUser(@FormParam("Email") String email,@FormParam("Password") String password) {
		System.out.println("Api called");
		String[] output = userObj.loginUser(email, password);
		String sessionDetails="";
		if(output!=null) {
			sessionDetails=(output[0]+","+output[1]+","+output[2]);
		}
		else {
			sessionDetails="Incorrect username or password..!";
		}		
		return sessionDetails;
	}
	
	// This API can use for reset password of user
	@PUT
	@Path("/reset")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(@FormParam("Email") String email,@FormParam("Password") String password) {
		String output = userObj.resetPassUser(email, password);
		return output;
	}
}
