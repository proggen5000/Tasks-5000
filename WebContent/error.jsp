<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="Fehler! :(" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>Fehler bei der Verarbeitung</h1>
		  		<div class="row">
		  			<div class="col-xs-2 error"><span class="glyphicon glyphicon-fire"></span></div>
			  		<div class="col-xs-10">
			  			<p>Leider ist ein Fehler beim Abrufen der Seite entstanden. Bitte &uuml;berpr&uuml;fen Sie, ob Sie eingeloggt sind und &uuml;ber die n&ouml;tigen Rechte verf&uuml;gen, diese Seite aufzurufen.</p>
				  		<p>Rufen Sie Seiten bei Tasks 5000 ausschlie&szlig;lich direkt &uuml;ber das System auf, um Fehler zu vermeiden.</p>
				  		<a class="btn btn-primary" href="/">Zur Startseite</a>
			  		</div>
		  		</div>
		  		
		  		<c:if test="${requestScope.error != null}">
		  			<h2>Fehlerdetails</h2>
		  			<p class="errorlog">${requestScope.error}</p>
		  		</c:if>
		  		
				<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />