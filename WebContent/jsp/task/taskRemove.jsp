<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
			<ol class="breadcrumb">
				<li><a href="index.jsp">Start</a></li>
				<li><a href="team.jsp?mode=view&id=X">${task.team.teamname}</a></li>
				<li>${task.gruppe.name}</li>
				<li class="active"></li>
			</ol>
			<h1>Aufgabe l&ouml;schen</h1>
			
			<c:choose>
				<%-- Abfrage --%>
				<c:when test="${!param.sure}">
					<p>Sind Sie sicher, dass Sie die Aufgabe "<b>${task.titel}</b>" l&ouml;schen m&ouml;chten?</p>
					<form action="/task" method="post">
						<input type="hidden" name="id" value="${task.id}" />
						<input type="hidden" name="sure" value="true" />
						<input type="hidden" name="mode" value="remove" />
						<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</button>
						<a class="btn btn-default" href="task?mode=view&id=X"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
					</form>
				</c:when>
				
				<%-- Abfrage bestätigt -> Löschen --%>
				<c:when test="${param.sure}">
					<%-- AufgabenVerwaltung.loeschen(aufgabe); --%>
					<p>Die Aufgabe "<b>${task.titel}</b>" wurde erfolreich gel&ouml;scht!</p>
					<a class="btn btn-primary" href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-ok"></span> Zur&uuml;ck zum Team</a>
				</c:when>
			</c:choose>

			<jsp:include page="../sidebar.jsp" />

<jsp:include page="../footer.jsp" />