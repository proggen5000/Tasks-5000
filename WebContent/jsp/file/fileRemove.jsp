<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Datei l&ouml;schen" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/">Start</a></li>
				<li><a href="${pageContext.request.contextPath}/team?mode=view&id=${file.team.id}">${file.team.name}</a></li>
				<li class="active"></li>
			</ol>
			
			<h1>Aufgabe l&ouml;schen</h1>
			<p>Sind Sie sicher, dass Sie die Datei "<b>${file.name}</b>" l&ouml;schen m&ouml;chten?</p>
			<form action="${pageContext.request.contextPath}/file" method="post">
				<input type="hidden" name="id" value="${file.id}" />
				<input type="hidden" name="mode" value="remove" />
				<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</button>
				<a href="${pageContext.request.contextPath}/file?mode=view&id=${file.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
			</form>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />