package com.digest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/RateCommentServlet")
public class RateCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RateCommentServlet() {
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
		Enumeration<String> names = request.getParameterNames();
		int uid = -1;
		int cid = -1;
		int tid = -1;
		String f = "unrate_comment"; //unrate
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			if(attr.equals("uid"))
				uid = Integer.parseInt(value);
			if(attr.equals("cid"))
				cid = Integer.parseInt(value);
			if(attr.equals("f") && value.equals("up"))
				f = "rate_comment"; //rate		
			if(attr.equals("tid"))
				tid = Integer.parseInt(value);
		}
		
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f="+ f+"&uid="+uid+"&cid="+cid;
		URL connpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) connpage.openConnection();
		int responseCode = urlcon.getResponseCode();
		if (responseCode == 200) {
			request.setAttribute("topic_id", tid);
			request.getRequestDispatcher("ViewTopicServlet").forward(request, response);
		}else if (responseCode == 400) {
			HttpSession session = request.getSession(true);
			String errMsg = "Unexpected Error occured!!";
			request.setAttribute("topic_id", tid);
			request.getRequestDispatcher("ViewTopicServlet").forward(request, response);
		}

	}
}
