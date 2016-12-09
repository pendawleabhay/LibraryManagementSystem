<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<%@ include file="NavBarUser.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
</head>

<body>
	<c:choose>
		<c:when test="${bookList != null}">
			<c:forEach items="${bookList}" var="book">
			
<div class="container">
	<div class="row">
		<div class="col-xs-8">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<div class="row">
							<div class="col-xs-6">
								
								<h5><span class="glyphicon glyphicon-shopping-cart"></span>Your Issued Book</h5>
							</div>
							<div class="col-xs-6">
								
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-2"><img class="img-responsive" src="http://placehold.it/100x70">
						</div>
						<div class="col-xs-4">
							<h4 class="product-name"><strong>Book Name: ${book.title}</strong></h4><h4><small>Book Issue Date: ${dueDate}</small></h4>
							
						</div>
						<div class="col-xs-6">
							<div class="col-xs-6 text-right">
								<h6><strong>Book Author: <span class="text-muted">${book.author}</span></strong></h6>
							</div>
							
							</div>
					</div>
					<hr>
					
					<hr>
					
				
			</div>
		</div>
	</div>
</div>
</c:forEach>
		
		</c:when>
		</c:choose>
		
		<c:if test="${message != null}">
			<h3>${message}</h3>
		</c:if>
	



</body>
</html>