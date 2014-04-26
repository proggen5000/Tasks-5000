<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Aufgabengruppe l&ouml;schen" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

				<ol class="breadcrumb">
					<li><a href="/">Start</a></li>
					<li><a href="/team?mode=view&id=${taskGroup.team.id}">${taskGroup.team.name}</a></li>
					<li class="active"></li>
				</ol>
				
				<h1>Aufgabengruppe l&ouml;schen</h1>
				<p>Sind Sie sicher, dass Sie die Aufgabengruppe "<b>${taskGroup.name}</b>" entfernen m&ouml;chten?</p>
				<p>Warnung: Dadurch werden auch <b>alle darin enthaltenen Aufgaben gel&ouml;scht</b>!</p>
				<form action="/taskGroup" method="post">
					<input type="hidden" name="id" value="${taskGroup.id}" />
					<input type="hidden" name="mode" value="remove" />
					<input type="hidden" name="sure" value="true" />
					<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</button>
					<a href="/taskGroup?mode=edit&id=${taskGroup.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
				</form>
					
				<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />