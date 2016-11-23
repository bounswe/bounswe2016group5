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
		HttpSession session = request.getSession(true);
		
		StringBuffer bf = new StringBuffer();
		bf.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_subscriber&");
		bf.append("uid="+session.getAttribute("id")+"&");
		Enumeration<String> names = request.getParameterNames();
		
		int tid=-1;
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			bf.append(attr + "=" + value);
			tid = Integer.parseInt(value);
			if (names.hasMoreElements())
				bf.append("&");
		}

		String url = bf.toString();
		URL connpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) connpage.openConnection();

		int responseCode = urlcon.getResponseCode();
		if (responseCode == 200 ) {
			request.setAttribute("topic_id", tid);
			request.getRequestDispatcher("/ViewTopicServlet").forward(request, response);
		} else if (responseCode == 400) {
			//HttpSession session = request.getSession(true);
			String errMsg = "Unexpected Error occured!!";
			session.setAttribute("error", errMsg);
			request.setAttribute("topic_id", tid);
			request.getRequestDispatcher("/ViewTopicServlet").forward(request, response);
		}
	}
}
