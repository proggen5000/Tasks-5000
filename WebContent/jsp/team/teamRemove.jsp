<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Team l&ouml;schen" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
		<h1>Team l&ouml;schen</h1>
		<p>Sind Sie sicher, dass Sie das Team "<b>${team.name}</b>" dauerhaft l&ouml;schen m&ouml;chten?</p>
		<p>Warnung: Dadurch werden auch alle darin enthaltenen Aufgaben und Dateien gel&ouml;scht!</p>
		
		<form action="${pageContext.request.contextPath}/team" method="post">
			<input type="hidden" name="id" value="${team.id}" />
			<input type="hidden" name="mode" value="remove" />
			<input type="hidden" name="sure" value="true" />
			<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</button>
			<a href="${pageContext.request.contextPath}/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
		</form>
						
		<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />