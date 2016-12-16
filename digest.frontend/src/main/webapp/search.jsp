<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Page</title>
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
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
	
<link rel="stylesheet" href="css/site.css">
<script src="js/site.js"></script>
<script>

$(document).ready(function(){
	var searchField = '<%=request.getParameter("search_field")%>';
	var showData = $('#show-data');
	
	$.ajax({url:'SearchServlet?f=search_with_tag&tag='+searchField,
			dataType: 'json',
			success: function(data){
				
			showData.empty();
			var content = '';
			$.each(data,function(key,val){					
					content  += '<a class="list-group-item" href="ViewTopicServlet?topic_id='+val.id+'">' + val.header + '</a>';
		
			});
			
			var list = $('<div class="list-group" />').html(content);
			showData.append(list);
	
		}
	});
	
	$('#advanced-search-btn').on('click',function(){
			$.ajax({url:'SearchServlet?f=advanced_search',
					dataType: 'json',
					data:{
						header: $('#header').val(),
						tag: $('#tag').val(),
						from_date: $('#from_date').val(),
						to_date: $('#to_date').val()
					},
					success: function(data){
						
						showData.empty();
						var content = '';
						$.each(data,function(key,val){					
								content  += '<a class="list-group-item" href="ViewTopicServlet?topic_id='+val.id+'">' + val.header + '</a>';
					
						});
					
					var list = $('<div class="list-group" />').html(content);
					showData.append(list);
			
				}
			});
	});
    var from_date_input=$('input[name="from_date"]'); //our date input has the name "from_date"
    var options={
      format: 'dd/mm/yyyy',
      todayHighlight: true,
      autoclose: true,
    };
    from_date_input.datepicker(options);
    
    var to_date_input=$('input[name="to_date"]'); //our date input has the name "to_date"
    var options={
      format: 'dd/mm/yyyy',
      todayHighlight: true,
      autoclose: true,
    };
    to_date_input.datepicker(options);
  
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
						<div id="show-data"></div>
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
			<%
				}
			%>
	<div class="row col-sm-12" id="content">
	
	<%
		if (session.getAttribute("session") != null) {
	%>
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
			
			<%
				}
			%>
			<div class="col-sm-9">
				<div class="container" id="advanced-search">
					<form class="form-horizontal" id="advanced-search-form">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="header">Header:</label>
							<div class="col-sm-10"><input class="form-control" type="text" name="header" id="header"></div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label" for="tag">Tag:</label>
							<div class="col-sm-10"><input class="form-control" type="text" name="tag" id="tag"></div>
						</div>
						
						<div class="form-group">
					        <label class="col-sm-2 control-label" for="from_date">From:</label>
					        <div class="col-sm-10"><input class="form-control" id="from_date" name="from_date" placeholder="DD/MM/YYY" type="text" readonly/></div>
						</div>
	
						<div class="form-group">
					        <label class="col-sm-2 control-label" for="to_date">To:</label>
					        <div class="col-sm-10"><input class="form-control" id="to_date" name="to_date" placeholder="DD/MM/YYY" type="text" readonly/></div>
						</div>
											
						<div class="form-group" style="text-align: center">
							<a class="btn btn-primary" id="advanced-search-btn">Advanced Search</a>									
						</div>
					</form>
				</div>
				<div class="container" id="show-data"></div>
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