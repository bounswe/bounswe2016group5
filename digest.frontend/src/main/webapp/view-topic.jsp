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
<title>View Topic</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/comment.css">
<link rel="stylesheet" href="css/quiz.css">
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
		$('#add_comment_form').validate({
			rules : {
				topic_id : {
					required : true
				},
				user_id : {
					required : true
				},
				body : {
					required : true
				},
			}
		});
        $('#add_media_form').validate({ 
			rules : {
				tid : {
					required : true
				},
				url : {
					required : true
				},
			}
		});
		
		$('.upButton').click(function(){
			var cid = $(this).val().split('&')[0];
			var uid = $(this).val().split('&')[1];
		    var count =  parseInt(document.getElementById(cid+"-rate").textContent);
		    var count = count + 1 ;
		    document.getElementById(cid+"-rate").textContent = count;
		    $.post( "RateCommentServlet", { cid: cid, uid: uid, f:"rate_comment" },function( data ) {
		    		
		    });
		    
		    
		 });
		$('.downButton').click(function(){
			
			var cid = $(this).val().split('&')[0];
			var uid = $(this).val().split('&')[1];
		    var count =  parseInt(document.getElementById(cid+"-rate").textContent);
		    var count = count -1 ;
		    document.getElementById(cid+"-rate").textContent = count;
		    
		    $.post( "RateCommentServlet", { cid: cid, uid: uid, f:"unrate_comment" },function( data ) {
	    		
		    });
		    
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
						alt="digest-icon" src="img/logo.jpg" height=35 width=35
						style="margin: 0 0 0 10px"> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-6 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group col-sm-12">
							<input type="text" class="form-control" placeholder="Search"
								name="searchterm" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default"> <i
									class="glyphicon glyphicon-search"></i>
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
						alt="digest-icon" src="img/logo.jpg" height=35 width=35
						style="margin: 0 0 0 10px"> </span></a>
			</div>
			<div class=" collapse navbar-collapse" id="myNavbar">
				<div class="col-sm-3 pull">
					<form action="_search" method="POST" class="navbar-form"
						role="search">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search"
								name="searchterm" id="search">
							<div class="input-group-btn">
								<a id="search-link" class="btn btn-default"> <i
									class="glyphicon glyphicon-search"></i>
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
								<li class="active"><a href="MainServlet"><span
										class="glyphicon glyphicon-home"></span> Homepage</a></li>
								<li><a href="UserProfileServlet"><span
										class="glyphicon glyphicon-user"></span> Profile</a></li>
								<li><a href="FollowingTopicsServlet"><span
										class="glyphicon glyphicon-star-empty"></span> Following
										Topics</a></li>
							</ul>
							<div class="panel panel-default"
								style="height: 200px; overflow-y: auto;">
								<div class="panel-header">Channels</div>
								<div class="panel-body" id="sub_channels"></div>

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

				if (session.getAttribute("session") == null) {
			%>

			<div class="row col-sm-9" style="margin: 0 100px 0 100px;">
				<%
					} else {
				%>

				<div class="row col-sm-9">
					<%
						}
						String header = (String) request.getAttribute("header");
						int owner = (Integer) request.getAttribute("ownerId");
						String ownerName = (String) request.getAttribute("ownerName");
						int tid = (Integer) request.getAttribute("id");
						//int topicFollower = (Integer) request.getAttribute("topicFollower");
						String image = (String) request.getAttribute("image");
						String body = (String) request.getAttribute("body");
						int subscribed = (Integer) request.getAttribute("subscribed");
					%>
					<div class="col-sm-12">
						<h1><%=header%></h1>
						<p>
							Owner:
							<%=ownerName%></p>
						<!-- <p>Followers:</p>  -->
					</div>
					<%
						if (session.getAttribute("session") != null) {
							if (session.getAttribute("id") != null) {
								if (owner != (Integer) session.getAttribute("id") && subscribed == 0) {
					%>
					<form class="form-horizontal" id="add_subscriber_form"
						action="SubscribeServlet" method="POST">
						<div class="form-group col-sm-2 pull-right">
							<div class="col-xs-9 col-xs-offset-3">
								<input type="hidden" name="tid" value=<%=tid%>>
								<button type="submit" class="btn btn-primary"
									style="margin: 20px 20px 0 0;">Subscribe</button>
							</div>
						</div>
					</form>
					<%
						} else if (subscribed == 1) {
							int progress = (Integer) request.getAttribute("progress");
					%>
					<div class="col-sm-5 pull-right">
						<div class="text">Progress:</div>
						<div class="progress">
							<div data-percentage="0%" style="width: <%=progress %>%;"
								class="progress-bar progress-bar-primary" role="progressbar"
								aria-valuemin="0" aria-valuemax="100"></div>
						</div>
					</div>
					<%
						}
					%>
					<%
						if (owner == (Integer) session.getAttribute("id")) {
					%>
					<form class="form-horizontal" id="add_quiz_form"
						action="QuizServlet" method="POST">
						<div class="form-group col-sm-2 pull-right">
							<div class="col-xs-9 col-xs-offset-3">
								<input type="hidden" name="tid" value=<%=tid%>>
								<button type="submit" class="btn btn-primary"
									style="margin: 20px 20px 0 0;">Add Quiz</button>
							</div>
						</div>
					</form>
					<%
						}
							}
						}
					%>
				</div>
				<%
					if (session.getAttribute("session") == null) {
				%>

				<div class="row col-sm-9" style="margin: 0 100px 0 100px;">
					<%
						} else {
					%>

					<div class="row col-sm-9">
						<%
							}
						%>

						<div class="view-topic col-sm-12">
							<div class="topic-header">
								<div class="row">
									<div class="container col-sm-2">
										<img id="topic-img"
											style="display: block; width: 120%; margin: 9px 9px 9px 9px;"
											alt="topic image" src="<%=image%>"></img>
									</div>
									<div class="container col-sm-10">
										<div class="panel panel-default"
											style="height: 140px; overflow-y: auto; margin: 9px 9px 9px 9px;">
											<div class="panel-body"><%=body%></div>
										</div>
									</div>
								</div>
							</div>
							<div class="topic-tabs container col-sm-12"
								style="margin: 25px 0 0 0;">
								<ul class="nav nav-tabs">
									<li class="active"><a data-toggle="tab" href="#comments">Comments</a></li>
									<li><a data-toggle="tab" href="#materials">Materials</a></li>
									<li><a data-toggle="tab" href="#quiz">Quiz</a></li>
									<li><a data-toggle="tab" href="#related_topics">Related Topics</a></li>
								</ul>

								<div class="tab-content">
									<!-- comments begin -->
									<div id="comments" class="tab-pane fade in active">
										<div class="panel panel-default"
											style="height: 500px; overflow-y: auto;">
											<div class="row">
												<div class="col-sm-10 col-sm-offset-1"
													style="margin: 25px 5px 25px 25px;">
													<ul class="media-list">
														<%
															if (request.getAttribute("comments") != null) {

																JSONArray comments = (JSONArray) request.getAttribute("comments");

																for (Object comment : comments) {

																	JSONObject com = (JSONObject) comment;

																	if (com.getInt("ucid") == -1) {

																		int cid = com.getInt("id");
																		int uid = com.getInt("uid");

																		System.out.println(uid);
																		int rate = com.getInt("rate");
																		int type = com.getInt("type");

																		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_username&uid="
																				+ uid;
																		URL jsonpage = new URL(url);
																		HttpURLConnection urlcon = (HttpURLConnection) jsonpage.openConnection();

																		String username = "";
																		if (urlcon.getResponseCode() == 200) {
																			BufferedReader buffread = new BufferedReader(
																					new InputStreamReader(urlcon.getInputStream()));
																			String recv = "";
																			String recvbuff = "";
																			while ((recv = buffread.readLine()) != null)
																				recvbuff += recv;
																			buffread.close();

																			username = recvbuff;

																		}
														%>
														<li class="media">
															<div class="media-body ">
																<div class="well well-lg">
																	<div class="row">
																		<div class="col-sm-4">
																			<p class="media-heading text-uppercase reviews"><%=username%></p>
																			<p class="media-comment"><%=com.get("body")%></p>
																		</div>
																		<div class="col-sm-4 pull-right">
																			<span class="comment-rate" id="<%=cid%>-rate"
																				style="margin: 0 10px 0 0"> <%=rate %></span>
																			<button type="button" value="<%=cid %>&<%=uid%>"
																				class="upButton btn btn-info glyphicon glyphicon-thumbs-up"
																				data-loading-text=" ... " style="margin: 10px 0 0 0"></button>
																			<button type="button" value="<%=cid %>&<%=uid%>"
																				class="downButton btn btn-info glyphicon glyphicon-thumbs-down"
																				data-loading-text=" ... "
																				style="margin: 10px 10px 0 10px"></button>

																			<form class="form-horizontal" id="instructive_form"
																				action="InstructiveCommentServlet" method="POST">
																				<input type="hidden" name="cid" value="<%=cid%>">
																				<input type="hidden" name="tid" value="<%=tid%>">
																				<%
																				if (session.getAttribute("session") != null) {
																					if ( owner == (Integer) session.getAttribute("id") && type == 0 ) { //and not instructive already
																				%>
																				<button type="submit" id="insButton"
																					class="btn btn-danger " data-loading-text=" ... "
																					style="margin: 20px 30px 10px 10px">Mark
																					as Instructive</button>
																			</form>



																			<%
																			}
																			else if(type == 2) { 
																			%>
																			<h3>
																				<span class="label label-danger">Instructive</span>
																			</h3>
																			<%
																			}else{
																			%><h3>
																				<span class="label label-danger">Question</span>
																			</h3>
																			<%
																			}
																			}
																			%>
																		</div>
																	</div>

																	<a class="btn btn-info btn-circle"
																		data-toggle="collapse" href="#addcomment<%=cid%>"
																		id="reply"><span
																		class="glyphicon glyphicon-share-alt"></span> Reply</a> <a
																		class="btn btn-warning btn-circle "
																		data-toggle="collapse" href="#reply1<%=cid%>"><span
																		class="glyphicon glyphicon-comment"></span> Comments</a>
																</div>
															</div>

															<div class="collapse col-sm-9 pull-right "
																id="reply1<%=cid%>">
																<ul class="media-list">
																	<%
																		for (Object commentOfcomments : comments) {

																						JSONObject comOfcoms = (JSONObject) commentOfcomments;

																						if (comOfcoms.getInt("ucid") == com.getInt("id")) {
																							int lid = comOfcoms.getInt("id");
																							int luid = comOfcoms.getInt("uid");

																							String lurl = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_username&uid="
																									+ luid;
																							URL ljsonpage = new URL(lurl);
																							HttpURLConnection lurlcon = (HttpURLConnection) ljsonpage.openConnection();

																							String lusername = "";
																							if (lurlcon.getResponseCode() == 200) {
																								BufferedReader buffread = new BufferedReader(
																										new InputStreamReader(lurlcon.getInputStream()));
																								String recv = "";
																								String recvbuff = "";
																								while ((recv = buffread.readLine()) != null)
																									recvbuff += recv;
																								buffread.close();

																								lusername = recvbuff;

																							}
																	%>
																	<li class="media media-replied"><a
																		class="pull-left" href="#"> <img
																			class="media-object img-circle" alt="profile">
																	</a>
																		<div class="media-body">
																			<div class="well well-lg">
																				<h4 class="media-heading text-uppercase reviews">
																					<span class="glyphicon glyphicon-share-alt"></span>
																					<%=lusername%>
																				</h4>
																				<p class="media-comment"><%=comOfcoms.getString("body")%></p>
																			</div>
																		</div></li>
																	<%
																		}
																					}
																	%>
																</ul>
															</div>
														</li>
														<%
															if (session.getAttribute("id") != null) {
														%>
														<div class="collapse row col-sm-9 pull-right"
															id="addcomment<%=cid%>">
															<div class="row">
																<div class="widget-area no-padding blank">
																	<div class="status-upload">
																		<form class="form-horizontal" id="add_comment_form"
																			action="AddCommentServlet" method="POST">
																			<div class="form">
																				<textarea placeholder="Reply message here"
																					name="body"></textarea>
																				<label style="margin: 10px 10px 10px 10px"><%=session.getAttribute("username")%></label>
																				<input type="hidden" name="uid"
																					value=<%=session.getAttribute("id")%>> <input
																					type="hidden" name="ucid" value="<%=cid%>">
																				<input type="hidden" name="tid" value="<%=tid%>">
																				<label for="mark_reply" class="btn btn-success"
																					style="margin: 10px 0 10px 300px;">Mark as
																					a Question <input type="checkbox" name="mark_reply"
																					value="1" id="mark_reply" class="badgebox">
																					<span class="badge">&check; </span>
																				</label>
																				<button type="submit" class="btn btn-success green"
																					style="margin: 10px 10px 10px 10px">
																					<i class="fa fa-share"></i> Share
																				</button>
																			</div>
																		</form>
																	</div>
																</div>
															</div>
														</div>

														<%
															}
																	}
																}
															}
														%>
													</ul>
												</div>
											</div>
											<%
												if (session.getAttribute("id") != null) {
											%>
											<div class="container">
												<div class="row">
													<div class="col-sm-8">
														<div class="widget-area no-padding blank">
															<div class="status-upload">

																<form class="form-horizontal" id="add_comment_form"
																	action="AddCommentServlet" method="POST">
																	<div class="form">
																		<textarea placeholder="Comment here" name="body"></textarea>
																		<label style="margin: 10px 10px 10px 10px"><%=session.getAttribute("username")%></label>
																		<input type="hidden" name="uid"
																			value=<%=session.getAttribute("id")%>> <input
																			type="hidden" name="ucid" value="-1"> <input
																			type="hidden" name="tid" value=<%=tid%>> <label
																			for="mark_ques" class="btn btn-success"
																			style="margin: 10px 0 10px 300px;">Mark as a
																			Question <input type="checkbox" name="mark_ques"
																			value="1" id="mark_ques" class="badgebox"> <span
																			class="badge">&check; </span>
																		</label>
																		<button type="submit" class="btn btn-success green"
																			style="margin: 10px 10px 10px 10px">
																			<i class="fa fa-share"></i> Share
																		</button>
																	</div>
																</form>
															</div>
														</div>
													</div>

												</div>
											</div>
											<%
												}
											%>
										</div>
									</div>
									<div id="materials" class="tab-pane fade">
										<div class="panel panel-default"
											style="height: 500px; overflow-y: auto;">
											<%
												if (session.getAttribute("session") != null) {
													if (subscribed == 0 && owner != (Integer) session.getAttribute("id")) {
											%>
											<h4 style="margin: 20px 20px 20px 20px">You need to be
												subscribed to see the materials.</h4>
											<%
												} else {
											%>


											<%
												if (request.getAttribute("media") != null) {
															JSONArray media = (JSONArray) request.getAttribute("media");
															int mcount = 0;
															for (Object mediaUrl : media) {
																mcount++;
											%>

											<h3 align="center">
												<%
													String url = mediaUrl.toString();
																	if (url.matches(".*\\byoutube\\b.*")) {
																		url = "https://www.youtube.com/embed/" + url.substring(url.indexOf("v=") + 2);
												%>
												<label>Material <%=mcount%>:
												</label>
												<iframe width="420" height="315" src=<%=url%>> </iframe>
												<%
													} else if (url.matches("([^\\s]+(\\.(?i)(jpg|png|gif))$)")) {
												%>
												<label>Material <%=mcount%>:
												</label> <img src=<%=url%> style="width: 420px;">
												<%
													} else {
												%>
												<label>Material <%=mcount%>:
												</label> <a href=<%=url%> name="url" id="url"><%=mediaUrl.toString()%></a>
												<%
													}
												%>


											</h3>
											<%
												}
														}
													}

													if (owner == (Integer) session.getAttribute("id")) {
											%>
											<form class="form-horizontal" id="add_media_form"
												action="AddMediaServlet" method="POST">
												<div class="form-group col-sm-8"
													style="margin: 20px 20px 0 0;">
													<div class="col-xs-9 col-xs-offset-3">
														<input type="text" class="form-control" name="url"
															id="url"> <input type="hidden" name="tid"
															value=<%=tid%>>
														<button type="submit" class="btn btn-primary"
															style="margin: 20px 20px 0 0;">Add Media</button>
													</div>
												</div>
											</form>
											<%
												}
												}
											%>
										</div>
									</div>

									<div id="quiz" class="tab-pane fade">
										<form class="form-horizontal" id="submit_quiz_form"
											action="SubmitQuizServlet" method="POST">
											<div class="panel panel-default"
												style="height: 500px; overflow-y: auto;">
												<%
													if (session.getAttribute("session") != null) {
														if (subscribed == 0 && owner != (Integer) session.getAttribute("id")) {
												%>
												<h4 style="margin: 20px 20px 20px 20px">You need to be
													subscribed to see the quizzes.</h4>
												<%
													} else {
												%>

												<%
													if (request.getAttribute("quizzes") != null) {

																JSONArray quizzes = (JSONArray) request.getAttribute("quizzes");

																for (Object quiz : quizzes) {
																	JSONObject qu = (JSONObject) quiz;
																	String quizName = qu.getString("name");
																	JSONArray questions = qu.getJSONArray("questions");
																	int len = questions.length();
												%>
												<div class="quiz-1">
													<h3 style="margin: 20px 0 20px 20px;">
														Quiz:
														<%=quizName%></h3>
													<%
														for (int j = 0; j < len; j++) {

																			JSONObject ques = questions.getJSONObject(j);
																			String question = ques.getString("text");
													%>
													<h4 style="color: #377bb5; margin: 20px 0 20px 20px;"><%=j + 1 + ") " + question%></h4>
													<%
														JSONArray options = ques.getJSONArray("choices");

																			if (options != null) {
																				for (int i = 0; i < options.length(); i++) {
																					String option = (String) options.get(i);
													%>
													<label for="quizName&<%=j%>&<%=i%>" class="btn btn-primary"
														style="margin: 0 0 0 20px;"><%=option%> <input
														type="checkbox" name="quizName&<%=j%>&<%=i%>"
														value="<%=i%>" id="quizName&<%=j%>&<%=i%>"
														class="badgebox"> <span class="badge">&check;
													</span> </label>
													<!--  <div class="checkbox">
										<label><input type="checkbox"
											name="q<%=j%>answer<%=i%>" value=""><%=option%></label>
									</div>-->

													<%
														}
																			}
																		}
													%>
													<div class="widget-area no-padding blank">
														<input type="hidden" name="tid" value=<%=tid%>>
														<button type="submit" class="btn btn-success"
															style="margin: 20px 20px 20px 20px;">Submit Quiz</button>
														<!--  submit -->
													</div>
													<%
														}
																}
															}
														}
													%>
												</div>
											</div>
										</form>
									</div>
									
									<div id="related_topics" class="tab-pane fade">
										<div class="panel panel-default"
											style="height: 500px; overflow-y: auto;">
											<%
												if (session.getAttribute("session") != null) {
													if (subscribed == 0 && owner != (Integer) session.getAttribute("id")) {
											%>
											<h4 style="margin: 20px 20px 20px 20px">You need to be
												subscribed to see the materials.</h4>
											<%
												} else {
											%>

											
											<%
												}
												}
											%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>



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