<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

			<%-- Aufgabe ansehen --%>				
			<c:set var="title" scope="page" value="Aufgabe bearbeiten" />
			<c:set var="submit_button" scope="page" value="Speichern" />
			
			<ol class="breadcrumb">
				<li><a href="/">Start</a></li>
				<li><a href="team?mode=view&id=${task.team.id}">${task.team.teamname}</a></li>
				<li>${task.gruppe.name}</li>
				<li class="active"></li>
			</ol>
			
			<h1>${task.titel} <span class="glyphicon glyphicon-time small"></span></h1>
			<p>${task.beschreibung}</p>
			
			<%-- prüfen, ob Dateien vorhanden --%>
			<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title"><span class="glyphicon glyphicon-paperclip"></span> Dateien</h3></div>
				<div class="panel-body">
					<span class="glyphicon glyphicon-file"></span> <a href="file.jsp?id=X">meinedatei.docx</a> (72 KB)<br />
					<span class="glyphicon glyphicon-file"></span> <a href="file.jsp?id=X">einbild.jpg</a> (205 KB)
				</div>
			</div>
			
			</div><%-- Ende content --%>
			<%-- Sidebar --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
					<div class="list-group">
						<a href="task?mode=edit&id=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Aufgabe bearbeiten</a>
						<a href="task?mode=remove&id=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
					</div>
					<div class="list-group">
						<a href="file?mode=new&task=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-file"></span> Datei hochladen</a>
					</div>	
			
				<h1>Details</h1>
					<div class="list-group">
						<div class="list-group-item"><span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate pattern="dd.MM.yyyy" value="${task.dateAsDate}" /></div>
						<div class="list-group-item"><span class="glyphicon glyphicon-bell"></span> <fmt:formatDate pattern="dd.MM.yyyy" value="${task.deadlineAsDate}" /></div>
						<div class="list-group-item"><span class="glyphicon glyphicon-dashboard"></span> Status: ${task.status}%</div>
					</div>
					<div class="list-group">
						<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker <span class="label label-default">Ersteller</span></a>
						<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Felix Fichte</a>
					</div>
			</div><%-- Ende Sidebar --%>

<jsp:include page="../footer.jsp" />