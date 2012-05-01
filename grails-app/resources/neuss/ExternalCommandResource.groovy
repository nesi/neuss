package neuss

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import static org.grails.jaxrs.response.Responses.*

@Path('/api/commands')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ExternalCommandResource {

	def externalCommandService
	
		@GET
		@Path('/log')
		Response getCommands() {
			ok externalCommandService.readAll()
		}
	
}
