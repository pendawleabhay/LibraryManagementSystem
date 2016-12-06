 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
	<head>
		<title>
 Group - 28 update delete phone
</title>
	</head>
	User ID <input type="text" name="userId" value=${user.userId} readonly/><br /> <br />
	<form action="/user/${user.userId}" >
	    <input type="hidden" name="method" value="update">
	   firstname <input type="text" name="firstname" value=${user.firstname} /><br /> <br /> 
	   lastname <input type="text" name="lastname" value=${user.lastname} /><br /> <br /> 
	   title <input type="text" name="title" value=${user.title} /><br /> <br /> 
	   street <input type="text" name="street" value=${user.address.street} /><br /><br />  
	   city <input type="text" name="city" value=${user.address.city} /><br /> <br /> 
	   state <input type="text" name="state" value=${user.address.state} /><br /> <br /> 
	   zip <input type="text" name="zip" value=${user.address.zip} /><br /> <br /> 
	   Phone <br />
		<c:forEach items = "${user.phones}" var = "phone">
			Phone Id : <c:out value = "${phone.phoneId}" />
	   		Number : <c:out value = "${phone.number}" />
	   		Description : <c:out value = "${phone.description}" /><br />
	 	</c:forEach>
		<input type="submit" value="Update User" />
	</form>
	 	
 	<form action="/user/${user.userId}">
 		<input type="hidden" name="method" value="delete">
 		<input type="submit" value="Delete User" />
 	</form>
</html>