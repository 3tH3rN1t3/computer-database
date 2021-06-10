<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@   taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->

<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>

	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${computer.id ne '0'}">
						<div class="form:label form:label-default pull-right">
							id: <c:out value="${computer.id}" />
						</div>
					</c:if>
					<h1><c:out value="${operation}" /> Computer</h1>
					<form:form action="editComputer" method="POST" modelAttribute="computer">
						<form:input type="hidden" path="id"/>
						<fieldset>
							<div class="form-group">
								<form:label path="name">Computer name (required)</form:label>
								<c:forEach var="error" items="${errors}">
									<c:if test="${error.field eq 'name'}">
										<br/>
										<c:out value="${error.defaultMessage}" />
									</c:if>
								</c:forEach>
								<form:input type="text" class="form-control" path="name" placeholder="Computer name" required="required" />
							</div>
							
							<div class="form-group">
								<form:label path="introduced">Introduced date</form:label>
								<c:forEach var="error" items="${errors}">
									<c:if test="${error.field eq 'introduced'}">
										<br/>
										<c:out value="${error.defaultMessage}" />
									</c:if>
								</c:forEach>
								<form:input type="date" class="form-control" path="introduced" />
							</div>
							
							<div class="form-group">
								<form:label path="discontinued">Discontinued date</form:label>
								<c:forEach var="error" items="${errors}">
									<c:if test="${error.field eq 'discontinued'}">
										<br/>
										<c:out value="${error.defaultMessage}" />
									</c:if>
								</c:forEach>
								<form:input type="date" class="form-control" path="discontinued" />
							</div>
							
							<div class="form-group">
								<form:label path="companyId">Company</form:label>
								<c:forEach var="error" items="${errors}">
									<c:if test="${error.field eq 'companyId'}">
										<br/>
										<c:out value="${error.defaultMessage}" />
									</c:if>
								</c:forEach>
								<form:select class="form-control" path="companyId" >
									<form:option value="">--</form:option>
									<c:forEach var="company" items = "${companies}">
										<form:option value="${company.id}">${company.name}</form:option>
									</c:forEach>
						 		</form:select>
							</div>				  
						</fieldset>
						<div class="actions pull-right">
							<input  type="submit" value="${operation}" class="btn btn-primary" />
							or
							<a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
    
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/editComputer.js" />"></script>

</body>
</html>
