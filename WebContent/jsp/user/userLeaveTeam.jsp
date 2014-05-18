<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>
	
<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Team verlassen" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<h1>Team verlassen</h1>
			<p>Sind Sie sicher, dass Sie das Team "<b>${team.name}</b>" endg&uuml;ltig verlassen m&ouml;chten?</p>
			<form action="${pageContext.request.contextPath}/user" method="post">
				<input type="hidden" name="id" value="${currentUser}" />
				<input type="hidden" name="teamId" value="${team.id}" />
				<input type="hidden" name="mode" value="leaveTeam" />
				<input type="hidden" name="sure" value="true" />
				<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, Team verlassen</button>
				<a href="${pageContext.request.contextPath}/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
			</form>
			
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />