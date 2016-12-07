<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Issued Books</title>
</head>
<body>
	<c:choose>
		<c:when test="${issuedBooksList != null}">
			<c:forEach var = "index" begin = "0" end = "${size-1}">
				<div>BookId : ${issuedBooksList[index].bookid} --- Title : ${issuedBooksList[index].title}</div>
				<div>Issue Date : ${issuedBookIdList[index].issueDate}</div>
				<div>Due Date : ${issuedBookIdList[index].dueDate}</div>
			</c:forEach>
		</c:when>
		
		<c:when test="${message != null}">
			<h3>${message}</h3>
		</c:when>
	</c:choose>
</body>
</html>