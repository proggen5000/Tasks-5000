<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${file.titel}" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<ol class="breadcrumb">
				<li><a href="/">Start</a></li>
				<li><a href="team?mode=view&id=${file.team.id}">${file.team.name}</a></li>
				<c:if test="${group.id != null}"><li>${group.name}</li></c:if>
				<li class="active"></li>
			</ol>
			
			<h1>${file.titel} <span class="glyphicon glyphicon-file small"></span></h1>
			<p>${file.beschreibung}</p>
			
			<form action="/file" method="post">
				<input type="hidden" name="id" value="${task.id}" />
				<input type="hidden" name="mode" value="download" />
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-download"></span> Download <small>(XY KB)</small></button>
			</form>
			
			<%-- Aktionen (bearbeiten, löschen) und Details (Version, Ersteller) in Sidebar zeigen! --%>
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />