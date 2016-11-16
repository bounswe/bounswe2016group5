# Digest API
	Welcome to DigestAPI.
# Using The API
	Please provide the passwords as SHA-1 hash. 
	
	The all transactions by api are database.model objects in the project as JSON format.
## Current API Path
	digest.us-east-1.elasticbeanstalk.com/digest.api 
	
## Login
	<API_PATH>/?f=login&username=<USERNAME>&password=<PASSWORD>
	
	The login returns an Error object if an error occurred (i.e. wrong credentials, otherwise returns an User object.)
## Register
	<API_PATH>/?f=register&username=<USERNAME>&password=<PASSWORD>&email=<EMAIL>&first_name=<FIRST_NAME>&<LAST_NAME>=<LAST_NAME>&status=<STATUS_INT>
	
	For now status might be sent as 0, later on we can use status as registered but not active, registered, blocked, etc.
	also implicitly role set as user.

## Create Topic
	Database model implemented but api side is not implemented yet.
	It will have the following link
	
	<API_PATH>/?f=create_topic&username=<USERNAME>&session=<SESSION>&<TOPIC RELATED ITEMS>

# Deployment Information
## Requirements
	Tomcat v8.0
	Maven
## Push Notes
	Do not push database.properties file with the database information in it.
	
	PLEASE DO NOT PUSH TO API ANYTHING RELATED TO FRONTEND. THE FRONTEND MUST BE DEVELOPED AS A SEPARATE PROJECT.
## Notes
	If database.properties cannot be opened, it is most probably because of the tomcat working directory is not set correct. 
	To set it in the eclipse, open run configurations, select tomcat server and open arguments tab. In arguments tap select working directory as workspace, digest.api and apply the changes.
## MySQL Editing
	For MySQL editing purposes, I suggest to use MySQL Workbench.

		
