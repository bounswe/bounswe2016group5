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

@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfileServlet() {
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
		session.getAttribute("id");
		
		String recv = "";
		String recvbuff = "";

		StringBuffer bf = new StringBuffer();
		bf.append("http://digest.us-east-1.elasticbeanstalk.com/digest.api/");
		bf.append("?f=get_topics_of_user&ruid="+session.getAttribute("id"));
		
		

		String url = bf.toString();
		System.out.println(url);
		URL jsonpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) jsonpage.openConnection();
		BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

		while ((recv = buffread.readLine()) != null)
			recvbuff += recv;
		buffread.close();
		System.out.println(recvbuff);
		try {
			JSONArray topicArray = new JSONArray(recvbuff);
			
			if(topicArray != null){
				request.setAttribute("topics", topicArray);
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
			}

		} catch (JSONException ex) {
			request.setAttribute("err", "Unexpected error occured!!");
			request.getRequestDispatcher("/profile.jsp").forward(request, response);
		}
		
		
		
	}
}
