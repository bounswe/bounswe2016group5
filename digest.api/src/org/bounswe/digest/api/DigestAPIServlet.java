package org.bounswe.digest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bounswe.digest.api.database.ConnectionPool;
import org.bounswe.digest.api.database.TopicJDBC;
import org.bounswe.digest.api.database.UserJDBC;
import org.bounswe.digest.api.database.model.*;
import org.bounswe.digest.semantic.*;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@WebServlet("/")
public class DigestAPIServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8204342910649235663L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String f = req.getParameter(DigestParameters.FUNC);
		if (f == null || f.length() == 0) {
			resp.getWriter().append("Welcome to Digest API");
		} else if (f.equals(DigestParameters.GET_TOPICS_OF_USER)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int ruid = Integer.parseInt(req.getParameter(DigestParameters.RUID));
			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(TopicJDBC.getTopicsWithUser(ruid));
			// } else {
			// resp.getWriter().append(invalidSession());

			// }
		} else if (f.equals(DigestParameters.GET_COMMENTS_OF_TOPIC)) {
			//int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			//String session = req.getParameter(DigestParameters.SESSION);
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			//if (UserJDBC.isSessionValid(uid, session)) {
				resp.getWriter().append(TopicJDBC.getCommentsOfTopic(tid));
				// resp.getWriter().append(TopicJDBC.getCommentsOfTopic(tid));
			//} else {
				//resp.getWriter().append(invalidSession());

			//}
		}else if (f.equals(DigestParameters.GET_TOPIC)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			resp.getWriter().append(TopicJDBC.getTopic(tid));
		}else if (f.equals(DigestParameters.GET_USERNAME)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			resp.getWriter().append(UserJDBC.getUserName(uid));
		}else if (f.equals(DigestParameters.ADD_MEDIA)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			String url = req.getParameter(DigestParameters.URL);
			if(TopicJDBC.addMedia(tid, url)==0){
				resp.setStatus(200);
			}else{
				resp.setStatus(400);
			}
		}else if (f.equals(DigestParameters.GET_QUIZ)) {
			//int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			//String session = req.getParameter(DigestParameters.SESSION);
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			//if (UserJDBC.isSessionValid(uid, session)) {
				resp.getWriter().append(TopicJDBC.getQuizzesOfTopic(tid));
		//	} else {
			//	resp.getWriter().append(invalidSession());

			//}		
		} else if (f.equals(DigestParameters.GET_RECENT_TOPICS)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int count = Integer.parseInt(req.getParameter(DigestParameters.COUNT));
			//if (UserJDBC.isSessionValid(uid, session)) {
				resp.getWriter().append(TopicJDBC.getRecentTopics(count));
			//} else {
				//resp.getWriter().append(invalidSession());
			//}
		} else if (f.equals(DigestParameters.ADD_COMMENT)){
			String body = req.getParameter(DigestParameters.BODY);
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int ucid = Integer.parseInt(req.getParameter(DigestParameters.UCID));
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			if (TopicJDBC.addComment(body, uid, ucid, tid) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}	
		}else if (f.equals(DigestParameters.ADD_SUBSCRIBER)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));

			// if (UserJDBC.isSessionValid(uid, session)) {
			if(TopicJDBC.addSubscriberToTopic(tid, uid) != -1){
				resp.setStatus(200);
				
			}else{
				resp.setStatus(400);
			}
			// } else {
				// resp.getWriter().append(invalidSession());

			//}	
		}else if (f.equals(DigestParameters.GET_SUBSCRIBED_TOPICS)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));

			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(TopicJDBC.getSubscribedTopics(uid));
			// } else {
				// resp.getWriter().append(invalidSession());

			//}	
		} else if (f.equals(DigestParameters.GET_TOPICS_WITH_TAG)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			String tag = req.getParameter(DigestParameters.TAG);

			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(TopicJDBC.getTopicsWithTag(tag));
			// } else {
				// resp.getWriter().append(invalidSession());

			//}	
		}else if (f.equals(DigestParameters.LOGIN)) {
			String username = req.getParameter(DigestParameters.USERNAME);
			String password = req.getParameter(DigestParameters.PASSWORD);
			resp.getWriter().append(UserJDBC.login(username, password));
		} else if (f.equals(DigestParameters.REGISTER)) {
			String username = req.getParameter(DigestParameters.USERNAME);
			String password = req.getParameter(DigestParameters.PASSWORD);
			String email = req.getParameter(DigestParameters.EMAIL);
			String first_name = req.getParameter(DigestParameters.FIRST_NAME);
			String last_name = req.getParameter(DigestParameters.LAST_NAME);
			int status = Integer.parseInt(req.getParameter(DigestParameters.STATUS));
			/* role is implicit for now */
			Role role = new Role(2, "user");
			if (UserJDBC.register(username, password, email, first_name, last_name, status, role) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}
		} else if (f.equals(DigestParameters.GET_TAG_SUGGESTIONS)){ 
			String queryText = req.getParameter(DigestParameters.BODY);
			CalaisAPI httpClientPost = new CalaisAPI();
			httpClientPost.input = queryText;
			httpClientPost.client = new HttpClient();
			httpClientPost.client.getParams().setParameter("http.useragent", "Calais Rest Client");
			httpClientPost.run();
			JSONObject jsonObj = new JSONObject(httpClientPost.getOutput());
	        Iterator<String> i = jsonObj.keys();
	        JSONArray ja = new JSONArray();
	        JSONObject jo = new JSONObject();
			while(i.hasNext()) {
				String key = (String)i.next();
				if(key != null) {
					JSONObject item = (JSONObject)jsonObj.get(key);
					if (item.has("_typeGroup")) {
						String typeGroup = item.getString("_typeGroup");
						if(typeGroup != null && typeGroup.equals("entities")) {
							String name = item.getString("name");
							Double relevance = item.getDouble("relevance");
							jo.put(name, relevance);
						}
						if(typeGroup != null && typeGroup.equals("socialTag")) {
							String name = item.getString("name");
							Double relevance = item.getDouble("importance");
							jo.put(name, relevance);
						}
					}
	        	}
				ja.put(jo);
	       	}
			Gson gson = new Gson();
			resp.getWriter().append(gson.toJson(ja));
		}
			else {
			resp.getWriter().append("Welcome to Digest API");
		}
		// doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String f = req.getParameter(DigestParameters.FUNC);
		if (f == null || f.length() == 0) {

		} else if (f.equals(DigestParameters.ADD_QUIZ)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			BufferedReader bufferedReader = new BufferedReader(req.getReader());
			Gson gson = new Gson();
			Quiz quiz = gson.fromJson(bufferedReader, Quiz.class);
			if (TopicJDBC.addQuizToTopic(tid, quiz) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}
		} else if (f.equals(DigestParameters.CREATE_TOPIC)) {

			BufferedReader bufferedReader = new BufferedReader(req.getReader());
			Gson gson = new Gson();
			Topic topic = gson.fromJson(bufferedReader, Topic.class);
			int tid = TopicJDBC.createTopic(topic);
			// TODO add tags
			if (tid != -1) {
				resp.setStatus(200);
				resp.getWriter().append("" + tid);
			} else {
				resp.setStatus(400);
			}

		}else{
			doGet(req, resp);
		}
		/*
		 * else if(f=){ BufferedReader bufferedReader = new
		 * BufferedReader(req.getReader()); Gson gson = new Gson(); Role
		 * role=gson.fromJson(bufferedReader, Role.class);
		 * //resp.getWriter().append(role.getName());
		 * resp.getWriter().append(role.getName()); }
		 */
		// 
		/*
		 * try {
		 * 
		 * 
		 * MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		 * crypt.reset(); String username="kerimgokarslan"; String
		 * password="123456"; crypt.update(password.getBytes("UTF-8")); String
		 * sha1 = byteToHex(crypt.digest());
		 * resp.getWriter().append("Hello world" + } catch (SQLException |
		 * NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String invalidSession() {
		return "Invalid session";

	}

}
