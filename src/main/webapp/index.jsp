<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="resources/css/style.css">
		<title> nmap JDBC Web application </title>
	</head>
	<body>
		<h1>Enter your NMap targets and settings 
		</h1>
		<form action="ScanTarget" method="post">
			<table style="with: 50%">
				<tr>
					<td>Target IP</td>
					<td><input type="text" name="target" /></td>
				</tr>
				<tr>
					<td>Scan Options</td>
					<td><input type="text" name="scan_options" /></td>
				</tr>
				<tr>
					<td>Expected OS Type</td>
					<td><input type="text" name="os_type" /></td>
				</tr>
				</table>
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>
