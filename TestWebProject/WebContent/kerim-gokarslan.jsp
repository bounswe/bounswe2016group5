<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CmpE Group5 - Assignment6 - Kerim Gokarslan - Automobiles</title>
</head>
<body>

	<form id="queryform" method="post" name="queryform"
		action="/TestWebProject/kerim-gokarslan">
			Name/Manufacturer/Type: <input type="text" name="query" size="32">
		<br> <input type="submit" name="submit"
			value="Search for automobiles">
	</form>
		Example texts are "Toyota" or "supermini"
	<br>
	<br> 	Or you can retrieve the previous saved items of yours
		(Cookies must be enabled!)
	<form id="retrieveform" method="post" name="retrieveform"
		action="/TestWebProject/kerim-gokarslan">
		<input type="submit" name="retrieve" value="Retrieve Saved Items">
	</form>
</body>
</html>