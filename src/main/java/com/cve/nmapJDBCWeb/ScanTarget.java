package com.cve.nmapJDBCWeb;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ScanTarget
 */
public class ScanTarget extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScanTarget() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String my_target= request.getParameter("target");
		String scan_options = request.getParameter("scan_options");
		String os_type = request.getParameter("os_type");

		if(my_target.isEmpty()|| scan_options.isEmpty() || os_type.isEmpty())
		{
			RequestDispatcher req = request.getRequestDispatcher("index.jsp");
			req.include(request, response);
		}
		else
		{
			nmapScan ns = new nmapScan();
			ArrayList<String> resultsList = ns.scanTarget(my_target);
			Dictionary<String,String> d = ns.processResults(resultsList);
			resultsList.forEach(System.out::println);
			
			request.setAttribute("resultsList", resultsList);
			request.setAttribute("target",my_target);
			request.setAttribute("scan_options", scan_options);
			request.setAttribute("os_type",d.get("os"));
			RequestDispatcher req = request.getRequestDispatcher("register2.jsp");
			req.forward(request, response);
		}
	}

}
