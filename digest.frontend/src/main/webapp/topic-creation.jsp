<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.Enumeration"%>
<%
	session = request.getSession(false);
	if (session.getAttribute("session") == null) {
		response.sendRedirect("index.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Topic</title>
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

		$('#create_topic_form').validate({ // initialize the plugin
			rules : {
				header : {
					required : true
				},
				body : {
					required : true
				},
				owner : {
					required : true
				},
				image : {
					required : false
				},
				status : {
					required : true
				},
				url : {
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
				<a class="navbar-brand" href="index.jsp">DIGest <span><img
						alt="digest-icon" src="dig-icon.png"></span></a>
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
					<li><a href="#"><span
							class="glyphicon glyphicon-envelope"></span> Messages</a></li>
					<li><a href="#"><span
							class="glyphicon glyphicon-cog"></span> Settings</a></li>
					<li><a href="#"><span
							class="glyphicon glyphicon-th-list"></span> Notifications</a></li>
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
								<li class="active"><a href="#"><span
										class="glyphicon glyphicon-home"></span> Homepage</a></li>
								<li><a href="profile.jsp"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="followed-topics.jsp"><span
										class="glyphicon glyphicon-star-empty"></span> Followed Topics</a></li>
								<li><a href="user-topics.jsp"><span
										class="glyphicon glyphicon-upload"></span> My Topics</a></li>
							</ul>
							<div class="panel panel-default"
								style="height: 200px; overflow-y: auto;">
								<div class="panel-header">Your Notes</div>
								<div class="panel-body">Notes go here. adsd asda fc sd ds
									cds cds dsc sd \n sdf \n sdcds\n</div>

							</div>
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
				<h1>Open a new topic</h1>
				<div class="open-topic col-sm-12">
					<form class="form-horizontal" id="create_topic_form" action="CreateTopicServlet"
				method="POST">
						<div class="form-group">
							<div class="topic-header">
								<div class="row col-sm-6">
									<div class="form-group">
										<label class="control-label col-sm-2" for="header">Header:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="header"
												id="header">
										</div>
									</div>
									<!--  <div class="form-group">
										<label class="control-label col-sm-2" for="type">Type:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="type" id="type">
										</div>
									</div>-->
									<div class="form-group">
										<label class="control-label col-sm-2" for="tags">Tags:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="tags" id="tags">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="admins">Owner:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="owner"
												id="admins" value="<%=session.getAttribute("id")%>">
										</div>
									</div>
									<!--<div class="form-group">
										<label class="control-label col-sm-2" for="mods">Mods:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="mods" id="mods">
										</div>
									</div>-->
								</div>
							</div>
							<div class="row col-sm-6">
								<div class="container col-sm-6">
									<label class="control-label col-sm-12" for="topic-img">Topic
										Image:</label> <img id="topic-img"
										style="display: block; width: 150px; height: 150px;"
										alt="topic image" src="topic.png"></img>
								</div>
								<div class="container col-sm-6">
									<div class="form-group">
										<label class="control-label" for="img-url">URL:</label> <input
											type="text" class="form-control" name="image" id="image">

									</div>
									<div class="form-group">
										<a href="#" class="btn btn-default btn-block"
											type="submit">Upload</a>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="topic-body col-sm-12">
								<div class="form-group">
									<label class="control-label" for="body">Body:</label>
									<textarea class="form-control" name="body" id="body" rows="15"></textarea>

								</div>

							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-9 col-xs-offset-3">
								<input type="hidden" name="status" value="0">
								<button type="submit" class="btn btn-primary" name="f"
							value="create_topic">Create Topic</button>
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