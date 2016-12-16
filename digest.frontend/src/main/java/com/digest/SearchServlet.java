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
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_with_tag&tag="
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
				
				response.getWriter().write(content);
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
