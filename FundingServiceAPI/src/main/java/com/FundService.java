package com;

import model.Fund;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Funds")
public class FundService {


	Fund fundObj = new Fund();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readFunds() {
		return fundObj.readFunds();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFund(@FormParam("resID") String rID, @FormParam("resName") String rName,
			@FormParam("fAmount") String fAmount, @FormParam("subject") String subject,
			@FormParam("description") String desc) {
		String output = fundObj.insertFund(rID, rName, fAmount, subject, desc);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFund(@FormParam("fundID") String fundID, @FormParam("resID") String rID,
			@FormParam("resName") String rName, @FormParam("fAmount") String fAmount,
			@FormParam("subject") String subject, @FormParam("description") String desc) {
		String output = fundObj.updateFund(fundID, rID, rName, fAmount, subject, desc);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteFund(@FormParam("fundID") String fundID) {
		String output = fundObj.deleteFund(fundID);
		return output;
	}
}