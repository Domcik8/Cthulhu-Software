<%@page import="lt.vu.mif.labanoro_draugai.authentication.FBConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	FBConnection fbConnection = new FBConnection();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Java Facebook Login</title>
</head>
<body style="text-align: center; margin: 0 auto;">
	<div
		style="margin: 0 auto; background-image: url(resources/img/fbloginbckgrnd.jpg); height: 360px; width: 610px;">
		<a> <img
			style="margin-top: 138px;" src="resources/img/facebookloginbutton.png" />
		</a>
	</div>
</body>
</html>