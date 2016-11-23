package com.digest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubscribeServlet")
public class SubscribeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubscribeServlet() {
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
		bf.append("http://localhost:8080/digest.api/");
		bf.append("?");
		Enumeration<String> names = request.getParameterNames();
		
		
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			bf.append(attr + "=" + value);
			if (names.hasMoreElements())
				bf.append("&");
		}

		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_subscriber&tid=38&uid=36";
		//String url = bf.toString();
		URL connpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) connpage.openConnection();

		int responseCode = urlcon.getResponseCode();
		System.out.println("response " + responseCode);
		if (responseCode == 200 && request.getParameter("f") != null) {
			response.sendRedirect("index.jsp");
		} else if (responseCode == 400) {
			HttpSession session = request.getSession(true);
			String errMsg = "Unexpected Error occured!!";
			session.setAttribute("error", errMsg);
			response.sendRedirect("signup.jsp");
		}
	}
}
