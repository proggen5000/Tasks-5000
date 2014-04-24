<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<%-- Zugriff nicht über Servlet --%>
			<c:if test="${!valid_request}">
				<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
			</c:if>

			<ol class="breadcrumb">
				<li><a href="index.jsp">Start</a></li>
				<li><a href="team.jsp?mode=view&id=X">${team}</a></li>
				<li>${group}</li>
				<li class="active"></li>
			</ol>
			
			<h1>Datei herunterladen</h1>
			<p>Ihre Datei wird nun heruntergeladen.</p>
			<p>Klicken Sie <a href="${file_path}">hier</a>, falls Sie die Datei erneut anfordern m&ouml;chten.</p>
			
			<%--<c:redirect url="${file_path}" /> --%>
			
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />