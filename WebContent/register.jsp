<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>
		  		
		  		<h1>Registrierung</h1>
		  		<p>Registrieren Sie sich mit einem einzigartigen Benutzernamen und Ihrer E-Mail-Adresse, um Tasks 5000 zu benutzen.<br />
		  		Falls Sie bereits ein Benutzerprofil angelegt haben, k&ouml;nnen Sie sich auf der rechten Seite unter "Login" anmelden.</p>
		  		<form class="form" role="form">
		  		
		  		  <div class="form-group" style="margin-bottom: 5px;">
				    <label for="username"><span class="glyphicon glyphicon-user"></span> Benutzername*</label>
				    <input type="text" class="form-control input-lg" id="username" placeholder="">
				  </div>
				  <div class="form-group" style="margin-bottom: 5px;">
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
				  <div class="form-group" style="margin-bottom: 5px;">
				    <label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*</label>
				    <input type="text" class="form-control" id="email" placeholder="">
				  </div>
				  <div class="form-group" style="margin-bottom: 5px;">
				    <label for="password"><span class="glyphicon glyphicon-lock"></span> Passwort*</label>
				    <input type="password" class="form-control" id="password" placeholder="">
				  </div>
				  <div class="form-group">
				    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Registrieren</button>
				    <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
				  </div>
				</form>
		  		
			</div><%-- Ende content --%>
			<div class="sidebar col-sm-3">
				<h1>Login</h1>
				<form class="form-horizontal" role="form">
			      <input type="text" class="form-control" style="margin-bottom: 5px;" id="inputUsername" placeholder="Benutzername">
			      <input type="password" class="form-control" id="inputPassword" placeholder="Passwort">
			      <div class="checkbox" style="margin-bottom: 5px;">
			        <label><input type="checkbox"> Login merken</label>
			      </div>
			      <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Login</button><br /><br />
			      <a href="/lostpw"><span class="glyphicon glyphicon-lock"></span> Passwort vergessen?</a><br />
				</form>
				
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
<%@ include file="jsp/footer.jsp" %>