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
		try{
			topicId = (Integer) request.getAttribute("topic_id");
		}
		
		catch(NullPointerException e){
			while (names.hasMoreElements()) {
				String attr = names.nextElement();
				String value = request.getParameter(attr);
				//System.out.println(attr +  " --- " + value);
				if(attr.equalsIgnoreCase("topic_id"))
					topicId= Integer.parseInt(value);
			}
		}
			
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topic&tid="+topicId;
		//String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topic&tid="+35;
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
					System.out.println(attribute + " "+ obj.get(attribute));
					if(attribute.equalsIgnoreCase("owner") || 
							attribute.equalsIgnoreCase("image") || 
							attribute.equalsIgnoreCase("header")||
							attribute.equalsIgnoreCase("body") ||
							attribute.equalsIgnoreCase("id")){
						request.setAttribute(attribute, obj.get(attribute));	
					}
				}
				request.getRequestDispatcher("/view-topic.jsp").forward(request, response);
			}

		} catch (JSONException ex) {
			// Do nothing
		}
		
	}
}
