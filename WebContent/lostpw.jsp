<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="Impressum" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />
		  		
		  		<h1>Passwort wiederherstellen</h1>
		  		<p>Haben Sie Ihr Passwort vergessen? Kein Problem, lassen Sie es sich erneut per E-Mail zusenden.</p>
		  		<p>Tragen Sie dazu Ihre E-Mail-Adresse, mit der Sie sich bei Tasks 5000 registriert haben, unten ein.</p>
		  		
		  		<form class="form" action="form">
				  <div class="form-group">
				    <label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*</label>
				    <input type="email" class="form-control" id="email" placeholder="you@domain.com">
				  </div>
				  <div class="form-group">
				    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Passwort zusenden lassen</button>
				  </div>
				</form>
		  		
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />