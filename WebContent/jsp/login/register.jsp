<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Registrieren" /></jsp:include>
<jsp:include page="../menu.jsp" />
		  		
		  		<h1>Registrierung</h1>
		  		<p>Registrieren Sie sich mit einem einzigartigen Benutzernamen und Ihrer E-Mail-Adresse, um Tasks 5000 zu benutzen.<br />
		  		Falls Sie bereits ein Benutzerprofil angelegt haben, k&ouml;nnen Sie sich auf der rechten Seite unter "Login" anmelden.</p>
		  		
		  		<form class="form" action="/login" method="post">
		  		  <div class="form-group">
				    <label for="username"><span class="glyphicon glyphicon-user"></span> Benutzername*</label>
				    <input type="text" class="form-control input-lg" id="username" placeholder="">
				  </div>
				  <div class="form-group">
				    <label for="vorname"><span class="glyphicon glyphicon-user"></span> Echter Name</label>
				    <div class="row">
				    	<div class="col-xs-6">
				    		<input type="text" class="form-control" id="vorname" placeholder="Vorname">
				    	</div>
				    	<div class="col-xs-6">
				    		<input type="text" class="form-control" id="Nachname" placeholder="Nachname">
				    	</div>
				    </div>
				  </div>
				  <div class="form-group">
				    <label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*</label>
				    <input type="email" class="form-control" id="email" placeholder="ich@email.de">
				  </div>
				  <div class="form-group">
				    <label for="password"><span class="glyphicon glyphicon-lock"></span> Passwort*</label>
				    <div class="row">
				    	<div class="col-xs-6">
				    		<input type="password" class="form-control" id="password" placeholder="Passwort">
				    	</div>
				    	<div class="col-xs-6">
				    		<input type="password" class="form-control" id="passwordRepeat" placeholder="Passwort wiederholen">
				    	</div>
			    	</div>
				  </div>
				  
				  <input type="hidden" name="mode" value="register">
				  
				  <div class="form-group">
				    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Registrieren</button>
				    <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
				  </div>
				</form>

			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />