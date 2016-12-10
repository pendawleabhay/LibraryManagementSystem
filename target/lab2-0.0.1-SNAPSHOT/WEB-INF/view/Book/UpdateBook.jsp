<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
<style>

body {
    padding-top: 180px;
}
div.centerpage {
    width: 1000px;
    height: 90px;
    

    position: absolute;
    top:0;
    bottom: 0;
    left: 0;
    right: 0;

    margin: auto;
}
div.absolute {
    position: absolute;
    top: 500px;
    width: 200px;
    height: 1000px;
    
} 
.dropdown.dropdown-lg .dropdown-menu {
    margin-top: -1px;
    padding: 6px 20px;
}
.input-group-btn .btn-group {
    display: flex !important;
}
.btn-group .btn {
    border-radius: 0;
    margin-left: -1px;
}
.btn-group .btn:last-child {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px;
}
.btn-group .form-horizontal .btn[type="submit"] {
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}
.form-horizontal .form-group {
    margin-left: 0;
    margin-right: 0;
}
.form-group .form-control:last-child {
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
}

@media screen and (min-width: 768px) {
    #adv-search {
        width: 500px;
        margin: 0 auto;
    }
    .dropdown.dropdown-lg {
        position: static !important;
    }
    .dropdown.dropdown-lg .dropdown-menu {
        min-width: 500px;
    }
}

li
{
background-image: url(arrow.gif);
background-repeat: no-repeat;
background-position: 100% .4em;
padding-right: .6em;
margin: 1em 0;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page | Librarian</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<style>
#custom-search-input{
    padding: 3px;
    border: solid 1px #E4E4E4;
    border-radius: 6px;
    background-color: #fff;
}

#custom-search-input input{
    border: 0;
    box-shadow: none;
}

#custom-search-input button{
    margin: 2px 0 0 0;
    background: none;
    box-shadow: none;
    border: 0;
    color: #666666;
    padding: 0 8px 0 10px;
    border-left: solid 1px #ccc;
}

#custom-search-input button:hover{
    border: 0;
    box-shadow: none;
    border-left: solid 1px #ccc;
}

#custom-search-input .glyphicon-search{
    font-size: 23px;
}

</style>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" href="style.css">
 <link rel="stylesheet" href="styles.css">
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

<title>Book | Add</title>
 <link rel="stylesheet" href="style.css">
 <link rel="stylesheet" href="styles.css">
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

<title>Book | Update</title>
</head>
<body>
<%@ include file="NavBarLib.jsp" %>

<div id="main" class="container absolute centerpage form-group"> 	
	<form name="loginform" id="loginform" action="/book/updateBook" method="post" class="wpl-track-me">
	<!-- <form name="loginform" id="loginform" action="/book/updateBook" method="post" class="wpl-track-me"> --> 
		<!-- Update Book -->
		<h3 style="top:50px;">Update Book</h3>
		<!-- Book ID -->
		<p class="login-username">
		<label for="user_login">Book ID</label> 
			<input type="text" name="bookid" id="bookid" class="input" placeholder="Book Id" value="${book.bookid}" size="20" readonly/>
		</p>
		
		<!-- Author -->
		<p class="login-username">
		<label for="user_login">Author</label> 
			<input type="text" name="author" id="author" class="input" placeholder="Author Name" value="${book.author}" size="20" required/>
		</p> 
		
		<!-- Title -->
		<p class="login-username">
		<label for="user_login">Title</label> 
			<input type="text" name="title" id="title" class="input" placeholder="Book Title" value="${book.title}" size="20" required/> 
		</p> 
		
		<!-- Call Number -->
		<p class="login-username">
		<label for="user_login">Call-Number</label> 
			<input type="text" name="call_number" id="call_number" class="input" placeholder="Call Number" value="${book.call_number}" size="20" required/>
		</p> 
		
		<!-- Publisher -->
		<p class="login-username">
		<label for="user_login">Publisher</label> 
			<input type="text" name="publisher" id="publisher" class="input" placeholder="Publisher Name" value="${book.publisher}" size="20" required/>
		</p> 
		
		<!-- Year Of Publication -->
		<p class="login-username">
		<label for="user_login">Year Of Publication</label> 
			<input type="text" name="year_of_publication" id="year_of_publication" class="input" placeholder="Publication Year" value="${book.year_of_publication}" size="20" required />
		</p> 
		
		<!-- Location -->
		<p class="login-username">
		<label for="user_login">Location</label> 
			<input type="text" name="location_in_library" id="location_in_library" class="input" placeholder="Location in Library" value="${book.location_in_library}" size="20" required />
		</p>
		
		<!-- Copies --> 
		<p class="login-username">
		<label for="user_login">Number of Copies</label> 
			<input type="text" name="number_of_copies" id="number_of_copies" class="input" placeholder="Number of Copies" value="${book.number_of_copies}" size="20" required /> 
		</p> 
		
		<!-- Copies Available --> 
		<p class="login-username">
		<label for="user_login">Copies Available</label> 
			<input type="text" name="copies_available" id="copies_available" class="input" placeholder="Copies Available" value="${book.copies_available}" size="20" required />
		</p>
		
		<!-- Current Status --> 
		<p class="login-username">
		<label for="user_login">Current Status</label> 
			<input type="text" name="current_status" id="current_status" class="input" placeholder="Book Status" value="${book.current_status}" size="20" required />
		</p> 
		
		<!-- Keywords --> 
		<p class="login-username">
		<label for="user_login">Keywords</label> 
			<input type="text" name="keywords" id="keywords" class="input" placeholder="Book Status" value="${book.keywords}" size="20" required />
		</p>
		
		<!-- Submit Button -->
		<p class="login-submit">
			<input type="submit" name="wp-submit" id="wp-submit" class="btn btn-success" value="Update Book"/>
		</p> 	
	</form> 
</div>

</body>
</html>