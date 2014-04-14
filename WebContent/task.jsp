<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>

			<ol class="breadcrumb">
				<li><a href="index.jsp">Start</a></li>
				<li><a href="team.jsp">Microsoft Windows Core Development Team</a></li>
				<li class="active"></li>
			</ol>

			<h1>Design m&ouml;glichst h&auml;sslich gestalten <span class="glyphicon glyphicon-time small"></span></h1>
			<dl class="dl-horizontal">
				<dt>Ersteller</dt>
				<dd><a href="#"><span class="glyphicon glyphicon-user"></span> banoodle</a></dd>
				<dt>Datum</dt>
				<dd>01.04.2014 | 14.36 Uhr</dd>
				<dt>Deadline</dt>
				<dd>14.04.2014</dd>
				<dt>Status</dt>
				<dd>50%</dd>
			</dl>
			<p>Hier steht eine &uuml;ppige Beschreibung der Aufgabe, ggf. mit Formatierungen und Listen?!</p>
			
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