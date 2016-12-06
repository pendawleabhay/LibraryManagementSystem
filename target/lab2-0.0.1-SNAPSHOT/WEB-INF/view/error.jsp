<%@ page isErrorPage="true" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Group 28 Error</title>
</head>
<body>
<h2>404</h2>

<% String[] splits = pageContext.getErrorData().getRequestURI().split("/"); %>
	404
	Group - 13
	<p>
		<%= "The " + splits[1] + " with ID " + splits[2] + " not found in the system!" %>
	<p>

</body>
</html>