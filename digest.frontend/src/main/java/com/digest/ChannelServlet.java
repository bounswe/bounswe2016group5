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

/**
 * Servlet implementation class ChannelServlet
 */
@WebServlet("/ChannelServlet")
public class ChannelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChannelServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String f = request.getParameter("f");
		
		if(f!=null && f.contentEquals("add_channel")){
			
			String name = request.getParameter("channel_name");
			name = name.replaceAll(" ", "%20");
			
			if(session.getAttribute("id")!=null){
				String add_channel_url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_channel&name="
						+ name + "&uid="+session.getAttribute("id");
				
				System.out.println(add_channel_url);
				URL jsonPage = new URL(add_channel_url);
				HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
				
				if(con.getResponseCode()!=200){
					String err = "Unexpected error occured!!";
					request.setAttribute("error", err);
					request.getRequestDispatcher("topic-creation.jsp").forward(request, response);
				}
				
			}
			
		}else if(f!=null && f.contentEquals("get_topics")){
			if(request.getParameter("cid")!=null){
				String cid = request.getParameter("cid");
				
				if(cid!=null && !cid.contentEquals("")){
					String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_from_channel&cid="
							+ cid;
					
					URL jsonPage = new URL(url);
					HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					
					String line = "";
					String content = "";
					
					while((line = reader.readLine()) != null){
						content += line;
					}
					
					reader.close();
					
					response.getWriter().write("[{\""
							+ "id\":32,\"header\":\"header111asdc\"},{\"id\":43,\"header\":\"body2345\"}]");
					response.getWriter().flush();
					response.getWriter().close();
				}
			}
		}else if(f!=null && f.contentEquals("add_topic_to_channel")){
			String cid = request.getParameter("cid");
			
			if(cid!=null && !cid.contentEquals("")){
				
				String tid = request.getParameter("tid");
				System.out.println(tid);
				System.out.println(cid);
				if(tid!=null && !tid.contentEquals("")){
					String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_topic_to_channel&cid="
							+ cid +"&tid=" + tid;
					System.out.println(url);
					
					URL jsonPage = new URL(url);
					HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
					System.out.println(con.getResponseMessage());
					if(con.getResponseCode() != 200){
						
						String err = con.getResponseMessage();
						request.setAttribute("error", err);
						request.getRequestDispatcher("/channel.jsp?cid="+cid).forward(request, response);
						
					}else{
						response.sendRedirect("channel.jsp?cid="+cid);
					}
				}
				
			}
			
		} else if(f!=null && f.contentEquals("get_user_channels")){
			response.getWriter().write("[{\"id\":23,\"header\":\"atakan\"},{\"id\":5,\"header\":\"husnenaz\"}]");
			response.getWriter().flush();
			response.getWriter().close();
			
		}
	}

}
