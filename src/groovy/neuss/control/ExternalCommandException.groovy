package neuss.control

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

import static javax.ws.rs.core.MediaType.APPLICATION_XML
import static javax.ws.rs.core.Response.Status.NOT_FOUND

class ExternalCommandException extends WebApplicationException {
	
	public ExternalCommandException(String command) {
		super(notFound(command))
	}

	public ExternalCommandException(String command, Status status, String message) {
		super(createResponse(command, status, message))
	}

/**
 * Creates a NOT FOUND response (status code 404) and response entity
 * containing an error message including the id and clazz of the requested
 * resource.
 *
 * @param clazz Grails domain object clazz.
 * @param id Grails domain object id.
 * @return JAX-RS response.
 */
	private static Response notFound(String command) {
		Response.status(NOT_FOUND).entity(notFoundMessage(command)).type(APPLICATION_XML).build()
	}

	private static String notFoundMessage(String command) {
		"<error>command ${command} not found</error>"
	}
	
	private static Response createResponse(String command, Status status, String msg) {
		Response.status(status).entity(messageMessage(command, msg)).type(APPLICATION_XML).build()
	}
	
	private static String messageMessage(String command, String msg) {
		"<error>Command '"+command+"': " + msg+"</error>"
	}
}
