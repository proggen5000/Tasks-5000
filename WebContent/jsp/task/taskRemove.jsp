<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Aufgabe l&ouml;schen" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/">Start</a></li>
				<li><a href="${pageContext.request.contextPath}/team?mode=view&id=${task.taskGroup.team.id}">${task.taskGroup.team.name}</a></li>
				<li><a href="${pageContext.request.contextPath}/taskGroup?mode=edit&id=${task.taskGroup.id}">${task.taskGroup.name}</a></li>
				<li class="active"></li>
			</ol>
			<h1>Aufgabe l&ouml;schen</h1>
			<p>Sind Sie sicher, dass Sie die Aufgabe "<b>${task.name}</b>" l&ouml;schen m&ouml;chten?</p>
			<form action="${pageContext.request.contextPath}/task" method="post">
				<input type="hidden" name="id" value="${task.id}" />
				<input type="hidden" name="mode" value="remove" />
				<button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</button>
				<a href="${pageContext.request.contextPath}/task?mode=view&id=${task.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
			</form>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />