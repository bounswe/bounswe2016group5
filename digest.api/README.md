# Digest API
	Welcome to DigestAPI.
# Using The API
	Please provide the passwords as SHA-1 hash. 
	
	The all transactions by api are database.model objects in the project as JSON format.
## Current API Path
	digest.us-east-1.elasticbeanstalk.com/digest.api 

## Add Channel
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=add_channel
		uid=<user_id> // owner of the channel
		name=<channel_name> 
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_channel&uid=8&name=cmpe
		

## Get Tag Suggestions
	Explanation : 
		Send body to retrieve suggested tags with their entities listed as a JSONArray in a JSONObject "suggestions".
		In this JSONArray, you can pick JSONObject<tag>:[<entities>] format one by one.
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_tag_suggestions
		body=<body> // channel id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_suggestions&body=some_text
		say some_text is : ConceptNet is a semantic network based on the information in the OMCS database. ConceptNet is expressed as a directed graph whose nodes are concepts, and whose edges are assertions of common sense about these concepts. Concepts represent sets of closely related natural language phrases, which could be noun phrases, verb phrases, adjective phrases, or clauses. ConceptNet is created from the natural-language assertions in OMCS by matching them against patterns using a shallow parser. Assertions are expressed as relations between two concepts, selected from a limited set of possible relations. The various relations represent common sentence patterns found in the OMCS corpus, and in particular, every \"fill-in-the-blanks\" template used on the knowledge-collection Web site is associated with a particular relation.
		
		result will be : {"suggestions":[{"artificial_intelligence":["computer science","computing","artificial"]},{"syntax":["linguistics","grammar","structure","system"]},{"grammar":["linguistics"]},{"cognitive_science":["science","cognitive psychology"]},{"information_science":["science","lysis"]}]}

## Get Tag Entities
	Explanation : 
		Send tag to retrieve at most 6 entities, which user must select one of them before it can tag.
		Result format will be JSONObject "entities" : JSONArray [<entities>].
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_tag_entities
		tag=<tag> // channel id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_entities&tag=python
		
		result will be : {"entities":["programming language","snake","Greek mythology","animal","roller coaster","prototype based language"]}
		
## Get Related Entities
	Explanation : 
		Send entity to retrieve at most 10 entities, which will be related to sent entity in descending order.
		Result format will be JSONObject "relatedEntities" : JSONArray [<entities>].
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_related_entities
		tag=<entity> // channel id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_related_entities&tag=computer_science
		
		result will be : {"relatedEntities":["informatics","alan turing","decompiler","terminal emulation","artificial intelligence","computability","undecidable","compilable","decidability","compsci"]}

		
## Get Channel
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=add_channel
		cid=<channel_id> // channel id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_channel&cid=8
		
## Add Topic To Channel
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=add_topic_to_channel
		cid=<channel_id> // channel id
		tid=<topic_id> //topic id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_topic_to_channel&cid=8&tid=24

## Get Topics From Channel
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_topics_from_channel
		cid=<channel_id> // channel id
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_from_channel&cid=8

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
	URL:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=create_topic
	Method:
		POST
	Body:
		JSON object for topic
	Succes response:
		status:200
		content: <tid>
	Error response:
		status:400
	
	The create_topic returns topic id with resonse code "200" if it succeeds otherwise response code is set to "400"
	
	JSON Object example:
	{"owner":<user-id>,"image":<image-url>,"header":<topic-header>,"body":<topic-body>,"tags":[{"tag":<tag-string>},{"tag":<tag-string>}]}

	Remark: You don't need to specify the fields that are automatically assigned in api. You need to be careful about fields names of JSON object. Names should be exactly the same with Topic model in the api.
	
## Get all topics of user
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_topics_of_user
		ruid=<user_id>
	Succes response:
		status:200
		content: array_of_topics--->[<topic>,<topic>,...]
    		please see get topic section for the json of <topic>
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_of_user&ruid=25

