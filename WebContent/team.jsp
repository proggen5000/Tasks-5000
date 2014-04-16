<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="jsp/header.jsp" />
<jsp:include page="jsp/menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>

		  		<h1>Microsoft Windows Core Development Team <span class="glyphicon glyphicon-briefcase small"></span></h1>
		  		Hier k&ouml;nnte ein &auml;u&szlig;erst schlauer Slogan stehen.
		  		<h2>Windows 8.1</h2>
				<div class="list-group">
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker<br /><span class="glyphicon glyphicon-file"></span> 2</div>
						<h4 class="list-group-item-heading">Design m&ouml;glichst h&auml;sslich gestalten</h4>
						<p class="list-group-item-text">Wir sollten das Design von Win 8.1 so schlimm wie m...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Felix Fichte<br /><span class="glyphicon glyphicon-file"></span> 4</div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> xXx_bastman5000_xXx<br /><span class="glyphicon glyphicon-file"></span> 129</div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress">
							<span class="badge">?</span>
						</div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 2</div>
						<h4 class="list-group-item-heading">Marketing betreiben</h4>
						<p class="list-group-item-text">Irgend wie m&uuml;ssen wir dies und das tun, um dan...</p>
					</a>
				</div>
		  		
		  		<h2>Hier eine weitere Gruppe an Aufgaben</h2>
		  		<div class="list-group">
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="76" aria-valuemin="0" aria-valuemax="100" style="width: 76%;">76%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 0</div>
						<h4 class="list-group-item-heading">Design m&ouml;glichst h&auml;sslich gestalten</h4>
						<p class="list-group-item-text">Wir sollten das Design von Win 8.1 so schlimm wie m...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="26" aria-valuemin="0" aria-valuemax="100" style="width: 26%;">26%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 12</div>
						<h4 class="list-group-item-heading">Mehr Geld verlangen</h4>
						<p class="list-group-item-text">F&uuml;r unsere sehr gute Arbeit verlangen wir am b...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress"><div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="17" aria-valuemin="0" aria-valuemax="100" style="width: 17%;">17%</div>
						</div></div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 13</div>
						<h4 class="list-group-item-heading">Bugfixing: Startmen&uuml;</h4>
						<p class="list-group-item-text">Leider hatten wir das Startmen&uuml; gel&ouml;scht ...</p>
					</a>
					<a href="task.jsp?mode=view&id=X" class="list-group-item">
						<div class="task-progress">
							<span class="badge">?</span>
						</div>
						<div class="task-details"><span class="glyphicon glyphicon-user"></span> Manuel Taya<br /><span class="glyphicon glyphicon-file"></span> 1</div>
						<h4 class="list-group-item-heading">Marketing betreiben</h4>
						<p class="list-group-item-text">Irgend wie m&uuml;ssen wir dies und das tun, um dan...</p>
					</a>
				</div>

			</div><%-- Ende content --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
					<div class="list-group">
						<a href="task.jsp?mode=new&team=X" class="list-group-item"><span class="glyphicon glyphicon-time"></span> Aufgabe erstellen</a>
						<a href="taskGroup.jsp?mode=new&team=X" class="list-group-item"><span class="glyphicon glyphicon-list"></span> Aufgabengruppe erstellen</a>
					</div>
					<div class="list-group">
						<a href="team.jsp?mode=edit&id=X" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Team bearbeiten</a>
					</div>			
			
				<h1>Mitglieder</h1>
				<div class="list-group">
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker <span class="label label-default">Manager</span></a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Manuel Taya</a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Felix Fichte</a>
					<a href="profile.jsp?mode=view&id=X" class="list-group-item"><span class="glyphicon glyphicon-user"></span> xXx_bastman5000_xXx</a>		
				</div>
											
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
<jsp:include page="jsp/footer.jsp" />