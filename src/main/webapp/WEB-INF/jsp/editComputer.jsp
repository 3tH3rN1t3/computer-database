<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@   taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
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
					<c:if test="${computer ne null and computer.id ne '0'}">
						<div class="label label-default pull-right">
							id: <c:out value="${computer.id}" />
						</div>
					</c:if>
					<h1><c:out value="${operation}" /> Computer</h1>
					<form action="editComputer" method="POST">
						<c:choose>
							<c:when test="${computer ne null}">
								<input type="hidden" value="${computer.id}" id="id" name="id"/>
							</c:when>
							<c:otherwise>
								<input type="hidden" value=0 id="id" name="id"/>
							</c:otherwise>
						</c:choose>
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name (required)</label>
								<c:if test="${errors ne null and errors.name}">
									<br/>
									Le nom doit être renseigné
								</c:if>
								<c:choose>
									<c:when test="${computer ne null}">
										<input type="text" class="form-control" id="computerName" name="computerName" value="${computer.name}" required="required" >
									</c:when>
									<c:otherwise>
										<input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" required="required" >
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label>
								<c:if test="${errors ne null and errors.introduced}">
									<br/>
									La date doit être comprise entre le 01/01/1970 et le 19/01/2038 inclus
								</c:if>
								<c:if test="${errors ne null and errors.interval}">
									<br/>
									Les dates d'ajout est antérieure à la date de retrait
								</c:if>
								<c:choose>
									<c:when test="${computer ne null and computer.introduced.isPresent()}">
										<input type="date" class="form-control" id="introduced" name="introduced" value="${computer.introduced.get().toString()}">
									</c:when>
									<c:otherwise>
										<input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label>
								<c:if test="${errors ne null and errors.discontinued}">
									<br/>
									La date doit être comprise entre le 01/01/1970 et le 19/01/2038 inclus
								</c:if>
								<c:if test="${errors ne null and errors.interval}">
									<br/>
									Les dates d'ajout est antérieure à la date de retrait
								</c:if>
								<c:choose>
									<c:when test="${computer ne null and computer.discontinued.isPresent()}">
										<input type="date" class="form-control" id="discontinued" name="discontinued" value="${computer.discontinued.get().toString()}">
									</c:when>
									<c:otherwise>
										<input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
									</c:otherwise>
								</c:choose>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<c:if test="${errors ne null and errors.company}">
									<br/>
									La companie choisie n'existe pas
								</c:if>
								<select class="form-control" id="companyId" name="companyId" >
									<option value="">--</option>
									<c:forEach var = "company" items = "${companies}">
										<c:choose>
											<c:when test="${computer ne null and computer.company.isPresent() and company.id eq computer.company.get().id}">
												<option value="${company.id}" selected>${company.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${company.id}">${company.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
						 		</select>
							</div>				  
						</fieldset>
						<div class="actions pull-right">
							<input  type="submit" value="${operation}" class="btn btn-primary">
							or
							<a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
    
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/editComputer.js"></script>

</body>
</html>
