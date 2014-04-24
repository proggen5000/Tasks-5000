<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request or !login}">
	<c:redirect url="/index"><c:param name="error" value="Zugriff verweigert" /><c:param name="page" value="error" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Start" /></jsp:include>
<jsp:include page="../menu.jsp" />
				
				<%-- Benutzer-Startseite (eingeloggt) --%>
		  		<h1>Meine Teams</h1>
		  		
		  		<table class="table table-striped table-hover">
		  			<thead>
		  			<tr>
				        <th>Team</th>
				        <th>Aufgaben <small>(eigene)</small></th>
				        <th>Mitglieder</th>
				    </tr>
				    </thead>
		  			<tbody>
		  				<c:set var="currentUserID" value="${param.currentUser}" />
		  				<c:forEach var="team" items="${teams}">
		  					<tr>
						        <td><a href="team?mode=view&id=${team.id}">${team.teamname}</a></td>
						        <td>${team.aufgabenAnzahl} ${team.getAufgabenAnzahlVonMitglied(currentUserID)}</td>
						        <td>${team.getAnzahlMitglieder()}</td>
						    </tr>
		  				</c:forEach>
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
								<span class="glyphicon glyphicon-file"></span> ${task.getAnzahlDateien()}
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
		  		
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />