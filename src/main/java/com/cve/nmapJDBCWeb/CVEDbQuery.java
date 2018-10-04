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
			
			String sql = "SELECT * FROM vuln WHERE vendor_id=\'"+vendor_id+"\' AND product_id=\'"+prod_id+"\' LIMIT 10";
			
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				
				Dictionary<String,String> vDict = new Hashtable<String,String>();
				
				String vuln_id = resultSet.getString("id");
				String impact = resultSet.getString("impact");
				if(resultSet.wasNull()) {
					impact = "";
				}
				String modified = resultSet.getString("modified");
				if(resultSet.wasNull()) {
					modified = "";
				}
				String access = resultSet.getString("access");
				if(resultSet.wasNull()) {
					access = "";
				}
				String references = resultSet.getString("my_references");
				if(resultSet.wasNull()) {
					references = "";
				}
				String published = resultSet.getString("published");
				if(resultSet.wasNull()) {
					published = "";
				}
				String cvssTime = resultSet.getString("cvss_time");
				if(resultSet.wasNull()) {
					cvssTime = "";
				}
				String vulnConf2 = resultSet.getString("vulnerable_configuration_cpe_2_2");
				if(resultSet.wasNull()) {
					vulnConf2 = "";
				}
				String summary = resultSet.getString("summary");
				if(resultSet.wasNull()) {
					summary = "";
				}
				String cwe = resultSet.getString("cwe");
				if(resultSet.wasNull()) {
					cwe = "";
				}
				String cvss = resultSet.getString("cvss");
				if(resultSet.wasNull()) {
					cvss = "";
				}
				String vulnConf = resultSet.getString("vulnerable_configuration");
				if(resultSet.wasNull()) {
					vulnConf = "";
				}
				String lastModified = resultSet.getString("last_modified");
				if(resultSet.wasNull()) {
					lastModified = "";
				}
				
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
