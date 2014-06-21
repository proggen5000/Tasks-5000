<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isErrorPage="true" %>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="Fehler! :(" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>Fehler bei der Verarbeitung</h1>
		  		<div class="row">
			  		<div class="col-xs-9">
			  			<p>Leider ist ein Fehler beim Abrufen der Seite entstanden. Bitte &uuml;berpr&uuml;fen Sie, ob Sie eingeloggt sind und &uuml;ber die n&ouml;tigen Rechte verf&uuml;gen, diese Seite aufzurufen.</p>
				  		<p>Rufen Sie Seiten bei Tasks 5000 ausschlie&szlig;lich direkt &uuml;ber das System auf, um Fehler zu vermeiden.</p>
			  		</div>
			  		<div class="col-xs-2 error"><span class="glyphicon glyphicon-fire"></span></div>
		  		</div>
		  		<c:if test="${requestScope.error != null or sessionScope.error != null or param.error != null}">
		  			<h2>Fehlermeldung</h2>
		  			<p class="errorlog">${requestScope.error}</p>
		  			<p class="errorlog">${sessionScope.error}</p>
		  			<p class="errorlog">${param.error}</p>
		  			<c:remove var="error" />
		  		</c:if>
		  		
		  		<br />
		  		<a class="btn btn-primary" href="${pageContext.request.contextPath}/">Zur Startseite</a>
		  		
		  		<c:if test="${pageContext.exception != null}">
		  			<h2>Ausnahme (Exception)</h2>
		  			<p class="errorlog">${pageContext.exception}</p>
		  		</c:if>
		  		
		  		<c:if test="${pageContext.errorData.statusCode > 0}">
		  			<h2>Status Code</h2>
		  			<p class="errorlog">${pageContext.errorData.statusCode}</p>
		  		</c:if>
		  		
		  		<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
		  			<p class="errorlog">${trace}</p>
	  			</c:forEach>
		  	
				<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />