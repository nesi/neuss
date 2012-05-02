neuss
=====

# ReST API

### Get all users

path: /users
method: GET

produces: xml & json

### Add a new user

path: /users
method: POST

consumes: xml & json
produces: xml & json

example input (json):

    {
        "class":"neuss.User",
        "userName":"markus",
        "firstName":"Markus",
		"middleName":"",
        "lastName":"Binsteiner",
        "email":"m.binsteiner@auckland.ac.nz",
        "nationality":"Germany",
		"phone":"012345",
		"institution":"University of Auckland",
		"department":"Centre for eResearch",
		"position":"Software developer",
		"address":"Building 409, Rm G21, LG, 24 Symonds Street, Auckland"
    }
	
example result (json):

    {
        "class":"neuss.User",
		"id":1,
	    "address":"Building 409, Rm G21, LG, 24 Symonds Street, Auckland",
        "dateCreated":"2012-05-01T21:56:59Z",
	    "department":"Centre for eResearch",
	    "email":"m.binsteiner@auckland.ac.nz",
	    "firstName":"Markus",
	    "institution":"University of Auckland",
		"lastName":"Binsteiner",
	    "lastUpdated":"2012-05-01T21:56:59Z",
	    "middleName":null,
	    "nationality":"Germany",
	    "phone":"012345",
	    "position":"Software developer",
	    "userName":"markus"
	}

( for all possible fields have a look at https://github.com/nesi/neuss/blob/develop/grails-app/domain/neuss/User.groovy )

### Change user details

path: /users/{username}
method: PUT

consumes: xml & json
produces: xml & json

example input (json):

    {
	        "class":"neuss.User",    
			"id":1               // optional
	        "userName":"markus", // optional
			"firstName":"Markus",
			"middleName":"H.",
			"lastName":"Binsteiner",
			"email":"m.binsteiner@auckland.ac.nz",
			"nationality":"Germany",
			"phone":"012345",
			"institution":"University of Auckland",
			"department":"Centre for eResearch",
			"position":"Software developer",
			"address":"Building 409, Rm G21, LG, 24 Symonds Street, Auckland"
	}

