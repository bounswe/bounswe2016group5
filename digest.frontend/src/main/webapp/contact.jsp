<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.io.*,java.util.*"%>
<%@page import="org.json.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Contact Us</title>
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

<link rel="stylesheet" href="css/site.css">
<script src="js/site.js"></script>
</head>
<body>
	<%
		Object sessionID = session.getAttribute("session");
		if (sessionID == null || sessionID == "") {
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
						alt="digest-icon" src="img/logo.jpg" height=35 width=35
						style="margin: 0 0 0 10px"> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-6 pull">
				
					<form action="SearchServlet" method="POST" class="navbar-form"
						role="search">
						<div class="input-group col-sm-12">
							<input type="text" class="form-control" placeholder="Search"
								name="search" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default"> <i
									class="glyphicon glyphicon-search"></i>
								</a>
							</div>
						</div>
						
					</form>
					<div style="
							position:absolute;
							z-index: 100 !important;
							width:80%;
						" id="show-data"></div>
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

	<div class="col-sm-6 col-sm-offset-3">
		<div class="container">
			<div class="col-md-5">
				<div class="form-area">
					<form role="form">
						<br style="clear: both">
						<h3 style="margin-bottom: 25px; text-align: center;">Contact
							Form</h3>
						<div class="form-group">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="Name" required>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="email" name="email"
								placeholder="Email" required>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="mobile" name="mobile"
								placeholder="Mobile Number" required>
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="subject"
								name="subject" placeholder="Subject" required>
						</div>
						<div class="form-group">
							<textarea class="form-control" type="textarea" id="message"
								placeholder="Message" maxlength="140" rows="7"></textarea>
						</div>

						<button type="button" id="submit" name="submit"
							class="btn btn-primary pull-right">Submit Form</button>
					</form>
				</div>
			</div>
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
				<a class="navbar-brand" href="MainServlet">DIGest <span><img
						alt="digest-icon" src="img/logo.jpg" height=35 width=35
						style="margin: 0 0 0 10px"> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-3 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="search" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default"> <i
									class="glyphicon glyphicon-search"></i>
								</a>
							</div>
						</div>
						<div id="show-data"></div>
					</form>
				</div>
				<ul class="nav navbar-nav navbar-right">
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
								<li><a href="FollowingTopicsServlet"><span
										class="glyphicon glyphicon-star-empty"></span> Following
										Topics</a></li>
							</ul>

							<div class="panel panel-default">
								<div class="panel-header">Channels</div>
								<div class="panel-body" id="sub_channels"></div>

							</div>
							<div class="panel panel-default">
								<div class="panel-header">Recents</div>
								<div class="panel-body">Some recent topics</div>

							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-9 ">
				<div class="container">
					<div class="col-md-5">
						<div class="form-area">
							<form role="form">
								<br style="clear: both">
								<h3 style="margin-bottom: 25px; text-align: center;">Contact
									Form</h3>
								<div class="form-group">
									<input type="text" class="form-control" id="name" name="name"
										placeholder="Name" required>
								</div>
								<div class="form-group">
									<input type="text" class="form-control" id="email" name="email"
										placeholder="Email" required>
								</div>
								<div class="form-group">
									<input type="text" class="form-control" id="mobile"
										name="mobile" placeholder="Mobile Number" required>
								</div>
								<div class="form-group">
									<input type="text" class="form-control" id="subject"
										name="subject" placeholder="Subject" required>
								</div>
								<div class="form-group">
									<textarea class="form-control" type="textarea" id="message"
										placeholder="Message" maxlength="140" rows="7"></textarea>
								</div>

								<button type="button" id="submit" name="submit"
									class="btn btn-primary pull-right">Submit Form</button>
							</form>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>

	<%
		}
	%>

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