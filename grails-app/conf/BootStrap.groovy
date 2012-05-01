import neuss.User

class BootStrap {

    def init = { servletContext ->
		if (!User.count()) {
			new User(userName:"markus", firstName:"Markus", lastName:"Binsteiner", email:"m.binsteiner@auckland.ac.nz", nationality:"Germany", phone:'012345').save(failOnError:true)
			new User(userName:"martin", firstName:"Martin", lastName:"Feller", email:"m.feller@auckland.ac.nz", nationality:"Germany", phone:'123242134').save(failOnError:true)
		}
    }
    def destroy = {
    }
}
