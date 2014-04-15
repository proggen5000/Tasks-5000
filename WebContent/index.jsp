<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entities.Team" %>
<%@ page import="entities.Mitglied" %>
<jsp:useBean id="team" class="entities.Team" scope="page"></jsp:useBean>

<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>

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
					        <td><a href="team.jsp">${team.name}</a></td>
					        <td>13 (2)</td>
					        <td>4</td>
					    </tr>
					    <tr>
					        <td><a href="team.jsp">Hallo Crazytown</a></td>
					        <td>25 (0)</td>
					        <td>5</td>
					    </tr>
					    <tr>
					        <td><a href="team.jsp">The Uh Oh's</a></td>
					        <td>108 (1)</td>
					        <td>9</td>
					    </tr>
					    <tr>
					        <td><a href="team.jsp">Microsoft Windows Core Development Team</a></td>
					        <td>1337 (2)</td>
					        <td>109</td>
					    </tr>
				</tbody>
				</table>
		  		
		  		<h1>Meine Aufgaben</h1>
		  		<div class="list-group">
					<a href="task.jsp" class="list-group-item list-group-item-success">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<h4 class="list-group-item-heading">Kuchen essen</h4>
						<p class="list-group-item-text">Microsoft Windows Core Development Team</p>
					</a>
					<a href="task.jsp" class="list-group-item list-group-item-warning">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="task.jsp" class="list-group-item list-group-item-danger">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
				</div>
		  		
			</div>
			<%@ include file="jsp/sidebar.jsp" %>
<%@ include file="jsp/footer.jsp" %>