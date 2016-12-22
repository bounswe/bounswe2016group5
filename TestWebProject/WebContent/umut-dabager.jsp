<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CmpE Group5 - Assignment6 - Umut Dabager - Musicians</title>
</head>
<body>

	<form id="queryform" method="post" name="queryform" action="/TestWebProject/umut-dabager">
			Enter a musician or genre to search : 
			<input type="text" name="input" size="32">
		<br> 
		<input type="submit" name="submit" value="Search">
		<br>
		<input type="submit" name="retrieve" value="Retrieve saved results">
		<input type="submit" name="clear" value="Clear saved results">
	</form>
	
</body>
</html>