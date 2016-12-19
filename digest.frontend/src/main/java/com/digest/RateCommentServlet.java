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
		int uid = Integer.parseInt(request.getParameter("uid"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		String f = request.getParameter("f");
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f="+ f+"&uid="+uid+"&cid="+cid;
		
		URL connpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) connpage.openConnection();
		int responseCode = urlcon.getResponseCode();
		System.out.println(responseCode);
	}
}
