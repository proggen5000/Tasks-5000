<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${file.name}" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<ol class="breadcrumb">
				<li><a href="/">Start</a></li>
				<li><a href="team?mode=view&id=${file.team.id}">${file.team.name}</a></li>
				<c:if test="${group.id != null}"><li>${group.name}</li></c:if>
				<c:if test="${task.id != null}"><li>${task.name}</li></c:if>
				<li class="active"></li>
			</ol>
			
			<h1>${file.name} <span class="glyphicon glyphicon-file small"></span></h1>
			<p>${file.beschreibung}</p>
			<p>Hochgeladen von ${file.ersteller}</p>
			
			<form action="/file" method="post">
				<input type="hidden" name="id" value="${task.id}" />
				<input type="hidden" name="mode" value="download" />
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-download"></span> Download <small>(XY KB)</small></button>
			</form>
			
			</div><%-- Ende content --%>
			<%-- Sidebar --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
				<div class="list-group">
					<a href="/file?mode=edit&id=${file.id}" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Datei bearbeiten</a>
					<a href="/file?mode=remove&id=${file.id}" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Datei l&ouml;schen</a>
				</div>
			
				<h1>Details</h1>
				<div class="list-group">
					<div class="list-group-item"><span class="glyphicon glyphicon-barcode"></span> ID: ${file.id}</div>
					<a href="/user?mode=view&id=${file.ersteller.id}" class="list-group-item"><span class="glyphicon glyphicon-user"></span> ${file.ersteller.username} <span class="label label-default">Ersteller</span></a>
				</div>
			</div><%-- Ende Sidebar --%>
<jsp:include page="../footer.jsp" />