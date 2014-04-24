<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request or !login}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Start" /></jsp:include>
<jsp:include page="../menu.jsp" />
				
				<%-- Benutzer-Startseite (eingeloggt) --%>
				<c:if test="${login}">
					<%@ page import="administration.DateiVerwaltung" %>
			  		<h1>Meine Teams</h1>
			  		
			  		<c:forEach var="team" items="${teams}">
			  			${team.name}
			  		
			  		</c:forEach>
			  		
			  		<table class="table table-striped table-hover">
			  			<thead>
			  			<tr>
					        <th>Team</th>
					        <th>Aufgaben <small>(eigene)</small></th>
					        <th>Mitglieder</th>
					    </tr>
					    </thead>
			  			<tbody>
						    <tr>
						        <td><a href="team?mode=view&id=X">Test-Team</a></td>
						        <td>13 (2)</td>
						        <td>4</td>
						    </tr>
						    <tr>
						        <td><a href="team?mode=view&id=X">Hallo Crazytown</a></td>
						        <td>25 (0)</td>
						        <td>5</td>
						    </tr>
						    <tr>
						        <td><a href="team?mode=view&id=X">The Uh Oh's</a></td>
						        <td>108 (1)</td>
						        <td>9</td>
						    </tr>
						    <tr>
						        <td><a href="team?mode=view&id=X">Microsoft Windows Core Development Team</a></td>
						        <td>1337 (2)</td>
						        <td>109</td>
						    </tr>
					</tbody>
					</table>
			  		
			  		<h1>Meine Aufgaben</h1>
			  		<div class="list-group">
				  		<c:forEach var="task" items="${tasks}">
				  			<a href="task?mode=view&id=${task.id}" class="list-group-item">
								<div class="task-progress"><div class="progress">
									<div class="progress-bar" role="progressbar" aria-valuenow="${task.status}" aria-valuemin="0" aria-valuemax="100" style="width: ${task.status}%;">${task.status}%</div>
								</div></div>
								<div class="task-details">
									<span class="glyphicon glyphicon-user"></span> ${task.ersteller.username}<br />
									<span class="glyphicon glyphicon-file"></span> ${task.filecount}
								</div>
								<h4 class="list-group-item-heading">${task.titel}</h4>
								<p class="list-group-item-text">
									
									<c:if test="${fn:length(task.beschreibung) <= 40}">
										${task.beschreibung}
									</c:if>
									<c:if test="${fn:length(task.beschreibung) > 40}">
										${fn:substring(task.beschreibung, 0, 40)}...
									</c:if>
								</p>
							</a>			  		
				  		</c:forEach>
					</div>
				</c:if>
		  		
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />