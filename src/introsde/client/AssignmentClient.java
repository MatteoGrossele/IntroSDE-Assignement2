package introsde.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import java.io.*;


import org.glassfish.jersey.client.ClientConfig;

public class AssignmentClient {
	//URI of the web server
	static String URI = "http://127.0.1.1:5700/introsde-assignment2/";

	//Names of the logs files
	static String XML_LOGS = "client_xml.txt";
	static String JSON_LOGS= "client_json.txt";


	public static void main(String[] args) throws IOException {
		//Connection to the web service
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());
		//Variable needed to parse the response
		Response response;
		int status;
		String body = "";
		String result = "";
		String message = "";
		//Open logs file for XMl and Json to write response from server
        BufferedWriter writerXML = new BufferedWriter(new FileWriter(new File(XML_LOGS)));
        BufferedWriter writerJson = new BufferedWriter(new FileWriter(new File(JSON_LOGS)));
        //Path for the current request
        String requestPath = "";

        writerXML.write("------------------------------ REQUEST 1 XML --------------------------\n");
        System.out.print("------------------------------ REQUEST 1 XML --------------------------\n");
        requestPath = "person";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,"<person>") > 3)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(1, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ REQUEST 1 JSON --------------------------\n");
        System.out.print("------------------------------ REQUEST 1 JSON --------------------------\n");
        requestPath = "person";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(countOccourences(body,"\"firstname\":") > 3)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(1, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		writerXML.write("------------------------------ REQUEST 2 XML --------------------------\n");
        System.out.print("------------------------------ REQUEST 2 XML --------------------------\n");
        requestPath = "person/1";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(2, "GET",URI + requestPath,"APPLICATION/XML", "APPLICATION/XML", result, status, body);

		//Print result from the request
		writerXML.write(message);
		System.out.print(message);

		writerJson.write("------------------------------ REQUEST 2 JSON --------------------------\n");
        System.out.print("------------------------------ REQUEST 2 JSON --------------------------\n");
		requestPath = "person/1";

		response = service.path(requestPath).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		//Condition for the request to be successfull
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		//Get message to print out 
		message = getMessageInfo(2, "GET",URI + requestPath,"APPLICATION/JSON", "APPLICATION/JSON", result, status, body);

		//Print result from the request
		writerJson.write(message);
		System.out.print(message);

		/*
		// // GET BASEURL/rest/helloworld
		// // Accept: text/plain
		System.out.println(service.path("salutation").request().accept(MediaType.TEXT_PLAIN).get().readEntity(String.class));
		// // Get plain text
		System.out.println(service.path("salutation")
				.request().accept(MediaType.TEXT_PLAIN).get().readEntity(String.class));
		// Get XML
		System.out.println(service.path("salutation")
				.request()
				.accept(MediaType.TEXT_XML).get().readEntity(String.class));
		// // The HTML
		System.out.println(service.path("salutation").request()
.accept(MediaType.TEXT_HTML).get().readEntity(String.class));
*/
	}

	private static int countOccourences(String stringa, String token)
	{
		int counter = 0;
		int lastIndex = 0;

		while(lastIndex != -1){

			lastIndex = stringa.indexOf(token,lastIndex);

			if(lastIndex != -1){
				counter ++;
				lastIndex += token.length();
			}
		}

		return counter;
	}

	private static String getMessageInfo(int numero, String httpMetodo, String url, String tipo, String contentType, String output, int status, String risposta)
	{
		return  "\n\nRequest #"+numero+": "+httpMetodo+" "+url+" Accept: "+tipo+" content-type: "+contentType +
				"\n=> Result: "+ output +
				"\n=> HTTP Status: "+ status +
				"\n\n" +
				risposta + "\n\n";
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(AssignmentClient.URI).build();
	}
}