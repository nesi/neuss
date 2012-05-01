package neuss.control

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

import static javax.ws.rs.core.MediaType.APPLICATION_XML
import static javax.ws.rs.core.Response.Status.NOT_FOUND

class DomainObjectException extends WebApplicationException {
	

	public DomainObjectException(def obj, Status status, String message) {
		super(createResponse(obj, status, message))
	}

	
	private static Response createResponse(def obj, Status status, String msg) {
		Response.status(status).entity(messageMessage(obj, msg)).type(APPLICATION_XML).build()
	}
	
	private static String messageMessage(def obj, String msg) {
		"<error>"+obj.toString()+": " + msg+"</error>"
	}
}
