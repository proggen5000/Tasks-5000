<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%-- Team erstellen --%>
	<c:if test="${login and param.mode == 'new'}">
		<c:set var="name" scope="page" value="" />
		<c:set var="slogan" scope="page" value="" />
		
		<c:set var="title" scope="page" value="Team erstellen" />
		<c:set var="submit_button" scope="page" value="Erstellen" />
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Team bearbeiten/ansehen --%>
	<c:if test="${login and param.id != null and (param.mode == 'edit' or param.mode == 'view')}">
		<c:set var="name" scope="page" value="Microsoft Windows Core Development Team" />
		<c:set var="slogan" scope="page" value="Hier k&ouml;nnte ein toller Slogan stehen." />
		
		<c:set var="title" scope="page" value="Team bearbeiten"/>
		<c:set var="submit_button" scope="page" value="Speichern"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Team löschen --%>
	<c:if test="${login and param.id != null and param.mode == 'remove'}">
		<c:set var="name" scope="page" value="Microsoft Windows Core Development Team" />
		
		<c:set var="title" scope="page" value="Team l&ouml;schen"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${name}" /></jsp:include>
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

			<%-- Team ansehen --%>
			<c:if test="${valid_request and param.mode == 'view'}">

		  		<h1>${name} <span class="glyphicon glyphicon-briefcase small"></span></h1>
		  		${slogan}
		  		<h2><a href="taskGroup.jsp?mode=edit&id=X" title="Aufgabengruppe bearbeiten">Windows 8.1</a></h2>
		  		<p>Alle Aufgaben und T&auml;tigkeiten zu Windows 8.1</p>
				<div class="list-group">
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker<br /><span class="glyphicon glyphicon-file"></span> 2</div>
						<h4 class="list-group-item-heading">Design m&ouml;glichst h&auml;sslich gestalten</h4>
						<p class="list-group-item-text">Wir sollten das Design von Win 8.1 so schlimm wie m...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Felix Fichte<br /><span class="glyphicon glyphicon-file"></span> 4</div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> xXx_bastman5000_xXx<br /><span class="glyphicon glyphicon-file"></span> 129</div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress">
							<span class="badge">?</span>
						</div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 2</div>
						<h4 class="list-group-item-heading">Marketing betreiben</h4>
						<p class="list-group-item-text">Irgend wie m&uuml;ssen wir dies und das tun, um dan...</p>
					</a>
				</div>
		  		
		  		<h2><a href="taskGroup.jsp?mode=edit&id=X" title="Aufgabengruppe bearbeiten">Windows 9</a></h2>
		  		<p>Alle Aufgaben und T&auml;tigkeiten zu Windows 9, welches 2015 erscheinen wird</p>
		  		<div class="list-group">
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 0</div>
						<h4 class="list-group-item-heading">Design m&ouml;glichst h&auml;sslich gestalten</h4>
						<p class="list-group-item-text">Wir sollten das Design von Win 8.1 so schlimm wie m...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 12</div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 13</div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress">
							<span class="badge">?</span>
						</div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 1</div>
						<h4 class="list-group-item-heading">Marketing betreiben</h4>
						<p class="list-group-item-text">Irgend wie m&uuml;ssen wir dies und das tun, um dan...</p>
					</a>
				</div>

			</div><%-- Ende content --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
					<div class="list-group">
						<a href="task.jsp?mode=new&team=X" class="list-group-item"><span class="glyphicon glyphicon-time"></span> Aufgabe erstellen</a>
						<a href="taskGroup.jsp?mode=new&team=X" class="list-group-item"><span class="glyphicon glyphicon-tag"></span> Aufgabengruppe erstellen</a>
					</div>
					<div class="list-group">
						<a href="team.jsp?mode=edit&id=X" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Team bearbeiten</a>
						<a href="team.jsp?mode=remove&id=X" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
						<a href="profile.jsp?mode=leave&id=X" class="list-group-item"><span class="glyphicon glyphicon-log-out"></span> Team verlassen</a>
					</div>			
			
				<h1>Mitglieder</h1>
				<div class="list-group">
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker <span class="label label-default">Manager</span></a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Manuel Taya</a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Felix Fichte</a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Sebastian Herrmann</a>		
				</div>
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
			
		</c:if>
		
		<%-- Team bearbeiten/erstellen --%>
		<c:if test="${valid_request and (param.mode == 'edit' or param.mode == 'new')}">				
			<h1>${title}</h1>
			<form class="form" action="">
		  		<div class="form-group col-xs row">
		  			<div class="col-xs-6">
		  				<label for="name"><span class="glyphicon glyphicon-briefcase"></span> Teamname*</label>
						<input id="name" name="name" type="text" class="form-control input-lg" placeholder="" value="${name}">
		  			</div>
		  			<div class="col-xs-6">
		  				<label for="leader"><span class="glyphicon glyphicon-user"></span> Teammanager*</label>
						<select name="leader" size="1" class="form-control input-lg">
							<option value="1" selected>Sebastian Herrmann</option>
							<option value="34">Felix Fichte</option>
							<option value="2">Gunnar Lehker</option>
							<option value="74">Manuel Taya</option>
						</select>
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="slogan"><span class="glyphicon glyphicon-align-left"></span> Slogan / Beschreibung</label>
					<textarea id="slogan" name="slogan" class="form-control" rows="2">${slogan}</textarea>
				</div>
				<div class="form-group col-xs">
					<label for="members"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <small>(mehrere Mitglieder durck Gedr&uuml;ckthalten von <kbd>Strg</kbd> bzw. <kbd>Cmd</kbd> markieren)</small>
					<div class="row">
						<div class="col-xs-4">
							<select multiple name="members" id="members" size="8" class="form-control">
							</select>
						</div>
						
						<div class="col-xs-1" style="vertical-align: center;">
							<input type="button" id="btnLeft" class="btn btn-default" value="&lt;&lt;" />
        					<input type="button" id="btnRight" class="btn btn-default" value="&gt;&gt;" />
						</div>
						
						<div class="col-xs-4">
							<select multiple name="membersAll" id="membersAll" size="8" class="form-control">
								<option value="1">Felix Fichte</option>
								<option value="34">Gunnar Lehker</option>
								<option value="2">Manuel Taya</option>
								<option value="74">Sebastian Herrmann</option>
								<option value="11">Rainer Rumpel</option>
								<option value="79">Klaus Ringhand</option>
								<option value="102">Gert Faustmann</option>
							</select>
						</div>
					</div>
					
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> ${submit_button}</button>
					<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
					<c:if test="${param.mode == 'edit'}">
						<a href="team.jsp?mode=remove&id=X" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			<jsp:include page="jsp/sidebar.jsp" />
		
		</c:if>
		
		
		<%-- Team löschen --%>
		<c:if test="${valid_request and param.mode == 'remove'}">
			
			<h1>${title}</h1>
			<p>Sind Sie sicher, dass Sie das Team "<b>${name}</b>" dauerhaft l&ouml;schen m&ouml;chten?</p>
			<a class="btn btn-danger" href="team.jsp?mode=remove&id=X&sure=true"><span class="glyphicon glyphicon-ok"></span> Ja, l&ouml;schen</a>
			<a class="btn btn-default" href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
							
			<jsp:include page="jsp/sidebar.jsp" />
		</c:if>

<jsp:include page="jsp/footer.jsp" />