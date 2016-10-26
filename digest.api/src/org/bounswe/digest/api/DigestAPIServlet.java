package org.bounswe.digest.api;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bounswe.digest.api.database.ConnectionPool;
import org.bounswe.digest.api.database.UserJDBC;

@WebServlet("/")
public class DigestAPIServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8204342910649235663L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Connection conn = ConnectionPool.getConnection();
		try {
			/* test */
			
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        String username="kerimgokarslan";
	        String password="123456";
	        crypt.update(password.getBytes("UTF-8"));
	        String sha1 = byteToHex(crypt.digest());
			resp.getWriter().append("Hello world" + UserJDBC.login(username, sha1).toString());
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}


}
