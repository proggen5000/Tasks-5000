<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>
	
<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${team.name}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

			<h1>${team.name} <span class="glyphicon glyphicon-briefcase small"></span></h1>
	  		<p>${team.description}</p>
	  		
	  		<h1>Aufgaben</h1>
	  		<c:if test="${fn:length(taskGroups) == 0}">
	  			<p>Erstellen Sie zuerst <a href="${pageContext.request.contextPath}/taskGroup?mode=new&teamId=${team.id}">Aufgabengruppen</a>, danach k&ouml;nnen Sie Aufgaben erstellen und den Gruppen zuordnen.</p>
	  		</c:if>
	  		<c:forEach var="taskGroup" items="${taskGroups}">
	  			<h2><a href="${pageContext.request.contextPath}/taskGroup?mode=edit&id=${taskGroup.id}" title="Aufgabengruppe bearbeiten">${taskGroup.name}</a></h2>
		  		<p>${taskGroup.description}</p>
				<div class="list-group">
					<c:if test="${fn:length(taskGroup.tasks) == 0}">
			  			<p>Diese Gruppe enth&auml;lt noch keine Aufgaben. <a href="${pageContext.request.contextPath}/task?mode=new&teamId=${team.id}">Aufgabe erstellen</a></p>
			  		</c:if>
					<c:forEach var="task" items="${taskGroup.tasks}">
		  				<a href="${pageContext.request.contextPath}/task?mode=view&id=${task.id}" class="list-group-item">
							<div class="task-progress"><div class="progress">
								<div class="progress-bar" role="progressbar" aria-valuenow="${task.status}" aria-valuemin="0" aria-valuemax="100" style="width: ${task.status}%;">${task.status}%</div>
							</div></div>
							<div class="task-details">
								<span class="glyphicon glyphicon-user"></span> ${task.author.name}<br />
								<span class="glyphicon glyphicon-paperclip"></span> ${task.filesCount}
							</div>
							<h4 class="list-group-item-heading"><span class="glyphicon glyphicon-time"></span> ${task.name}</h4>
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
	  		</c:forEach>
	  		
	  		<h1>Dateien</h1>
	  		<c:if test="${fn:length(files) == 0}">
	  			<p><a href="${pageContext.request.contextPath}/file?mode=new&teamId=${team.id}">Laden Sie Dateien hoch</a>, um diese hier f&uuml;r Ihr Team aufzulisten.</p>
	  		</c:if>
	  		<c:if test="${fn:length(files) > 0}">
	  			<ul class="list-group">
					<c:forEach var="file" items="${files}">				
						<a href="${pageContext.request.contextPath}/file?mode=view&id=${file.id}" class="list-group-item">
							<div class="task-details">
								<span class="glyphicon glyphicon-user"></span> ${file.author.name}<br />
								<span class="glyphicon glyphicon-paperclip"></span> ${file.size} KB
							</div>
							<h4 class="list-group-item-heading">
								<span class="glyphicon glyphicon-file"></span> ${file.name}
								<c:if test="${file.isLinked()}"><span class="glyphicon glyphicon-paperclip small"></span></c:if>
							</h4>
							<p class="list-group-item-text">
								<c:if test="${fn:length(file.description) == 0}">&nbsp;</c:if>
								<c:if test="${fn:length(file.description) <= 50}">
									${file.description}
								</c:if>
								<c:if test="${fn:length(file.description) > 50}">
									${fn:substring(file.description, 0, 50)}...
								</c:if>
							</p>
						</a>
					</c:forEach>
				</ul>
			</c:if>

		</div><%-- Ende content --%>
		<%-- Sidebar --%>
		<div class="sidebar col-sm-3">
			<h1>Aktionen</h1>
				<div class="list-group">
					<c:if test="${fn:length(taskGroups) > 0}">
						<a href="${pageContext.request.contextPath}/task?mode=new&teamId=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-time"></span> Aufgabe erstellen</a>
					</c:if>
					<a href="${pageContext.request.contextPath}/taskGroup?mode=new&teamId=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppe erstellen</a>
					<a href="${pageContext.request.contextPath}/file?mode=new&teamId=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-file"></span> Datei hochladen</a>
				</div>
				<div class="list-group">
					<c:if test="${sessionScope.currentUser == team.manager.id}">
						<a href="${pageContext.request.contextPath}/team?mode=edit&id=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Team bearbeiten</a>
						<a href="${pageContext.request.contextPath}/team?mode=remove&id=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
					</c:if>
					<a href="${pageContext.request.contextPath}/user?mode=leaveTeam&teamId=${team.id}" class="list-group-item"><span class="glyphicon glyphicon-log-out"></span> Team verlassen</a>
				</div>	
		
			<h1>Mitglieder</h1>
			<div class="list-group">
				<c:forEach var="user" items="${users}">
					<a href="${pageContext.request.contextPath}/user?mode=view&id=${user.id}" class="list-group-item">
						<span class="glyphicon glyphicon-user"></span> ${user.name}
						<c:if test="${user.id == team.manager.id}">
							<span class="label label-default pull-right">Manager</span>
						</c:if>
					</a>
				</c:forEach>
			</div>
		</div><%-- Ende Sidebar --%>

<jsp:include page="../footer.jsp" />