<%@page import="javax.swing.text.StyledEditorKit.ForegroundAction"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="org.json.*"%>
<%
	session = request.getSession(false);
	if (session.getAttribute("session") != null) {
		response.sendRedirect("index.jsp");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Login</title>
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

		$('#login_form').validate({ // initialize the plugin
			rules : {
				username : {
					required : true

				},
				password : {
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
					<li><a href="signup.jsp"><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					<li class="active"><a href="#"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="row col-sm-12" id="content">
		<div class="col-sm-offset-3 col-sm-6"
			style="border: 1px solid white; padding: 10px">
			<form class="form-horizontal" id="login_form"action="login.jsp" method="POST">
				<div class="form-group">
					<label class="control-label col-sm-2" for="username">Username:</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="username"
							id="username">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="password">Password:</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" name="password"
							id="password">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-5 col-sm-2">
						<button type="submit" class="btn btn-default" name="f"
							value="login">Login</button>
					</div>
				</div>
			</form>
			<%
				String recv = "";
				String recvbuff = "";

				StringBuffer bf = new StringBuffer();
				bf.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/");
				bf.append("?");
				Enumeration<String> names = request.getParameterNames();

				while (names.hasMoreElements()) {
					String attr = names.nextElement();
					String value = request.getParameter(attr);
					bf.append(attr + "=" + value);
					if (names.hasMoreElements())
						bf.append("&");
				}
				String url = bf.toString();
				URL jsonpage = new URL(url);
				URLConnection urlcon = jsonpage.openConnection();

				BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

				while ((recv = buffread.readLine()) != null)
					recvbuff += recv;
				buffread.close();

				try {
					JSONObject obj = new JSONObject(recvbuff);
					System.out.println(obj);
					if (obj.has("errorName")) {

						String msg = obj.getString("errorDescription");
			%>
			<p><%=msg%></p>
			<%
				} else {
						session = request.getSession();

						Set<String> sattr = obj.keySet();

						for (String attribute : sattr) {
							if (!attribute.contentEquals("role")) {
								session.setAttribute(attribute, obj.get(attribute));
							}
						}

						response.sendRedirect("index.jsp");

					}

				} catch (JSONException ex) {
					//Do nothing
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

