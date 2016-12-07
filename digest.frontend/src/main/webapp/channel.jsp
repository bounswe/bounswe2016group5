<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Channel</title>
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

<style>
body {
	background-color: lightgrey;
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
	background: black;
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
	color: grey;
}

ul#horizontal-list a:hover {
	text-decoration: none;
	color: white;
}

.open-topic {
	width: 100%;
	heigth: 100%;
}

.open-topic button {
	float: right;
}

.topic-header {
	width: 100%;
}

.topic-body {
	with: 100%;
	margin: 2px 2px 2px 2px;
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
						alt="digest-icon" src="img/logo.jpg" height=35 width=42></span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-6 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group col-sm-12">
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
						alt="digest-icon" src="img/logo.jpg" height=35 width=42></span></a>
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
								<li class="active"><a href="MainServlet"><span
										class="glyphicon glyphicon-home"></span> Homepage</a></li>
								<li><a href="UserProfileServlet"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="followed-topics.jsp"><span
										class="glyphicon glyphicon-star-empty"></span> Followed Topics</a></li>
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
			
			<%
				}
			%>
			<div class="col-sm-9">
				<h2 align="center">Channel Name</h2>
				<div class="progress">
				  <div class="progress-bar" role="progressbar" aria-valuenow="70"
				  aria-valuemin="0" aria-valuemax="100" style="width:70%">
				    70%
				 </div>
			</div>
			<form class="form-horizontal" id="view_topic_form"
						action="ViewTopicServlet" method="POST">
			<div class=" well " >
			    <table class="table">
			      <thead>
			        <tr>
			          <th>Topic Name</th>
			          <th>Owner</th>
			          <th>Progress</th>
			          <th></th>
			        </tr>
			      </thead>
			      <tbody>
			        <tr >
			          <td>Why Fenerbahce is one of the biggest sports club in Turkey?</a></td>
			          <td>atakanguney</td>
			          <td>60%</td>
			          <td> 
			          		<button name="topic_id"  value="64" type="submit">View Topic</button></td> <!-- value should be corresponding topicID  -->
			        </tr>
			        <tr>
			          <td>Why girls like Instagram?</td>
			          <td>seyma7</td>
			          <td>70%</td>
			          <td> 
			          		<button name="topic_id"  value="75" type="submit">View Topic</button></td>
			        </tr>
			      </tbody>
			    </table>
			</div>
			</form>
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