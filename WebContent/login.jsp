<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="administration.MitgliederVerwaltung" %>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="Login" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<c:choose>
		  			<%-- Loginversuch --%>
			  		<c:when test="${param.mode == null and param.username != null and param.password != null}">
			  			
						<%-- Erfolg --%>
						<c:if test="<%= MitgliederVerwaltung.pruefeLogin(request.getParameter(\"username\"), request.getParameter(\"password\")) %>">
				  			<c:set var="login" scope="session" value="true" />
				  			<c:set var="currentUser" scope="session" value="admin" /> <%-- !!! --%>
				  			<h1>Login erfolgreich</h1>
				  			<p>Willkommen, ${param.username}.</p>
				  			<a href="index.jsp" class="btn btn-primary">Weiter zur eigenen Startseite</a>
				  		</c:if>
				  		
				  		<%-- Fehler --%>
				  		<c:if test="<%= !MitgliederVerwaltung.pruefeLogin(request.getParameter(\"username\"), request.getParameter(\"password\")) %>">
				  			<h1>Fehler bei der Anmeldung</h1>
			  				<p>Ihre Logindaten scheinen nicht zu stimmen. Bitte &uuml;berpr&uuml;fen Sie diese und versuchen Sie es erneut.</p>
				  		</c:if>
			  		</c:when>
			  		
			  		<%-- Logout --%>
			  		<c:when test="${login and param.mode == 'logout'}">
			  			<c:remove var="login" scope="session" />
			  			<c:remove var="currentUser" scope="session" /> <%-- ggf. nochmal ändern? --%>
			  			<h1>Logout erfolgreich</h1>
			  			<p>Sie haben sich erfolgreich aus dem System ausgeloggt.</p>
			  			<p>Sch&ouml;nen Tag noch!</p>
			  			<a href="index.jsp" class="btn btn-primary">Zur Startseite</a>
			  		</c:when>
			  		
			  		<%-- Fehler --%>
			  		<c:otherwise>
			  			<h1>Fehler bei der Anmeldung</h1>
			  			<p>Ihre Logindaten scheinen nicht zu stimmen. Bitte &uuml;berpr&uuml;fen Sie diese und versuchen Sie es erneut.</p>
			  		</c:otherwise>
			  	</c:choose>

			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />