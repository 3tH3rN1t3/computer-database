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
			<h1 id="homeTitle">
				<c:choose>
					<c:when test="${page.search == null || page.search.isEmpty()}">
						<c:out value="${page.totalItems}" /> <spring:message code="text.found" />
					</c:when>
					<c:otherwise>
						<c:out value="${page.totalItems}" /> <spring:message code="text.found" /> <spring:message code="text.for" /> <spring:message code="label.${page.searchBy.toString().toLowerCase()}" /> &laquo<c:out value="${page.search}" />&raquo
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
							<select class="form-control" id="searchby" name="searchby">
								<c:forEach var = "search" items = "${searches}">
									<c:choose>
										<c:when test="${page.searchBy eq search}">
											<option value="${search.toString()}" selected><spring:message code="label.${search.toString().toLowerCase()}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${search.toString()}"><spring:message code="label.${search.toString().toLowerCase()}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							<span class="input-group-addon" style="border-width: 1px; padding: 0px;"></span>
							<input type="submit" id="searchsubmit" value="${searchText}" class="form-control btn btn-primary" />
						</div>
						
					</form>
				</div>
				<div class="btn-group pull-right" role="group">
					<a class="btn btn-success" id="addComputer" href="editComputer"><spring:message code="text.addComputer" /></a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="text.edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
		
			<div class="pull-left input-group">
				<a href="?includeNull=${!page.includeNull}" class="form-control">
					 <input type="checkbox" ${page.includeNull ? 'checked' : ''} />
				</a>
				<span class="input-group-addon" style="border-width: 0px; width: 0px; padding: 0px;">
				</span>
				<a href="?includeNull=${!page.includeNull}" class="form-control">
					 <spring:message code="text.includeNull" />
				</a>
			</div>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th class="editMode" style="width: 60px; height: 22px;" hidden="true">
							<div class="input-group">
								<span class="input-group-addon">
									<input type="checkbox" id="selectall">
								</span>
								<a href="" id="deleteSelected" class=" form-control" onclick="$.fn.deleteSelected();">
									<i class="fa fa-trash-o fa-lg"></i>
								</a>
							</div>
						</th>
						<th style="width: 35%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderby=${page.orderBy.column ne 'computer.name' or page.order eq 'ASC' ? 'name' : 'id'}&order=${page.orderBy.column eq 'computer.name' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.name" />
								<c:if test="${page.orderBy.column eq 'computer.name'}">
									<spring:message code="text.${page.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 15%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderby=${page.orderBy.column ne 'introduced' or page.order eq 'ASC' ? 'introduced' : 'id'}&order=${page.orderBy.column eq 'introduced' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.introduced" />
								<c:if test="${page.orderBy.column eq 'introduced'}">
									<spring:message code="text.${page.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 15%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderby=${page.orderBy.column ne 'discontinued' or page.order eq 'ASC' ? 'discontinued' : 'id'}&order=${page.orderBy.column eq 'discontinued' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.discontinued" />
								<c:if test="${page.orderBy.column eq 'discontinued'}">
									<spring:message code="text.${page.order.toLowerCase()}" />
								</c:if>
							</a>
						</th>
						<th style="width: 35%; text-align: center; vertical-align: middle;">
							<a href="dashboard?orderby=${page.orderBy.column ne 'company.name' or page.order eq 'ASC' ? 'company' : 'id'}&order=${page.orderBy.column eq 'company.name' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">
								<spring:message code="label.company" />
								<c:if test="${page.orderBy.column eq 'company.name'}">
									<spring:message code="text.${page.order.toLowerCase()}" />
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
								<input type="checkbox" name="cb" class="cb" value="${computer.id}">
							</td>
							<td>
								<a href="editComputer?id=${computer.id}#" onclick="">${computer.name}</a>
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
					<a href="?page=1" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				
				<li class="${page.numPage-1 > 0 ? '' : 'disabled'}">
					<a href="?page=${page.numPage-1}">&lt;</a>
				</li>
				
				<li>
					<c:if test="${page.numPage-2 > 0}" var="variable">
						<a href="?page=${page.numPage-2}">${page.numPage-2}</a>
					</c:if>
				</li>
				
				<li>
					<c:if test="${page.numPage-1 > 0}" var="variable">
						<a href="?page=${page.numPage-1}">${page.numPage-1}</a>
					</c:if>
				</li>
				
				<li class="active"><a href="?page=${page.numPage}"> ..  </a></li>
				
				<li>
					<c:if test="${page.numPage+1 <= page.getMaxPage()}" var="variable">
						<a href="?page=${page.numPage+1}">${page.numPage+1}</a>
					</c:if>
				</li>
				
				<li>
					<c:if test="${page.numPage+2 <= page.getMaxPage()}" var="variable">
						<a href="?page=${page.numPage+2}">${page.numPage+2}</a>
					</c:if>
				</li>
				
				<li class="${page.numPage+1 <= page.getMaxPage() ? '' : 'disabled'}">
					<a href="?page=${page.numPage+1}">&gt;</a>
				</li>
				
				<li>
					<a href="?page=${page.getMaxPage()}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>

			<div class="pull-right" >
				<form id="nombreElementPage" action="#" method="GET" class="navbar-form" >
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
		</div>
	</footer>
    
	<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/dashboard.js" />"></script>
	<script src="<c:url value="/resources/js/lang.js" />"></script>


</body>
</html>