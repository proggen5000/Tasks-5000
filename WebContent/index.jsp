<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entities.Team" %>
<%@ page import="entities.Mitglied" %>
<jsp:useBean id="team" class="entities.Team" scope="page"></jsp:useBean>

<jsp:include page="jsp/header.jsp"><jsp:param name="page_title" value="Start" /></jsp:include>
<jsp:include page="jsp/menu.jsp" />

				<%-- Startseite (ausgeloggt) --%>
				<c:if test="${!login}">
					<h1>Willkommen bei Tasks 5000!</h1>
					<p><b>Tasks 5000</b> ist unser Studienprojekt f&uuml;r das Fach Client-Server-Programmierung.</p>
					<p>Wir stellen Ihnen mit dieser Webanwendung unglaubliche Funktionen zur Verwaltung von Aufgaben und Dateien innerhalb von Teams zur Verf&uuml;gung. Weitere Informationen zum Projekt finden Sie unter <a href="about.jsp">Impressum</a>.</p>
					<p>Registrieren Sie sich oder loggen Sie sich in Ihren bestehenden Tasks 5000-Account ein, um die Anwendung zu nutzen.</p>
					
					<h1>Features</h1>
					<div class="row">
						<div class="col-xs-4"><h3><span class="glyphicon glyphicon-time"></span> Aufgaben</h3>Erstellen Sie Aufgaben und teilen Sie diese Ihren Kollegen oder Mitsch&uuml;lern zu. <b>Wow!</b></div>
						<div class="col-xs-4"><h3><span class="glyphicon glyphicon-briefcase"></span> Teams</h3>Erstellen Sie Teams bzw. Gruppen, in denen Sie kooperativ Ihre Aufgaben organisieren. <b>Unglaublich!</b></div>
						<div class="col-xs-4"><h3><span class="glyphicon glyphicon-file"></span> Dateien</h3>Laden Sie Dateianh&auml;nge hoch, um Dokumente, Darstellungen und andere Dinge zu teilen. <b>Verr&uuml;ckt!</b></div>
					</div>
					
					<h1>Benutzerrezensionen</h1>
					<p>Das sagen Anwender und die Fachpresse* &uuml;ber Tasks 5000:</p>
					<blockquote>
						"So etwas habe ich noch nie erlebt - Form, Funktion und Verf&uuml;gbarkeit erleben in dieser Anwendung ihre absoluten H&ouml;hepunkte."
						<footer>PC Welt</footer>
					</blockquote>
					<blockquote>
						"Was Tasks 5000 bietet, ist sensationell. Und der Preis rockt auch. Danke f&uuml;r diese Leistung!"
						<footer>Die Zeit</footer>
					</blockquote>
					<blockquote>
						"Aufgaben, Teams und Dateien - die Menschheit ist einen Schritt weiter dank Tasks 5000."
						<footer>Sebastian H., Java-Guru</footer>
					</blockquote>
					<small style="color: silver">*Einige Rezensionen wurden gek&uuml;rzt und/oder gef&auml;lscht, um Tasks 5000 besser dastehen zu lassen.</small>
					
				</c:if>
				
				<%-- Benutzer-Startseite (eingeloggt) --%>
				<c:if test="${login}">
			  		<h1>Meine Teams</h1>
			  		<table class="table table-striped table-hover">
			  			<thead>
			  			<tr>
					        <th>Team</th>
					        <th>Aufgaben <small>(eigene)</small></th>
					        <th>Mitglieder</th>
					    </tr>
					    </thead>
			  			<tbody>
						    <tr>
						        <td><a href="team.jsp?mode=view&id=X">${team.name}</a></td>
						        <td>13 (2)</td>
						        <td>4</td>
						    </tr>
						    <tr>
						        <td><a href="team.jsp?mode=view&id=X">Hallo Crazytown</a></td>
						        <td>25 (0)</td>
						        <td>5</td>
						    </tr>
						    <tr>
						        <td><a href="team.jsp?mode=view&id=X">The Uh Oh's</a></td>
						        <td>108 (1)</td>
						        <td>9</td>
						    </tr>
						    <tr>
						        <td><a href="team.jsp?mode=view&id=X">Microsoft Windows Core Development Team</a></td>
						        <td>1337 (2)</td>
						        <td>109</td>
						    </tr>
					</tbody>
					</table>
			  		
			  		<h1>Meine Aufgaben</h1>
			  		<div class="list-group">
						<a href="task.jsp?mode=view&id=X" class="list-group-item">
							<div class="task-progress"><div class="progress">
								<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
							</div></div>
							<h4 class="list-group-item-heading">Kuchen essen</h4>
							<p class="list-group-item-text">Microsoft Windows Core Development Team</p>
						</a>
						<a href="task.jsp?mode=view&id=X" class="list-group-item">
							<div class="task-progress"><div class="progress">
								<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
							</div></div>
							<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
							<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
						</a>
						<a href="task.jsp?mode=view&id=X" class="list-group-item">
							<div class="task-progress"><div class="progress">
								<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
							</div></div>
							<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
							<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
						</a>
					</div>
				</c:if> <%-- Ende Benutzer-Startseite --%>
		  		
			<jsp:include page="jsp/sidebar.jsp" />
<jsp:include page="jsp/footer.jsp" />