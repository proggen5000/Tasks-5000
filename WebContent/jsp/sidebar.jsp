<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</div><%-- Ende content --%>
<div class="sidebar col-sm-3">

	<c:if test="${!login}">
		<h1>Login</h1>
		<form class="form" action="login" method="POST">
	      <input type="text" class="form-control" id="username" name="username" placeholder="Benutzername">
	      <input type="password" class="form-control" id="password" name="password" placeholder="Passwort">
	      <input type="hidden" name="mode" value="login">
	      <div class="row" style="margin-top:5px">
	      	<div class="col-xs-5"><button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Login</button></div>
	      	<div class="checkbox col-xs-7"><label><input type="checkbox" name="cookie"> Login merken</label></div>
	      </div>
	      <a href="/?page=lostpw"><span class="glyphicon glyphicon-lock"></span> Passwort vergessen?</a><br />
	      <a href="/?page=register"><span class="glyphicon glyphicon-pencil"></span> Registrieren</a>
		</form>
	</c:if>

	<c:if test="${login}">
		<h1>Willkommen</h1>
		<p>Hallo, ${currentUser}! <3</p>
	</c:if>
</div><%-- Ende sidebar --%>