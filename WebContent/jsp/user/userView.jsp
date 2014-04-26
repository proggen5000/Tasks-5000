<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>
	
<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${user.username}" /></jsp:include>
<c:if test="${user.id != currentUser}"><jsp:include page="../menu.jsp" /></c:if>
<c:if test="${user.id == currentUser}"><jsp:include page="../menu.jsp"><jsp:param name="menu" value="me" /></jsp:include></c:if>

			<h1>${user.username} <span class="glyphicon glyphicon-user small"></span></h1>
			<dl class="dl-horizontal">
				<dt>Benutzer-ID</dt><dd>${user.id}</dd>
				<dt>Echter Name</dt><dd>${user.vorname} ${user.nachname}</dd>
				<dt>Mitglied seit</dt><dd><fmt:formatDate pattern="dd.MM.yyyy" value="${user.regdatumAsDate}" /></dd>
				<dt>Mitglied in</dt>
					<c:forEach var="team" items="${teams}">
	  					<dd><a href="/team?mode=view&id=${team.id}"><span class="glyphicon glyphicon-briefcase"></span> ${team.teamname}</a></dd>
					</c:forEach>
			</dl>	
			<c:if test="${user.id == currentUser}">
				<br /><a href="/user?mode=edit" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> Profil bearbeiten</a>
			</c:if>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />