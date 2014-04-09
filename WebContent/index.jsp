<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>
		  		
		  		<h1>Meine Teams</h1>
		  		<table class="table table-striped table-hover">
		  			<thead>
		  			<tr>
				        <th>#</th>
				        <th>Team</th>
				        <th>Mitglieder</th>
				    </tr>
				    </thead>
		  			<tbody>
					    <tr>
					        <td>1</td>
					        <td>The Banoodles</td>
					        <td>13</td>
					    </tr>
					    <tr>
					        <td>25</td>
					        <td>Hallo Crazytown</td>
					        <td>4</td>
					    </tr>
					    <tr>
					        <td>108</td>
					        <td>The Uh Oh's</td>
					        <td>31</td>
					    </tr>
					    <tr>
					        <td>109</td>
					        <td>Microsoft Windows Core Development Team</td>
					        <td>1337</td>
					    </tr>
				</tbody>
				</table>
		  		
		  		<h1>Meine Aufgaben</h1>
		  		<div class="list-group">
					<a href="#" class="list-group-item list-group-item-success">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<h4 class="list-group-item-heading">Kuchen essen</h4>
						<p class="list-group-item-text">Microsoft Windows Core Development Team</p>
					</a>
					<a href="#" class="list-group-item list-group-item-warning">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="#" class="list-group-item list-group-item-danger">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
				</div>
		  		
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
			      <a href="/register"><span class="glyphicon glyphicon-pencil"></span> Registrieren</a>
				</form>
				
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
<%@ include file="jsp/footer.jsp" %>