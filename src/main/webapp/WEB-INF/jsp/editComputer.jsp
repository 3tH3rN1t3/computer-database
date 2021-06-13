<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="text.title" /></title>
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
			<a class="navbar-brand" href="dashboard"> <spring:message code="text.navbar" /> </a>
			<div class="btn-group pull-right">
				<button type="button" class="btn btn-primary navbar-btn dropdown-toggle" style="background-color: black; border-color: black;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<spring:message code="label.lang" /> <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<c:forEach var="language" items="${languages}" >
						<c:choose>
							<c:when test="${lang.getLang() eq language.getLang()}">
								<li class="disabled"><a href="?lang=${language.toString()}">${language.getLang()}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="?lang=${language.toString()}">${language.getLang()}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
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
					<h1><spring:message code="${'text.'.concat(operation).concat('Computer')}" /></h1>
					<form:form action="editComputer" method="POST" modelAttribute="computer">
						<form:input type="hidden" path="id"/>
						<fieldset>
							<div class="form-group">
								<form:label path="name"><spring:message code="label.name" /> (<spring:message code="text.required" />)</form:label>
								<c:if test="${errors.hasFieldErrors('name')}">
									<div class="alert alert-danger">
										<spring:message code="errors.name" />
									</div>
								</c:if>
								<form:input type="text" class="form-control" path="name" placeholder="Computer name" required="required" />
							</div>
							
							<div class="form-group">
								<form:label path="introduced"><spring:message code="label.introduced" /></form:label>
								<c:if test="${errors.hasFieldErrors('introduced')}">
									<div class="alert alert-danger">
										<spring:message code="errors.name" />
									</div>
								</c:if>
								<form:input type="date" class="form-control" path="introduced" />
							</div>
							
							<div class="form-group">
								<form:label path="discontinued"><spring:message code="label.discontinued" /></form:label>
								<c:forEach var="error" items="${errors.getFieldErrors('discontinued')}" >
									<div class="alert alert-danger">
										<spring:message code="${'errors.'.concat(error.getDefaultMessage())}" />
									</div>
								</c:forEach>
								<form:input type="date" class="form-control" path="discontinued" />
							</div>
							
							<div class="form-group">
								<form:label path="companyId"><spring:message code="label.company" /></form:label>
								<c:if test="${errors.hasFieldErrors('company')}">
									<div class="alert alert-danger">
										<spring:message code="errors.company" />
									</div>
								</c:if>
								<form:select class="form-control" path="companyId" >
									<form:option value="">--</form:option>
									<c:forEach var="company" items="${companies}">
										<form:option value="${company.id}">${company.name}</form:option>
									</c:forEach>
						 		</form:select>
							</div>				  
						</fieldset>
						<div class="actions pull-right">
							<spring:message code="${'text.'.concat(operation)}" var="operationText" />
							<input  type="submit" value="${operationText}" class="btn btn-primary" />
							<spring:message code="text.or" />
							<a href="dashboard" class="btn btn-default"><spring:message code="text.cancel" /></a>
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
