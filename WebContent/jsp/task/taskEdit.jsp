<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<c:if test="${param.mode == 'new'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Aufgabe erstellen" /></jsp:include>
</c:if>
<c:if test="${param.mode == 'edit'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${task.name}" /></jsp:include>
</c:if>

<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
			<c:if test="${param.mode == 'new'}">
				<ol class="breadcrumb">
					<li><a href="/">Start</a></li>
					<li><a href="/team?mode=view&id=${team.id}">${team.name}</a></li>
					<li class="active"></li>
				</ol>
			</c:if>
			
			<c:if test="${param.mode == 'edit'}">
				<ol class="breadcrumb">
					<li><a href="/">Start</a></li>
					<li><a href="/team?mode=view&id=${task.gruppe.team.id}">${task.gruppe.team.name}</a></li>
					<li class="active"></li>
				</ol>
			</c:if>
			
			
			<%-- Alerts, falls übergeben --%>
			<c:if test="${requestScope.alert != null}">
	  			<c:if test="${requestScope.alert_mode == null}">
	  				<div class="alert alert-success">${requestScope.alert}</div>
	  			</c:if>
	  			<c:if test="${requestScope.alert_mode != null}">
	  				<div class="alert alert-${requestScope.alert_mode}">${requestScope.alert}</div>
	  			</c:if>
	  		</c:if>
		
			<c:if test="${param.mode == 'new'}"><h1>Aufgabe erstellen</h1></c:if>
			<c:if test="${param.mode == 'edit'}"><h1>Aufgabe bearbeiten</h1></c:if>
			
			<form class="form" action="/task" method="post">
		  		<div class="form-group col-xs row">
		  			<div class="col-md-6">
		  				<label for="name"><span class="glyphicon glyphicon-time"></span> Name</label>
						<input id="name" name="name" type="text" class="form-control input-lg" value="${task.name}" />
		  			</div>
		  			<div class="col-md-6">
		  				<label for="group"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppe</label>
						<select name="group" size="1" class="form-control input-lg">
							<c:forEach var="group" items="${taskGroups}">
								<c:if test="${group.id == task.gruppe.id}">
									<option value="${group.id}" selected>${group.name}</option>
								</c:if>
								<c:if test="${group.id != task.gruppe.id}">
									<option value="${group.id}">${group.name}</option>
								</c:if>
							</c:forEach>
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
				    		<c:if test="${param.mode == 'new'}">
				    			<p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy" value="${today}" /></p>
				    		</c:if>
				    		<c:if test="${param.mode == 'edit'}">
				    			<p class="form-control-static"><fmt:formatDate pattern="dd.MM.yyyy" value="${task.erstellungsdatumAsDate}" /></p>
				    		</c:if>
				    	</div>
				    	<div class="col-xs-4">
				    		<label for="deadline"><span class="glyphicon glyphicon-bell"></span> Deadline</label>
							<input id="deadline" name="deadline" type="date" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${task.deadlineAsDate}" />" />
				    	</div>
				    	<div class="col-xs-4">
				    		<label for="status"><span class="glyphicon glyphicon-dashboard"></span> Status</label> <small>(%)</small>
							<input id="status" name="status" type="number" min="0" max="100" class="form-control" value="${task.status}" />								
				    	</div>
				</div>
				<div class="form-group col-xs">
					<label for="users"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <span class="badge" data-toggle="tooltip" data-placement="right" data-original-title="Mehrere Mitglieder durck Gedr&uuml;ckthalten von STRG bzw. CMD markieren">?</span>
					<select multiple name="users" id="users" size="3" class="form-control">
						<c:forEach var="userSelected" items="${usersSelected}">
							<option value="${userSelected.id}" selected>${userSelected.username}</option>
						</c:forEach>
						<c:forEach var="user" items="${users}">
							<option value="${user.id}">${user.username}</option>
						</c:forEach>
					</select>
				</div>
				
				<table class="table table-hover col-xs">
		  			<thead>
		  			<tr>
				        <th><span class="glyphicon glyphicon-paperclip"></span> Dateien</th>
				        <th>Gr&ouml;&szlig;e</th>
				        <th>L&ouml;schen</th>
				    </tr>
				    </thead>
		  			<tbody>
		  				<c:if test="${fn:length(files) > 0}">
		  					<c:forEach var="file" items="${files}">
			  					<tr>
							        <td><a href="file?mode=view&id=${file.id}"><span class="glyphicon glyphicon-file"></span> ${file.name}</a></td>
							        <td>XY KB</td><%-- // TODO --%>
							        <td><input type="checkbox" name="delete" value="${file.id}"></td>
							    </tr>
			  				</c:forEach>
		  				</c:if>
		  				<c:if test="${fn:length(files) == 0}">
		  					<tr><td colspan="3">Sie haben noch keine Dateien zu dieser Aufgabe zugeordnet. Dies k&ouml;nnen Sie in den jeweiligen Dateieigenschaften tun.</td></tr>
		  				</c:if>
					</tbody>
				</table>
				
				<c:if test="${param.mode == 'new'}"><input type="hidden" name="mode" value="new" /></c:if>
				<c:if test="${param.mode == 'edit'}"><input type="hidden" name="mode" value="edit" /></c:if>
				<input type="hidden" name="team" value="${task.id}" />
				
				<div class="form-group col-xs">
					<c:if test="${param.mode == 'new'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Erstellen</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
						<a href="/task?mode=view&id=${task.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="/task?mode=remove&id=${task.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			<jsp:include page="../sidebar.jsp" />

<jsp:include page="../footer.jsp" />