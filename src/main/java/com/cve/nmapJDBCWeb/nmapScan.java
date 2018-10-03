package com.cve.nmapJDBCWeb;

import java.io.*;
import java.util.*;

public class nmapScan {

		public ArrayList<String> scanTarget (String target) {
			if (validateIP(target) == true) {
				Runtime rt = Runtime.getRuntime();
				String[] commands = {"nmap",  "-Pn", "-O", target};
				try {
					Process proc =  rt.exec(commands);
				
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				
					BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					
					String stdIn = null;
					ArrayList<String> scanResults = new ArrayList<String>();
					ArrayList<String> scanErrors = new ArrayList<String>();
					
					while((stdIn = stdInput.readLine()) != null) {
						scanResults.add(stdIn);
					}
					
					String stdErr = null;
					while((stdErr = stdError.readLine()) != null) {
						scanErrors.add(stdErr);
					}
					return scanResults;
				} catch (IOException i) {
					System.out.println("Caught exception"+i);
					return new ArrayList<String>();
				}
			} else {
				System.out.println("Invalid IP address: " + target);
				return new ArrayList<String>();
			}
		}
		
		public static boolean validateIP (String Ip) {
			try {
				if (Ip == null || Ip.isEmpty())
				{
					return false;
				}
				
				String[] parts = Ip.split("\\.");
				if (parts.length != 4) {
					return false;
				}
				for (String s : parts) {
					int i = Integer.parseInt(s);
					if ((i <0) || (i > 255)) {
						return false;
					}
				}
				if (Ip.endsWith(".")) {
					return false;
				}
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		
		public Dictionary<String,String> processResults (ArrayList<String> resultsList) {
			Dictionary<String,String> d = new Hashtable<String,String>();
			
			for (String items: resultsList) {
				if(items.contains("OS CPE:")) {
					String[] fields = items.split(":");
					d.put("vendor", fields[3]);
					if(fields[4].contains(" ")) {
						String[] products = fields[4].split(" ");
						d.put("product", products[0]);
					}
					else {
						d.put("product", fields[4]);						
					}
					return d;
				}
			}
			return d;
		}
}
