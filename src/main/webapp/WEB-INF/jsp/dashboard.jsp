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
			<a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:choose>
					<c:when test="${page.search == null || page.search.isEmpty()}">
						<c:out value="${page.totalItems}" /> Computers found
					</c:when>
					<c:otherwise>
						<c:out value="${page.totalItems}" /> Computers found for &laquo<c:out value="${page.search}" />&raquo
					</c:otherwise>
				</c:choose>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="editComputer">Add Computer</a> 
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;">
							<input type="checkbox" id="selectall" /> 
							<span style="vertical-align: top;">
								<a href="" id="deleteSelected" onclick="$.fn.deleteSelected();">
									<i class="fa fa-trash-o fa-lg"></i>
								</a>
							</span>
						</th>
						<th>
							<a href="dashboard?orderby=${page.orderBy.column ne 'computer.name' or page.order eq 'ASC' ? 'name' : 'id'}&order=${page.orderBy.column eq 'computer.name' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">Computer name</a>
						</th>
						<th>
							<a href="dashboard?orderby=${page.orderBy.column ne 'introduced' or page.order eq 'ASC' ? 'introduced' : 'id'}&order=${page.orderBy.column eq 'introduced' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">Introduced date</a>
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
							<a href="dashboard?orderby=${page.orderBy.column ne 'discontinued' or page.order eq 'ASC' ? 'discontinued' : 'id'}&order=${page.orderBy.column eq 'discontinued' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">Discontinued date</a>
						</th>
						<!-- Table header for Company -->
						<th>
							<a href="dashboard?orderby=${page.orderBy.column ne 'company.name' or page.order eq 'ASC' ? 'company' : 'id'}&order=${page.orderBy.column eq 'company.name' and page.order eq 'ASC' ? 'DESC' : 'ASC'}" onclick="">Company</a>
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var = "computer" items = "${computers}">
						<tr>
							<td class="editMode">
								<input type="checkbox" name="cb" class="cb" value="${computer.id}">
							</td>
							<td>
								<a href="editComputer?id=${computer.id}#" onclick="">${computer.name}</a>
							</td>
							<td>
								<c:if test="${computer.introduced.isPresent()}" var="variable">
									${computer.introduced.get()}
								</c:if>
							</td>
							<td>
								<c:if test="${computer.discontinued.isPresent()}" var="variable">
									${computer.discontinued.get()}
								</c:if>
							</td>
							<td>
								<c:if test="${computer.company.isPresent()}" var="variable">
									${computer.company.get().name}
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
				
				<li>
					<c:if test="${page.numPage-1 > 0}" var="variable">
						<a href="?page=${page.numPage-1}">&lt;</a>
					</c:if>
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
				
				<li><a href="?page=${page.numPage}"> ..  </a></li>
				
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
				
				<li>
					<c:if test="${page.numPage+1 <= page.getMaxPage()}" var="variable">
						<a href="?page=${page.numPage+1}">&gt;</a>
					</c:if>
				</li>
				
				<li>
					<a href="?page=${page.getMaxPage()}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>

		<div class="btn-group btn-group-sm pull-right" role="group" >
		<form id="nombreElementPage" action="#" method="GET" >
			<button type="submit" class="btn btn-default" name="itemsPerPage" value="10">10</button>
			<button type="submit" class="btn btn-default" name="itemsPerPage" value="50">50</button>
			<button type="submit" class="btn btn-default" name="itemsPerPage" value="100">100</button>
		</form>
		</div>
	</div>
	</footer>
    
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>


</body>
</html>
