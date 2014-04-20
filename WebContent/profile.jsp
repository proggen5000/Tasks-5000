<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%-- Profil ansehen --%>
	<c:if test="${login and param.mode == 'view'}">
		<c:set var="username" scope="page" value="herrherrmann"/>
		<c:set var="realname" scope="page" value="Sebastian Herrmann"/>
		<c:set var="reg_date" scope="page" value="03.02.2014"/>
		<c:set var="teams" scope="page" value=""/> <%-- ??? --%>
		
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Profil bearbeiten --%>
	<c:if test="${login and param.mode == 'edit'}">
		<c:set var="username" scope="page" value="herrherrmann"/>
		<c:set var="firstname" scope="page" value="Sebastian"/>
		<c:set var="lastname" scope="page" value="Herrmann"/>
		<c:set var="email" scope="page" value="basti@email.de"/>
	
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Profil löschen --%>
	<c:if test="${login and param.mode == 'remove'}">
		<c:set var="username" scope="page" value="herrherrmann"/>
	
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Team verlassen --%>
	<c:if test="${login and param.mode == 'leave'}">
		<c:set var="name" scope="page" value="Microsoft Windows Core Development Team"/>
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="${username}" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />

			<c:if test="${valid_request != true}">
				<div class="alert alert-danger">Bitte rufen Sie diese Seite &uuml;ber eine g&uuml;ltige Verkn&uuml;pfung auf!</div>
			</c:if>

			<%-- Profil ansehen --%>
			<c:if test="${valid_request and param.mode == 'view'}">
				<h1>${username} <span class="glyphicon glyphicon-user small"></span></h1>
				<dl class="dl-horizontal">
					<dt>Echter Name</dt><dd>${realname}</dd>
					<dt>Mitglied seit</dt><dd>${reg_date}</dd>
					<dt>Mitglied in</dt>
						<dd><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Microsoft Windows Core Development Team</a></dd>
						<dd><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Team X</a></dd>
						<dd><a href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-briefcase"></span> Team Y</a></dd>
				</dl>
				<a href="profile.jsp?mode=edit" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> Profil bearbeiten</a>
			</c:if>
			<%-- falls eigenes Team --%>
			
			<%-- Profil bearbeiten --%>
			<c:if test="${valid_request == true and param.mode == 'edit'}">
				<h1>Profil bearbeiten</h1>
				<form class="form" role="form">
			  		  <div class="form-group col-xs">
					    <label for="username"><span class="glyphicon glyphicon-user"></span> Benutzername</label>
					    <input type="text" class="form-control input-lg" id="username" placeholder="" value="herrherrmann">
					  </div>
					  <div class="form-group">
					    <label for="vorname"><span class="glyphicon glyphicon-user"></span> Echter Name</label>
					    <div class="row">
					    	<div class="col-xs-6">
					    		<input type="text" class="form-control" id="vorname" placeholder="Vorname" value="Sebastian">
					    	</div>
					    	<div class="col-xs-6">
					    		<input type="text" class="form-control" id="Nachname" placeholder="Nachname" value="Herrmann">
					    	</div>
					    </div>
					  </div>
					  <div class="form-group">
					    <label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse</label>
					    <input type="text" class="form-control" id="email" placeholder="" value="basti@email.de">
					  </div>
					  <div class="form-group">
					  	<label for="newPassword"><span class="glyphicon glyphicon-lock"></span> Neues Passwort</label>
					    <div class="row">
					    	<div class="col-xs-6">
					    		<input type="password" class="form-control" id="newPassword" placeholder="Passwort">
					    	</div>
					    	<div class="col-xs-6">
					    		<input type="password" class="form-control" id="newPasswordRepeat" placeholder="Passwort wiederholen">
					    	</div>
				    	</div>
					  </div>
					  <div class="form-group">
					    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
					    <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
						<a href="profile.jsp?mode=remove&id=X" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Profil l&ouml;schen</a>
					  </div>
				</form>
			</c:if>
			
			<%-- Profil löschen --%>
			<c:if test="${valid_request and param.mode == 'remove'}">
				<h1>Profil l&ouml;schen</h1>
				<p>Sind Sie sicher, dass Sie Ihr Tasks 5000-Profil endg&uuml;ltig l&ouml;schen m&ouml;chten? Es kann danach nicht wiederhergestellt werden, zudem werden gewisse mit Ihnen verkn&uuml;pfte Elemente gel&ouml;scht:</p>
				<ul>
					<li>alle Teams, in denen Sie Teammanager sind</li>
					<li>alle Verkn&uuml;pfungen zu Ihren Aufgaben und Dateien</li>
				</ul>
				<a class="btn btn-danger" href="profile.jsp?mode=remove&sure=true"><span class="glyphicon glyphicon-ok"></span> Ja, mein Profil f&uuml;r immer l&ouml;schen</a>
				<a class="btn btn-default" href="profile.jsp?mode=edit"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
			</c:if>
			
			<%-- Team verlassen --%>
			<c:if test="${valid_request and param.mode == 'leave'}">
				<h1>Team verlassen</h1>
				<p>Sind Sie sicher, dass Sie das Team "<b>${name}</b>" endg&uuml;ltig verlassen m&ouml;chten?</p>
				<a class="btn btn-danger" href="profile.jsp?mode=leave&sure=true"><span class="glyphicon glyphicon-ok"></span> Ja, Team verlassen</a>
				<a class="btn btn-default" href="team.jsp?mode=view&id=X"><span class="glyphicon glyphicon-remove"></span> Nein, abbrechen</a>
			</c:if>
			
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />