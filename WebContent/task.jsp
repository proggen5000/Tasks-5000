<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="jsp/header.jsp" %>
<jsp:include page="jsp/menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

	<%-- Aufgabe erstellen --%>
	<c:if test="${param.mode == 'new'}">
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team"/>
		<c:set var="group" scope="page" value="Windows 8.1"/>
		<c:set var="name" scope="page" value=""/>
		<c:set var="description" scope="page" value=""/>
		<c:set var="date" scope="page" value=""/>
		<c:set var="deadline" scope="page" value=""/>
		<c:set var="status" scope="page" value=""/>
		<c:set var="members" scope="page" value=""/> <%-- ??? --%>
		
		<c:set var="title" scope="page" value="Aufgabe erstellen"/>
		<c:set var="submit_button" scope="page" value="Erstellen"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Aufgabe bearbeiten/ansehen --%>
	<c:if test="${param.mode == 'edit' or param.mode == 'view'}">
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team"/>
		<c:set var="group" scope="page" value="Windows 8.1"/>
		<c:set var="name" scope="page" value="Banoodle-Aufgabe"/>
		<c:set var="description" scope="page" value="Hallo, hier steht eine bisherige HTML-Beschreibung."/>
		<c:set var="date" scope="page" value="2014-04-01"/>
		<c:set var="deadline" scope="page" value="2014-04-14"/>
		<c:set var="status" scope="page" value="33"/>
		<c:set var="members" scope="page" value=""/> <%-- ??? --%>
		
		<c:set var="title" scope="page" value="Aufgabe bearbeiten"/>
		<c:set var="submit_button" scope="page" value="Speichern"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Aufgabe entfernen --%>
	<c:if test="${param.mode == 'remove'}">
		<c:set var="team" scope="page" value="Microsoft Windows Core Development Team"/>
		<c:set var="group" scope="page" value="Windows 8.1"/>
		<c:set var="name" scope="page" value="Banoodle-Aufgabe"/>
		
		<c:set var="title" scope="page" value="Aufgabe l&ouml;schen"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>

			<c:if test="${valid_request != true}">
				<div class="alert alert-danger">Bitte rufen Sie diese Seite &uuml;ber eine g&uuml;ltige Verkn&uuml;pfung auf!</div>
			</c:if>

			
			<%-- Aufgabe ansehen --%>
			<c:if test="${valid_request == true and param.mode == 'view'}">
				
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp">${team}</a></li>
					<li>${group}</li>
				</ol>
				
				<h1>${name} <span class="glyphicon glyphicon-time small"></span></h1>
				<p>${description}</p>
				
				<%-- prüfen, ob Dateien vorhanden --%>
				<div class="panel panel-default">
					<div class="panel-heading"><h3 class="panel-title"><span class="glyphicon glyphicon-paperclip"></span> Dateien</h3></div>
					<div class="panel-body">
						<span class="glyphicon glyphicon-file"></span> <a href="#">meinedatei.docx</a> (72 KB)<br />
						<span class="glyphicon glyphicon-file"></span> <a href="#">einbild.jpg</a> (205 KB)
					</div>
				</div>
				
				</div><%-- Ende content --%>
				<div class="sidebar col-sm-3">
					<h1>Aktionen</h1>
						<div class="list-group">
							<a href="task.jsp?mode=edit&id=XX" class="list-group-item list-group-item-info"><span class="glyphicon glyphicon-pencil"></span> Aufgabe bearbeiten</a>
							<a href="task.jsp?mode=remove&id=XX" class="list-group-item list-group-item-danger"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
						</div>
						<div class="list-group">
							<a href="file.jsp?mode=new&task=X" class="list-group-item"><span class="glyphicon glyphicon-file"></span> Datei hochladen</a>
						</div>	
				
					<h1>Details</h1>
						<div class="list-group">
							<div class="list-group-item"><span class="glyphicon glyphicon-calendar"></span> ${date}</div>
							<div class="list-group-item"><span class="glyphicon glyphicon-bell"></span> ${deadline}</div>
							<div class="list-group-item"><span class="glyphicon glyphicon-dashboard"></span> Status: ${status}%</div>
						</div>
						<div class="list-group">
							<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker <span class="label label-default">Ersteller</span></a>
							<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Felix Fichte</a>
						</div>
				</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
			</c:if>
		
			<%-- Aufgabe bearbeiten/erstellen --%>
			<c:if test="${valid_request == true and (param.mode == 'edit' or param.mode == 'new')}">
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp">${team}</a></li>
				</ol>
				
				<h1>${title}</h1>
				<form class="form" role="form">
			  		<div class="form-group col-xs row">
			  			<div class="col-xs-6">
			  				<label for="name"><span class="glyphicon glyphicon-time"></span> Name</label>
							<input id="name" name="name" type="text" class="form-control input-lg" placeholder="" value="${name}">
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
						<textarea id="description" name="description" class="form-control" rows="5">${description}</textarea>
					</div>
					<div class="form-group col-xs row">
					    	<div class="col-xs-4">
					    		<label><span class="glyphicon glyphicon-calendar"></span> Erstellungsdatum</label>
					    		<p class="form-control-static">${date}</p>
					    	</div>
					    	<div class="col-xs-4">
					    		<label for="deadline"><span class="glyphicon glyphicon-bell"></span> Deadline</label>
								<input id="deadline" name="deadline" type="date" class="form-control" value="${deadline}">
					    	</div>
					    	<div class="col-xs-4">
					    		<label for="status"><span class="glyphicon glyphicon-dashboard"></span> Status</label> <small>(%)</small>
								<input id="status" name="status" type="number" min="0" max="100" class="form-control" value="${status}">
					    	</div>
					</div>
					<div class="form-group col-xs">
						<label for="members"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <small>(mehrere Mitglieder durck Gedr&uuml;ckthalten von Strg markieren)</small>
						<select multiple name="members" id="members" size="3" class="form-control">
							<option value="1">Felix Fichte</option>
							<option value="34">Gunnar Lehker</option>
							<option value="2">Manuel Taya</option>
							<option value="74">Sebastian Herrmann</option>
							<option value="74">x</option>
							<option value="74">y</option>
							<option value="74">z</option>
						</select>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> ${submit_button}</button>
						<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
					</div>
				</form>
				<%@ include file="jsp/sidebar.jsp" %>
			
			</c:if>
			
			
			<%-- Aufgabe entfernen --%>
			<c:if test="${valid_request == true and param.mode == 'remove'}">
				
				<ol class="breadcrumb">
					<li><a href="index.jsp">Start</a></li>
					<li><a href="team.jsp">${team}</a></li>
					<li>${group}</li>
				</ol>
				
				<h1>${title}</h1>
				<p>Sind Sie sicher, dass Sie die Aufgabe "<b>${name}</b>" entfernen m&ouml;chten?</p>
				<a class="btn btn-danger" href="task.jsp?remove=view&id=X&sure=true"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</a>
				<a class="btn btn-default" href="task.jsp?mode=view&id=X"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
								
				</div><%-- Ende content --%>
				<div class="sidebar col-sm-3">
				</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
			</c:if>

<%@ include file="jsp/footer.jsp" %>