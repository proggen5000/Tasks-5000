<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Registrierung" /></jsp:include>
<jsp:include page="../menu.jsp" />

			<h1>Registrierung</h1>
			<p>Registrieren Sie sich mit einem einzigartigen Benutzernamen und Ihrer E-Mail-Adresse, um Tasks 5000 zu benutzen.<br />
	  		Falls Sie bereits ein Benutzerprofil angelegt haben, k&ouml;nnen Sie sich auf der rechten Seite unter "Login" anmelden.</p>
	  		
	  		<form class="form" action="${pageContext.request.contextPath}/login" method="post">
	  		  <div class="form-group">
			    <label for="username" data-toggle="tooltip" data-placement="right" data-original-title="Bitte w&auml;hlen Sie einen einzigartigen Benutzernamen.">
			    	<span class="glyphicon glyphicon-user"></span> Benutzername*
		    	</label>
			    <input type="text" class="form-control input-lg" name="username" id="username" />
			  </div>
			  <div class="form-group">
			    <label for="vorname"><span class="glyphicon glyphicon-user"></span> Echter Name</label>
			    <div class="row">
			    	<div class="col-xs-6">
			    		<input type="text" class="form-control" name="vorname" id="vorname" placeholder="Vorname" />
			    	</div>
			    	<div class="col-xs-6">
			    		<input type="text" class="form-control" name="nachname" id="Nachname" placeholder="Nachname" />
			    	</div>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="email" data-toggle="tooltip" data-placement="right" data-original-title="Ihre E-Mail-Adresse dient zum Zusenden Ihrer Registrierungsbest&auml;tigung und ggf. zum Zur&uuml;cksetzen Ihres Passworts.">
			    	<span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*
		    	</label>
			    <input type="email" class="form-control" name="email" id="email" placeholder="benutzer@email.de" />
			  </div>
			  <div class="form-group">
			    <label for="password" data-toggle="tooltip" data-placement="right" data-original-title="Ihr Passwort wird verschl&uuml;sselt gespeichert.">
			    	<span class="glyphicon glyphicon-lock"></span> Passwort*
		    	</label>
			    <div class="row">
			    	<div class="col-xs-6">
			    		<input type="password" class="form-control" name="password" id="password" placeholder="Passwort" />
			    	</div>
			    	<div class="col-xs-6">
			    		<input type="password" class="form-control" name="passwordRepeat" id="passwordRepeat" placeholder="Passwort wiederholen" />
			    	</div>
		    	</div>
			  </div>
			  
			  <input type="hidden" name="mode" value="register" />
			  
			  <div class="form-group">
			    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Registrieren</button>
			    <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
			  </div>
			</form>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />