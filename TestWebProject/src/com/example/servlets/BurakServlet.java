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
 *         CmpE 352 Spring '16. version 1.0
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
		System.out.println("asdasd\n");
		response.setContentType("text/html");
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
					+ "SELECT (SAMPLE(?human) AS ?human) ?award ?awardWork (SAMPLE(?awardEdition) AS ?awardEdition) (SAMPLE(?time) AS ?time)"
					+ "WHERE {\n"
					+ "?award wdt:P31 wd:Q19020 . \n{\n"
					+ "?human p:P166 ?awardStat . \n"
					+ "?awardStat ps:P166 ?award . \n"
					+ "?awardStat pq:P805 ?awardEdition . \n"
					+ "?awardStat pq:P1686 ?awardWork . \n"
					+ "?human wdt:P31 wd:Q5 . \n"
					+ "} UNION { \n"
					+ "?awardWork wdt:P31 wd:Q11424 . \n"
					+ "?awardWork p:P166 ?awardStat . \n"
					+ "?awardStat ps:P166 ?award . \n"
					+ "?awardStat pq:P805 ?awardEdition . \n"
					+ "}\n OPTIONAL {\n"
					+ "?awardEdition wdt:P585 ?time . \n}\n}\n"
					+ "GROUP BY ?awardWork ?award \n}\n"
					+ "SERVICE wikibase:label {\n"
					+ "bd:serviceParam wikibase:language \"en\" .\n}\n}\n"
					+ "ORDER BY DESC(?time)\n LIMIT 200\n";
			System.out.println("11111\n");
			
			Query query = QueryFactory.create(s1);
			System.out.println("22222\n");
			QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			System.out.println("33333\n");
			ResultSet results = qExe.execSelect();
			
			String query1 = request.getParameter("query");
			createResultTable(results, out, query1);

		} else if (request.getParameter("selected") != null) {
			String[] selectedItems = request.getParameterValues("selected");
			out.println(Arrays.toString(selectedItems));

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
					sql = "SELECT human, humanLabel, awardEditionLabel, awardLabel, awardWork, awardWorkLabel, time FROM burak_awards WHERE nameURI='"
							+ elts[0] + "';";
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					if (!rs.next()) {
						sql = "INSERT INTO burak_awards " + "VALUES ( \"" + elts[0] + "\", \"" + elts[1] + "\", \"" + elts[2]
								+ "\", \"" + elts[3] + "\", \"" + elts[4] + "\", \"" + elts[5] + "\", \"" + elts[6] + "\")";
						stmt.executeUpdate(sql);

					}
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
		} else if (request.getParameter("show") != null) {
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
						+ "<th>humanLabel</th>\n" + "<th>awardEditionLabel</th>\n" + "<th>awardLabel</th>\n"
						+ "<th>awardWork</th>\n" + "<th>awardWorkLabel</th>\n" + "<th>time</th>\n" + "</tr>\n";

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
					table += "<td>\n" + "<a href=\"" + human + "\">" + humanLabel + "</td>\n";
					table += "<td>\n" + awardEditionLabel + "</td>\n";
					table += "<td>\n" + awardLabel + "</td>\n";
					table += "<td>\n" + "<a href=\"" + awardWork + "\">" + awardWorkLabel + "</td>\n";
					table += "<td>\n" + time + "</td>\n";
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
		} else {
			response.sendRedirect("/TestWebProject/burak-suyunu.jsp");
		}
	}

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

	public void createResultTable(ResultSet results, PrintWriter out, String searchQuery) {
		String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/burak-suyunu\">";
		table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>human</th>\n"
				+ "<th>humanLabel</th>\n" + "<th>awardEditionLabel</th>\n" + "<th>awardLabel</th>\n"
				+ "<th>awardWork</th>\n" + "<th>awardWorkLabel</th>\n" + "<th>time</th>\n" + "</tr>\n";

		while (results.hasNext()) {
			QuerySolution currentSolution = results.nextSolution();
			String human = currentSolution.getLiteral("?human").toString();
			String humanLabel = currentSolution.getResource("?humanLabel").toString();
			String awardEditionLabel = currentSolution.getLiteral("?awardEditionLabel").toString();
			String awardLabel = currentSolution.getResource("?awardLabel").toString();
			String awardWork = currentSolution.getLiteral("?awardWork").toString();
			String awardWorkLabel = currentSolution.getLiteral("?awardWorkLabel").toString();
			String time = currentSolution.getResource("?time").toString();
			
			if (!humanLabel.toLowerCase().contains(searchQuery.toLowerCase()))
				if (!awardEditionLabel.toLowerCase().contains(searchQuery.toLowerCase()))
					if (!awardWorkLabel.toLowerCase().contains(searchQuery.toLowerCase()))
						continue;
			table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"selected\" value=\"" + human + "," + humanLabel
					+ "," + awardEditionLabel + "," + awardLabel  + "," + awardWork
					+ "," + awardWorkLabel + "," + time + "," + "\"/>" + "</td>\n" + "<td>\n";
			
			table += "<tr>\n";
			table += "<td>\n" + "<a href=\"" + human + "\">" + humanLabel + "</td>\n";
			table += "<td>\n" + awardEditionLabel + "</td>\n";
			table += "<td>\n" + awardLabel + "</td>\n";
			table += "<td>\n" + "<a href=\"" + awardWork + "\">" + awardWorkLabel + "</td>\n";
			table += "<td>\n" + time + "</td>\n";
			table += "</tr>\n";
		}
		table += "</table>";
		table += "<table><tr><td><input id=\"submit\" name=\"submit\" type=\"submit\" value=\"save\"/></td></tr></table>";
		table += "</form>";
		out.println(table);

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