<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.Enumeration"%>
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

<link rel="stylesheet" href="css/site.css">
<script src="js/site.js"></script>
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
				}
			}
		});

		$('INPUT[type="file"]').change(function() {
			var ext = this.value.match(/\.(.+)$/)[1];
			switch (ext) {
			case 'jpg':
			case 'jpeg':
			case 'png':
			case 'gif':
				$('#uploadButton').attr('disabled', false);
				break;
			default:
				alert('This is not an allowed file type.');
				this.value = '';
			}
		});

		$('#upload-form').validate({
			rules : {
				image : {
					required : true
				}
			},

			messages : {
				image : "A valid image file must be selected."
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
								name="searchterm" id="search">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
						<div id="show-data"></div>
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
								<li><a href="UserProfileServlet"><span
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
				<h1>Open a new topic</h1>
				<div class="open-topic col-sm-12">

					<form method="post" class="form-horzontal"
						action="CreateTopicServlet" enctype="multipart/form-data"
						id="upload-form">
						<div class="row col-sm-12">
							<div class="form-group">
								<label class="control-label" for="image">Image
									(.jpg,.jpeg,.gif and .png are valid):</label> <input type="file"
									class="form-control" name="image" id="image"
									accept=".jpg,.jpeg,.gif,.png">
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary" id="uploadButton">Upload</button>
							</div>
						</div>
					</form>

					<form method="post" class="form-horizontal"
						action="CreateTopicServlet">
						<div class="row col-sm-12">
							<div class="form-group">
								<label class="control-label" for="image-url">Image Url:</label>
								<input type="text" class="form-control" name="image-url"
									id="image-url">
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary" name="f" value="upload_via_url">Upload</button>
							</div>
						</div>

					</form>

					<form class="form-horizontal" id="create_topic_form"
						action="CreateTopicServlet" method="POST">
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
									<div class="form-group">
										<label class="control-label col-sm-2" for="tags">Tags:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="tags" id="tags">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="owner">Owner:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="owner"
												id="owner" disabled="disabled"
												value="<%=session.getAttribute("first_name") + " " + session.getAttribute("last_name")%>">
										</div>
									</div>

								</div>
							</div>
							<div class="row col-sm-6">
								<div class="container col-sm-12">
									<label class="control-label" for="topic-img">Topic
										Image:</label> <img id="topic-img"
										style="display: block; width: 150px; height: 150px;"
										alt="topic image"
										src="<%if (session.getAttribute("image") != null) {%><%=session.getAttribute("image")%>

<%}%>"
										class="img-responsive center-block"></img> <input
										type="hidden" class="form-control" name="image" id="image"
										value="<%if (session.getAttribute("image") != null) {%><%=session.getAttribute("image")%><%}%>">
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
								<button type="submit" class="btn btn-primary" name="f"
									value="create_topic">Create Topic</button>
							</div>
						</div>
					</form>
					<%
						if (request.getAttribute("error") != null) {
					%><p><%=request.getAttribute("error")%></p>
					<%
						}
					%>
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