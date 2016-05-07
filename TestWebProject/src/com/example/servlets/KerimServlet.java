package com.example.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/kerim-gokarslan")
public class KerimServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getParameter("query") == null) {
			save();
			resp.sendRedirect("/TestWebProject/kerim-gokarslan.jsp");
		} else {
			String userQuery = req.getParameter("query");
			resp.getWriter().append(userQuery);
			String query = "https://query.wikidata.org/sparql?query=";
			query += "SELECT%20?h%20WHERE{?h%20wdt:P31%20wd:Q3624078}";
			URL url = new URL(query);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String xmlResult = "";
			String line;
			while ((line = in.readLine()) != null)
				xmlResult += line;
			in.close();
			resp.getWriter().append(xmlResult);
		}
		resp.getWriter().append("Hello world, my name is Kerim!");
		// super.doPost(req, resp);

	}
	public void save(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			
		}
		String url = "jdbc:mysql://group5db.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/";
		//String url = "jdbc:mysql://asasdasdd.com:3306/";
		String userName = "group5";
		String password = "Cmpe352*";
		String dbName = "group5";
		String driver = "com.mysql.jdbc.Driver";
		try {
			Connection connection = DriverManager.getConnection(url + dbName, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		System.out.println("Succeed");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 3818282702335774732L;

	public KerimServlet() {
		super();
	}

}
