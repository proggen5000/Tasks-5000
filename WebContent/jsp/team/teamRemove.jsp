<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Team l&ouml;schen" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
		<h1>Team l&ouml;schen</h1>
		<p>Sind Sie sicher, dass Sie das Team "<b>${team.teamname}</b>" dauerhaft l&ouml;schen m&ouml;chten?</p>
		
		<form action="/team" method="post">
			<input type="hidden" name="id" value="${team.id}" />
			<input type="hidden" name="mode" value="remove" />
			<input type="hidden" name="sure" value="true" />
			<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, Team l&ouml;schen</button>
			<a class="btn btn-default" href="team?mode=view&id=${team.id}"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
		</form>
						
		<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />