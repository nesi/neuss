package neuss

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.PUT
import javax.ws.rs.core.Response

import neuss.control.DomainObjectException

@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class UserResource {
    
    def userResourceService
    def user
    
    @GET
    Response read() {
        ok user
    }
    
    @PUT
    Response update(User newUser) {
		def username = newUser.userName
		if ( ! username ) {
			newUser.setUserName(user.userName)
		} else {
			if ( username != user.userName ) {
				throw new DomainObjectException(newUser, CONFLICT, "Username can't be changed.")
			}
		}
        ok userResourceService.update(newUser)
    }
    
    @DELETE
    void delete() {
        userResourceService.delete(user.id)
    }
	

    
}

