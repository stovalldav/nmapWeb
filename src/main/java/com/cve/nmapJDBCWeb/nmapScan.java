package com.cve.nmapJDBCWeb;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
				final Pattern ptn = Pattern.compile("^(OS CPE: cpe\\[a-zA-Z]:)([a-zA-Z]*[^:]+:)(:[^:\r\n]+)$");
				Matcher mtch = ptn.matcher(items);
				if(mtch.find()) {
					System.out.println(mtch.group(1));
					System.out.println(mtch.group(2));
					d.put("os", mtch.group(2));
				}
				
			}
			return d;
		}
}
