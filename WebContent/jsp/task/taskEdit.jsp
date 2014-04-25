<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht über Servlet 
<c:if test="${!valid_request}">
	<c:redirect url="error.jsp"><c:param name="error" value="Zugriff verweigert"></c:param></c:redirect>
</c:if>--%>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
			<%-- Aufgabe erstellen --%>		
			<c:if test="${param.mode == 'new'}">
				<c:set var="submit_button" scope="page" value="Erstellen" />
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp?mode=view&id=X">${team.teamname}</a></li>
					<li class="active"></li>
				</ol>
				<h1>Aufgabe erstellen</h1>
			</c:if>
			
			<%-- Aufgabe bearbeiten --%>
			<c:if test="${param.mode == 'edit'}">
				<c:set var="submit_button" scope="page" value="Speichern" />
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp?mode=view&id=X">${task.team.teamname}</a></li>
					<li class="active"></li>
				</ol>
				<h1>Aufgabe bearbeiten</h1>
			</c:if>
			
			<form class="form" action="/task" method="post" enctype="multipart/form-data">
		  		<div class="form-group col-xs row">
		  			<div class="col-xs-6">
		  				<label for="name"><span class="glyphicon glyphicon-time"></span> Name</label>
						<input id="name" name="name" type="text" class="form-control input-lg" placeholder="" value="${task.titel}">
		  			</div>
		  			<div class="col-xs-6">
		  				<label for="group"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppe</label>
						<select name="group" size="1" class="form-control input-lg">
							<option value="1" selected>Windows 8.1</option>
							<option value="34">Windows 9</option>
							<option value="2">Windows 10</option>
							<option value="74">Windows ME</option>
						</select>
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="description"><span class="glyphicon glyphicon-align-left"></span> Beschreibung</label>
					<textarea id="description" name="description" class="form-control" rows="5">${task.beschreibung}</textarea>
				</div>
				<div class="form-group col-xs row">
				    	<div class="col-xs-4">
				    		<label><span class="glyphicon glyphicon-calendar"></span> Erstellungsdatum</label>
				    		<p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy" value="${task.erstellungsdatumAsDate}" /></p>
				    	</div>
				    	<div class="col-xs-4">
				    		<label for="deadline"><span class="glyphicon glyphicon-bell"></span> Deadline</label>
							<input id="deadline" name="deadline" type="date" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${task.deadlineAsDate}" />">
				    	</div>
				    	<div class="col-xs-4">
				    		<label for="status"><span class="glyphicon glyphicon-dashboard"></span> Status</label> <small>(%)</small>
							<input id="status" name="status" type="number" min="0" max="100" class="form-control" value="${task.status}">								
				    	</div>
				</div>
				<div class="form-group col-xs">
					<label for="users"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <small>(mehrere Mitglieder durck Gedr&uuml;ckthalten von <kbd>Strg</kbd> bzw. <kbd>Cmd</kbd> markieren)</small>
					<select multiple name="users" id="users" size="3" class="form-control">
						<c:forEach var="user" items="${users}">
							<%-- hier prüfen, welche Mitglieder schon ausgewaehlt wurden!! //TODO --%>
							<option value="${user.id}">${user.username}</option>
						</c:forEach>
					</select>
				</div>
				
				<table class="table table-hover">
		  			<thead>
		  			<tr>
				        <th><span class="glyphicon glyphicon-paperclip"></span> Dateien</th>
				        <th>Gr&ouml;&szlig;e <small>(KB)</small></th>
				        <th>L&ouml;schen</th>
				    </tr>
				    </thead>
		  			<tbody>
					    <tr>
					        <td><a href="file.jsp?id=X"><span class="glyphicon glyphicon-file"></span> Test.jpg</a></td>
					        <td>132</td>
					        <td><input type="checkbox" name="delete" value="2"></td>
					    </tr>
					    <tr>
					        <td><a href="file.jsp?id=X"><span class="glyphicon glyphicon-file"></span> doku.docx</a></td>
					        <td>78</td>
					        <td><input type="checkbox" name="delete" value="12"></td>
					    </tr>
					    <tr>
					        <td><a href="file.jsp?id=X"><span class="glyphicon glyphicon-file"></span> foto.png</a></td>
					        <td>108</td>
					        <td><input type="checkbox" name="delete" value="12312"></td>
					    </tr>
				</tbody>
				</table>
				<div class="form-group">
					<label for="fileUpload"><span class="glyphicon glyphicon-file"></span> Neue Datei hochladen</label>
					<input type="file" id="fileUpload" name="fileUpload">
					<p class="help-block">Die Datei wird mit dem Speichern &uuml;bernommen.</p>
				</div>
				
				<c:if test="${param.mode == 'new'}">
					<input type="hidden" name="mode" value="new">
				</c:if>
				<c:if test="${param.mode == 'edit'}">
					<input type="hidden" name="mode" value="edit">
				</c:if>
				
				<input type="hidden" name="team" value="${task.team.id}">
				
				<div class="form-group">
					<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> ${submit_button}</button>
					<c:if test="${param.mode == 'new'}">
						<a class="btn btn-default" href="/team?mode=view&id=${task.gruppe.team.id}"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<a class="btn btn-default" href="/task?mode=view&id=${task.id}"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="task?mode=remove&id=${task.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			<jsp:include page="../sidebar.jsp" />

<jsp:include page="../footer.jsp" />