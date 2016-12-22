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

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String f = request.getParameter("f");

		if (f != null && f.contentEquals("search")) {

			String searchTerm = request.getParameter("searchterm");
			if (searchTerm != null && !searchTerm.contentEquals("")) {
				System.out.println(searchTerm);
			}

		} else if (f!=null && f.contentEquals("search_with_tag")) {
			if (request.getParameter("tag") != null && !request.getParameter("tag").contentEquals("")) {
				String tag = request.getParameter("tag");
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=search_topics&text="
						+ tag;
				
				URL jsonPage = new URL(url);
				HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				
				String line = "";
				String content = "";
				
				while((line = reader.readLine()) != null){
					content += line;
				}
				
				reader.close();
				System.out.println(content);
				//String example = "[{\"id\":1,\"uid\":4,\"name\":\"animal\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0}]";
				response.getWriter().write(content);
				//response.getWriter().write("[{\"id\":134,\"header\":\"ttt\",\"image\":\"\",\"owner\":32,\"status\":0,\"timestamp\":\"Dec 20, 2016 7:49:50 PM\"},{\"id\":133,\"header\":\"ttt\",\"image\":\"\",\"owner\":32,\"status\":0,\"timestamp\":\"Dec 20, 2016 7:49:09 PM\"}");
				response.getWriter().flush();
				response.getWriter().close();
			}

		}else if (f!=null && f.contentEquals("search_page")) {
			if (request.getParameter("tag") != null && !request.getParameter("tag").contentEquals("")) {
				String tag = request.getParameter("tag");
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=search_topics&text="
						+ tag;
				
				URL jsonPage = new URL(url);
				HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String recv = "";
				String recvbuff = "";
				while ((recv = reader.readLine()) != null)
					recvbuff += recv;
				reader.close();
				
				try {
					JSONArray topicArray = new JSONArray(recvbuff);
					
					if(topicArray != null){
						request.setAttribute("searchTopics", topicArray);
						request.getRequestDispatcher("/search.jsp?search_field=" + tag).forward(request, response);

					}
					
				} catch (JSONException ex) {
				}
			}

		}else if(f!=null && f.contentEquals("advanced_search")){
			
			String header = request.getParameter("header");
			String tag = request.getParameter("tag");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			
			String url="http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=advanced_search&tag="+tag+"&header="+header;
			
			System.out.println(header + " " +tag +" "+fromDate+" "+toDate);
		
		}

	}

}
