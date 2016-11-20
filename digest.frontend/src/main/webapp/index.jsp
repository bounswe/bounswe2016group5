<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.io.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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
		Object sessionID = session.getAttribute("session");
		if (sessionID == null||sessionID == "") {
	%>

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


	<div class="row col-sm-12" id="content">
		<div class="row"
			style="height: 33%; text-align: center; padding: 15px;">
			<h1>Popular</h1>
			<br /> Contents Goes Here
		</div>
		<div class="row"
			style="height: 33%; text-align: center; padding: 15px;">
			<h1>Might Interest</h1>
			<br /> Contents Goes Here
		</div>

		<div class="row"
			style="height: 33%; text-align: center; padding: 15px;">
			<h1>Recent</h1>
			<br /> Contents Goes Here
		</div>
	</div>
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
					<li><a href="messages.jsp"><span
							class="glyphicon glyphicon-envelope"></span> Messages</a></li>
					<li><a href="settings.jsp"><span
							class="glyphicon glyphicon-cog"></span> Settings</a></li>
					<li><a href="notifications.jsp"><span
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
								<li><a href="UserProfileServlet"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="followed-topics.jsp"><span
										class="glyphicon glyphicon-star-empty"></span> Followed Topics</a></li>
								<li><a href="user-topics.jsp"><span
										class="glyphicon glyphicon-upload"></span> My Topics</a></li>
							</ul>

							<div class="panel panel-default">
								<div class="panel-header">Your Notes</div>
								<div class="panel-body">
									Notes go here. adsd asda fc sd ds cds cds dsc sd \n sdf \n
									sdcds\n<br /> Notes go here. adsd asda fc sd ds cds cds dsc sd
									\n sdf \n sdcds\n<br /> Notes go here. adsd asda fc sd ds cds
									cds dsc sd \n sdf \n sdcds\n<br />

								</div>

							</div>
							<div class="panel panel-default">
								<div class="panel-header">Channels</div>
								<div class="panel-body">Channels and some links</div>

							</div>
							<div class="panel panel-default">
								<div class="panel-header">Recents</div>
								<div class="panel-body">Some recent topics</div>

							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-9">
				<form class="form-horizontal" id="view_topic_form"
					action="ViewTopicServlet" method="POST">
					<% 
						session.getAttribute("id");
						@SuppressWarnings("unchecked")
						ArrayList<Integer> popularTopicIds = (ArrayList<Integer>) request.getAttribute("popularTopicIds");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> popularTopicHeaders = (HashMap<Integer,String>) request.getAttribute("popularTopicHeaders");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> popularTopicImages = (HashMap<Integer,String>) request.getAttribute("popularTopicImages");
						%>
					<div class="row form-group" style="height: 33%; padding: 15px;">
						<h4 class="panel-header" style="margin: 10px 10px 10px 30px">Popular</h4>
						<div class="container panel panel-default"
							style="height: 200px; width: 95%; overflow-x: scroll;">
							<div class="panel-body" id="user-topics" class="list-group">
								<% 
								if(popularTopicIds != null)
									for(int topicId : popularTopicIds  ) {
							%>
								<div class="topic-view col-xs-4 col-lg-4"
									style="padding: 9px 9px 0px 9px;">
									<div class="thumbnail">
										<input  type="image" class="group list-group-image" style=" display: block; margin: 0 auto;"  
											height="100" width="100" name="topic_id" id="topic_id" value=<%=topicId %>
											src=<%=popularTopicImages.get(topicId) %> alt="" />
										<div class="caption">
											<h4 class="group inner list-group-item-heading"
												align="center"><%=popularTopicHeaders.get(topicId) %></h4>
										</div>
									</div>
								</div>
								<% 		
								} 
							%>
							</div>
						</div>
					</div>
					<% 
						@SuppressWarnings("unchecked")
						ArrayList<Integer> interestTopicIds = (ArrayList<Integer>) request.getAttribute("interestTopicIds");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> interestTopicHeaders = (HashMap<Integer,String>) request.getAttribute("interestTopicHeaders");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> interestTopicImages = (HashMap<Integer,String>) request.getAttribute("interestTopicImages");
						%>
					<div class="row form-group" style="height: 33%; padding: 15px;">
						<h4 class="panel-header" style="margin: 10px 10px 10px 30px">Might
							Interest</h4>
						<div class="container panel panel-default"
							style="height: 200px; width: 95%; overflow-x: scroll;">
							<div class="panel-body" id="user-topics" class="list-group">
								<% 
								if(interestTopicIds != null)
									for(int topicId : interestTopicIds  ) {
							%>
								<div class="topic-view col-xs-4 col-lg-4"
									style="padding: 9px 9px 0px 9px;">
									<div class="thumbnail">
										<input  type="image" class="group list-group-image" style=" display: block; margin: 0 auto;"  
											height="100" width="100" name="topic_id" id="topic_id" value=<%=topicId %>
											src=<%=interestTopicImages.get(topicId) %> alt="" />
										<div class="caption">
											<h4 class="group inner list-group-item-heading"
												align="center"><%=interestTopicHeaders.get(topicId) %></h4>
										</div>
									</div>
								</div>
								<% 		
								} 
							%>
							</div>
						</div>
					</div>
					<div class="row form-group" style="height: 33%; padding: 15px;">
						<% 
						@SuppressWarnings("unchecked")
						ArrayList<Integer> recentTopicIds = (ArrayList<Integer>) request.getAttribute("recentTopicIds");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> recentTopicHeaders = (HashMap<Integer,String>) request.getAttribute("recentTopicHeaders");
						@SuppressWarnings("unchecked")
						HashMap<Integer,String> recentTopicImages = (HashMap<Integer,String>) request.getAttribute("recentTopicImages");
						%>
						<h4 class="panel-header" style="margin: 10px 10px 10px 30px">Recent</h4>
						<div class="container panel panel-default"
							style="height: 200px; width: 95%; overflow-x: scroll;">
							<div class="panel-body" id="user-topics" class="list-group">
							<% 
								if(recentTopicIds != null)
									for(int topicId : recentTopicIds  ) {
							%>
								<div class="topic-view col-xs-4 col-lg-4"
									style="padding: 9px 9px 0px 9px;">
									<div class="thumbnail">
										<input  type="image" class="group list-group-image" style=" display: block; margin: 0 auto;"  
											height="100" width="100" name="topic_id" id="topic_id" value=<%=topicId %>
											src=<%=recentTopicImages.get(topicId) %> alt="" />
										<div class="caption">
											<h4 class="group inner list-group-item-heading"
												align="center"><%=recentTopicHeaders.get(topicId) %></h4>
										</div>
									</div>
								</div>
								<% 		
								} 
							%>
							</div>
						</div>
					</div>
				</form>

			</div>

		</div>
	</div>

	<%
		}
	%>

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