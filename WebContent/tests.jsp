<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Test 5000</title>
</head>
<body>

	<%@ include file="jsp/header.jsp" %> 

	<%
		// Ein Skriptlet, um z.B. Code einzubetten.
	%> 

	<%= request.getHeader("User-Agent") %> 
	<%= "<br />Eine Expression, um Ausgaben zu basteln!<br /><br />" %>
	<% for (int i = 0; i <= 5; i++) { %>
   		<p><%=i%></p>
	<% } %>
	
	<%-- JSP-Kommentar, welcher im HTML-Quelltext nicht mehr auftaucht. --%> 
	
</body>
</html>