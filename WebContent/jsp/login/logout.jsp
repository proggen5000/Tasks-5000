<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="administration.MitgliederVerwaltung" %>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Logout erfolgreich" /></jsp:include>
<jsp:include page="../menu.jsp" />

  			<c:remove var="login" scope="session" />
  			<c:remove var="currentUser" scope="session" /> <%-- ggf. nochmal ändern? --%>
  			<h1>Logout erfolgreich</h1>
  			<p>Sie haben sich erfolgreich aus dem System ausgeloggt.</p>
  			<p>Sch&ouml;nen Tag noch!</p>
  			<a href="/" class="btn btn-primary">Zur Startseite</a>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />