package org.bounswe.digest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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

//import org.apache.commons.httpclient.HttpClient;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bounswe.digest.api.database.ChannelJDBC;
import org.bounswe.digest.api.database.CommentJDBC;
import org.bounswe.digest.api.database.ConnectionPool;
import org.bounswe.digest.api.database.ProgressJDBC;
import org.bounswe.digest.api.database.QuizJDBC;
import org.bounswe.digest.api.database.TopicJDBC;
import org.bounswe.digest.api.database.UserJDBC;
import org.bounswe.digest.api.database.model.*;
import org.bounswe.digest.semantic.*;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@WebServlet("/")
/**
 * Handles HTTP requests.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class DigestAPIServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8204342910649235663L;
	/**
	 * Handles get requests
	 * @param req Request
	 * @param resp Response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String f = req.getParameter(DigestParameters.FUNC);
		if (f == null || f.length() == 0) {
			resp.getWriter().append("Welcome to Digest API");
		} else if (f.equals(DigestParameters.GET_TOPICS_OF_USER)) {
			int ruid = Integer.parseInt(req.getParameter(DigestParameters.RUID));
			resp.getWriter().append(TopicJDBC.getTopicsWithUser(ruid));
		} else if (f.equals(DigestParameters.GET_COMMENTS_OF_TOPIC)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			resp.getWriter().append(CommentJDBC.getCommentsOfTopic(tid));
		} else if (f.equals(DigestParameters.GET_TOPIC)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			resp.getWriter().append(TopicJDBC.getTopic(tid));
		} else if (f.equals(DigestParameters.GET_USERNAME)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			resp.getWriter().append(UserJDBC.getUserName(uid));
		} else if (f.equals(DigestParameters.ADD_MEDIA)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			String url = req.getParameter(DigestParameters.URL);
			if (TopicJDBC.addMedia(tid, url) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}
		} else if (f.equals(DigestParameters.GET_QUIZ)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(QuizJDBC.getQuizzesOfTopic(tid));
			// } else {
			// resp.getWriter().append(invalidSession());

			// }
		} else if (f.equals(DigestParameters.GET_RECENT_TOPICS)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int count = Integer.parseInt(req.getParameter(DigestParameters.COUNT));
			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(TopicJDBC.getRecentTopics(count));
			// } else {
			// resp.getWriter().append(invalidSession());
			// }
		} else if (f.equals(DigestParameters.ADD_COMMENT)) {
			String body = req.getParameter(DigestParameters.BODY);
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int ucid = Integer.parseInt(req.getParameter(DigestParameters.UCID));
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			int type = Integer.parseInt(req.getParameter(DigestParameters.TYPE));
			if (CommentJDBC.addComment(body, uid, ucid, tid, type) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}
		} else if (f.equals(DigestParameters.ADD_SUBSCRIBER)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));

			// if (UserJDBC.isSessionValid(uid, session)) {
			if (TopicJDBC.addSubscriberToTopic(tid, uid) != -1) {
				resp.setStatus(200);

			} else {
				resp.setStatus(400);
			}
			// } else {
			// resp.getWriter().append(invalidSession());

			// }
		} else if (f.equals(DigestParameters.GET_SUBSCRIBED_TOPICS)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));

			// if (UserJDBC.isSessionValid(uid, session)) {
			resp.getWriter().append(TopicJDBC.getSubscribedTopics(uid));
			// } else {
			// resp.getWriter().append(invalidSession());

			// }
		} else if (f.equals(DigestParameters.GET_TOPICS_WITH_TAG)) {
			// int uid =
			// Integer.parseInt(req.getParameter(DigestParameters.UID));
			// String session = req.getParameter(DigestParameters.SESSION);
			String tag = req.getParameter(DigestParameters.TAG);

			// if (UserJDBC.isSessionValid(uid, session)) {
			ArrayList<TopicPreview> topics = TopicJDBC.getTopicsWithTag(tag);
			Gson gson = new Gson();
			resp.getWriter().append(gson.toJson(topics));
			// } else {
			// resp.getWriter().append(invalidSession());

			// }
		} else if (f.equals(DigestParameters.LOGIN)) {
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
		} else if (f.equals(DigestParameters.GET_TAG_SUGGESTIONS)) {
			String queryText = req.getParameter(DigestParameters.BODY);
			queryText.replaceAll("_", " ");
			CalaisAPI httpClientPost = new CalaisAPI();
			Gson gson = new Gson();
			resp.getWriter().append(gson.toJson(httpClientPost.extractTags(queryText)));
		} else if (f.equals(DigestParameters.GET_TAG_ENTITIES)) {
			String queryText = req.getParameter(DigestParameters.TAG);
			ConceptNetAPI httpClientPost = new ConceptNetAPI();
			Gson gson = new Gson();
			resp.getWriter().append(gson.toJson(httpClientPost.extractEntities(queryText)));
		} else if (f.equals(DigestParameters.GET_RELATED_ENTITIES)) {
			String queryText = req.getParameter(DigestParameters.TAG);
			ConceptNetAPI httpClientPost = new ConceptNetAPI();
			Gson gson = new Gson();
			resp.getWriter().append(gson.toJson(httpClientPost.getRelatedEntities(queryText)));
		} else if (f.equals(DigestParameters.ADD_CHANNEL)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			String name = req.getParameter(DigestParameters.NAME);
			ChannelJDBC.addChannel(uid, name);
		} else if (f.equals(DigestParameters.RATE_COMMENT)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			CommentJDBC.rateComment(uid, cid);
		} else if (f.equals(DigestParameters.UNRATE_COMMENT)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			CommentJDBC.unrateComment(uid, cid);
		} else if (f.equals(DigestParameters.ADD_TOPIC_TO_CHANNEL)) {
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			ChannelJDBC.addTopicToChannel(tid, cid);
		} else if (f.equals(DigestParameters.GET_CHANNEL)) {
			if (req.getParameter(DigestParameters.CID) != null) {
				int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
				resp.getWriter().append(ChannelJDBC.getChannel(cid));
			} else {
				int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
				resp.getWriter().append("" + ChannelJDBC.getChannelTid(tid));
			}

		} else if (f.equals(DigestParameters.GET_TOPICS_FROM_CHANNEL)) {
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			resp.getWriter().append(ChannelJDBC.getTopicsOfChannel(cid));
		} else if (f.equals(DigestParameters.GET_CHANNELS_OF_USER)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			resp.getWriter().append(ChannelJDBC.getChannelsOfUser(uid));
		} else if (f.equals(DigestParameters.MARK_COMMENT_AS_QUESTION)) {
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			CommentJDBC.updateType(cid, CommentJDBC.QUESTION);
		} else if (f.equals(DigestParameters.MARK_COMMENT_AS_INSTRUCTIVE)) {
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			CommentJDBC.updateType(cid, CommentJDBC.INSTRUCTIVE);
		} else if (f.equals(DigestParameters.ADD_PROGRESS)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			ProgressJDBC.addProgres(uid, tid);
		} else if (f.equals(DigestParameters.GET_PROGRESS_TOPIC)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			resp.getWriter().append("" + ProgressJDBC.getProgressOnTopic(tid, uid));
		} else if (f.equals(DigestParameters.SET_PROGRESS)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int tid = Integer.parseInt(req.getParameter(DigestParameters.TID));
			int val = Integer.parseInt(req.getParameter(DigestParameters.VAL));
			ProgressJDBC.setProgressTopic(tid, uid, val);
		} else if (f.equals(DigestParameters.GET_PROGRESS_CHANNEL)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			int cid = Integer.parseInt(req.getParameter(DigestParameters.CID));
			resp.getWriter().append("" + ProgressJDBC.getProgressOnChannel(cid, uid));
		} else if (f.equals(DigestParameters.GET_SUBSCRIBED_CHANNELS)) {
			int uid = Integer.parseInt(req.getParameter(DigestParameters.UID));
			resp.getWriter().append(ChannelJDBC.getSubscribedChannels(uid));
		} else if (f.equals(DigestParameters.GET_TRENDING_TOPICS)) {
			resp.getWriter().append(TopicJDBC.getTrendingTopics());
		} else if (f.equals(DigestParameters.SEARCH_TOPICS)) {
			String text = req.getParameter(DigestParameters.TEXT);
			resp.getWriter().append(TopicJDBC.getTopicWithString(text));
		} else if (f.equals("test")) {
			ArrayList<TopicTag> tag = new ArrayList<TopicTag>();
			tag.add(new TopicTag("test(exam)"));
			tag.add(new TopicTag("apple(fruit)"));
			Topic topic = new Topic(0, "header", "image", "body", 1, 0, tag, null, null, null, null);

			System.out.println(TopicJDBC.createTopic(topic));
		}

		else {
			resp.getWriter().append("Welcome to Digest API");
		}

		// doPost(req, resp);
	}
	/**
	 * Handles post requests.
	 * @param req Request.
	 * @param resp Response.
	 * @throws ServletException
	 * @throws IOException
	 */
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
			if (QuizJDBC.addQuizToTopic(tid, quiz) == 0) {
				resp.setStatus(200);
			} else {
				resp.setStatus(400);
			}
		} else if (f.equals(DigestParameters.CREATE_TOPIC)) {

			BufferedReader bufferedReader = new BufferedReader(req.getReader());
			Gson gson = new Gson();
			Topic topic = gson.fromJson(bufferedReader, Topic.class);
			int tid = TopicJDBC.createTopic(topic);
			if (tid != -1) {
				// resp.setStatus(200);
				resp.getWriter().append("" + tid);
			} else {
				resp.setStatus(400);
			}

		} else {
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
	/**
	 * @deprecated
	 * Makes hexadecimal number from byte array
	 * @param hash Byte array.
	 * @return Hexadecimal number as <String>
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	

}
