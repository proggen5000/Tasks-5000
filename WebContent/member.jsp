<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>

			<h1>Sebastian Herrmann <span class="glyphicon glyphicon-user small"></span></h1>
				<dl class="dl-horizontal">
					<dt>Benutzername</dt><dd>herrherrmann</dd>
					<dt>Mitglied seit</dt><dd>21.02.2013</dd>
					<dt>Mitglied in</dt>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Microsoft Windows Core Development Team</a></dd>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Team X</a></dd>
						<dd><a href="team.jsp"><span class="glyphicon glyphicon-briefcase"></span> Team Y</a></dd>
				</dl>
			
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
			      <a href="/register.jsp"><span class="glyphicon glyphicon-pencil"></span> Registrieren</a>
				</form>
				
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
<%@ include file="jsp/footer.jsp" %>