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
		HttpSession session = request.getSession();
		int topicId = -1;
		try {
			if (request.getParameter("topic_id") != null) {
				topicId = Integer.parseInt(request.getParameter("topic_id"));
			}else
				topicId = (Integer) request.getAttribute("topic_id");
		}

		catch (NullPointerException e) {

		}
		
		if(session.getAttribute("topic_id") != null){
			topicId = (Integer) session.getAttribute("topic_id");
			session.removeAttribute("topic_id");
		}
		

		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topic&tid=" + topicId;
		URL jsonpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) jsonpage.openConnection();
		BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

		String recv = "";
		String recvbuff = "";
		while ((recv = buffread.readLine()) != null)
			recvbuff += recv;
		buffread.close();
		System.out.println(recvbuff);

		try {
			JSONObject obj = new JSONObject(recvbuff);

			Set<String> sattr = obj.keySet();
			int owner = -1;
			for (String attribute : sattr) {
				if (attribute.equalsIgnoreCase("owner")) {
					owner = (Integer) obj.get(attribute);
					// şeyma
					String url2 = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_username&uid="
							+ owner;
					URL jsonpage2 = new URL(url2);
					HttpURLConnection urlcon2 = (HttpURLConnection) jsonpage2.openConnection();
					BufferedReader buffread2 = new BufferedReader(new InputStreamReader(urlcon2.getInputStream()));

					String recv2 = "";
					String recvbuff2 = "";
					while ((recv2 = buffread2.readLine()) != null)
						recvbuff2 += recv2;
					buffread2.close();

					request.setAttribute("ownerId", owner);
					request.setAttribute("ownerName", recvbuff2.toString());
				}
				if (attribute.equalsIgnoreCase("media")) {
					JSONArray mediaArray = (JSONArray) obj.get(attribute);
					request.setAttribute("media", mediaArray);
				}
				if (attribute.equalsIgnoreCase("image") || attribute.equalsIgnoreCase("header")
						|| attribute.equalsIgnoreCase("body") || attribute.equalsIgnoreCase("id")) {
					request.setAttribute(attribute, obj.get(attribute));
				}if(attribute.equalsIgnoreCase("tags")){
					System.out.println("view" + obj.get(attribute));
					request.setAttribute(attribute, obj.get(attribute));
					
				}
			}

			int subscribed = 0;
			if (session.getAttribute("id") != null) {
				if ((Integer) session.getAttribute("id") != owner) {
					String recv2 = "";
					String recvbuff2 = "";

					StringBuffer bf2 = new StringBuffer();
					bf2.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/");
					bf2.append("?f=get_subscribed_topics&uid=" + session.getAttribute("id"));

					String url2 = bf2.toString();
					URL jsonpage2 = new URL(url2);
					HttpURLConnection urlcon2 = (HttpURLConnection) jsonpage2.openConnection();
					BufferedReader buffread2 = new BufferedReader(new InputStreamReader(urlcon2.getInputStream()));

					while ((recv2 = buffread2.readLine()) != null)
						recvbuff2 += recv2;
					buffread2.close();

					try {
						JSONArray topicArray = new JSONArray(recvbuff2);

						for (Object t : topicArray) {
							if ((Integer) t == topicId) { // Already subscribed
								subscribed = 1;
							}
						}
					} catch (JSONException ex) {
						request.setAttribute("err", "Unexpected error occured!!");
						// request.getRequestDispatcher("/profile.jsp").forward(request,
						// response);
					}

				}
			}
			request.setAttribute("subscribed", subscribed);
			
			if(subscribed == 1){
				String progressURLString = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_progress_topic&tid="
						+ topicId +"&uid=" + session.getAttribute("id");
				URL progressURL = new URL(progressURLString);
				HttpURLConnection progressCon = (HttpURLConnection) progressURL.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(progressCon.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					res.append(inputLine);
				}
				in.close();
				int progress = Integer.parseInt(res.toString());
				
				System.out.println("progress "+progress);
				request.setAttribute("progress", progress);
				
			}
			

			String commentURLString = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_comments_of_topic&tid="
					+ topicId;
			URL commentURL = new URL(commentURLString);
			HttpURLConnection commentCon = (HttpURLConnection) commentURL.openConnection();

			buffread = new BufferedReader(new InputStreamReader(commentCon.getInputStream()));

			recv = "";
			recvbuff = "";
			while ((recv = buffread.readLine()) != null)
				recvbuff += recv;
			buffread.close();
			try {
				JSONArray comments = new JSONArray(recvbuff);
				request.setAttribute("comments", comments);

			} catch (JSONException ex) {

			}
			
			
			
			String cidURLString = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_channel&tid="
					+ topicId;
			URL cidURL = new URL(cidURLString);
			HttpURLConnection cidCon = (HttpURLConnection) cidURL.openConnection();

			buffread = new BufferedReader(new InputStreamReader(cidCon.getInputStream()));

			recv = "";
			recvbuff = "";
			int cid = -1;
			while ((recv = buffread.readLine()) != null)
				recvbuff += recv;
			buffread.close();
			try {
				cid = Integer.parseInt(recvbuff.toString());
			} catch (JSONException ex) {

			}
			
			if(cid != -1){
				String relatedURLString = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_from_channel&cid="
						+ cid;
				URL relatedURL = new URL(relatedURLString);
				HttpURLConnection relatedCon = (HttpURLConnection) relatedURL.openConnection();
	
				buffread = new BufferedReader(new InputStreamReader(relatedCon.getInputStream()));
	
				recv = "";
				recvbuff = "";
				while ((recv = buffread.readLine()) != null)
					recvbuff += recv;
				buffread.close();
				try {
					JSONArray topicArray = new JSONArray(recvbuff);
					
					if(topicArray != null){
						request.setAttribute("relatedTopics", topicArray);
					}
	
				} catch (JSONException ex) {
	
				}
			}

			String quizURLString = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_quiz&tid=" + topicId;
			URL quizURL = new URL(quizURLString);
			HttpURLConnection quizCon = (HttpURLConnection) quizURL.openConnection();

			buffread = new BufferedReader(new InputStreamReader(quizCon.getInputStream()));

			recv = "";
			recvbuff = "";
			while ((recv = buffread.readLine()) != null)
				recvbuff += recv;
			buffread.close();
			System.out.println(recvbuff);
			try {
				JSONArray quizzes = new JSONArray(recvbuff);
				request.setAttribute("quizzes", quizzes);

			} catch (JSONException ex) {

			}

			request.getRequestDispatcher("/view-topic.jsp").forward(request, response);

		} catch (

		JSONException ex) {
			// Do nothing
		}

	}
}
