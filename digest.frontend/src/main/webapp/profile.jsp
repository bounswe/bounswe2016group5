<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.*"%>
<%
	session = request.getSession(false);
	if (session.getAttribute("session") == null) {
		response.sendRedirect("MainServlet");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.12.0/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
body {
	background-color: #f5f5f5;
}

@media ( min-width : 768px) {
	.sidebar-nav {
		padding: 12px;
	}
	.sidebar-nav .navbar .navbar-collapse {
		padding: 0;
		max-height: none;
	}
	.sidebar-nav .navbar ul {
		float: none;
		display: block;
	}
	.sidebar-nav .navbar li {
		float: none;
		display: block;
	}
	.sidebar-nav .navbar li a {
		font-family: Helvetica Neue;
		padding-top: 12px;
		padding-bottom: 12px;
	}
	.sidebar-nav .navbar .panel {
		padding-left: 12px;
		padding-write: 12px;
		height: 200px;
		overflow-y: auto;
	}
}

#menu-outer {
	height: 84px;
	width: 100%;
	background: white;
	position: fixed;
	bottom: 0;
}

#content {
	position: relative;
	width: 100%;
	overflow: auto;
	margin-bottom: 84px;
}

#form-aligned {
	border: 1px solid white;
	padding: 10px;
}

ul#horizontal-list {
	min-width: 696px;
	list-style: none;
	padding-top: 20px;
}

ul#horizontal-list li, ul#horizontal-list a {
	display: inline;
	float: left;
	color: white;
}

ul#horizontal-list a:hover {
	text-decoration: none;
	color: white;
}

.navbar-inverse {
    background-color: #377bb5;
    border-color: #377bb5;
}

.navbar-inverse .navbar-brand {
    color: white;
}

.navbar-inverse .navbar-nav > li > a {
    color: white;
}

.panel{

	background-color: #white
}
.panel-header {
	font-family: Helvetica Neue;
}
.list-group-item-heading{
	font-family: Helvetica Neue;
}


</style>
<script>
	$(document).ready(function() {

		$('#view_topic_form').validate({ // initialize the plugin
			rules : {
				topic_id : {
					required : true
				},
			}
		});

	});
</script>
</head>
<body>
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
								name="searchterm" id="srch-term">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-envelope"></span>
							Messages</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-cog"></span>
							Settings</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-th-list"></span>
							Notifications</a></li>
					<li><a href="LogoutServlet"><span
							class="glyphicon glyphicon-log-out"></span> Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="row col-sm-12" id="content">
		<div class="row">
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
								<li class="active"><a href="UserProfileServlet"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="FollowingTopicsServlet"><span
										class="glyphicon glyphicon-star-empty"></span> Following Topics</a></li>
								<li><a href="user-topics.jsp"><span
										class="glyphicon glyphicon-upload"></span> My Topics</a></li>
							</ul>
							<div class="panel panel-default"
								style="height: 200px; overflow-y: auto;">
								<div class="panel-header">Channels</div>
								<div class="panel-body">Channels and some links</div>

							</div>
							<div class="panel panel-default"
								style="height: 200px; overflow-y: auto;">
								<div class="panel-header">Recents</div>
								<div class="panel-body">Some recent topics</div>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-9">
				<div class="user-profile col-sm-12">
				<form class="form-horizontal" id="create_topic_form"
						action="topic-creation.jsp" method="POST">
						<div class="form-group">
							<div class="row col-sm-12">
								<div class="form-group col-sm-2">

									<h2 class="control-label" for="username" style="color: #295e8b"><%=session.getAttribute("username")%></h2>
								</div>
								<div class="form-group col-sm-2 pull-right">
									<div class="col-xs-9 col-xs-offset-3">
										<input type="hidden" name="status" value="0">
										<button type="submit" class="btn btn-primary" name="f"
											value="edit_profile" style="margin: 20px 20px 0 0;">Edit
											Profile</button>
									</div>
								</div>
								<div class="form-group col-sm-2 pull-right">
									<div class="col-xs-9 ">
										<input type="hidden" name="status" value="0">
										<button type="submit" class="btn btn-primary" name="f"
											value="edit_profile" style="margin: 20px 20px 0 0;">Create Topic</button>
									</div>
								</div>
							</div>
						</div>
						</form>
						<form class="form-horizontal" id="view_topic_form"
						action="ViewTopicServlet" method="POST">
						<h4 class="panel-header" style="margin: 10px 10px 10px 30px">My
							Topics</h4>
						<div class="container panel panel-default"
							style="height: 200px; width: 95%; overflow-x: scroll;">
							<%
								if (request.getAttribute("my_topics") != null) {

									JSONArray topicArray = (JSONArray) request.getAttribute("my_topics");
							%><div class="panel-body" id="user-topics" class="list-group">

								<%
									for (Object top : topicArray) {
											JSONObject topic = (JSONObject) top;
								%>

								<div class="topic-view col-xs-4 col-lg-4"
									style="padding: 9px 9px 0px 9px;">
									<div class="thumbnail">
										<input type="image" class="group list-group-image"
											style="display: block; margin: 0 auto;" height="100"
											width="100" name="topic_id" id="topic_id"
											value=<%=topic.get("id")%> src="<%=topic.get("image")%>" alt="" />
										<div class="caption">
											<h4 class="group inner list-group-item-heading"
												align="center"><%=topic.get("header")%></h4>
										</div>
									</div>
								</div>

								<%
									}
									}
								%>

							</div>
						</div>

						<h4 class="panel-header" style="margin: 10px 10px 10px 30px">Following
							Topics</h4>
							<%
								if (request.getAttribute("fol_topics") != null) {

									JSONArray topicArray = (JSONArray) request.getAttribute("fol_topics");
							%>
						<div class="container panel panel-default"
							style="height: 200px; width: 95%; overflow-x: scroll;">
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
	</div>




	<footer id="menu-outer">
		<div class="col-sm-offset-2 col-sm-10">
			<ul id="horizontal-list">
				<li class="col-sm-2"><a href="#">About</a></li>
				<li class="col-sm-2"><a href="#">Terms</a></li>
				<li class="col-sm-2"><a href="#">Developers</a></li>
				<li class="col-sm-2"><a href="#">Feedback</a></li>
				<li class="col-sm-2"><a href="#">Privacy</a></li>
			</ul>
		</div>
	</footer>
</body>
</html>