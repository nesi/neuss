package neuss

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class ExternalCommandService {


    def read(def id) {
        def obj = ExternalCommand.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(ExternalCommand.class, id)
        }
        obj
    }
    
    def readAll() {
        ExternalCommand.findAll()
    }
    
    
}
