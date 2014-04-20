<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%-- Datei herunterladen --%>
	<c:if test="${login and param.id != null}">
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team" />
		<c:set var="group" scope="page" value="Windows 8.1" />
		<c:set var="file_path" scope="page" value="http://herrherrmann.net/files/5413/9367/4451/notizenindigital.jpg" />
		
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />

			<c:if test="${valid_request}">
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp?mode=view&id=X">${team}</a></li>
					<li>${group}</li>
					<li class="active"></li>
				</ol>
				
				<h1>Datei herunterladen</h1>
				<p>Ihre Datei wird nun heruntergeladen.</p>
				<p>Klicken Sie <a href="${file_path}">hier</a>, falls Sie die Datei erneut anfordern m&ouml;chten.</p>
				
				<c:redirect url="${file_path}" />
			</c:if>
			
			<c:if test="${!valid_request}">
				<h1>Zugriff verweigert</h1>
				<p>Sie haben keine Befugnis, auf diese Datei zuzugreifen.</p>
				<p>Wahrscheinlich fehlen Ihnen hierzu die Zugriffsrechte, da Sie nicht eingeloggt oder Mitglied des Teams sind, welches diese Datei bereitstellt.</p>
				<a href="index.jsp" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> Zur Startseite</a>
			</c:if>
			
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />