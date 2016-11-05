package org.bounswe.digest.api;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bounswe.digest.api.database.ConnectionPool;
import org.bounswe.digest.api.database.TopicJDBC;
import org.bounswe.digest.api.database.UserJDBC;
import org.bounswe.digest.api.database.model.Role;
import org.bounswe.digest.api.database.model.Topic;

@WebServlet("/")
public class DigestAPIServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8204342910649235663L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String f = req.getParameter(DigestParameters.FUNC);
		if(f == null || f.length() == 0){
			resp.getWriter().append("Welcome to Digest API");
		}else if(f.equals(DigestParameters.CREATE_TOPIC)){
			//header, type, image, url, body, owner, status
			String header = req.getParameter(DigestParameters.HEADER);
			String type = req.getParameter(DigestParameters.TYPE);
			String image = req.getParameter(DigestParameters.IMAGE);
			String url = req.getParameter(DigestParameters.URL);
			String body = req.getParameter(DigestParameters.BODY);
			int owner = Integer.parseInt(req.getParameter(DigestParameters.OWNER));
			int status = Integer.parseInt(req.getParameter(DigestParameters.STATUS));
			//TODO Tag, topic manager should be added
		    if(TopicJDBC.createTopic(header,type,image,url,body,owner,status,null,null)==0){
		    	resp.setStatus(200);
			}else{
				resp.setStatus(400);
			}
		}else if(f.equals(DigestParameters.GET_TOPICS_OF_USER)){
			int uid=Integer.parseInt(req.getParameter(DigestParameters.UID));
			ArrayList<Topic> ar=TopicJDBC.getTopicsWithUser(uid);
		}else if(f.equals(DigestParameters.LOGIN)){
			String username = req.getParameter(DigestParameters.USERNAME);
			String password = req.getParameter(DigestParameters.PASSWORD);
			resp.getWriter().append(UserJDBC.login(username,password));
		}else if(f.equals(DigestParameters.REGISTER)){
			String username = req.getParameter(DigestParameters.USERNAME);
			String password = req.getParameter(DigestParameters.PASSWORD);
			String email = req.getParameter(DigestParameters.EMAIL);
			String first_name = req.getParameter(DigestParameters.FIRST_NAME);
			String last_name = req.getParameter(DigestParameters.LAST_NAME);
			int status = Integer.parseInt(req.getParameter(DigestParameters.STATUS));
			/* role is implicit for now */
			Role role = new Role(2, "user");
			if(UserJDBC.register(username, password, email, first_name, last_name, status, role) == 0){
				resp.setStatus(200);
			}else{
				resp.setStatus(400);
			}
		}
		else{
			resp.getWriter().append("Welcome to Digest API");
		}
		// doPost(req, resp);
	}

	@Override

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
		/*
		 * try {
		 * 
		 * 
		 * MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		 * crypt.reset(); String username="kerimgokarslan"; String
		 * password="123456"; crypt.update(password.getBytes("UTF-8")); String
		 * sha1 = byteToHex(crypt.digest());
		 * resp.getWriter().append("Hello world" +  } catch (SQLException | NoSuchAlgorithmException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
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

}
