<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${requestScope.title}" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>
		  			${requestScope.title}
		  			${sessionScope.title}
		  			<c:remove var="title" />
		  		</h1>
		  		<c:if test="${requestScope.message != null or sessionScope.message != null}">
		  			<p>${requestScope.message}</p>
		  			<p>${sessionScope.message}</p>
		  			<c:remove var="message" />
		  		</c:if>
		  		
		  		<c:if test="${requestScope.link_url != null and requestScope.link_text != null}">
		  			<a class="btn btn-primary" href="${pageContext.request.contextPath}/${requestScope.link_url}">${requestScope.link_text}</a>
		  		</c:if>
		  		<c:if test="${sessionScope.link_url != null and sessionScope.link_text != null}">
		  			<a class="btn btn-primary" href="${pageContext.request.contextPath}/${sessionScope.link_url}">${sessionScope.link_text}</a>
		  		</c:if>
		  		
		  		<c:if test="${requestScope.link_url == null and requestScope.link == null and sessionScope.link_url == null and sessionScope.link == null}">
		  			<a class="btn btn-primary" href="${pageContext.request.contextPath}/">Zur Startseite</a>
		  		</c:if>

				<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />