<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${login}">
	<%-- Menü --%>
	<ul class="nav nav-tabs nav-justified">	  
	  <c:if test="${param.menu == null}"><li class="active"><a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home"></span> Start</a></li></c:if>
	  <c:if test="${param.menu != null}"><li class=""><a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home"></span> Start</a></li></c:if>
	
		<c:if test="${param.menu == 'teams'}"><li class="dropdown active"></c:if>
		<c:if test="${param.menu != 'teams'}"><li class="dropdown"></c:if>
		    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
		      <span class="glyphicon glyphicon-briefcase"></span> Meine Teams <span class="caret"></span>
		    </a>
		    <ul class="dropdown-menu">
		    	<c:forEach var="team" items="${teams_menu}">
		    		<li><a href="${pageContext.request.contextPath}/team?mode=view&id=${team.id}"><span class="glyphicon glyphicon-briefcase"></span> ${team.name}</a></li>
		    	</c:forEach>
				<li class="divider"></li>
				<li><a href="${pageContext.request.contextPath}/team?mode=new"><span class="glyphicon glyphicon-pencil"></span> Team erstellen</a></li>
		    </ul>
		</li>
	  
		<c:if test="${param.menu == 'me'}"><li class="dropdown active"></c:if>
		<c:if test="${param.menu != 'me'}"><li class="dropdown"></c:if>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<span class="glyphicon glyphicon-user"></span> Ich <span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
				<li><a href="${pageContext.request.contextPath}/user?mode=view&id=${currentUser}"><span class="glyphicon glyphicon-user"></span> Mein Profil</a></li>
				<li><a href="${pageContext.request.contextPath}/user?mode=edit"><span class="glyphicon glyphicon-pencil"></span> Profil bearbeiten</a></li>
				<li class="divider"></li>
				<li><a href="${pageContext.request.contextPath}/login?mode=logout"><span class="glyphicon glyphicon-remove"></span> Logout</a></li>
		    </ul>
		</li>  
	</ul>
</c:if>