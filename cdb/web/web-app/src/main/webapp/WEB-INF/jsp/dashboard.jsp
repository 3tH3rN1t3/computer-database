<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			<a class="navbar-brand" href="dashboard"><spring:message code="text.navbar" /></a>
			<div class="btn-group pull-right">
				<form class="navbar-form navbar-right" action="logout" method="get">
					<input type="submit" class="btn btn-default" value="Logout" />
				</form>
				<button type="button" class="btn btn-primary navbar-btn dropdown-toggle" style="background-color: black; border-color: black;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<spring:message code="label.lang" /> <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<c:forEach var="language" items="${languages}" >
						<c:choose>
							<c:when test="${pageContext.response.locale eq language.toString().toLowerCase()}">
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
			<h1 id="homeTitle">
				<c:choose>
					<c:when test="${session.search == null || session.search.isEmpty()}">
						<c:out value="${session.totalItems}" /> <spring:message code="text.found" />
					</c:when>
					<c:otherwise>
						<c:out value="${session.totalItems}" /> <spring:message code="text.found" /> <spring:message code="text.for" /> <spring:message code="label.${session.searchBy.toString().toLowerCase()}" /> &laquo<c:out value="${session.search}" />&raquo
					</c:otherwise>
				</c:choose>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<spring:message code="text.search" var="searchText" />
						<div class="input-group">
							<input type="search" id="searchbox" name="search" class="form-control" placeholder="${searchText}" />
							<span class="input-group-addon"> <spring:message code="text.by" /> </span>
							<select class="form-control" id="searchBy" name="searchBy">
								<c:forEach var = "search" items = "${searches}">
									<c:choose>
										<c:when test="${session.searchBy eq search}">
											<option value="${search.toString()}" selected><spring:message code="label.${search.toString().toLowerCase()}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${search.toString()}"><spring:message code="label.${search.toString().toLowerCase()}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<span class="input-group-addon" style="border-width: 0px; width: 0px; padding: 0px;"></span>
							<input type="submit" id="searchsubmit" value="${searchText}" class="form-control btn btn-primary" />
						</div>
						
					</form>
				</div>
				<c:if test="${admin}" >
					<div class="btn-group pull-right" role="group">
						<a class="btn btn-success" id="addComputer" href="editComputer"><spring:message code="text.addComputer" /></a>
						<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="text.edit" /></a>
					</div>
				</c:if>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th class="editMode" style="width: 60px; height: 22px;" hidden="true">
							<div class="input-group">
								<span class="input-group-addon">
									<input type="checkbox" id="selectall">
								</span>
								<button class="form-control" type="submit" form="deleteForm" value="Submit"><i class="fa fa-trash-o fa-lg"></i></button>
							</div>
						</th>
						<th style="width: 35%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderBy=${session.orderBy.toString() ne 'NAME' or session.order eq 'ASC' ? 'name' : 'id'}&order=${session.orderBy.toString() eq 'NAME' and session.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.name" />
								<c:if test="${session.orderBy.toString() eq 'NAME'}">
									<spring:message code="text.${session.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 15%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderBy=${session.orderBy.toString() ne 'INTRODUCED' or session.order eq 'ASC' ? 'introduced' : 'id'}&order=${session.orderBy.toString() eq 'INTRODUCED' and session.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.introduced" />
								<c:if test="${session.orderBy.toString() eq 'INTRODUCED'}">
									<spring:message code="text.${session.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 15%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderBy=${session.orderBy.toString() ne 'DISCONTINUED' or session.order eq 'ASC' ? 'discontinued' : 'id'}&order=${session.orderBy.toString() eq 'DISCONTINUED' and session.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.discontinued" />
								<c:if test="${session.orderBy.toString() eq 'DISCONTINUED'}">
									<spring:message code="text.${session.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 35%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderBy=${session.orderBy.toString() ne 'COMPANY' or session.order eq 'ASC' ? 'company' : 'id'}&order=${session.orderBy.toString() eq 'COMPANY' and session.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.company" />
								<c:if test="${session.orderBy.toString() eq 'COMPANY'}">
									<spring:message code="text.${session.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var = "computer" items = "${computers}">
						<tr>
							<td class="editMode" style="text-align: center; vertical-align: middle;" hidden="true">
								<input type="checkbox" name="cb" class="cb" form="deleteForm" value="${computer.id}">
							</td>
							<td>
								<c:choose>
									<c:when test="${admin}" >
										<a href="editComputer?id=${computer.id}#" onclick="">${computer.name}</a>
									</c:when>
									<c:otherwise>
										${computer.name}
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${computer.introduced ne null}" var="variable">
									${computer.introduced}
								</c:if>
							</td>
							<td>
								<c:if test="${computer.discontinued ne null}" var="variable">
									${computer.discontinued}
								</c:if>
							</td>
							<td>
								<c:if test="${computer.companyId ne null}" var="variable">
									${computer.companyName}
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li>
					<a href="?pageNum=1" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				
				<li class="${session.numPage-1 > 0 ? '' : 'disabled'}">
					<a href="?pageNum=${session.numPage-1}">&lt;</a>
				</li>
				
				<li>
					<c:if test="${session.numPage-2 > 0}" var="variable">
						<a href="?pageNum=${session.numPage-2}">${session.numPage-2}</a>
					</c:if>
				</li>
				
				<li>
					<c:if test="${session.numPage-1 > 0}" var="variable">
						<a href="?pageNum=${session.numPage-1}">${session.numPage-1}</a>
					</c:if>
				</li>
				
				<li class="active"><a href="?pageNum=${session.numPage}"> ..  </a></li>
				
				<li>
					<c:if test="${session.numPage+1 <= session.getMaxPage()}" var="variable">
						<a href="?pageNum=${session.numPage+1}">${session.numPage+1}</a>
					</c:if>
				</li>
				
				<li>
					<c:if test="${session.numPage+2 <= session.getMaxPage()}" var="variable">
						<a href="?pageNum=${session.numPage+2}">${session.numPage+2}</a>
					</c:if>
				</li>
				
				<li class="${session.numPage+1 <= session.getMaxPage() ? '' : 'disabled'}">
					<a href="?pageNum=${session.numPage+1}">&gt;</a>
				</li>
				
				<li>
					<a href="?pageNum=${session.getMaxPage()}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>
			<form id="nombreElementPage" action="#" method="GET" class="navbar-form navbar-right" >
				<div class="form-group" role="group" >
					<div class="input-group">
						<span class="input-group-addon"> <spring:message code="text.itemsPerPage"/> </span>
						<a href="?itemsPerPage=10" class="form-control">10</a>
						<span class="input-group-addon" style="border-width: 0px; width: 0px; padding: 0px;"></span>
						<a href="?itemsPerPage=50" class="form-control">50</a>
						<span class="input-group-addon" style="border-width: 0px; width: 0px; padding: 0px;"></span>
						<a href="?itemsPerPage=100" class="form-control">100</a>
					</div>
				</div>
			</form>
		</div>
	</footer>
    
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/dashboard.js" />"></script>


</body>
</html>