package neuss

import neuss.control.DomainObjectException

import org.grails.jaxrs.provider.DomainObjectNotFoundException
import static javax.ws.rs.core.Response.Status.CONFLICT

class UserResourceService {
    
    def create(User dto) {
        dto.save()
    }

    def read(def id) {
        def obj = User.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(User.class, id)
        }
        obj
    }
    
    def readAll() {
        User.findAll()
    }
    
    def update(User newUser) {
        def obj = User.get(newUser.id)
        if (!obj) {
			throw new DomainObjectNotFoundException(User.class, newUser.id)
        }
        if ( obj.userName != newUser.userName ) {
			throw new DomainObjectException(newUser, CONFLICT, "Username can't be changed.")
		}
			
		for ( e in newUser.properties) {
			if ( e.key != 'userName' && e.key != 'dateCreated' )
			obj."$e.key" = e.value
		}
		obj.setLastUpdated(new Date())
		
		if ( ! obj.validate() ) {
			throw new DomainObjectException(newUser, CONFLICT, "Error when validating user: "+obj.errors.allErrors)
		} else {
			obj.save()
		}
		return obj
		
    }
    
    void delete(def id) {
        def obj = User.get(id)
        if (obj) { 
            obj.delete()
        }
    }
	
	def findByUserName(def username) {
		def obj = User.findByUserName(username)
		if (!obj) {
			throw new DomainObjectNotFoundException(User.class, username)
		}
		obj
	}
    
}

