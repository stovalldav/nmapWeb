<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>NMap Web Success Page</title>
	</head>
	<body>
           <a><b>Congratulations User!!!!</b></a>
           <table border="1">
           	<tr>
           		<td align="left">Target</td>
           		<td>
           		<% out.println(request.getParameter("target")); %>
           		</td>
           	</tr>
           	<tr>
           		<td align="left">Options</td>
           		<td><% out.println(request.getParameter("scan_options")); %></td>
           	</tr>
            <tr>
           		<td align="left">OS Type</td>
           		<td><% out.println(request.getParameter("os_type")); %></td>  
           	</tr>        		
           </table>
	</body>
</html>