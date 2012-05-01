package neuss

class User {
	
	static def countries = [] as SortedSet
	
	{
	Locale.availableLocales.displayCountry.each {
	  if (it) {
		countries << it
	  }
	}
	}
	
	
	static mapping = {
		autoTimestamp true
		}


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
	
	public String toString() {
		return userName
	}

}