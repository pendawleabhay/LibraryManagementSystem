<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book | Search</title>
</head>
<body>
	<c:if test="${bookList!=null}">	
		<c:forEach items="${bookList}" var="book">
			<c:if test="${user.userType == 'patron'}">
				
			</c:if>
    		<div>${book.bookid} - ${book.title}</div>
    		<div>
    			<c:if test="${user.userType == 'librarian'}">
    				<!-- Form for submitting update -->
    				<form action = "/lab2/book/update" method="POST">
    					<input type="hidden" name="bookid" value="${book.bookid }"/>
    					<input type="submit" value="Update"/>
					</form>
					
					<!-- Form for submitting delete -->
					<form action = "/lab2/book/delete" method="POST">
						<input type="hidden" name="bookid" value="${book.bookid }"/>
    					<input type="submit" value="Delete"/>
					</form>
    			</c:if>
    		</div>
    		<br>
		</c:forEach>
	</c:if>
	
	<c:if test="${message!=null}">
		<div>${message}</div>
	</c:if>
</body>
</html>