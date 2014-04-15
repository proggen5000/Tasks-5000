<%@ include file="jsp/header.jsp" %>
<%@ include file="jsp/menu.jsp" %>

			<ol class="breadcrumb">
				<li><a href="index.jsp">Start</a></li>
				<li><a href="team.jsp">Microsoft Windows Core Development Team</a></li>
				<li class="active"></li>
			</ol>

			<h1>Design m&ouml;glichst h&auml;sslich gestalten <span class="glyphicon glyphicon-time small"></span></h1>
			<p>Hier steht eine &uuml;ppige Beschreibung der Aufgabe, ggf. mit Formatierungen und Listen?!</p>
			

			<div class="panel panel-default">
				<div class="panel-heading"><h3 class="panel-title"><span class="glyphicon glyphicon-paperclip"></span> Dateien</h3></div>
				<div class="panel-body">
					<span class="glyphicon glyphicon-file"></span> <a href="#">meinedatei.docx</a> (72 KB)<br />
					<span class="glyphicon glyphicon-file"></span> <a href="#">einbild.jpg</a> (205 KB)
				</div>
			</div>
			
			</div><%-- Ende content --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
					<div class="list-group">
						<a href="#" class="list-group-item list-group-item-info"><span class="glyphicon glyphicon-pencil"></span> Aufgabe bearbeiten</a>
						<a href="#" class="list-group-item list-group-item-danger"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
					</div>
					<div class="list-group">
						<a href="#" class="list-group-item"><span class="glyphicon glyphicon-file"></span> Datei hochladen</a>
					</div>	
			
				<h1>Details</h1>
					<div class="list-group">
						<div class="list-group-item"><span class="glyphicon glyphicon-calendar"></span> 01.04.2014 <small>14.36 Uhr</small></div>
						<div class="list-group-item"><span class="glyphicon glyphicon-bell"></span> 14.04.2014</div>
						<div class="list-group-item"><span class="glyphicon glyphicon-dashboard"></span> Status: 50%</div>
						<a href="#" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Gunnar Lehker <span class="label label-default">Ersteller</span></a>
						<a href="#" class="list-group-item"><span class="glyphicon glyphicon-user"></span> Felix Fichte</a>
					</div>
											
			</div><%-- Ende Sidebar, ggf. durch Methode ergänzen --%>
<%@ include file="jsp/footer.jsp" %>