<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${login}">
	<%-- Menü --%>
	<ul class="nav nav-tabs nav-justified">
	  <c:if test="${param.menu == null}"><li class="active"><a href="/index.jsp"><span class="glyphicon glyphicon-home"></span> Start</a></li></c:if>
	  <c:if test="${param.menu != null}"><li class=""><a href="/index.jsp"><span class="glyphicon glyphicon-home"></span> Start</a></li></c:if>
	
	  <c:if test="${param.menu == 'teams'}"><li class="dropdown active"></c:if>
	  <c:if test="${param.menu != 'teams'}"><li class="dropdown"></c:if>
	    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	      <span class="glyphicon glyphicon-briefcase"></span> Meine Teams <span class="caret"></span>
	    </a>
	    <ul class="dropdown-menu">
	      <li><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Microsoft Windows Core Development Team</a></li>
	      <li><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Team X</a></li>
	      <li><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Team Y</a></li>
	      <li class="divider"></li>
	      <li><a href="team?mode=new"><span class="glyphicon glyphicon-pencil"></span> Team erstellen</a></li>
	    </ul>
	  </li>
	  
	  <c:if test="${param.menu == 'me'}"><li class="dropdown active"></c:if>
	  <c:if test="${param.menu != 'me'}"><li class="dropdown"></c:if>
	    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	      <span class="glyphicon glyphicon-user"></span> Ich <span class="caret"></span>
	    </a>
	    <ul class="dropdown-menu">
	      <li><a href="profile.jsp?mode=view&id=XX"><span class="glyphicon glyphicon-user"></span> Mein Profil</a></li>
	      <li><a href="profile.jsp?mode=edit&id=XX"><span class="glyphicon glyphicon-cog"></span> Profil &auml;ndern</a></li>
	      <li class="divider"></li>
	      <li><a href="login.jsp?mode=logout"><span class="glyphicon glyphicon-remove"></span> Logout</a></li>
	    </ul>
	  </li>  
	</ul>
</c:if>