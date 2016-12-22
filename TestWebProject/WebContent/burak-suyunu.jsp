<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CmpE Group5 - Assignment6 - Burak Suyunu - Academy Awards</title>
</head>
<body>
	<h1>Academy Awards</h1>

	<form id="queryform" method="post" name="queryform"
		action="/TestWebProject/burak-suyunu">
			Name/Award/Movie: <input type="text" name="query" size="32" value="88th Academy Awards">
		<br> <br> <input type="submit" name="submit"
			value="Search">
	</form>
	<br>
	Example texts are "88th Academy Awards" or "Inside Out" or "Leonardo DiCaprio"
	<br> <br> <br>
	
	Or you can take a look at your saved data:
	<form id="savedfilesform" method="post" name="savedfilesform"
		action="/TestWebProject/burak-suyunu">
		<br> <input type="submit" name="show"
			value="Retrieve Saved Items">
	</form>
	
	<br> <br>
	<form id="deletefilesform" method="post" name="deletefilesform"
		action="/TestWebProject/burak-suyunu">
		<br> <input type="submit" name="delete"
			value="Delete Saved Items">
	</form>
</body>
</html>
