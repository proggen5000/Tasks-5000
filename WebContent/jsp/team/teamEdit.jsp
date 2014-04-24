<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${team.teamname}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
		
		<%-- Team bearbeiten/erstellen --%>
		<c:if test="${param.mode == 'edit'}"><h1>Team bearbeiten</h1></c:if>
		<c:if test="${param.mode == 'new'}"><h1>Team erstellen</h1></c:if>
		
		<form class="form" action="/team" method="post">
	  		<div class="form-group col-xs row">
	  			<div class="col-xs-6">
	  				<label for="name"><span class="glyphicon glyphicon-briefcase"></span> Teamname*</label>
					<input id="name" name="name" type="text" class="form-control input-lg" placeholder="" value="${team.teamname}">
	  			</div>
	  			<div class="col-xs-6">
	  				<label for="leader"><span class="glyphicon glyphicon-user"></span> Teammanager*</label>
					<select name="leader" size="1" class="form-control input-lg">
						<option value="1" selected>Sebastian Herrmann</option>
						<option value="34">Felix Fichte</option>
						<option value="2">Gunnar Lehker</option>
						<option value="74">Manuel Taya</option>
					</select>
	  			</div>
			</div>
			<div class="form-group col-xs">
				<label for="slogan"><span class="glyphicon glyphicon-align-left"></span> Slogan / Beschreibung</label>
				<textarea id="slogan" name="slogan" class="form-control" rows="2">${team.slogan}</textarea>
			</div>
			<div class="form-group col-xs">
				<label for="members"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <small>(mehrere Mitglieder durck Gedr&uuml;ckthalten von <kbd>Strg</kbd> bzw. <kbd>Cmd</kbd> markieren)</small>
				<div class="row">
					<div class="col-xs-4">
						<select multiple name="members" id="members" size="8" class="form-control">
						</select>
					</div>
					
					<div class="col-xs-1" style="vertical-align: center;">
						<input type="button" id="btnLeft" class="btn btn-default" value="&lt;&lt;" />
       					<input type="button" id="btnRight" class="btn btn-default" value="&gt;&gt;" />
					</div>
					
					<div class="col-xs-4">
						<select multiple name="membersAll" id="membersAll" size="8" class="form-control">
							<option value="1">Felix Fichte</option>
							<option value="34">Gunnar Lehker</option>
							<option value="2">Manuel Taya</option>
							<option value="74">Sebastian Herrmann</option>
							<option value="11">Rainer Rumpel</option>
							<option value="79">Klaus Ringhand</option>
							<option value="102">Gert Faustmann</option>
						</select>
					</div>
				</div>
			</div>
			
			<c:if test="${param.mode == 'new'}">
				<input type="hidden" name="mode" value="new" />
			</c:if>
			<c:if test="${param.mode == 'edit'}">
				<input type="hidden" name="mode" value="edit" />
			</c:if>
			
			<div class="form-group">
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> ${submit_button}</button>
				<button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Zur&uuml;cksetzen</button>
				<c:if test="${param.mode == 'edit'}">
					<a href="team?mode=remove&id=${team.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
				</c:if>
			</div>
		</form>
		
		<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />