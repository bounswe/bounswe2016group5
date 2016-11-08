<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.Enumeration"%>
<%
	session = request.getSession(false);
	if (session.getAttribute("session") != null) {
		response.sendRedirect("index.jsp");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Signup</title>
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
</style>
<script>
	$(document).ready(function() {

		$('#signup_form').validate({ // initialize the plugin
			rules : {
				username : {
					required : true

				},
				first_name : {
					required : true

				},
				last_name : {
					required : true

				},
				email : {
					required : true,
					email : true

				},
				password : {
					required : true,
					minlength : 8
				},
				agree : {
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
					<li class="active"><a href="#"><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li><a href="login.jsp"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="row col-sm-12" id="content">
		<div id="form-aligned" class="col-sm-offset-3 col-sm-6">
			<form class="form-horizontal" id="signup_form" action="SignupServlet"
				method="POST">
				<div class="form-group">
					<label class="col-xs-3 control-label">Full name</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="first_name"
							placeholder="First name" />
					</div>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="last_name"
							placeholder="Last name" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Username</label>
					<div class="col-xs-5">
						<input type="text" class="form-control" name="username" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Email address</label>
					<div class="col-xs-5">
						<input type="text" class="form-control" name="email" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Password</label>
					<div class="col-xs-5">
						<input type="password" class="form-control" name="password" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">Gender</label>
					<div class="col-xs-6">
						<div class="radio">
							<label> <input type="radio" name="gender" value="male" />
								Male
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="gender" value="female" />
								Female
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="gender" value="other" />
								Other
							</label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-xs-6 col-xs-offset-3">
						<div class="checkbox">
							<label> <input type="checkbox" name="agree" value="agree" />
								Agree with the terms and conditions
							</label>
						</div>
					</div>
				</div>



				<div class="form-group">
					<div class="col-xs-9 col-xs-offset-3">
						<input type="hidden" name="status" value="0">
						<button type="submit" class="btn btn-primary" name="f"
							value="register">Sign Up</button>
					</div>
				</div>
			</form>
			<%
				Object errMsg = session.getAttribute("error");
				if (errMsg != null) {
			%>
			<p><%=errMsg%></p>
			<%
				session.removeAttribute("error");
				}
			%>
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