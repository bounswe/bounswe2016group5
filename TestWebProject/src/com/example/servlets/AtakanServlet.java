package com.example.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
	static final String DB_URL = "jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/ATAKAN";

	// Database credentials
	static final String USER = "group5";
	static final String PASS = "Cmpe352*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AtakanServlet() {
		super();
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
			String query1 = request.getParameter("query");
			search(query1, out);
		} else if (request.getParameter("show") != null) {
			Connection conn = null;
			Statement stmt = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				String sql = "SELECT * FROM ATAKAN_SAVE";
				java.sql.ResultSet rs = stmt.executeQuery(sql);
				String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/atakan-guney\">";
				table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Mathematician</th>\n"
						+ "<th>Sample</th>\n" + "<th>Count</th>\n" + "<th>Place of Birth</th>\n"
						+ "<th>Area in Math</th>\n" + "</tr>\n";

				while (rs.next()) {
					String name = rs.getString("name");
					String nameURI = rs.getString("nameURI");
					String sample = rs.getString("sample");
					String sampleURI = rs.getString("sampleURI");
					String count = rs.getString("count");
					String birthPlace = rs.getString("birthplace");
					String birthPlaceURI = rs.getString("birthplaceURI");
					String area = rs.getString("area");
					String areaURI = rs.getString("areaURI");
					table += "<tr>\n";

					table += "<td>\n" + "<a href=\"" + nameURI + "\">" + name + "</td>\n";

					table += "<td>\n" + "<a href=\"" + sampleURI + "\">" + sample + "</td>\n";

					table += "<td>\n" + count + "</td>\n";

					table += "<td>\n" + "<a href=\"" + birthPlaceURI + "\">" + birthPlace + "</td>\n";

					table += "<td>\n" + "<a href=\"" + areaURI + "\">" + area + "</td>\n";

					table += "</tr>\n";
				}
				table += "</table>";
				table += "</form>";
				out.println(table);
				out.println("<a href=\"/TestWebProject/atakan-guney\"> Click here</a> to redirect.");
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
		} else if (request.getParameter("selected") != null) {
			String[] selectedItems = request.getParameterValues("selected");

			Connection conn = null;
			Statement stmt = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				String sql = "";
				for (int i = 0; i < selectedItems.length; i++) {
					String uniqueID = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String[] elts = selectedItems[i].split(",");
					sql = "INSERT INTO ATAKAN_SAVE " + "VALUES ( \"" + uniqueID + i + "\", \"" + elts[0] + "\", \""
							+ elts[1] + "\", \"" + elts[2] + "\", \"" + elts[3] + "\", \"" + elts[4] + "\", \""
							+ elts[5] + "\", \"" + elts[6] + "\", \"" + elts[7] + "\", \"" + elts[8] + "\")";
					stmt.executeUpdate(sql);

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

		} else if (request.getParameter("delete") != null) {
			dropSaveTable();
			createSaveTable();
			out.println("DONE!!!<a href=\"/TestWebProject/atakan-guney\"> click here</a> to redirect.");
		} else {
			response.sendRedirect("/TestWebProject/atakan-guney.jsp");
		}
	}

	public void dropSaveTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "DROP TABLE ATAKAN_SAVE";
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

	public void createSaveTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE ATAKAN_SAVE (ID VARCHAR(255) not NULL, nameURI VARCHAR(255),name VARCHAR(255),sampleURI VARCHAR(255),sample VARCHAR(255),count VARCHAR(255), birthplaceURI VARCHAR(255), birthplace VARCHAR(255), areaURI VARCHAR(255), area VARCHAR(255), PRIMARY KEY (ID))";
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

	public void createTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE ATAKAN_MATHEMATICIANS" + "("
					+ "nameURI VARCHAR(255), name VARCHAR(255), sampleURI VARCHAR(255), sample VARCHAR(255), count VARCHAR(255) , birthplaceURI VARCHAR(255), birthplace VARCHAR(255), areaURI VARCHAR(255), area VARCHAR(255))";
			stmt.executeUpdate(sql);
			System.out.println("Table created");
			String s1 = "PREFIX wd: <http://www.wikidata.org/entity/>\n"
					+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"
					+ "PREFIX wikibase: <http://wikiba.se/ontology#>\n" + "PREFIX p: <http://www.wikidata.org/prop/>\n"
					+ "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n"
					+ "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
					+ "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +

					"#Most eponymous mathematicians\n"
					+ "SELECT ?eponym ?eponymLabel ?count ?sample ?sampleLabel ?birthplace ?birthplaceLabel ?area ?areaLabel\n"
					+ "WHERE\n" + "{\n" + "{\n" + "SELECT ?eponym (COUNT(?item) as ?count) (SAMPLE(?item) AS ?sample)\n"
					+ "WHERE\n" + "{\n" + "?item wdt:P138 ?eponym.\n" + "?eponym wdt:P106 wd:Q170790.\n" + "}\n"
					+ "GROUP BY ?eponym\n" + "}\n" + "?eponym wdt:P106 wd:Q170790 .\n" + " ?eponym wdt:P101 ?area.\n"
					+ "?eponym wdt:P19 ?place .\n" + "?place wdt:P625 ?coord .\n" + "?place wdt:P17 ?birthplace.\n"
					+ "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }\n" + "}\n"
					+ "ORDER BY DESC(?count)\n";
			Query query = QueryFactory.create(s1);
			QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
			ResultSet results = qExe.execSelect();
			int i = 0;
			while (results.hasNext()) {
				sql = "INSERT INTO ATAKAN_MATHEMATICIANS " + "VALUES (";
				QuerySolution currentSolution = results.nextSolution();
				String name = currentSolution.getLiteral("?eponymLabel").toString();
				String nameURI = currentSolution.getResource("?eponym").toString();
				String sample = currentSolution.getLiteral("?sampleLabel").toString();
				String sampleURI = currentSolution.getResource("?sample").toString();
				String birthPlace = currentSolution.getLiteral("?birthplaceLabel").toString();
				String birthPlaceURI = currentSolution.getResource("?birthplace").toString();
				String area = currentSolution.getLiteral("?areaLabel").toString();
				String areaURI = currentSolution.getResource("?area").toString();
				String count = currentSolution.getLiteral("?count").toString();
				if (name.contains("@"))
					sql += " \"" + nameURI + "\", \"" + name.substring(0, name.indexOf('@'));
				else {
					sql += "\"" + nameURI + "\", \"" + name;
				}
				sql += "\", ";
				if (sample.contains("@"))
					sql += "\"" + sampleURI + "\", \"" + sample.substring(0, sample.indexOf('@'));
				else {
					sql += "\"" + sampleURI + "\", \"" + sample;
				}
				sql += "\", \"" + count.substring(0, count.indexOf('^'));
				sql += "\", ";
				if (birthPlace.contains("@"))
					sql += " \"" + birthPlaceURI + "\", \"" + birthPlace.substring(0, birthPlace.indexOf('@'));
				else {
					sql += "\"" + birthPlaceURI + "\", \"" + birthPlace;
				}
				sql += "\", ";
				if (area.contains("@"))
					sql += " \"" + areaURI + "\", \"" + area.substring(0, area.indexOf('@'));
				else {
					sql += "\"" + areaURI + "\", \"" + area;
				}
				sql += "\" )";
				stmt.executeUpdate(sql);
				i++;
				System.out.println(i + "th data added!");
			}

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

	public void dropTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "DROP TABLE ATAKAN_MATHEMATICIANS";
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

	public void search(String searchQuery, PrintWriter out) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "SELECT * FROM ATAKAN_MATHEMATICIANS";
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/atakan-guney\">";
			table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Select</th>\n"
					+ "<th>Mathematician</th>\n" + "<th>Sample</th>\n" + "<th>Count</th>\n"
					+ "<th>Place of Birth</th>\n" + "<th>Area in Math</th>\n" + "</tr>\n";

			while (rs.next()) {
				String name = rs.getString("name");
				if (!name.toLowerCase().contains(searchQuery.toLowerCase()))
					continue;
				String nameURI = rs.getString("nameURI");
				String sample = rs.getString("sample");
				String sampleURI = rs.getString("sampleURI");
				String count = rs.getString("count");
				String birthPlace = rs.getString("birthplace");
				String birthPlaceURI = rs.getString("birthplaceURI");
				String area = rs.getString("area");
				String areaURI = rs.getString("areaURI");

				table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"selected\" value=\"" + nameURI + ","
						+ name + "," + sampleURI + "," + sample + "," + count + "," + birthPlaceURI + "," + birthPlace
						+ "," + areaURI + "," + area + "\"/>" + "</td>\n";
				table += "<td>\n" + "<a href=\"" + nameURI + "\">" + name + "</td>\n";

				table += "<td>\n" + "<a href=\"" + sampleURI + "\">" + sample + "</td>\n";

				table += "<td>\n" + count + "</td>\n";

				table += "<td>\n" + "<a href=\"" + birthPlaceURI + "\">" + birthPlace + "</td>\n";

				table += "<td>\n" + "<a href=\"" + areaURI + "\">" + area + "</td>\n";

				table += "</tr>\n";
			}
			table += "</table>";
			table += "<table><tr><td><input id=\"submit\" name=\"submit\" type=\"submit\" value=\"save\"/></td></tr></table>";
			table += "</form>";
			out.println(table);

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

	public void createDatabase() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(
					"jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/", USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();

			String sql = "CREATE DATABASE ATAKAN";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
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
		// createDatabase();
		this.doGet(request, response);

	}
}