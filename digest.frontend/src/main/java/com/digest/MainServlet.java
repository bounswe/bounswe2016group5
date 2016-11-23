package com.digest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
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
		String recv = "";
		String recvbuff = "";
		
		//String url = bf.toString();
		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_recent_topics&count=15";
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
				request.setAttribute("recentTopics", topicArray);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}

		} catch (JSONException ex) {
			request.setAttribute("err", "Unexpected error occured!!");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
	}	
}
