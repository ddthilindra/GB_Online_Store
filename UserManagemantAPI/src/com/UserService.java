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

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String RegisterUser(@FormParam("Name") String username, @FormParam("Email") String email,@FormParam("Password") String password) {
		//System.out.println("Api called");
		String output = userObj.RegisterUser(username, email, password);
		return output;
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String readAllUsers() {
		//System.out.println("Api called");
		return userObj.readAllUsers();
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(@FormParam("Id") String uID,@FormParam("Name") String uName, @FormParam("Email") String uEmail,@FormParam("Password") String uPass) {
		System.out.println("Api called");
		String output = userObj.updateUser(uID, uName, uEmail, uPass);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUser(@FormParam("Id") String uID) {
		System.out.println("Api called");
		String output = userObj.deleteUser(uID);
		System.out.println(uID);
		return output;
	}
}
