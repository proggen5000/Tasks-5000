<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request or !login}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Start" /></jsp:include>
<jsp:include page="../menu.jsp" />

		  		<h1>Meine Teams</h1>
		  		<c:if test="${fn:length(teams_menu) > 0}">
		  			<table class="table table-striped table-hover">
			  			<thead>
				  			<tr>
						        <th>Team</th>
						        <th>Aufgaben <small>(eigene)</small></th>
						        <th>Dateien</th>
						        <th>Mitglieder</th>
						    </tr>
					    </thead>
			  			<tbody>
			  				<c:forEach var="team" items="${teams_menu}">
			  					<tr>
							        <td><span class="glyphicon glyphicon-briefcase"></span> <a href="${pageContext.request.contextPath}/team?mode=view&id=${team.id}">${team.name}</a></td>
							        <td>${team.tasksCount} (${team.getTasksCount(currentUser)})</td>
							        <td>${team.filesCount}</td>
							        <td>${team.usersCount} </td>
							    </tr>
			  				</c:forEach>
						</tbody>
					</table>
		  		</c:if>
		  		<c:if test="${fn:length(teams_menu) == 0}">
		  			<p>
		  				Momentan sind Sie kein Mitglied in einem Team.<br />
		  				<a href="${pageContext.request.contextPath}/team?mode=new">Erstellen</a> Sie Ihr eigenes Team oder lassen Sie sich von einem anderem Teammanager einladen.
		  			</p>
		  		</c:if>
		  		
		  		<h1>Meine Aufgaben</h1>
		  		<c:if test="${fn:length(tasks) > 0}">
		  			<div class="list-group">
				  		<c:forEach var="task" items="${tasks}">
				  			<a href="${pageContext.request.contextPath}/task?mode=view&id=${task.id}" class="list-group-item">
								<div class="task-progress"><div class="progress">
									<div class="progress-bar" role="progressbar" aria-valuenow="${task.status}" aria-valuemin="0" aria-valuemax="100" style="width: ${task.status}%;">${task.status}%</div>
								</div></div>
								<div class="task-details">
									<span class="glyphicon glyphicon-user"></span> ${task.author.name}<br />
									<span class="glyphicon glyphicon-paperclip"></span> ${task.getFilesCount()}
								</div>
								<h4 class="list-group-item-heading">
									<span class="glyphicon glyphicon-time"></span> ${task.name}
									<small>(<span class="glyphicon glyphicon-briefcase"></span> ${task.taskGroup.team.name})</small>
								</h4>
								<p class="list-group-item-text">
									<c:if test="${fn:length(task.description) == 0}">&nbsp;</c:if>
									<c:if test="${fn:length(task.description) <= 50}">
										${task.description}
									</c:if>
									<c:if test="${fn:length(task.description) > 50}">
										${fn:substring(task.description, 0, 50)}...
									</c:if>
								</p>
							</a>			  		
				  		</c:forEach>
					</div>
		  		</c:if>
		  		<c:if test="${fn:length(tasks) == 0}">
		  			<p>Momentan wurden Ihnen keine Aufgaben zugewiesen.</p>
		  		</c:if>
		  		
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />