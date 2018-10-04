<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.*" %>
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
           	<%
           			Dictionary<String,String> rDict = (Dictionary<String,String>) request.getAttribute("os_type");
           	%>
            <tr>
           		<td align="left">Vendor</td>
           		<td align="left"> <%=rDict.get("vendor") %></td>
           	</tr>        		
            <tr>
           		<td align="left">Product</td>
           		<td align="left"> <%=rDict.get("product") %></td>
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
			<textarea name='vulnResults' id='vulnResults'>
           		<%
	           		Dictionary<String,Dictionary<String,String>> vulnDict = (Dictionary<String,Dictionary<String,String>>) request.getAttribute("vulns");
	           		for (Enumeration k = vulnDict.keys(); k.hasMoreElements();){
	           			String vulnID = String.valueOf(k.nextElement());
	           			System.out.println("Vuln : "+vulnID);
	           			Dictionary<String,String> vulnList = vulnDict.get(vulnID);
	           			for(Enumeration v = vulnList.keys(); v.hasMoreElements();){
	           				String vulnKey = String.valueOf(v.nextElement());
	           				String vulnValue = String.valueOf(vulnList.get(vulnKey));
	           				System.out.println(vulnKey+":"+vulnValue);
	           			}
	           		}
           		%>
           		</textarea>
	</body>
</html>