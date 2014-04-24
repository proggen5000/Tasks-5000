<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="administration.MitgliederVerwaltung" %>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Login" /></jsp:include>
<jsp:include page="../menu.jsp" />
	  		
  			<h1>Login erfolgreich</h1>
  			<p>Willkommen, ${user.username}!</p>
  			<a href="/" class="btn btn-primary">Weiter zur eigenen Startseite</a>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />