package neuss.model

import javax.xml.bind.annotation.XmlAttribute;

import neuss.ExternalCommand
import neuss.User
import neuss.control.ExternalCommandException

import static javax.ws.rs.core.Response.Status.CONFLICT

class GoldUser implements ExternalUser {
	
	static GoldUser create(User user) {
		def gu = new GoldUser(user)
		return gu
	}
	

	
	public GoldUser() {
	}
	
	public void setUser(User user) {
		this.user = user
	}
	
	User user
	
	public boolean isRegistered() {
		
		ExternalCommand gc = executeGoldCommand("glsuser -show Name -quiet")
		
		if ( gc.stdout.contains(getUserName()) ) {
			return true
		} else {
			return false
		}
		
	}
	
	
	public String register() throws ExternalCommandException {
		
		def name = user.lastName + ', ' + user.firstName
		def email = user.email
		def phone = user.phone

		if ( isRegistered() ) {
			throw new ExternalCommandException("register", CONFLICT, 'User '+getUserName()+' already in Gold.')
		}
		
		ExternalCommand gc = executeGoldCommand('gmkuser -n "'+name+'" -E '+email+' '+getUserName())
		
		if ( ! isRegistered() ) {
			throw new ExternalCommandException('register', CONFLICT, 'Adding of user '+getUserName()+' to Gold failed')
		}
		
		getUserName()
	}
	
	public String getUserName() {
		
		return user.getUserName()
	}
	
	private ExternalCommand executeGoldCommand(String command) {
		ExternalCommand gc = new ExternalCommand(command)
		gc.execute()
		gc
	}

	@Override
	public void deregister() throws ExternalCommandException {
		if ( ! isRegistered() ) {
			//throw new ExternalCommandException("deregister", CONFLICT, 'User '+getUserName()+' not registered in Gold.')
			return
		}
		def name = user.lastName + ', ' + user.firstName
		def email = user.email
		def phone = user.phone
		ExternalCommand gc = executeGoldCommand('grmuser '+getUserName())
		
		if ( isRegistered() ) {
			throw new ExternalCommandException('deregister', CONFLICT, 'Removing of user '+getUserName()+' from Gold failed')
		}
		
	}
	
}
