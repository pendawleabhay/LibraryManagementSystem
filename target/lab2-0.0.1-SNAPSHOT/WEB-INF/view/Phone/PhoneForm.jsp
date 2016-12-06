 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><script src="http://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
	<title>
 Group - 28 create phone
</title>
</head>
<script>

</script>
<body>
	<h1>Create Phone</h1>
 
	<form action="/phone/phoneId" >
	   number <input type="text" name="number" /><br /> <br /> 
	   description <input type="text" name="description" /><br /> <br /> 
	   street <input type="text" name="street" /><br /><br />  
	   city <input type="text" name="city" /><br /> <br /> 
	   state <input type="text" name="state" /><br /> <br /> 
	   zip <input type="text" name="zip" /><br /> <br /> 

	<div class = "wrapper">
		
		Enter User ID<input type = "text" name = "users[]" />
		<input type = "button" value = "Add" id = "button" />
	</div>
		
	<br>
	
	<input type="submit" value="Create" />
</body>


	<script>
		$(document).ready(function(){
			$("#button").click(function(e){
				e.preventDefault();
				$(".wrapper").append('<div>Enter User ID<input type = "text" name = "users[]"><a href = "#" class = "remove" >Remove</a></div>');
			});
			
			$(".wrapper").on("click",".remove", function(e){ 
		        e.preventDefault(); 
		        $(this).parent('div').remove(); 
		    })
		});
	</script>
	

</html>