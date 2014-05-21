<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<c:if test="${param.mode == 'edit'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${team.name}" /></jsp:include>
</c:if>
<c:if test="${param.mode == 'new'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Team erstellen" /></jsp:include>
</c:if>

<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
			<%-- Team bearbeiten/erstellen --%>
			<c:if test="${param.mode == 'edit'}"><h1>Team bearbeiten</h1></c:if>
			<c:if test="${param.mode == 'new'}"><h1>Team erstellen</h1></c:if>
			
			<form class="form" action="${pageContext.request.contextPath}/team" method="post">
		  		<div class="form-group col-xs row">
		  			<div class="col-md-6">
		  				<label for="name"><span class="glyphicon glyphicon-briefcase"></span> Teamname*</label>
						<input id="name" name="name" type="text" class="form-control input-lg" value='${team.name}' />
		  			</div>
		  			<div class="col-md-6">
		  				<label for="manager"><span class="glyphicon glyphicon-user"></span> Teammanager*</label>
						<c:if test="${param.mode == 'new'}">
							<p class="form-control-static">Sie sind vorerst der Teammanager.<br />Dies k&ouml;nnen Sie sp&auml;ter &auml;ndern.</p>
						</c:if>
						<c:if test="${param.mode == 'edit'}">
							<select name="manager" size="1" class="form-control input-lg">					
								<c:forEach var="user" items="${users}">
									<c:if test="${user.id == team.gruppenfuehrer.id}">
										<option value="${user.id}" selected>${user.username}</option>
									</c:if>
									<c:if test="${user.id != team.gruppenfuehrer.id}">
										<option value="${user.id}">${user.username}</option>
									</c:if>
								</c:forEach>
							</select>
						</c:if>
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="description"><span class="glyphicon glyphicon-align-left"></span> Slogan / Beschreibung</label>
					<textarea id="description" name="description" class="form-control" rows="2">${team.beschreibung}</textarea>
				</div>
				<div class="form-group col-xs">
					<label for="users"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <span class="badge" data-toggle="tooltip" data-placement="right" data-original-title="W&auml;hlen Sie hier die Mitglieder f&uuml;r Ihr Team aus.">?</span>
					<div class="checkbox">
						<c:forEach var="user" items="${users}">
							<label><input type="checkbox" name="users" value="${user.id}" checked> ${user.username} (${user.vorname} ${user.nachname}) </label><br />
						</c:forEach>
						<c:forEach var="userRest" items="${usersRest}">
							<c:if test="${currentUser != userRest.id}">
								<label><input type="checkbox" name="users" value="${userRest.id}"> ${userRest.username} (${userRest.vorname} ${userRest.nachname}) </label><br />
							</c:if>
						</c:forEach>
					</div>
				</div>
				
				<c:if test="${param.mode == 'new'}"><input type="hidden" name="mode" value="new" /></c:if>
				<c:if test="${param.mode == 'edit'}">
					<input type="hidden" name="mode" value="edit" />
					<input type="hidden" name="id" value="${team.id}" />
				</c:if>
				
				<div class="form-group">
					<c:if test="${param.mode == 'new'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Erstellen</button>
						<a href="${pageContext.request.contextPath}/" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
						<a href="${pageContext.request.contextPath}/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="${pageContext.request.contextPath}/team?mode=remove&id=${team.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />
