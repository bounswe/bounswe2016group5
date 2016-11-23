# Digest API
	Welcome to DigestAPI.
# Using The API
	Please provide the passwords as SHA-1 hash. 
	
	The all transactions by api are database.model objects in the project as JSON format.
## Current API Path
	digest.us-east-1.elasticbeanstalk.com/digest.api 
	
## Login
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=login
		username=<USERNAME>
		password=<PASSWORD>
	Succes response:
		status:200
		content: {"id":<id>,"username":<username>,"password":<password>,"email":<email>,"first_name":<first_name>,"last_name":<last_name>,"status":1,"role":<for some reason>,"session":<session_id>}
	Error response:
		status:200
		content: {"errorName":"incorrect_credentials","errorDescription":"Incorrect username/email or password"}
	
	The login returns an Error object if an error occurred (i.e. wrong credentials, otherwise returns an User object.)
## Register
	<API_PATH>/?f=register&username=<USERNAME>&password=<PASSWORD>&email=<EMAIL>&first_name=<FIRST_NAME>&<LAST_NAME>=<LAST_NAME>&status=<STATUS_INT>
	
	For now status might be sent as 0, later on we can use status as registered but not active, registered, blocked, etc.
	also implicitly role set as user.

## Create Topic
	A json object with following parameters should be sent.
	
## Get all topics of user
	The required parameter is uid of the user of which the topics will return.
	
	<API_PATH>/?f=get_topics_of_user&uid=<REQUEST_OWNER_USER_ID>&session=<SESSION>&ruid=<TOPIC_OWNER_USER_ID>

## Add Quiz
	A json object with following parameters should be sent.
	
## Add Comment
	POST request with following json

## Get Comments
	<API_PATH>/?f=get_comment&uid=<REQUEST_OWNER_USER_ID>&session=<SESSION>&tid=<TOPICID>
	

## Get a topic
	<API_PATH>/?f=get_topics_of_user&uid=<REQUEST_OWNER_USER_ID>&session=<SESSION>&tid=<TOPIC_ID>
	
## Get recent topics
	<API_PATH>/?f=get_recent_topics&count=<MAX LIMIT OF TOPICS>
## Get Subscribed Topics
	<API_PATH>/?f=get_subscribed_topics&uid=<USER_ID>
## Subscribe A Topic
	<API_PATH>/?f=add_subscriber&uid=<USER_ID>&tid=<TOPIC_ID>
## Get Topics With Tag
	<API_PATH>?f=get_topics_with_tag&tag=<TAG>
	It will return not topic but topicpreview, which holds less information about the topic.


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

		
