<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.title" /></title>
<meta charset="UTF-8">
<!-- Bootstrap -->

<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><spring:message code="text.navbar" /></a>
		</div>
	</header>
	
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${computer.id ne '0'}">
						<div class="label label-default pull-right">
							id: <c:out value="${computer.id}" />
						</div>
					</c:if>
					<h1>LOGIN</h1>
					<form action="login" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="username"><spring:message code="label.name" /></label>
								<form:input type="text" class="form-control" name="username" id="username" placeholder="username" required="required" />
							</div>
							<div class="form-group">
								<label for="password"><spring:message code="label.name" /></label>
								<form:input type="text" class="form-control" name="password" id="password" placeholder="password" required="required" />
							</div>
						</fieldset>
						<div class="input-group">
							<input name="submit" type="submit" value="submit" class="form-control btn btn-primary" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<c:if test="${not empty errorMessge}">
		<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessge}</div>
	</c:if>
    
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/dashboard.js" />"></script>

</body>
</html>