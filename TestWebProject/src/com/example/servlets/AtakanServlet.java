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

/**
 * Servlet implementation class AtakanServlet
 */
@WebServlet("/atakan-guney")
public class AtakanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/group5db";

	// Database credentials
	static final String USER = "group5";
	static final String PASS = "Cmpe352*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AtakanServlet() {
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
		if (request.getParameter("query") != null) {
			String s1 = "PREFIX wd: <http://www.wikidata.org/entity/>\n"
					+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"
					+ "PREFIX wikibase: <http://wikiba.se/ontology#>\n" + "PREFIX p: <http://www.wikidata.org/prop/>\n"
					+ "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n"
					+ "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
					+ "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +

					"#Most eponymous mathematicians\n" + "SELECT ?eponym ?eponymLabel ?count ?sample ?sampleLabel\n"
					+ "WHERE\n" + "{\n" + "{\n" + "SELECT ?eponym (COUNT(?item) as ?count) (SAMPLE(?item) AS ?sample)\n"
					+ "WHERE\n" + "{\n" + "?item wdt:P138 ?eponym.\n" + "?eponym wdt:P106 wd:Q170790.\n" + "}\n"
					+ "GROUP BY ?eponym\n" + "}\n"
					+ "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }\n" + "}\n"
					+ "ORDER BY DESC(?count)\n";
			Query query = QueryFactory.create(s1);
			QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
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
				/*
				 * String sql = "DROP TABLE ATAKAN_SAVED_ITEMS";
				 * stmt.executeUpdate(sql); createTable();
				 */
				String sql = "";
				for (int i = 0; i < selectedItems.length; i++) {
					String[] elts = selectedItems[i].split(",");
					sql = "SELECT nameURI, name, sampleURI, sample, count FROM ATAKAN_SAVED_ITEMS WHERE nameURI='"
							+ elts[0] + "';";
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					if (!rs.next()) {
						sql = "INSERT INTO ATAKAN_SAVED_ITEMS " + "VALUES ( \"" + elts[0] + "\", \"" + elts[1]
								+ "\", \"" + elts[2] + "\", \"" + elts[3] + "\", \"" + elts[4] + "\")";
						stmt.executeUpdate(sql);

					}
				}
				out.print("DONE!!!<br>Please <a href=\"/TestWebProject/atakan-guney\"> click here</a> to redirect.");

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
				String sql = "SELECT nameURI, name, sampleURI, sample, count FROM ATAKAN_SAVED_ITEMS";
				java.sql.ResultSet rs = stmt.executeQuery(sql);
				String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/atakan-guney\">";
				table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Mathematician</th>\n"
						+ "<th>Sample</th>\n" + "<th>Count</th>\n" + "</tr>\n";

				while (rs.next()) {
					// Retrieve by column name
					String name = rs.getString("name");
					String nameURI = rs.getString("nameURI");
					String sample = rs.getString("sample");
					String sampleURI = rs.getString("sampleURI");
					String count = rs.getString("count");

					table += "<tr>\n"+"<td>\n";
					if (name.contains("@"))
						table += "<a href=\"" + nameURI + "\">" + name.substring(0, name.indexOf('@'));
					else {
						table += "<a href=\"" + nameURI + "\">" + name;
					}
					table += "</td>\n" + "<td>\n";
					if (sample.contains("@"))
						table += "<a href=\"" + sampleURI + "\">" + sample.substring(0, sample.indexOf('@'));
					else {
						table += "<a href=\"" + sampleURI + "\">" + sample;
					}
					table += "<td>\n" + count.substring(0, count.indexOf('^')) + "</td>\n" + "</tr>\n";
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
			response.sendRedirect("/TestWebProject/atakan-guney.jsp");
		}
	}

	public void createTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE ATAKAN_SAVED_ITEMS" + "(" + " nameURI VARCHAR(255), " + "name VARCHAR(255), "
					+ " sampleURI VARCHAR(255), " + "sample VARCHAR(255), " + " count VARCHAR(255)" + ")";

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
		String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/atakan-guney\">";
		table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Select</th>"
				+ "<th>Mathematician</th>\n" + "<th>Sample</th>\n" + "<th>Count</th>\n" + "</tr>\n";

		while (results.hasNext()) {
			QuerySolution currentSolution = results.nextSolution();
			String name = currentSolution.getLiteral("?eponymLabel").toString();
			String nameURI = currentSolution.getResource("?eponym").toString();
			String sample = currentSolution.getLiteral("?sampleLabel").toString();
			String sampleURI = currentSolution.getResource("?sample").toString();
			String count = currentSolution.getLiteral("?count").toString();
			if (!name.toLowerCase().contains(searchQuery.toLowerCase()))
				continue;
			table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"selected\" value=\"" + nameURI + "," + name
					+ "," + sampleURI + "," + sample + "," + count + "\"/>" + "</td>\n" + "<td>\n";
			if (name.contains("@"))
				table += "<a href=\"" + nameURI + "\">" + name.substring(0, name.indexOf('@'));
			else {
				table += "<a href=\"" + nameURI + "\">" + name;
			}
			table += "</td>\n" + "<td>\n";
			if (sample.contains("@"))
				table += "<a href=\"" + sampleURI + "\">" + sample.substring(0, sample.indexOf('@'));
			else {
				table += "<a href=\"" + sampleURI + "\">" + sample;
			}
			table += "<td>\n" + count.substring(0, count.indexOf('^')) + "</td>\n" + "</tr>\n";
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