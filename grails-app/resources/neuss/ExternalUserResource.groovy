package neuss

import grails.converters.JSON
import grails.converters.XML

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import neuss.control.ExternalCommandException
import neuss.model.ExternalUser
import neuss.model.GoldUser

import static org.grails.jaxrs.response.Responses.*




@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ExternalUserResource {
	
	User user
	String externalServiceName
	
	private Map generateDetailsMap(def command=null) {
		def propertiesMap = [:]
		propertiesMap['isRegistered'] = externalUser.isRegistered()
		propertiesMap['goldUserName'] = externalUser.getUserName()
		if ( command ) {
			propertiesMap['command'] = command
		}
		propertiesMap
	}
	
	@GET
	@Produces('application/json')
	JSON returnStateJSON() {
		generateDetailsMap(null) as JSON
	}
	
	@GET
	@Produces('application/xml')
	XML returnStateXML() {
		generateDetailsMap(null) as XML
	}
	
	@GET
	@Produces('text/plain')
	String returnStateString() {
		return generateDetailsMap(null).toString()
	}
	
	@POST
	@Path('/register')
	void register() {

		user.register(externalServiceName)

	}

	
	@POST
	@Produces('text/plain')
	Response executeString(String command) {
		
		def result = executeCommand(command)

		ok generateDetailsMap(result).toString()
	}

	@POST
	@Produces('application/xml')
	Response executeXML(String command) {
		def result = executeCommand(command)
		
		ok generateDetailsMap(result) as XML

	}
	
	@POST
	@Produces('application/json')
	Response executeJSON(String command) {
		def result = executeCommand(command)
		
		ok generateDetailsMap(result) as JSON
	}

	def executeCommand(String command) {
		
		try {
			def result = user."$command"()
			result
		} catch (MissingMethodException mme) {
			println mme
			throw new ExternalCommandException(command)
		}
		

		
		
	}
	 
}
