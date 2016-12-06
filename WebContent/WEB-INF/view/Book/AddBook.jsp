<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" type="text/css" href="../../css/styles.css">
 <link rel="stylesheet" type="text/css" href="../styles.css">
<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>

<title>Book | Add</title>
</head>
<body>
<div id="main" class="container"> 	
	<form name="loginform" id="loginform" action="#" method="post" class="wpl-track-me"> 
		<p class="login-username">
		<label for="user_login">Author</label> 
			<input type="text" name="log" id="author" class="input" placeholder="Author Name" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Title</label> 
			<input type="text" name="log" id="title" class="input" placeholder="Book Title" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Call-Number</label> 
			<input type="text" name="log" id="call_number" class="input" placeholder="Call Number" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Publisher</label> 
			<input type="text" name="log" id="publisher" class="input" placeholder="Publisher Name" value="" size="20" /> 
		</p> 
				<p class="login-username">
		<label for="user_login">Year Of Publication</label> 
			<input type="text" name="log" id="year_of_publication" class="input" placeholder="Publication Year" value="" size="20" /> 
		</p> 
		
		<p class="login-username">
		<label for="user_login">Location</label> 
			<input type="text" name="log" id="location_in_library" class="input" placeholder="Location in Library" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Copies</label> 
			<input type="text" name="log" id="number_of_copies" class="input" placeholder="Number of Copies" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Copies</label> 
			<input type="text" name="log" id="number_of_copies" class="input" placeholder="Number of Copies" value="" size="20" /> 
		</p> 
		<p class="login-username">
		<label for="user_login">Current Status</label> 
			<input type="text" name="log" id="current_status" class="input" placeholder="Book Status" value="" size="20" /> 
		</p> 

		<p class="login-submit"><input type="submit" name="wp-submit" id="wp-submit" class="button-primary" value="Add Book" />
		<input type="hidden" name="redirect_to" value="#"/>
		</p> 	
	</form> 
</div>

</body>
</html>