## Add Quiz
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		POST
	Params:
		f=add_quiz
		tid=<topic_id>
	Data Params:
		{"name":<quiz_name>,"questions":<array_of_questions ex:-- 
				[{"text":<question_text>,"choices":<array_of_choices
					[<answer_text>,<answer_text>,<answer_text>,...]
				,"answers":<array_of_ints: indexes of the answers >}
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_of_user&ruid=25
	
	
## Add Media
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=add_media
		tid=<topic_id>
		url=<media_url>
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_media&url=youtube.com&tid=45
	
## Add Comment
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=add_comment
		body=<comment_body_text>
		uid=<user_id> // owner of the comment
		ucid=<upper_comment_id> // -1 there are no upper comments
		tid=<topic_id>
		type=<type_id>(0 for nothing, 1 for question, 2 for instructive. See Comment class)
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_comment&body=MAASALLAH%20OGLUM&uid=9&ucid=-1&tid=8&type=1

## Get Comments of Topic
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_comments_of_topic
		tid=<topic_id>
	Succes response:
		status:200
		content: 
	Error response:
		hakkimizda hayirlisi olsun
	Example:	
		<API_PATH>/?f=get_comment&tid=<TOPICID>
		
## Rate Comment
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=rate_comment
		uid=<user_id> // user who rates
		cid=<comment_id> 
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=rate_comment&uid=8&cid=75

		
## Unrate Comment
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=unrate_comment
		uid=<user_id> // user who rates
		cid=<comment_id> 
	Succes response:
		status:200
	Error response:
		hakkimizda hayirlisi olsun
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=unrate_comment&uid=8&cid=75


## Get topic
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_topic
		tid=<topic_id>
	Succes response:
		status:200
		content:{
		"id":<topic_id>,"header":<topic_header>,"image":<topic_image_url>,"body":<body_text>,
		"owner":<owner_id>,"status":<kismet>,"timestamp":<sql_timestamp>,
		"tags":< array_of_tags ex:-- [{"id":<tag_id>,"tid":<topic_id>,"tag":<tag>}]>,
		"quizzes":<array_of_quizzes ex:-- 
			[{"name":<quiz_name>,"questions":<array_of_questions ex:-- 
				[{"text":<question_text>,"choices":<array_of_choices
					[<answer_text>,<answer_text>,<answer_text>,...]>>
				,"answers":<array_of_ints>},<quiz_object>,....,..
			],"media":<array_of_urls ex:-- [<url>,<url>,...]>,
			"comments":<array_of_topics ex:--
			[{"id":<comment_id>,"uid":<user_id>,"ucid":<upper_comment_id,-1 if there are no upper comments >,"tid":<topic_id>,"body":<comment_body>,"rate":<rating>,"timestamp":<sql_time_stamp>}]}
	Error response:
		Kismet artik..
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topic&tid=8
## Get recent topics
	<API_PATH>/?f=get_recent_topics&count=<MAX LIMIT OF TOPICS>
## Get Subscribed Topics
	<API_PATH>/?f=get_subscribed_topics&uid=<USER_ID>
## Subscribe A Topic
	<API_PATH>/?f=add_subscriber&uid=<USER_ID>&tid=<TOPIC_ID>
## Get Topics With Tag
	<API_PATH>?f=get_topics_with_tag&tag=<TAG>
	It will return not topic but topicpreview, which holds less information about the topic.
## Mark Topic as Question
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=mark_comment_as_question
		cid=Comment ID
	Succes response:
		status:200
	Error response:
		Nothing.
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=mark_comment_as_question&cid=8
		
## Mark Topic as Instructive
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=mark_comment_as_instructive
		cid=Comment ID
	Succes response:
		status:200
	Error response:
		Nothing.
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=mark_comment_as_instructive&cid=8
		
## Get Trending Topics
	URL:
		digest.us-east-1.elasticbeanstalk.com/digest.api 
	Method:
		GET
	Params:
		f=get_trending_topics
	Succes response:
		status:200
	Error response:
		Nothing.
	Example:
		http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_trending_topics

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

		
