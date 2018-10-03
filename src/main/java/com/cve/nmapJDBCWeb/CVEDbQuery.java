package com.cve.nmapJDBCWeb;

import java.sql.*;

public class CVEDbQuery {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public int readDataBase (String product, String vendor) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Looks like the Driver registered just fine");
			
			connect  = DriverManager.getConnection("jdbc:mysql://cvedb:3306/cve","cveuser@localhost","cvepass");
			
			if(connect != null) {
				System.out.println("Successful Connection");
			}
			else {
				System.out.println("Connection Failed");
			}
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT v.name AS vname, v.id AS vid, p.name AS pname, p.id AS pid FROM vendor v JOIN product p ON v.id=p.vendor_id WHERE p.name="+product+" AND v.name= "+vendor);
			String prod_id = null;
			String vendor_id = null;
			int numVulns = 0;
			while (resultSet.next()) {
				prod_id = resultSet.getString("pid");
				vendor_id = resultSet.getString("vid");
			}
			
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM vuln WHERE vendor_id="+vendor_id+" AND product_id="+prod_id);
			
			while(resultSet.next()) {
				numVulns = resultSet.getInt("COUNT(*)");
			}
			return numVulns;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void close() {
		try {
			if( resultSet != null) {
				resultSet.close();
			}
			if( statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			
		}
	}
}
