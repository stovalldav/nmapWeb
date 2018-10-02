<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Dictionary" %>
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
           		<% out.println(request.getAttribute("target")); %>
           		</td>
           	</tr>
           	<tr>
           		<td align="left">Options</td>
           		<td><% out.println(request.getAttribute("scan_options")); %></td>
           	</tr>
            <tr>
           		<td align="left">OS Type</td>
           		<td>
           		<textarea name='vendor' id='vendor'>
           		<%
           			Dictionary<String,String> rDict = (Dictionary<String,String>) request.getAttribute("os_type");
           			out.println(rDict.get("vendor"));
           			out.println(rDict.get("product"));
           		%>  
           		</textarea>
           		</td>
           	</tr>        		
           </table>
           <textarea name='results' id='results'>
           		<%
           		ArrayList<String> list = (ArrayList<String>) request.getAttribute("resultsList");
           		for(String item: list){
           			out.println(item);
           		}
           		%>
			</textarea>
	</body>
</html>