package neuss

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

import org.codehaus.groovy.grails.compiler.GrailsClassLoader;
import org.grails.jaxrs.GrailsResourceClass

@Path('/api/users')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class UserCollectionResource {

	def userResourceService
	def grailsApplication
	

	@POST
	Response create(User dto) {
		created userResourceService.create(dto)
	}

	@GET
	Response readAll() {
		ok userResourceService.readAll()
	}

//	@Path('/id/{id}')
//	UserResource getResource(@PathParam('id') Long id) {
//		User u = userResourceService.read(id)
//		new UserResource(userResourceService: userResourceService, user:u)
//	}

	@Path('/{username}')
	UserResource getResource(@PathParam('username') String username) {
		User u = userResourceService.findByUserName(username)
		new UserResource(userResourceService: userResourceService, user:u)
	}
	
	@Path('/{username}/{external}')
	def getExternalUserResource(@PathParam('username') String username, @PathParam('external') String external) {
		
		User u = userResourceService.findByUserName(username)
		def classname = 'neuss.model.'+external.capitalize()+'User'

		Class euc = Class.forName(classname, true, Thread.currentThread().contextClassLoader)

		def eu = euc.newInstance(u)
		
		ExternalUserResource er = new ExternalUserResource()
		er.setExternalUser(eu)
		er
	}

}
