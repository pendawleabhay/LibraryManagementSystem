<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<%@ include file="NavBarUser.jsp" %>
</head>

<body>

<c:choose>
		<c:when test="${bookList != null}">
			<c:forEach var = "index" begin = "0" end = "${size-1}">
			
			
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
							<h4 class="product-name"><strong>Book Name: ${dueDate}</strong></h4><h4><small>Book Description</small></h4>
						</div>
						<div class="col-xs-6">
							<div class="col-xs-6 text-right">
								<h6><strong>Book Author: ${bookList[index].author}<span class="text-muted">x</span></strong></h6>
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
		
		<c:when test="${message != null}">
			<h3>${message}</h3>
		</c:when>
	</c:choose>
	



</body>
</html>