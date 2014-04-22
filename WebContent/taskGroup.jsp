<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Aufgabengruppe erstellen --%>
	<c:if test="${login and param.mode == 'new'}">
		<c:set var="name" scope="page" value="" />
		<c:set var="description" scope="page" value="" />
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team" />
		
		<c:set var="title" scope="page" value="Aufgabengruppe erstellen" />
		<c:set var="submit_button" scope="page" value="Erstellen" />
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Aufgabengruppe bearbeiten --%>
	<c:if test="${login and param.id != null and (param.mode == 'edit' or param.mode == 'view') }">
		<c:set var="name" scope="page" value="Windows 8.1" />
		<c:set var="description" scope="page" value="Alle Aufgaben und T&auml;tigkeiten zu Windows 8.1" />
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team" />
		
		<c:set var="title" scope="page" value="Aufgabengruppe bearbeiten"/>
		<c:set var="submit_button" scope="page" value="Speichern"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Aufgabengruppe entfernen --%>
	<c:if test="${login and param.id != null and param.mode == 'remove'}">
		<c:set var="name" scope="page" value="Windows 8.1" />
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team" />
		
		<c:set var="title" scope="page" value="Aufgabengruppe l&ouml;schen"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>


<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${title}" /></jsp:include>
<jsp:include page="jsp/menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

		  		<%-- Zugriff verweigert --%>
				<c:if test="${!valid_request}">
					<h1>Fehler beim Zugriff</h1>
					<p class="alert alert-danger">
						Sie haben keinen Zugriff auf diese Seite. Bitte stellen Sie sicher, dass Sie eingeloggt und Mitglied des angeforderten Teams sind.<br />
						Rufen Sie diese Seite ausschlie&szlig;lich &uuml;ber Links aus dem System auf.
					</p>
					<a href="index.jsp" class="btn btn-primary">Zur&uuml;ck zur Startseite</a>
				</c:if>
		
				<%-- Aufgabengruppe bearbeiten/erstellen --%>
				<c:if test="${valid_request and (param.mode == 'edit' or param.mode == 'new')}">
					<ol class="breadcrumb">
						<li><a href="index.jsp">Start</a></li>
						<li><a href="team.jsp?mode=view&id=X">${team}</a></li>
						<li class="active"></li>
					</ol>
								
					<h1>${title}</h1>
					<form class="form" action="">
				  		<div class="form-group col-xs row">
				  			<div class="col-xs-6">
				  				<label for="name"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppenname*</label>
								<input id="name" name="name" type="text" class="form-control input-lg" placeholder="" value="${name}">
				  			</div>
						</div>
						<div class="form-group col-xs">
							<label for="description"><span class="glyphicon glyphicon-align-left"></span> Beschreibung</label>
							<textarea id="description" name="description" class="form-control" rows="2">${description}</textarea>
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> ${submit_button}</button>
							<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
							<c:if test="${param.mode == 'edit'}">
								<a href="taskGroup.jsp?mode=remove&id=X" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Aufgabengruppe l&ouml;schen</a>
							</c:if>
						</div>
					</form>
				</c:if>
				
				
				<%-- Aufgabengruppe entfernen --%>
				<c:if test="${valid_request and param.mode == 'remove'}">
					
					<ol class="breadcrumb">
						<li><a href="index.jsp">Start</a></li>
						<li><a href="team.jsp?mode=view&id=X">${team}</a></li>
						<li class="active"></li>
					</ol>
					
					<h1>${title}</h1>
					<p>Sind Sie sicher, dass Sie die Aufgabengruppe "<b>${name}</b>" entfernen m&ouml;chten?</p>
					<p>Dadurch werden auch <b>alle darin enthaltenen Aufgaben mitgel&ouml;scht</b>!</p>
					<a class="btn btn-danger" href="taskGroup.jsp?mode=remove&id=X&sure=true"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</a>
					<a class="btn btn-default" href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
				</c:if>
					
				<jsp:include page="jsp/sidebar.jsp" />
		
<jsp:include page="jsp/footer.jsp" />