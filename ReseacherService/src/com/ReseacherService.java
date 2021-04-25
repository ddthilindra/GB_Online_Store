package com;

import model.Researcher;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Researcher")
public class ReseacherService {
	Researcher ResearcherObj = new Researcher();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readResearchers() {
		return ResearcherObj.readResearchers();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("reId") String reId, 
			@FormParam("reName") String reName,
			@FormParam("proName") String proName, 
			@FormParam("proVal") String proVal,
			@FormParam("reDesp") String reDesp, 
			@FormParam("reDate") String reDate) {
		String output = ResearcherObj.insertResearcher(reId, reName, proName, proVal, reDesp, reDate);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)


		
		
		public String updateResearcher(
				@FormParam("reId") String reId,
				@FormParam("reName") String reName,
				@FormParam("proName") String proName, 
				@FormParam("proVal") String proVal,
				@FormParam("reDesp") String reDesp, 
				@FormParam("reDate") String reDate)
		{
		String output = ResearcherObj.updateResearcher(reId, reName, proName, proVal, reDesp, reDate);
		return output;
	  }

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(@FormParam("reId") String reId) 
	{
		
		String output = ResearcherObj.deleteResearcher(reId);
		return output;
	} 
}
