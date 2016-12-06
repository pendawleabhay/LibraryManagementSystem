<%-- 
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Phone Details</title>
</head>
<body>
		Phone ID <input type="text" name="phoneId" value=${phone.phoneId} readonly/><br /> <br />
	<form action="/lab2/user/${phone.phoneId}" >
	    <input type="hidden" name="method" value="update">
	   firstname <input type="text" name="firstname" value=${phone.number} /><br /> <br /> 
	   lastname <input type="text" name="lastname" value=${phone.description} /><br /> <br /> 
	  
	   street <input type="text" name="street" value=${phone.address.street} /><br /><br />  
	   city <input type="text" name="city" value=${phone.address.city} /><br /> <br /> 
	   state <input type="text" name="state" value=${phone.address.state} /><br /> <br /> 
	   zip <input type="text" name="zip" value=${phone.address.zip} /><br /> <br /> 
	    User <br />
		<c:forEach items = "${phone.users}" var = "user">
 		<div class = "outerWrapper">	
  			
  			User First Name : <c:out value = "${user.firstname}" />
  			User Last Name : <c:out value = "${user.lastname}" />
  			<a href = "#" class = "outerRemove">Remove</a>
  		</div> 
 	</c:forEach>
		<input type="submit" value="Update Phone" /> 
	</form>
	 	
 	<form action="/lab2/user/${user.userId}">
 		<input type="hidden" name="method" value="delete">
 		<input type="submit" value="Delete Phone" />
 	</form>
</body>

<script>
$(document).ready(function(){
	$("#button").click(function(e){
		e.preventDefault();
		$(".wrapper").append('<div>Enter User ID<input type = "text" name = "ids"><a href = "#" class = "remove" >Remove</a></div>');
	});
	
	$(".wrapper").on("click",".remove", function(e){ 
        e.preventDefault(); 
        $(this).parent('div').remove(); 
    })
    
    $(".outerRemove").on("click",function(e){
    	e.preventDefault();
    	$(this).parent('div.outerWrapper').remove(); 
    });
});
</script>
</html> --%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
<title>
 Group - 28 update delete phone
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
</head>
<body>

<form action = "/phone/update" >

Phone Id : <input type="text" name = "phoneId" value = "${phone.phoneId}" readonly /> <br><br>
 Number : <input type="text" name = "number" value = "${phone.number}"  /> <br><br>
 Description : <input type="text" name = "description" value = "${phone.description}" /> <br><br>
 Street : <input type="text" name = "street" value = "${phone.address.street}" /> <br><br>
 State : <input type="text" name = "state" value = "${phone.address.state}" /> <br><br>
 City : <input type="text" name = "city" value = "${phone.address.city}" /> <br><br>
 Zip : <input type="text" name = "zip" value = "${phone.address.zip}" /> <br><br>
 
 
 Users:
 	<c:forEach items = "${phone.users}" var = "user">
 		<div class = "outerWrapper">
 			User Id : <input type = "text" name = "us" value = "${user.userId}" readonly>	&nbsp;	&nbsp;	&nbsp;	&nbsp;
  			Firstname : <c:out value = "${user.firstname}" /> 	&nbsp;	&nbsp;	&nbsp;	&nbsp;
  			Lastname : <c:out value = "${user.lastname}" />		&nbsp;	&nbsp;	&nbsp;	&nbsp;
  			Title : <c:out value = "${user.title}" />			&nbsp;	&nbsp;	&nbsp;	&nbsp;
  			<a href = "#" class = "outerRemove">Remove</a>
  		</div> 
 	</c:forEach>
 <br>
 <div class = "wrapper">
	<input type = "button" value = "Add User" id = "button" />
</div>


<br><br><br>
 
 <input type="submit" value = "Update Phone" name = "update" />

</form>


<form action="/user/${phone.phoneId}">
	<input type="submit" value = "Delete Phone" name = "delete"/>
</form>

<br><br>



</body>
<script>
$(document).ready(function(){
	$("#button").click(function(e){
		e.preventDefault();
		$(".wrapper").append('<div>Enter User ID<input type = "text" name = "us" ><a href = "#" class = "remove" >Remove</a></div>');
	});
	
	$(".wrapper").on("click",".remove", function(e){ 
        e.preventDefault(); 
        $(this).parent('div').remove(); 
    })
    
    $(".outerRemove").on("click",function(e){
    	e.preventDefault();
    	$(this).parent('div.outerWrapper').remove(); 
    });
});
</script>
</html> 