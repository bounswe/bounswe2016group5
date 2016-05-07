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
		if (req.getParameter("query") == null) {
			//save();
			resp.sendRedirect("/TestWebProject/kerim-gokarslan.jsp");
		} else {
			String userQuery = req.getParameter("query");
			resp.getWriter().append(userQuery);
			String query = "https://query.wikidata.org/sparql?query=";
			query += 
"SELECT ?item \n"+
"WHERE  \n"+
"{ \n"+
"	?item wdt:P31 wd:Q1420 .  \n"+
"  	?model wdt:P1552 wd:Q3231690 . FILTER regex(str(?model), \"/yaris/i\") . \n"+
"	SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" } \n"+
"}";


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
		

	}
	public Connection mysqlConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
		String url = "jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/group5db";
		String userName = "group5";
		String password = "Cmpe352*";
	
		String driver = "com.mysql.jdbc.Driver";
		Connection connection;
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return connection;
		
	}
	public void save(){
		Connection connection = mysqlConnection();
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
