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
import org.json.JSONObject;

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
					JSONArray topicArray2 = new JSONArray();
					int channel_progress = 0;
					int topicNum = 0;
					try {
						JSONArray topicArray = new JSONArray(content);
						
						for (Object top : topicArray) {
							JSONObject topic = (JSONObject) top;
							String progressURL = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_progress_topic&tid="
									+ topic.get("id")+"&uid="+session.getAttribute("id");
							URL progressjsonPage = new URL(progressURL);
							HttpURLConnection progressCon = (HttpURLConnection) progressjsonPage.openConnection();
							BufferedReader in = new BufferedReader(new InputStreamReader(progressCon.getInputStream()));
							String inputLine;
							StringBuffer res = new StringBuffer();

							while ((inputLine = in.readLine()) != null) {
								res.append(inputLine);
							}
							in.close();
							int progress = Integer.parseInt(res.toString());
							topic.put("progress", progress);
							channel_progress += progress;
							topicNum ++;
							topicArray2.put(topic);
						}
							
							
						

					} catch (JSONException ex) {
						request.setAttribute("err", "Unexpected error occured!!");
					}
					if(topicNum != 0){
						channel_progress = channel_progress/topicNum;
					}
					JSONObject channelProgress = new JSONObject();
					reader.close();
					channelProgress.put("channel_progress", channel_progress);
					JSONArray aaa = new JSONArray(topicArray2.toString());
					aaa.put(channelProgress);
					
					response.getWriter().write(aaa.toString());
					response.getWriter().flush();
					response.getWriter().close();
				}
				
			}
		}else if(f!=null && f.contentEquals("add_topic_to_channel")){
			String cid = request.getParameter("cid");
			
			if(cid!=null && !cid.contentEquals("")){
				
				String tid = request.getParameter("tid");
				if(tid!=null && !tid.contentEquals("")){
					String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_topic_to_channel&cid="
							+ cid +"&tid=" + tid;
					
					URL jsonPage = new URL(url);
					HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
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
			String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_channels_of_user&uid=" +session.getAttribute("id");
	
			
			URL jsonPage = new URL(url);
			HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}
			reader.close();
			
			String example = "[{\"id\":1,\"uid\":4,\"name\":\"animal\",\"status\":0},{\"id\":22,\"uid\":24,\"name\":\"animal2\",\"status\":0}]";
			response.getWriter().write(content);
			response.getWriter().flush();
			response.getWriter().close();
			
		} else if(f!=null && f.contentEquals("get_channel")){
			String cid = request.getParameter("cid");
			if(cid!=null && !cid.contentEquals("")){
				String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_channel&cid=" + cid;
				
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
				response.getWriter().flush();
				response.getWriter().close();
			}
		} else if(f!=null && f.contentEquals("get_progress")){
			String cid = request.getParameter("cid");
			String uid = request.getParameter("uid");
			
			
			response.getWriter().write("0");
			response.getWriter().flush();
		} else if(f!=null && f.contentEquals("get_subscribed_channels")){
			String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_subscribed_channels&uid=" +session.getAttribute("id");

			
			URL jsonPage = new URL(url);
			HttpURLConnection con = (HttpURLConnection) jsonPage.openConnection();
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line = "";
			String content = "";
			
			while((line = reader.readLine()) != null){
				content += line;
			}
			reader.close();
			
			//String example = "[null,null,{\"id\":1,\"uid\":4,\"name\":\"animal\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0},{\"id\":2,\"uid\":24,\"name\":\"animal2\",\"status\":0}]";
			//response.getWriter().write(example);
			response.getWriter().write(content);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

}
