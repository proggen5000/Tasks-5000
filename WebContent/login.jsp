<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="administration.MitgliederVerwaltung" %>
<% MitgliederVerwaltung mv = new MitgliederVerwaltung(); %>

<jsp:include page="jsp/header.jsp" />
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>Login</h1>
		  		${param.username}<br />
		  		${param.password}
		  		<c:if test="mv.pruefeLogin(${param.username}, ${param.password}) == 'true'">
		  			<p>Login erfolgreich! Willkommen, ${param.username}.</p>
		  		</c:if>
		  		
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />