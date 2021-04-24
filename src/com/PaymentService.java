package com;

import model.Payment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Payments")

public class PaymentService {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment()

	{
		return PaymentObj.readPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("pyId") String pyId,
			@FormParam("pName") String pName,
			@FormParam("cName") String cName,
			@FormParam("pyDate") String pyDate, 
			@FormParam("quantity") String quantity,
			@FormParam("amount") String amount) {
		String output = PaymentObj.insertPayment(pyId, pName, cName, pyDate,quantity, amount);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String updatePayment(@FormParam("pyId") String pyId,
			@FormParam("pName") String pName,
			@FormParam("cName") String cName,
			@FormParam("pyDate") String pyDate,
			@FormParam("quantity") String quantity, 
			@FormParam("amount") String amount) 
	{

		String output = PaymentObj.updatePayment(pyId, pName, cName, pyDate, quantity,amount);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	 public String deleteItem(@FormParam("pyId") String pyId)
    {
   	 String output = PaymentObj.deletePayment(pyId);
   	 return output;
    }
}
