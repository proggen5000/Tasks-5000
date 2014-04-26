<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${requestScope.title}" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>${requestScope.title}</h1>
		  		<c:if test="${requestScope.message != null}">
		  			<p>${requestScope.message}</p>
		  		</c:if>
		  		<c:if test="${requestScope.link_url != null and requestScope.link_text != null}">
		  			<a class="btn btn-primary" href="${requestScope.link_url}">${requestScope.link_text}</a>
		  		</c:if>
		  		<c:if test="${requestScope.link_url == null and requestScope.link == null}">
		  			<a class="btn btn-primary" href="/">Zur Startseite</a>
		  		</c:if>
			  	
				<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />