package com.example.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

/*
 * 
 * @author buraksuyunu 
 *         2012400156 This code is written for the assignment 6 of the course
 *         CmpE 352 Spring '16. version 1.5
 *	
 */
@WebServlet("/burak-suyunu")
public class BurakServlet extends HttpServlet {
	private static final long serialVersionUID = 11242324L;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/group5db";

	// Database credentials
	static final String USER = "group5";
	static final String PASS = "Cmpe352*";
	private static final Object NULL = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BurakServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		/*
		 * Enters when the user enters a query
		 */
		if (request.getParameter("query") != null) {
			String s1 = "PREFIX wd: <http://www.wikidata.org/entity/>\n"
					+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"
					+ "PREFIX wikibase: <http://wikiba.se/ontology#>\n" + "PREFIX p: <http://www.wikidata.org/prop/>\n"
					+ "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n"
					+ "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
					+ "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +

					"SELECT ?human ?humanLabel ?awardEditionLabel ?awardLabel ?awardWork ?awardWorkLabel ?time\n"
					+ "WHERE\n{\n{\n"
					+ "SELECT (SAMPLE(?H) AS ?human) ?award ?awardWork (SAMPLE(?aE) AS ?awardEdition) (SAMPLE(?T) AS ?time)"
					+ "WHERE {\n"
					+ "?award wdt:P31 wd:Q19020 . \n{\n"
					+ "?H p:P166 ?awardStat . \n"
					+ "?awardStat ps:P166 ?award . \n"
					+ "?awardStat pq:P805 ?aE . \n"
					+ "?awardStat pq:P1686 ?awardWork . \n"
					+ "?H wdt:P31 wd:Q5 . \n"
					+ "} UNION { \n"
					+ "?awardWork wdt:P31 wd:Q11424 . \n"
					+ "?awardWork p:P166 ?awardStat . \n"
					+ "?awardStat ps:P166 ?award . \n"
					+ "?awardStat pq:P805 ?aE . \n"
					+ "}\n OPTIONAL {\n"
					+ "?aE wdt:P585 ?T . \n}\n}\n"
					+ "GROUP BY ?awardWork ?award \n}\n"
					+ "SERVICE wikibase:label {\n"
					+ "bd:serviceParam wikibase:language \"en\" .\n}\n}\n"
					+ "ORDER BY DESC(?time)\n LIMIT 200\n";
			
			Query query = QueryFactory.create(s1);
			QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			ResultSet results = qExe.execSelect();
			
			String query1 = request.getParameter("query");
			//createResultTable(results, out, query1);
			
			String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/burak-suyunu\">";
			table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Select</th>\n" + "<th>human</th>\n"
					+ "<th>awardEditionLabel</th>\n" + "<th>awardLabel</th>\n"
					+ "<th>awardWork</th>\n" + "<th>time</th>\n" + "</tr>\n";

			while (results.hasNext()) {
				QuerySolution currentSolution = results.nextSolution();
				String human = "@";
				String humanLabel  = "@";
				if(currentSolution.getResource("?human") != null)
					human = currentSolution.getResource("?human").toString();
				if(currentSolution.getLiteral("?humanLabel") != null)
					humanLabel = currentSolution.getLiteral("?humanLabel").toString();
				String awardEditionLabel = currentSolution.getLiteral("?awardEditionLabel").toString();
				String awardLabel = currentSolution.getLiteral("?awardLabel").toString();
				String awardWork = currentSolution.getResource("?awardWork").toString();
				String awardWorkLabel = currentSolution.getLiteral("?awardWorkLabel").toString();
				String time = currentSolution.getLiteral("?time").toString();
				
				if (!humanLabel.toLowerCase().contains(query1.toLowerCase()))
					if (!awardEditionLabel.toLowerCase().contains(query1.toLowerCase()))
						if (!awardWorkLabel.toLowerCase().contains(query1.toLowerCase()))
							continue;
				table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"selected\" value=\"" + human + "," + humanLabel
						+ "," + awardEditionLabel + "," + awardLabel  + "," + awardWork
						+ "," + awardWorkLabel + "," + time + "," + "\"/>" + "</td>\n";
				
				
				table += "<td>\n" + "<a href=\"" + human + "\">" + humanLabel.substring(0, humanLabel.indexOf('@')) + "</td>\n";
				table += "<td>\n" + awardEditionLabel.substring(0, awardEditionLabel.indexOf('@')) + "</td>\n";
				table += "<td>\n" + awardLabel.substring(0, awardLabel.indexOf('@')) + "</td>\n";
				table += "<td>\n" + "<a href=\"" + awardWork + "\">" + awardWorkLabel.substring(0, awardWorkLabel.indexOf('@')) + "</td>\n";
				table += "<td>\n" + time.substring(0, time.indexOf('^')) + "</td>\n";
				table += "</tr>\n";
			}
			table += "</table>";
			table += "<table><tr><td><input id=\"submit\" name=\"submit\" type=\"submit\" value=\"save\"/></td></tr></table>";
			table += "</form>";
			out.println(table);
			

		}
		/*
		 * Enters when the user wants to save data to database
		 */
		else if (request.getParameter("selected") != null) {
			String[] selectedItems = request.getParameterValues("selected");
			//out.println(Arrays.toString(selectedItems));

			Connection conn = null;
			Statement stmt = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				
				createTable();

				String sql = "";
				for (int i = 0; i < selectedItems.length; i++) {
					String[] elts = selectedItems[i].split(",");

						sql = "INSERT INTO burak_awards " + "VALUES ( \"" + elts[0] + "\", \"" + elts[1] + "\", \"" + elts[2]
								+ "\", \"" + elts[3] + "\", \"" + elts[4] + "\", \"" + elts[5] + "\", \"" + elts[6] + "\")";
						stmt.executeUpdate(sql);

				}
				out.print("<br><br>DATA SAVED<br>Please <a href=\"/TestWebProject/burak-suyunu\"> click here</a> to redirect.");

			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null)
						conn.close();
				} catch (SQLException se) {
				}
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		/*
		 * Enters when the user wants to see the saved data
		 */
		else if (request.getParameter("show") != null) {
			Connection conn = null;
			Statement stmt = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				String sql = "SELECT human, humanLabel, awardEditionLabel, awardLabel, awardWork, awardWorkLabel, time FROM burak_awards";
				java.sql.ResultSet rs = stmt.executeQuery(sql);
				String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/burak-suyunu\">";
				table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>human</th>\n"
						+ "<th>awardEditionLabel</th>\n" + "<th>awardLabel</th>\n"
						+ "<th>awardWork</th>\n" + "<th>time</th>\n" + "</tr>\n";

				while (rs.next()) {
					// Retrieve by column name
					String human = rs.getString("human");
					String humanLabel = rs.getString("humanLabel");
					String awardEditionLabel = rs.getString("awardEditionLabel");
					String awardLabel = rs.getString("awardLabel");
					String awardWork = rs.getString("awardWork");
					String awardWorkLabel = rs.getString("awardWorkLabel");
					String time = rs.getString("time");

					table += "<tr>\n";
					table += "<td>\n" + "<a href=\"" + human + "\">" + humanLabel.substring(0, humanLabel.indexOf('@')) + "</td>\n";
					table += "<td>\n" + awardEditionLabel.substring(0, awardEditionLabel.indexOf('@')) + "</td>\n";
					table += "<td>\n" + awardLabel.substring(0, awardLabel.indexOf('@')) + "</td>\n";
					table += "<td>\n" + "<a href=\"" + awardWork + "\">" + awardWorkLabel.substring(0, awardWorkLabel.indexOf('@')) + "</td>\n";
					table += "<td>\n" + time.substring(0, time.indexOf('^')) + "</td>\n";
					table += "</tr>\n";
				}
				table += "</table>";
				table += "</form>";
				out.println(table);

				rs.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null)
						conn.close();
				} catch (SQLException se) {
				}
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		/*
		 * Enters when the user choose to delete saved data
		 */
		else if (request.getParameter("delete") != null){
			emptyTable();
			out.println("Data Truncated!!!<br><br><a href=\"/TestWebProject/burak-suyunu\"> click here</a> to redirect.");
		}
		else {
			response.sendRedirect("/TestWebProject/burak-suyunu.jsp");
		}
	}

	/*
	 * Creates new DB table burak_awards to hold saved data
	 */
	public void createTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS burak_awards" + "(" + " human VARCHAR(255), " + "humanLabel VARCHAR(255), "
					+ " awardEditionLabel VARCHAR(255), " + "awardLabel VARCHAR(255), " + "awardWork VARCHAR(255), "
					+ "awardWorkLabel VARCHAR(255), " + " time VARCHAR(255)" + ")";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	/*
	 * Truncates burak_awards table
	 */
	public void emptyTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "TRUNCATE TABLE  burak_awards";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
