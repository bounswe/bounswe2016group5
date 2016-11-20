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

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/ViewTopicServlet")
public class ViewTopicServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewTopicServlet() {
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
		
		int topicId = -1;
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			if(attr.equalsIgnoreCase("topic_id"))
			topicId= Integer.parseInt(value);
		}

		//System.out.println("topic_id=" + topicId);
		
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=view_topic&topicId="+topicId;
		URL jsonpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) jsonpage.openConnection();
		BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
		
		String recv = "";
		String recvbuff = "";
		while ((recv = buffread.readLine()) != null)
			recvbuff += recv;
		buffread.close();

		try {
			JSONObject obj = new JSONObject(recvbuff);
			if (obj.has("errorName")) {							
				HttpSession session = request.getSession(true);
				String msg = obj.getString("errorDescription");
				session.setAttribute("error", msg);

			} else {
				Set<String> sattr = obj.keySet();
				for (String attribute : sattr) {
					//tek tek bütün topic attributelarını request.setattribute ekle
					if (!attribute.contentEquals("role")) {
					}
				}
				//response.setdispatcher(view-topic.jsp);

			}

		} catch (JSONException ex) {
			// Do nothing
		}

		
	}
}
