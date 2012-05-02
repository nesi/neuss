package neuss

import neuss.model.ExternalUser

class User {

	static def countries = []as SortedSet 
	
	{
		Locale.availableLocales.displayCountry.each {
			if (it) {
				countries << it
			}
		}
	}


	static mapping = { autoTimestamp true }

	static transients = [ "externalUserCache" ]

	static constraints = {
		userName(blank:false, unique:true)
		firstName(blank:false)
		middleName(nullable:true)
		lastName(blank:false)
		email(blank:false, email:true)
		phone(blank:false)
		institution(nullable:true)
		department(nullable:true)
		position(nullable:true)
		address(nullable:true)
		nationality(inList: countries.asList())
	}

	String userName
	String firstName
	String middleName
	String lastName
	String institution
	String department
	String phone
	String position
	String email
	String address
	String nationality = "New Zealand"

	Date dateCreated
	Date lastUpdated

	private Map properties = [:]
	private Map services = [:]
	
	private Map externalUserCache = [:]

	public String toString() {
		return userName
	}

	public void addUserProperty(String key, String value) {

		properties.put(key, value)
		save()
	}

	public void removeUserProperty(String key) {
		properties.remove(key)
		save()
	}

	public void register(String serviceName) {

		ExternalUser eu = getExternalUser(serviceName)
		def username = eu.register()

		services.put(serviceName, username)
	}
	
	public void deregister(String serviceName) {
		
		ExternalUser eu = getExternalUser(serviceName)
		def username = eu.deregister()
	
		services.remove(serviceName)
	}
	
	public boolean isRegistered(String serviceName) {
		
		ExternalUser eu = getExternalUser(serviceName)
		return eu.isRegistered()

	}
	
	/**
	 * This one creates external users whenever necessary.
	 * 
	 * @param serviceName the name of the service
	 * @return the external user object
	 */
	private ExternalUser getExternalUser(String serviceName) {
		
		if ( ! externalUserCache[serviceName] ) {
			def classname = 'neuss.model.'+serviceName.capitalize()+'User'
			Class euc = Class.forName(classname, true, Thread.currentThread().contextClassLoader)

			def eu = euc.newInstance()
			eu.setUser(this)
			externalUserCache[serviceName] = eu
		}
		externalUserCache[serviceName]
	}
	
	public String getUserName(String serviceName) {
		
		ExternalUser eu = getExternalUser(serviceName)
		return eu.getUserName()
	}
}
