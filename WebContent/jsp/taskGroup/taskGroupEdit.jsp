<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<c:if test="${param.mode == 'new'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Aufgabengruppe erstellen" /></jsp:include>
</c:if>
<c:if test="${param.mode == 'edit'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${taskGroup.name}" /></jsp:include>
</c:if>

<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
			<ol class="breadcrumb">
				<li><a href="/">Start</a></li>
				<li><a href="/team?mode=view&id=${team.id}">${team.name}</a></li>
				<li class="active"></li>
			</ol>
			
			<c:if test="${param.mode == 'new'}"><h1>Aufgabengruppe erstellen</h1></c:if>
			<c:if test="${param.mode == 'edit'}"><h1>Aufgabengruppe bearbeiten</h1></c:if>
			<form class="form" action="/taskGroup" method="post">
		  		<div class="form-group col-xs row">
		  			<div class="col-xs-6">
		  				<label for="name"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppenname*</label>
						<input type="text" id="name" name="name" class="form-control input-lg" value="${taskGroup.name}" />
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="description"><span class="glyphicon glyphicon-align-left"></span> Beschreibung</label>
					<textarea id="description" name="description" class="form-control" rows="2">${taskGroup.beschreibung}</textarea>
				</div>
				<div class="form-group col-xs">
					<c:if test="${param.mode == 'new'}">
						<input type="hidden" name="teamId" value="${team.id}" />
						<input type="hidden" name="mode" value="new" />
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Erstellen</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<input type="hidden" name="id" value="${taskGroup.id}" />
						<input type="hidden" name="mode" value="edit" />
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="/taskGroup?mode=remove&id=${taskGroup.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Aufgabengruppe l&ouml;schen</a>
					</c:if>
				</div>
			</form>
				
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />