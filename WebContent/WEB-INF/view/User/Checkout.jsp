<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<%@ include file="NavBarUser.jsp" %>
</head>

<body>
<div class="container">
	<div class="row">
		<div class="col-xs-8">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<div class="row">
							<div class="col-xs-6">
								
								<h5>${dueDate}<span class="glyphicon glyphicon-shopping-cart"></span> Books Cart</h5>
							</div>
							<div class="col-xs-6">
								<form action = "/lab2/"></form>
								<button type="submit" class="btn btn-primary btn-sm btn-block">
									<span class="glyphicon glyphicon-share-alt"></span> Add More Books
								</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-2"><img class="img-responsive" src="http://placehold.it/100x70">
						</div>
						<div class="col-xs-4">
							<h4 class="product-name"><strong>Book Name:</strong></h4><h4><small>Book Description</small></h4>
						</div>
						<div class="col-xs-6">
							<div class="col-xs-6 text-right">
								<h6><strong>Book Author<span class="text-muted">x</span></strong></h6>
							</div>
							<!-- <div class="col-xs-4">
								<input type="text" class="form-control input-sm" value="1">
							</div> -->
							<div class="col-xs-2">
								<button type="button" class="btn btn-link btn-xs">
									<span class="glyphicon glyphicon-trash"> </span>
								</button>
							</div>
						</div>
					</div>
					<hr>
					
					<hr>
					<div class="row">
						<div class="text-center">
							<div class="col-xs-9">
								<h6 class="text-right">Books in Your Cart</h6>
							</div>
							<div class="col-xs-3">
								<!-- <button type="button" class="btn btn-default btn-sm btn-block">
									Update cart
								</button> -->
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer">
					<div class="row text-center">
						<div class="col-xs-9">
							<h4 class="text-right"><strong></strong></h4>
						</div>
						<div class="col-xs-3">
							
							<button type="button" class="btn btn-success btn-block">
								Checkout
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>