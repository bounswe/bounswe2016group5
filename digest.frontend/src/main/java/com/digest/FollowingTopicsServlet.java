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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/FollowingTopicsServlet")
public class FollowingTopicsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FollowingTopicsServlet() {
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
		
		String recv2 = "";
		String recvbuff2 = "";

		StringBuffer bf2 = new StringBuffer();
		bf2.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/");
		bf2.append("?f=get_subscribed_topics&uid="+session.getAttribute("id"));		
	
		String url2 = bf2.toString();
		URL jsonpage2 = new URL(url2);
		HttpURLConnection urlcon2 = (HttpURLConnection) jsonpage2.openConnection();
		BufferedReader buffread2 = new BufferedReader(new InputStreamReader(urlcon2.getInputStream()));

		while ((recv2 = buffread2.readLine()) != null)
			recvbuff2 += recv2;
		buffread2.close();
		
		//followed topics
		try {
			JSONArray topicIdArray = new JSONArray(recvbuff2);			
			JSONArray topicArray = new JSONArray();
			for(int i=0; i< topicIdArray.length() ; i++){
				int topicId = (Integer) topicIdArray.get(i);
				String url3 = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topic&tid="+topicId;
				URL jsonpage3 = new URL(url3);
				HttpURLConnection urlcon3 = (HttpURLConnection) jsonpage3.openConnection();
				BufferedReader buffread3 = new BufferedReader(new InputStreamReader(urlcon3.getInputStream()));
				
				String recv3 = "";
				String recvbuff3 = "";
				while ((recv3 = buffread3.readLine()) != null)
					recvbuff3 += recv3;
				buffread3.close();

				try {
					JSONObject topicObject = new JSONObject();
					JSONObject obj = new JSONObject(recvbuff3);
						Set<String> sattr = obj.keySet();
					for (String attribute : sattr) {
						if(attribute.equalsIgnoreCase("header") || 
								attribute.equalsIgnoreCase("image") || 
								attribute.equalsIgnoreCase("id")){
							topicObject.put(attribute, obj.get(attribute));
						}
					}
					topicArray.put(topicObject);

				} catch (JSONException ex) {
					// Do nothing
				}

			}

			if(topicArray != null){
				request.setAttribute("fol_topics", topicArray);
				request.getRequestDispatcher("/following-topics.jsp").forward(request, response);
			}

		} catch (JSONException ex) {
			request.setAttribute("err", "Unexpected error occured!!");
			request.getRequestDispatcher("/following-topics.jsp").forward(request, response);
		}
		
		
	}
		
	
}
