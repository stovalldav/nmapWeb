package com.cve.nmapJDBCWeb;

import java.sql.*;
import java.util.*;

public class CVEDbQuery {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	int numVulns = 0;
	public Dictionary<String,Dictionary<String,String>> readDataBase (String product, String vendor) throws Exception {
		Dictionary<String,Dictionary<String,String>> cveDict = new Hashtable<String,Dictionary<String,String>>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Looks like the Driver registered just fine");
			
			connect  = DriverManager.getConnection("jdbc:mysql://cvedb:3306/cve","cveuser@localhost","cvepass");
			
			if(connect != null) {
				System.out.println("Successful Connection");
			}else {
				System.out.println("Connection Failed");
			}
			
			statement = connect.createStatement();
			String vsql = "SELECT v.name AS vname, v.id AS vid, p.name AS pname, p.id AS pid FROM vendor v JOIN product p ON v.id=p.vendor_id WHERE p.name=\'"+product+"\' AND v.name=\'"+vendor+"\'";
			
			resultSet = statement.executeQuery(vsql);
			String prod_id = null;
			String vendor_id = null;
			while (resultSet.next()) {
				prod_id = resultSet.getString("pid");
				vendor_id = resultSet.getString("vid");
			}
			
			String sql = "SELECT COUNT(*) FROM vuln WHERE vendor_id=\'"+vendor_id+"\' AND product_id=\'"+prod_id+"\'";
			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				
				Dictionary<String,String> vDict = new Hashtable<String,String>();

				numVulns = resultSet.getInt("COUNT(*)");
				System.out.println("Found vulns: "+String.valueOf(numVulns));
				
				String vuln_id =String.valueOf(resultSet.getInt("id"));
				String impact = resultSet.getString("impact");
				String modified = resultSet.getString("modified");
				String access = resultSet.getString("access");
				String references = resultSet.getString("references");
				String published = resultSet.getString("Published");
				String cvssTime = resultSet.getString("cvss_time");
				String vulnConf2 = resultSet.getString("vulnerable_configuration_cpe_2_2");
				String summary = resultSet.getString("summary");
				String cwe = resultSet.getString("cwe");
				String cvss = resultSet.getString("cvss");
				String vulnConf = resultSet.getString("vulnerable_configuration");
				String lastModified = resultSet.getString("last_modified");
				
				vDict.put("impact", impact);
				vDict.put("modified", modified);
				vDict.put("access", access);
				vDict.put("references", references);
				vDict.put("published", published);
				vDict.put("cvssTime", cvssTime);
				vDict.put("vulnConf2",vulnConf2);
				vDict.put("summary", summary);
				vDict.put("cwe", cwe);
				vDict.put("cvss", cvss);
				vDict.put("vulnConf",vulnConf);
				vDict.put("lastModified", lastModified);
				
				cveDict.put(vuln_id, vDict);
				
			}
			return cveDict;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cveDict;
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
