<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.mode == 'new'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Datei hochladen" /></jsp:include>
</c:if>
<c:if test="${param.mode == 'edit'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${file.name}" /></jsp:include>
</c:if>

<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
			<ol class="breadcrumb">
				<li><a href="/">Start</a></li>
				<li><a href="/team?mode=view&id=${team.id}">${team.name}</a></li>
				<li class="active"></li>
			</ol>

			<c:if test="${param.mode == 'new'}"><h1>Datei hochladen</h1></c:if>
			<c:if test="${param.mode == 'edit'}"><h1>${file.name}</h1></c:if>
			
			<form class="form" action="/file" method="post" enctype="multipart/form-data">
		  		<div class="form-group col-xs row">
		  			<div class="col-md-6">
		  				<label for="name"><span class="glyphicon glyphicon-file"></span> Name</label>
						<input id="name" name="name" type="text" class="form-control input-lg" value="${file.name}">
		  			</div>
		  			<div class="col-md-6">
		  				<label for="task"><span class="glyphicon glyphicon-tag"></span> Aufgabe (Zuordnung)</label>
						<select name="task" size="1" class="form-control input-lg">
							<c:forEach var="task" items="${tasks}">
								<c:if test="${task.id == file.task.id}">
									<option value="${task.id}" selected>${task.name}</option>
								</c:if>
								<c:if test="${task.id != file.task.id}">
									<option value="${task.id}">${task.name}</option>
								</c:if>
							</c:forEach>
						</select>
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="description"><span class="glyphicon glyphicon-align-left"></span> Beschreibung</label>
					<textarea id="description" name="description" class="form-control" rows="5">${file.beschreibung}</textarea>
				</div>
				
				<div class="form-group col-xs">
					<label for="fileUpload"><span class="glyphicon glyphicon-file"></span> Datei</label>
					<input type="file" id="fileUpload" name="fileUpload" accept=".png,.jpg,.gif,.txt,.pdf,.doc,.docx,.zip,.rar" />
					<p class="help-block">Maximal erlaubte Dateigr&ouml;&szlig;e: 2 MB<br />Erlaubte Dateitypen: .png, .jpg, .gif, .txt, .pdf, .doc, .docx, .zip und .rar</p>
				</div>
				
				<c:if test="${param.mode == 'new'}"><input type="hidden" name="mode" value="newTest" /></c:if>
				<c:if test="${param.mode == 'edit'}"><input type="hidden" name="mode" value="editTest" /></c:if>
				<input type="hidden" name="team" value="${team.id}" />
				
				<div class="form-group">
					<c:if test="${param.mode == 'new'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Hochladen</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="/file?mode=remove&id=${file.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Datei l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			<jsp:include page="../sidebar.jsp" />

<jsp:include page="../footer.jsp" />