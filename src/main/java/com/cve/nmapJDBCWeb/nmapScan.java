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
			
			String re1="(OS CPE)";	// Word 1
		    String re2=".*?";	// Non-greedy match on filler
		    String re3="(?:[a-z][a-z]+)";	// Uninteresting: word
		    String re4=".*?";	// Non-greedy match on filler
		    String re5="(?:[a-z][a-z]+)";	// Uninteresting: word
		    String re6=".*?";	// Non-greedy match on filler
		    String re7="((?:[a-z][a-z]+))";	// Word 2
		    String re8=".*?";	// Non-greedy match on filler
		    String re9="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
		    
			for (String items: resultsList) {
				//final Pattern ptn = Pattern.compile("^(OS CPE:).*((?:[a-zA-Z][a-zA-Z].*?:)).*((?:[a-z][a-z0-9_]*))");
				final Pattern ptn = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8+re9,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher mtch = ptn.matcher(items);
				System.out.println("**"+items);
				if(mtch.find()) {
					System.out.println("Match: "+items);
					System.out.println(mtch.group(2));
					System.out.println(mtch.group(3));
					System.out.println(mtch.group(1));
					d.put("vendor", mtch.group(2).toString());
					d.put("product", mtch.group(3).toString());
				}
				
			}
			return d;
		}
}
