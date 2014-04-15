<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>
			
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
			
			</div><%-- Ende content --%>
			<%@ include file="jsp/sidebar.jsp" %>
<%@ include file="jsp/footer.jsp" %>