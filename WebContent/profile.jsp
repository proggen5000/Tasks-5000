<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="jsp/header.jsp" />
<jsp:include page="jsp/menu.jsp" />

	<%-- Profil ansehen --%>
	<c:if test="${param.mode == 'view'}">
		<c:set var="username" scope="page" value="herrherrmann"/>
		<c:set var="realname" scope="page" value="Sebastian Herrmann"/>
		<c:set var="reg_date" scope="page" value="03.02.2014"/>
		<c:set var="teams" scope="page" value=""/> <%-- ??? --%>
		
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
	<%-- Profil bearbeiten --%>
	<c:if test="${param.mode == 'edit'}">
		<c:set var="username" scope="page" value="herrherrmann"/>
		<c:set var="firstname" scope="page" value="Sebastian"/>
		<c:set var="lastname" scope="page" value="Herrmann"/>
		<c:set var="email" scope="page" value="basti@email.de"/>
	
		<c:set var="valid_request" scope="page" value="true" />
	</c:if>
	
			<c:if test="${valid_request != true}">
				<div class="alert alert-danger">Bitte rufen Sie diese Seite &uuml;ber eine g&uuml;ltige Verkn&uuml;pfung auf!</div>
			</c:if>

			<%-- Profil ansehen --%>
			<c:if test="${valid_request == true and param.mode == 'view'}">
				<h1>${username} <span class="glyphicon glyphicon-user small"></span></h1>
				<dl class="dl-horizontal">
					<dt>Echter Name</dt><dd>${realname}</dd>
					<dt>Mitglied seit</dt><dd>${reg_date}</dd>
					<dt>Mitglied in</dt>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Microsoft Windows Core Development Team</a></dd>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Team X</a></dd>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Team Y</a></dd>
				</dl>
			</c:if>
			
			<%-- Profil bearbeiten --%>
			<c:if test="${valid_request == true and param.mode == 'edit'}">
				<h1>Profil &auml;ndern</h1>
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
					  </div>
				</form>
			</c:if>
			
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />