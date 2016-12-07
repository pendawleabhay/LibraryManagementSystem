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


.widget-products li>a:after {
    content: "\f138";
    font-family: FontAwesome;
    font-size: 0.875em;
    font-style: normal;
    font-weight: normal;
    margin-top: 2px;
    position: absolute;
    right: 10px;
    text-decoration: inherit;
    top: 0;
    color: #cccccc;
    font-size: 1.3em;
}
.btn-success {
    background-color: #2ecc71;
    border-color: #27ae60;
}
.btn {
    border: none;
    padding: 6px 12px;
    border-bottom: 4px solid;
    -webkit-transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
    transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
    outline: none;
}
.checkbox-nice label {
    padding-top: 3px;
}
label {
    font-weight: 400;
    font-size: 0.875em;
}
.checkbox-nice input[type=checkbox] {
    visibility: hidden;
}
.checkbox-nice {
    position: relative;
    padding-left: 15px;
}
.widget-todo .name {
    float: left;
}
.widget-todo>li {
    border-bottom: 1px solid #ebebeb;
    padding: 10px 5px;
}
.widget-todo {
    list-style: none;
    margin: 0;
    padding: 0;
}
.widget-products li .product>.warranty>i {
    color: #f1c40f;
}
.widget-products li .product>.warranty {
    display: block;
    text-decoration: none;
    width: 50%;
    float: left;
    font-size: 0.875em;
}
.widget-products li .product>.price>i {
    color: #2ecc71;
}
.widget-products li .product>.price {
    display: block;
    text-decoration: none;
    width: 50%;
    float: left;
    font-size: 0.875em;
}
.widget-products li .product>.name {
    display: block;
    font-weight: 600;
    padding-bottom: 7px;
}
.widget-products li .product {
    display: block;
    margin-left: 90px;
    margin-top: 19px;
}
.widget-products li .img {
    display: block;
    float: left;
    text-align: center;
    width: 70px;
    height: 68px;
    overflow: hidden;
    margin-top: 7px;
}
.widget-products li>a {
    height: 88px;
    display: block;
    width: 100%;
    color: #344644;
    padding: 3px 10px;
    position: relative;
    -webkit-transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
    transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
}
.widget-products li {
    border-bottom: 1px solid #ebebeb;
}
.widget-products {
    list-style: none;
    margin: 0;
    padding: 0;
}
.widget-users li {
    border-bottom: 1px solid #ebebeb;
    padding: 15px 0;
    height: 96px;
}
.label {
    border-radius: 3px;
    font-size: 0.875em;
    font-weight: 600;
}
.widget-users li>.details>.time {
    color: #3498db;
    font-size: 0.75em;
    padding-bottom: 7px;
}
.widget-users li>.details>.name>a {
    color: #344644;
}
.widget-users li>.details>.name {
    font-weight: 600;
}
.widget-users li>.details {
    margin-left: 60px;
}
.widget-users li>.img {
    float: left;
    margin-top: 8px;
    width: 50px;
    height: 50px;
    overflow: hidden;
    border-radius: 50%;
}
.widget-users {
    list-style: none;
    margin: 0;
    padding: 0;
}
.tabs-wrapper.tabs-no-header .tab-content {
    padding: 0 20px 20px;
}
.nav-tabs>li>a {
    border-radius: 0;
    font-size: 1.125em;
    font-weight: 300;
    outline: none;
    color: #555;
    margin-right: 3px;
}
.nav>li {
    float: left;
}
.tabs-wrapper .nav-tabs {
    margin-bottom: 15px;
}
.nav-tabs {
    background: #d0d8de;
    border-color: transparent;
    -moz-border-radius: 3px 3px 0 0;
    -webkit-border-radius: 3px 3px 0 0;
    border-radius: 3px 3px 0 0;
}
.main-box {
    background: #FFFFFF;
    -webkit-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -moz-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -o-box-shadow: 1px 1px 2px 0 #CCCCCC;
    -ms-box-shadow: 1px 1px 2px 0 #CCCCCC;
    box-shadow: 1px 1px 2px 0 #CCCCCC;
    margin-bottom: 16px;
    -webikt-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}

</style>


<title>Book | Search</title>
</head>
<body>

	
	<c:if test="${bookList!=null}">		
		<!-- For Librarian -->
		<c:if test="${user.userType == 'librarian'}">
			<%@ include file="NavBarLib.jsp" %>
			<c:forEach items="${bookList}" var="book">
				<div class="col-md-9">
			<ul class="widget-products">
                  <li>
                     
                     <span class="img">
                     </span>
                     <span class="product clearfix">
                     <span class="name"><b>Book Title:</b>
                     <i class="fa fa-money"></i>
                     ${book.title}
                     </span>
                     <span class="price"><b>Author:</b>
                     
                     <i class="fa fa-money"></i>${book.author}
                     </span>
                     
                     <span class="price"><b>Number Of Copies:</b>
                     
                     <i class="fa fa-money"></i>${book.number_of_copies}
                     </span>
                     
                     <span class="price"><b>Publisher:</b>
                     
                     <i class="fa fa-money"></i>${book.publisher}
                     </span>
                     <span class="price"><b>Year of Publication:</b>
                     
                     <i class="fa fa-money"></i>${book.year_of_publication}
                     </span>
                     
                     </span>
                     
            
	    		<div>
	    			<!-- Form for submitting update -->
	    			<form action = "/lab2/book/update" method="POST" class="product clearfix name">
	    				<input type="hidden" name="bookid" value="${book.bookid }"/>
	    				<input class="btn btn-success" type="submit" value="Update"/>
					</form>
						
					<!-- Form for submitting delete -->
					<form action = "/lab2/book/delete" method="POST" class="product clearfix name">
						<input type="hidden" name="bookid" value="${book.bookid}"/>
						<input class="btn btn-danger" type="submit" value="Update"/>
					</form>
	    		</div>
	    		</li>
	    		</ul>
	    		</div>
	    		<br>
			</c:forEach>
		</c:if>
		
		
		<!-- For Patron -->
		<c:if test="${user.userType == 'patron'}">
			<%@ include file="NavBarPat.jsp" %>
			<form action = "/lab2/issue/addToCart" method="POST">
				<c:forEach items="${bookList}" var="book">
					
					
					<div class="col-md-9">
			<ul class="widget-products">
                  <li>
                     <input type="checkbox" name="bookIssue" value="${book.bookid}"/>
                     <span class="img">
                     </span>
                     <span class="product clearfix">
                     <span class="name"><b>Book Title:</b>
                     <i class="fa fa-money"></i>
                     ${book.title}
                     </span>
                     <span class="price"><b>Author:</b>
                     
                     <i class="fa fa-money"></i>${book.author}
                     </span>
                     
                     <span class="price"><b>Number Of Copies:</b>
                     
                     <i class="fa fa-money"></i>${book.number_of_copies}
                     </span>
                     
                     <span class="price"><b>Publisher:</b>
                     
                     <i class="fa fa-money"></i>${book.publisher}
                     </span>
                     <span class="price"><b>Year of Publication:</b>
                     
                     <i class="fa fa-money"></i>${book.year_of_publication}
                     </span>
                     
                     </span>
            
		    		<br>
		    		</li>
		    		</ul>
		    		</div>
		    		
				</c:forEach>
				
				<input class="product clearfix name btn btn-success" type="submit" value="Add To Cart"/>
				
				
			</form>
			
		</c:if>
	</c:if>
	
	<c:if test="${message!=null}">
		<div>${message}</div>
	</c:if>
</body>
</html>