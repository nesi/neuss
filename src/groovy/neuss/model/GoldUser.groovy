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
	

	
	public GoldUser(User user) {
		this.user = user
	}
	
	User user
	String goldUserName
	
	public boolean isRegistered() {
		
		ExternalCommand gc = executeGoldCommand("glsuser -show Name -quiet")
		
		def un = user.getUserName()
		if ( gc.stdout.contains(un) ) {
			return true
		} else {
			return false
		}
		
	}
	
	public ExternalCommand register() throws ExternalCommandException {
		
		
		if ( isRegistered() ) {
			throw new ExternalCommandException("register", CONFLICT, 'User '+user.userName+' already in Gold.')
		}
		
		def name = user.lastName + ', ' + user.firstName
		def email = user.email
		def phone = user.phone
		def username = user.userName
		ExternalCommand gc = executeGoldCommand('gmkuser -n "'+name+'" -E '+email+' '+username)
		
		if ( ! isRegistered() ) {
			throw new ExternalCommandException('register', CONFLICT, 'Adding of user '+user.userName+' to Gold failed')
		}
		
		gc
		
	}
	
	@XmlAttribute(name="goldUserName")
	public String getUserName() {
		
		return user.getUserName()
	}
	
	private ExternalCommand executeGoldCommand(String command) {
		ExternalCommand gc = new ExternalCommand(command)
		gc.execute()
		gc
	}

	@Override
	public ExternalCommand deregister() throws ExternalCommandException {
		if ( ! isRegistered() ) {
			throw new ExternalCommandException("deregister", CONFLICT, 'User '+user.userName+' not registered in Gold.')
		}
		def name = user.lastName + ', ' + user.firstName
		def email = user.email
		def phone = user.phone
		ExternalCommand gc = executeGoldCommand('grmuser '+getUserName())
		
		if ( isRegistered() ) {
			throw new ExternalCommandException('deregister', CONFLICT, 'Removing of user '+user.userName+' from Gold failed')
		}
		
		gc
		
	}
	
}
