<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CmpE Group5 - Assignment6 - Atakan Guney - Mathematicians</title>
</head>
<body>
	<h1>Most eponymous mathematicians</h1>	
	<form id="queryform" method="post" name="queryform"
		action="/TestWebProject/atakan-guney">
			Search for name: <input type="text" name="query" size="32">
		<br> <input type="submit" name="submit"
			value="Search">
	</form>

	<form id="savedfilesform" method="post" name="savedfilesform"
		action="/TestWebProject/atakan-guney">
		<br> <input type="submit" name="show"
			value="Saved items">
		<br> <input type="submit" name="delete"
			value="Delete Saved Items">

	</form>
</body>
</html>
