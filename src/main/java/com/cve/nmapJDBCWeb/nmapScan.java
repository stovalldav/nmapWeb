package com.cve.nmapJDBCWeb;

import java.io.*;
import java.util.*;

public class nmapScan {

		public List<String> scanTarget (String target) {
			if (validateIP(target) == true) {
				Runtime rt = Runtime.getRuntime();
				String[] commands = {"nmap",  "-sS", target};
				try {
					Process proc =  rt.exec(commands);
				
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				
					BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					
					String stdIn = null;
					List<String> scanResults = new ArrayList<String>();
					List<String> scanErrors = new ArrayList<String>();
					
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
}
