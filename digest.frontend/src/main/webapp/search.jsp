<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/comment.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.12.0/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
	
<link rel="stylesheet" href="css/site.css">
<script src="js/site.js"></script>

</head>
<script>
$(document).ready(function() {
	window.alert(1);
});

</script>

<body>
	<%
		if (session.getAttribute("session") == null) {
	%>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="MainServlet">DIGest <span><img 
						alt="digest-icon" src="img/logo.jpg" height=35 width=35 style="margin:0 0 0 10px "> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-6 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group col-sm-12">
							<input type="text" class="form-control" placeholder="Search"
								name="searchterm" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default">
									<i class="glyphicon glyphicon-search"></i>
								</a>
							</div>
						</div>
						<div style="
							position:absolute;
							z-index: 100 !important;
							width:80%;
						" id="show-data"></div>
					</form>
				</div>
				<ul class="nav navbar-nav navbar-right">

					<li><a href="signup.jsp"><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>

					<li><a href="login.jsp"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>

				</ul>
			</div>
		</div>
	</nav>
	<%
		} else {
	%>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="MainServlet">DIGest <span><img 
						alt="digest-icon" src="img/logo.jpg" height=35 width=35 style="margin:0 0 0 10px "> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-3 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="searchterm" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default">
									<i class="glyphicon glyphicon-search"></i>
								</a>
							</div>
						</div>
					</form>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="LogoutServlet"><span
							class="glyphicon glyphicon-log-out"></span> Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
			<%
				}
			%>
	<div class="row col-sm-12" id="content">
	
	<%
		if (session.getAttribute("session") != null) {
	%>
			<div class="col-sm-3">
				<div class="sidebar-nav">
					<div class="navbar navbar-default" role="navigation">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".sidebar-navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<span class="visible-xs navbar-brand">Sidebar menu</span>
						</div>
						<div class="navbar-collapse collapse sidebar-navbar-collapse">
							<ul class="nav navbar-nav">
								<li><a href="MainServlet"><span
										class="glyphicon glyphicon-home"></span> Homepage</a></li>
								<li><a href="UserProfileServlet"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="FollowingTopicsServlet"><span
										class="glyphicon glyphicon-star-empty"></span> Following Topics</a></li>
							</ul>
							<div class="panel panel-default"
								style="height: 200px; overflow-y: auto;">
								<div class="panel-header">Channels</div>
								<div class="panel-body" id="sub_channels"></div>

							</div>
						</div>
					</div>
				</div>
			</div>
			
			<%
				}
			%>
		<!--  <div class="col-sm-9">
				<div class="container" id="advanced-search">
					<form class="form-horizontal" id="advanced-search-form">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="header">Header:</label>
							<div class="col-sm-10"><input class="form-control" type="text" name="header" id="header"></div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label" for="tag">Tag:</label>
							<div class="col-sm-10"><input class="form-control" type="text" name="tag" id="tag"></div>
						</div>
						
						<div class="form-group">
					        <label class="col-sm-2 control-label" for="from_date">From:</label>
					        <div class="col-sm-10"><input class="form-control" id="from_date" name="from_date" placeholder="DD/MM/YYY" type="text" readonly/></div>
						</div>
	
						<div class="form-group">
					        <label class="col-sm-2 control-label" for="to_date">To:</label>
					        <div class="col-sm-10"><input class="form-control" id="to_date" name="to_date" placeholder="DD/MM/YYY" type="text" readonly/></div>
						</div>
											
						<div class="form-group" style="text-align: center">
							<a class="btn btn-primary" id="advanced-search-btn">Advanced Search</a>									
						</div>
					</form>
				</div>
			</div>-->
			
			<div class="col-sm-9">
				<div class="search-topics col-sm-12">
						<form class="form-horizontal" id="view_topic_form"
						action="ViewTopicServlet" method="POST">

							<%
								if (request.getAttribute("search_topics") != null) {

									JSONArray topicArray = (JSONArray) request.getAttribute("search_topics");
							%>
						<div class="container panel panel-default"
							style="height: 500px; width: 95%; overflow-x: scroll;">
							<div class="panel-body" id="following-topics" class="list-group">
								<%
									for (Object top : topicArray) {
											JSONObject topic = (JSONObject) top;

											String header = "";
											try{
												header = topic.get("header").toString(); 
											}
											catch(JSONException e){
												
											}
								%>
								
								<div class="topic-view col-xs-4 col-lg-4"
									style="padding: 9px 9px 0px 9px;">
									<div class="thumbnail">
										<input type="image" class="group list-group-image"
											style="display: block; margin: 0 auto;" height="100"
											width="100" name="topic_id" id="topic_id" value=<%=topic.get("id")%>
											src="<%=topic.get("image")%>" alt="" />
										<div class="caption">
											<h4 class="group inner list-group-item-heading"
												align="center"><%=header %>
												</h4>
										</div>
									</div>
								</div>
								<%
									}
								}
								%>
							</div>
						</div>
					</form>
				</div>
			</div>
			
		</div>	
		
	<footer id="menu-outer">
		<div class="col-sm-offset-4 col-sm-4">
			<ul id="horizontal-list">
				<li class="col-sm-2"><a href="about.jsp">About</a></li>
				<li class="col-sm-2"><a href="contact.jsp">Contact Us</a></li>
			</ul>
		</div>
	</footer>
</body>
</html>