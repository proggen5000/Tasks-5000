<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Impressum" /></jsp:include>
<jsp:include page="../menu.jsp" />
		  		
		  		<h1>Passwort wiederherstellen</h1>
		  		<p>Haben Sie Ihr Passwort vergessen? Kein Problem, lassen Sie es sich erneut per E-Mail zusenden.</p>
		  		<p>Tragen Sie dazu Ihre E-Mail-Adresse, mit der Sie sich bei Tasks 5000 registriert haben, unten ein.</p>
		  		
		  		<form class="form" action="${pageContext.request.contextPath}/user">
				  <div class="form-group">
				    <label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*</label>
				    <input type="email" class="form-control" id="email" placeholder="nutzer@mail.de">
				  </div>
				  
				  <input type="hidden" name="mode" value="lostpw" />
				  
				  <div class="form-group">
				    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Passwort zusenden lassen</button>
				  </div>
				</form>
		  		
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />