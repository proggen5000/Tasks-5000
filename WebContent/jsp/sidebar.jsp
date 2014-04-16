</div><%-- Ende content --%>
<div class="sidebar col-sm-3">
	<h1>Login</h1>
	<form class="form" action="#">
      <input type="text" class="form-control" id="username" placeholder="Benutzername">
      <input type="password" class="form-control" id="password" placeholder="Passwort">
      <div class="row" style="margin-top:5px">
      	<div class="col-xs-5"><button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Login</button></div>
      	<div class="checkbox col-xs-7"><label><input type="checkbox"> Login merken</label></div>
      </div>

      <a href="/lostpw"><span class="glyphicon glyphicon-lock"></span> Passwort vergessen?</a><br />
      <a href="/register.jsp"><span class="glyphicon glyphicon-pencil"></span> Registrieren</a>
	</form>
</div>