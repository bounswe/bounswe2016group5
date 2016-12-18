<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
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
				channel : {
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
		
		$('#url-image-upload').validate({
			rules : {
				'image-url' : {
					required : true
				}
			},
			
			messages: {
				'image-url' : 'A URL must be written.'
			}
			
			
		});
		
		$('#tags').keyup(function(){
			var tag = $('#tags').val();
			var tagSelection = $('#tag-selections');
			
			if(tag=='')
				tagSelection.hide();
			else{
				$.ajax({
					url: 'CreateTopicServlet?f=get_tags&tag='+tag,
					dataType: 'json',
					success: function(data){
						var content = '';
						tagSelection.empty();
						var items = data.myArrayList;
						$.each(items,function(key,val){		
							content = '<a class="list-group-item">'+$('#tags').val()+'('+ val + ')' + '</a>';
							tagSelection.append(content);
						});
					}
				});
				tagSelection.show();
			}
		});
		
		$('#tag-selections').on('click','a',function(){
			var content = '<a class="add-tag btn btn-primary"><span class="glyphicon glyphicon-remove"></span>'+$(this).text()+'</a>';
			$('#show-tags').append(content);
		});
		
		$('#get_suggestions').on('click',function get_suggestions(){
			var body = $('#body').val();
			var showRes = $('#show-suggested-tags');
			
			$.ajax({
				url: 'CreateTopicServlet?f=get_suggested_tags',
				data:{
					body: body
				},		
				dataType: 'json',
				success: function(data){
					console.log(data);
					var content = '';
					showRes.empty();
					var items = data.myArrayList;
					$.each(items,function(key,val){	
						var map = val.map;
						$.each(map,function(key,val){
							content = '<a class="list-group-item">'+key + '</a>';
							showRes.append(content);
						});
					});
				}
			});
			
		});
		
		$('#show-suggested-tags').on('click','a',function(){
			var content = '<a class="add-tag btn btn-primary"><span class="glyphicon glyphicon-remove"></span>'+$(this).text()+'</a>';
			$('#show-tags').append(content);
		});
		
		$('#show-tags').on('click','a',function(){
			$(this).remove();
		});
		
		$('#show-added-tags').on('click',function(){
			var tags=[];
			$('.add-tag').each(function(i){
				tags.push($(this).text());
			});
			
			var jsonArr = JSON.stringify(tags);
			alert(jsonArr);
		});
		$('#show-my-channels').on('click',function(){
			
			var userChannels = $('#user-channels');
			
			if(userChannels.css('display') == 'none'){
				
				$.ajax({
					url: 'ChannelServlet?f=get_user_channels',
					dataType: 'json',
					success: function(data){
						userChannels.empty();
						var content = '';
						$.each(data,function(key,val){		
							content = '<a class="list-group-item" id="add_channel">'+ val.header + '</a>';
							userChannels.append(content);
						});
						
					}
				});		
				
				userChannels.show();
				
			}else{
				userChannels.hide();
			}
		});		
		
		$('#user-channels').on('click','a',function(event){
			$('#channel').val($(this).text());
		});
		
		$('#show-add-channel-form').on('click',function(){
			if($('#add-channel-form').css('display') == 'none')
				$('#add-channel-form').show();
			else
				$('#add-channel-form').hide();
		});
		
		$('#add-channel').on('click',function(){
			$.post('ChannelServlet',{
				f: 'add_channel',
				channel_name: $('#channel-name').val()
			},function(data,status){
				$('#channel-name').val('');
			});
		});
		
		$('#create_topic_form').on('submit',function(){
			var tags=[];
			$('.add-tag').each(function(i){
				tags.push($('.add-tag').text());
			});
			
			var jsonArr = JSON.stringify(tags);

			$(this).append('<textarea name="tags">'+jsonArr+'</textarea>');
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
								<a id="search-link" class="btn btn-default">
									<i class="glyphicon glyphicon-search"></i>
								</a>
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
						action="CreateTopicServlet" id="url-image-upload">
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
									<div id="show-tags"></div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="tags">Tags:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="tags">
										</div>
									</div>
									<a id="show-added-tags" class="btn btn-primary">Show added Tags</a>
									<div id="tag-selections"></div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="owner">Owner:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" name="owner"
												id="owner" disabled="disabled"
												value="<%=session.getAttribute("first_name") + " " + session.getAttribute("last_name")%>">
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3" for="channel">Channel:</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" name="channel"
												id="channel" readonly>
										</div>
									</div>
									
									<a class="btn btn-primary" id="show-my-channels">Show My Channels</a>
									<div class="container">
										<div class="list-group col-sm-5" id="user-channels" style="display: none;"></div>
									</div>
									
									<a class="btn btn-primary" id="show-add-channel-form">Add Channel</a>
									<div class="container">									
										<div class="col-sm-5" id="add-channel-form" style="display: none;">
										
												<div class="form-group">
													<label for="channel-name">Name:</label>
													<input type="text" id="channel-name" class="form-control">
												</div>
												<div class="form-group">
													<a class="btn btn-primary" id="add-channel">Add</a>
												</div>
												
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
						<div id="show-suggested-tags"></div>
						<div class="form-group">
							<div class="col-xs-9 col-xs-offset-3">
								<a class="btn btn-primary" id="get_suggestions">Get Suggested Tags</a>
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