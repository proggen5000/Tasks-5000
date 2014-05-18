<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${file.name}" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/">Start</a></li>
				<li><a href="${pageContext.request.contextPath}/team?mode=view&id=${file.team.id}">${file.team.name}</a></li>
				<c:if test="${task.id != null}"><li>${task.name}</li></c:if><%-- TODO für mehrere Tasks optimieren! --%>
				<li class="active"></li>
			</ol>
			
			<h1>${file.name} <span class="glyphicon glyphicon-file small"></span></h1>
			<p>${file.beschreibung}</p>
			
			<form action="${pageContext.request.contextPath}/file" method="post">
				<input type="hidden" name="id" value="${task.id}" />
				<input type="hidden" name="mode" value="download" />
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-download"></span> Download <small>(${file.size} KB)</small></button>
			</form>
			
			<c:if test="${fn:length(tasks) > 0}">
				<div class="panel panel-default" style="margin-top: 20px;">
					<div class="panel-heading"><h3 class="panel-title"><span class="glyphicon glyphicon-paperclip"></span> Verkn&uuml;pfte Aufgaben</h3></div>
					<div class="panel-body">
						<c:forEach var="task" items="${tasks}">
							<span class="glyphicon glyphicon-time"></span> <a href="${pageContext.request.contextPath}/task?mode=view&id=${task.id}">${task.name}</a>
						</c:forEach>
					</div>
				</div>
			</c:if>
			
			
			</div><%-- Ende content --%>
			<%-- Sidebar --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
				<div class="list-group">
					<a href="${pageContext.request.contextPath}/file?mode=edit&id=${file.id}" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Datei bearbeiten</a>
					<a href="${pageContext.request.contextPath}/file?mode=remove&id=${file.id}" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Datei l&ouml;schen</a>
				</div>
			
				<h1>Details</h1>
				<div class="list-group">
					<div class="list-group-item"><span class="glyphicon glyphicon-barcode"></span> ID: ${file.id}</div>
					<a href="${pageContext.request.contextPath}/user?mode=view&id=${file.ersteller.id}" class="list-group-item"><span class="glyphicon glyphicon-user"></span> ${file.ersteller.username} <span class="label label-default">Ersteller</span></a>
				</div>
			</div><%-- Ende Sidebar --%>
<jsp:include page="../footer.jsp" />