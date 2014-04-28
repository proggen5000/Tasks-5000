<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht über Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>
	
<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${user.username}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="me" /></jsp:include>
			<%-- Alerts, falls übergeben --%>
			<c:if test="${requestScope.alert != null}">
	  			<c:if test="${requestScope.alert_mode == null}">
	  				<div class="alert alert-success">${requestScope.alert}</div>
	  			</c:if>
	  			<c:if test="${requestScope.alert_mode != null}">
	  				<div class="alert alert-${requestScope.alert_mode}">${requestScope.alert}</div>
	  			</c:if>
	  		</c:if>
			
			<h1>Profil bearbeiten</h1>
			<form class="form" action="/user" method="post">
				<div class="form-group col-xs">
					<label for="username"><span class="glyphicon glyphicon-user"></span> Benutzername*</label>
					<input type="text" class="form-control input-lg" name="username" value="${user.username}" />
				</div>
				  <div class="form-group">
				    <label for="vorname"><span class="glyphicon glyphicon-user"></span> Echter Name</label>
				    <div class="row">
				    	<div class="col-xs-6">
				    		<input type="text" class="form-control" name="vorname" placeholder="Vorname" value="${user.vorname}">
				    	</div>
				    	<div class="col-xs-6">
				    		<input type="text" class="form-control" name="nachname" placeholder="Nachname" value="${user.nachname}">
				    	</div>
				    </div>
				  </div>
				  <div class="form-group">
				  	<label for="email"><span class="glyphicon glyphicon-envelope"></span> E-Mail-Adresse*</label>
				  	<input type="email" class="form-control" id="email" name="email" placeholder="" value="${user.email}" />
				  </div>
				  <div class="form-group">
				  	<label for="newPassword"><span class="glyphicon glyphicon-lock"></span> Neues Passwort</label>
				    <div class="row">
				    	<div class="col-xs-6">
				    		<input type="password" class="form-control" name="password" placeholder="Passwort" />
				    	</div>
				    	<div class="col-xs-6">
				    		<input type="password" class="form-control" name="passwordRepeat" placeholder="Passwort wiederholen" />
				    	</div>
			    	</div>
				  </div>
				  
				  <input type="hidden" name="mode" value="edit" />
				  
				  <div class="form-group">
					<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
					<a href="/user?mode=view&id=${user.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					<a href="/user?mode=remove&id=${user.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Profil l&ouml;schen</a>
				  </div>
			</form>
			
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />