package com.digest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateTopicServlet
 */
@WebServlet("/CreateTopicServlet")

public class CreateTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateTopicServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		StringBuffer bf = new StringBuffer();
		bf.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/");
		bf.append("?");
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			bf.append(attr + "=" + value);
			if (names.hasMoreElements())
				bf.append("&");
		}
		String url = bf.toString();
		URL connpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) connpage.openConnection();
		

		int responseCode = urlcon.getResponseCode();
		if (responseCode == 200 && request.getParameter("f") != null) {
			response.sendRedirect("index.jsp");
		} else if (responseCode == 400) {
			HttpSession session = request.getSession(true);
			String errMsg = "Unexpected Error occured!!";
			session.setAttribute("error", errMsg);
			response.sendRedirect("topic-creation.jsp");
		}
		
	}
	
}
