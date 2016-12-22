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

<link rel="stylesheet" href="css/site.css">
<script src="js/site.js"></script>

<script>
$(document).ready(function(){
	
	$.urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null){
	       return null;
	    }
	    else{
	       return results[1] || 0;
	    }
	}
	
	$.getJSON('ChannelServlet?f=get_channel&cid='+$.urlParam('cid'),function(data){
		var channelName = data.name;
		$('#channel-header').text(channelName);
	});
	
	$.getJSON('ChannelServlet?f=get_topics&cid='+$.urlParam('cid'),function(data){
			var topics = $('#topics');
			var content = '';
			$.each(data,function(key,val){
					if(val.header==null){
						var progress = val.channel_progress;
						$('#progress').text(progress);
						$('#progress').attr({
							'style': 'width:'+progress+'%',
							'areavaluenow': +progress
						});
						
					}else{
					
					content = '<td>'+val.header+'</td>'
				          + '<td>'+val.progress+'</td>'
				          + '<td>' 
				          +	'<a class="btn btn-primary" href="ViewTopicServlet?topic_id='+val.id+'">View Topic</a></td>';
				
					topics.append($('<tr />').html(content));
					}
			});
			
			
		});
	
	<%if(session.getAttribute("id")!=null){%>
	
	$.ajax({
		url: 'ChannelServlet',
		data: {
			f: 'get_progress',
			cid: $.urlParam('cid'),
			uid: <%=session.getAttribute("id")%>
		},
		success: function(data){
			var progress = data;
			$('#progress').text(progress);
			$('#progress').attr({
				'style': 'width:'+progress+'%',
				'areavaluenow': +progress
			});
		}
	});
	
	$('#progress-block').show();
	
	<%}%>
	
	
	$('#show-add-topic-form').on('click',function(){
		if($('#add-topic-form').css('display') == 'none')
			$('#add-topic-form').show();
		else
			$('#add-topic-form').hide();
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
					<form action="search.jsp" method="POST" class="navbar-form"
						role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="search_field" id="search">
							<div class="input-group-btn">
								<button type="submit" id="search-link" class="btn btn-default">
									<i class="glyphicon glyphicon-search"></i>
								</button>
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
			<div class="col-sm-9">
				<h2 id="channel-header" align="center"></h2>
				<div id="progress-block" class="progress" style="display: none;">
				  <div id="progress" class="progress-bar" role="progressbar"
				  aria-valuemin="0" aria-valuemax="100" >
				 </div>
				</div>
			<div class=" well " >
			    <table class="table">
			      <thead>
			        <tr>
			          <th>Topic Name</th>
			          <th>Progress</th>
			          <th></th>
			        </tr>
			      </thead>
			      <tbody id="topics">
			      </tbody>
			    </table>
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