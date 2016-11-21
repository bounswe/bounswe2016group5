package com.digest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

import org.json.JSONArray;
import org.json.JSONObject;

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

		/*
		 * StringBuffer bf = new StringBuffer();
		 * bf.append("http://localhost:8080/digest.api/?f=create_topic");
		 * bf.append("?"); Enumeration<String> names =
		 * request.getParameterNames(); while (names.hasMoreElements()) { String
		 * attr = names.nextElement(); String value =
		 * request.getParameter(attr); bf.append(attr + "=" + value); if
		 * (names.hasMoreElements()) bf.append("&"); } String url =
		 * bf.toString(); System.out.println(url);
		 */
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=create_topic";
		URL connpage = new URL(url);
		HttpURLConnection con = (HttpURLConnection) connpage.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");

		JSONObject topic = new JSONObject();
		JSONArray tags = new JSONArray();
		JSONArray quizes = new JSONArray();
		JSONArray media = new JSONArray();

		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			if (!(attr.contentEquals("f") || attr.contentEquals("tags"))) {
				String value = request.getParameter(attr);
				topic.put(attr, value);
			} else if (attr.contentEquals("tags")) {
				String tagsString = request.getParameter(attr);
				String[] tagsArray = tagsString.split(",");

				for (String tagString : tagsArray) {
					JSONObject tag = new JSONObject();
					tag.put("id", -1);
					tag.put("tid", -1);
					tag.put("tag", tagString);
					tags.put(tag);
				}
				
				topic.put("tags", tags);
			}
		}
		
		topic.put("id",-1);
		topic.put("url", "");
		topic.put("status", -1);
		topic.put("quizes", quizes);
		topic.put("media", media);

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		System.out.println(topic.toString());
		wr.writeBytes(topic.toString());
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
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
