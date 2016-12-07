<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" href="style.css">
 <link rel="stylesheet" href="styles.css">
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

<title>Book | Add</title>
</head>
<body>
<div id="main" class="container"> 	
	<form name="loginform" id="loginform" action="/lab2/book/addBook" method="post" class="wpl-track-me">
	<!-- <form name="loginform" id="loginform" action="/book/addBook" method="post" class="wpl-track-me"> --> 
		<!-- Author -->
		<p class="login-username">
		<label for="user_login">Author</label> 
			<input type="text" name="author" id="author" class="input" placeholder="Author Name" value="" size="20" /> 
		</p> 
		
		<!-- Title -->
		<p class="login-username">
		<label for="user_login">Title</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="title" id="title" class="input" placeholder="Book Title" value="${book.title}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="title" id="title" class="input" placeholder="Book Title" value="" size="20" />
				</c:when>
			</c:choose> 
			<c:if test="${errorMessage != null}">
				<p class="login-username">${errorMessage}</p>
			</c:if> --%>
			
			
			<input type="text" name="title" id="title" class="input" placeholder="Book Title" value="" size="20" />
		</p> 
		
		<!-- Call Number -->
		<p class="login-username">
		<label for="user_login">Call-Number</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="call_number" id="call_number" class="input" placeholder="Call Number" value="${book.call_number}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="call_number" id="call_number" class="input" placeholder="Call Number" value="" size="20" />
				</c:when>
			</c:choose> --%> 
			
			<input type="text" name="call_number" id="call_number" class="input" placeholder="Call Number" value="" size="20" />
		</p> 
		
		<!-- Publisher -->
		<p class="login-username">
		<label for="user_login">Publisher</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="publisher" id="publisher" class="input" placeholder="Publisher Name" value="${book.publisher}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="publisher" id="publisher" class="input" placeholder="Publisher Name" value="" size="20" /> 
				</c:when>
			</c:choose> --%>
			
			<input type="text" name="publisher" id="publisher" class="input" placeholder="Publisher Name" value="" size="20" />
		</p> 
		
		<!-- Year Of Publication -->
		<p class="login-username">
		<label for="user_login">Year Of Publication</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="year_of_publication" id="year_of_publication" class="input" placeholder="Publication Year" value="${book.year_of_publication}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="year_of_publication" id="year_of_publication" class="input" placeholder="Publication Year" value="" size="20" /> 
				</c:when>
			</c:choose> --%>
			
			<input type="text" name="year_of_publication" id="year_of_publication" class="input" placeholder="Publication Year" value="" size="20" />
		</p> 
		
		<!-- Location -->
		<p class="login-username">
		<label for="user_login">Location</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="location_in_library" id="location_in_library" class="input" placeholder="Location in Library" value="${book.location_in_library}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="location_in_library" id="location_in_library" class="input" placeholder="Location in Library" value="" size="20" /> 
				</c:when>
			</c:choose> --%>
			
			<input type="text" name="location_in_library" id="location_in_library" class="input" placeholder="Location in Library" value="" size="20" />
		</p>
		
		<!-- Number of Copies --> 
		<p class="login-username">
		<label for="user_login">Copies</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="number_of_copies" id="number_of_copies" class="input" placeholder="Number of Copies" value="${book.number_of_copies}" size="20" /> 
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="number_of_copies" id="number_of_copies" class="input" placeholder="Number of Copies" value="" size="20" />  
				</c:when>
			</c:choose> --%>
			
			<input type="text" name="number_of_copies" id="number_of_copies" class="input" placeholder="Number of Copies" value="" size="20" />
		</p> 
		
		<!-- Copies Available --> 
		<p class="login-username">
		<label for="user_login">Copies Available</label> 
			<input type="text" name="copies_available" id="copies_available" class="input" placeholder="Copies Available" value="" size="20" />
		</p>
		
		<!-- Current Status --> 
		<p class="login-username">
		<label for="user_login">Current Status</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="current_status" id="current_status" class="input" placeholder="Book Status" value="${book.current_status}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="current_status" id="current_status" class="input" placeholder="Book Status" value="" size="20" />
				</c:when>
			</c:choose> --%>
		
		<select name="current_status" id="current_status" class="input">
		  <option value="0" selected="selected">0</option>
		  <option value="1">1</option>
		</select>	
			<!-- <input type="text" name="current_status" id="current_status" class="input" placeholder="Book Status" value="" size="20" /> -->
		</p> 
		
		<!-- Keywords --> 
		<p class="login-username">
		<label for="user_login">Keywords</label> 
			<%-- <c:choose>
				<c:when test="${errorMessage != null}">
					<input type="text" name="keywords" id="keywords" class="input" placeholder="Book Status" value="${book.keywords}" size="20" />
				</c:when>
				<c:when test="${errorMessage == null }">
					<input type="text" name="keywords" id="keywords" class="input" placeholder="Book Keywords" value="" size="20" />
				</c:when>
			</c:choose> --%>
			
			<input type="text" name="keywords" id="keywords" class="input" placeholder="Book Keywords" value="" size="20" />
		</p>
		
		<!-- Submit Button -->
		<p class="login-submit">
			<input type="submit" name="wp-submit" id="wp-submit" class="button-primary" value="Add Book" />
		</p> 	
	</form> 
</div>

</body>
</html>