package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 * Written for the personal part of Assignment 6
 * @author dabager
 * 		   Umut M. Dabager
 * 		   dabager@outlook.com
 * 		   2015700165
 * @version 1
 * 
 */
@WebServlet("/umut-dabager")
public class UmutServlet extends HttpServlet 
{
	private static String valueSeperatorChar = "#";
	private static final long serialVersionUID = 1L;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://bounswegroup5.cpp0ryqf88fx.us-west-2.rds.amazonaws.com:3306/group5db";

	// Database credentials
	static final String USER = "group5";
	static final String PASS = "Cmpe352*";
    /**
     * Default constructor. 
     */
    public UmutServlet() 
    {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/**
		 * Check for the input parameter and redirect.
		 */
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
				
		if (request.getParameter("input") != null && request.getParameter("input") != "")
		{
			String wikiDataQuery = "PREFIX p: <http://www.wikidata.org/prop/>\r\n" + 
					"PREFIX wdt: <http://www.wikidata.org/prop/direct/>\r\n" + 
					"PREFIX wd: <http://www.wikidata.org/entity/>\r\n" +
					"PREFIX wikibase: <http://wikiba.se/ontology#>\n"
					+ "PREFIX bd: <http://www.bigdata.com/rdf#>\n" + 
					
					"\r\n" + 
					"SELECT ?musician ?musicianLabel ?genre ?genreLabel WHERE {\r\n" + 
					"  {\r\n" + 
					"      ?musician wdt:P106 wd:Q639669.\r\n" + 
					"      ?musician wdt:P136 ?genre .\r\n" + 
					"  }\r\n" + 
					"  SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\". }\r\n" + 
					"}\r\n" + 
					"ORDER BY DESC(?musician)"; 
			Query rawQuery = QueryFactory.create(wikiDataQuery);
			QueryExecution execute = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", rawQuery);
			ResultSet wikiDataResults = execute.execSelect();
			/*try 
			{
				insertRawDataToDB(wikiDataResults);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}*/
			createResultTable(wikiDataResults,writer,request.getParameter("input"));
		}
		else if(request.getParameter("check") != null)
		{
			insertSelectedValuesToDB(request);
			response.sendRedirect("/TestWebProject/umut-dabager.jsp");
		}
		else if(request.getParameter("retrieve") != null)
		{
			writer.println(retrieveSavedResults());
		}
		else if(request.getParameter("clear") != null)
		{
			clearSavedResults();
			response.sendRedirect("/TestWebProject/umut-dabager.jsp");
		}
		else
		{
			response.sendRedirect("/TestWebProject/umut-dabager.jsp");
		}
	}	
	
	/*
	 * Originally written as a utility function by Atakan Guney.
	 * Modified by Umut Dabager.
	 */
	public void createResultTable(ResultSet results, PrintWriter writer, String searchQuery)
	{
		String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/umut-dabager\">";
		table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Select</th>"
				+ "<th>Musician</th>\n" + "<th>Genre</th>\n" + "</tr>\n";

		while (results.hasNext())
		{
			QuerySolution currentSolution = results.nextSolution();
			String musicianName = currentSolution.getLiteral("?musicianLabel").toString().replace("@en", "");
			String musicianURI = currentSolution.getResource("?musician").toString();
			String genreName = currentSolution.getLiteral("?genreLabel").toString().replace("@en", "");
			String genreURI = currentSolution.getResource("?genre").toString();
			if (musicianName.toLowerCase().contains(searchQuery.toLowerCase()) || genreName.toLowerCase().contains(searchQuery.toLowerCase()))
			{
				table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"check\" value=\"" + musicianURI + valueSeperatorChar + musicianName
						+ valueSeperatorChar + genreURI + valueSeperatorChar + genreName + "\"/>" + "</td>\n" + "<td>\n";
				table += "<a href=\"" + musicianURI + "\">" + musicianName + "</td>\n" + "<td>\n" + "<a href=\"" + genreURI + "\">" + genreName;

			}
			
		}
		table += "</table>";
		table += "<table><tr><td><input id=\"submit\" name=\"submit\" type=\"submit\" value=\"Save the selected results.\"/></td></tr></table>";
		table += "</form>";
		writer.println(table);

	}
	

	/*
	 * Inserts raw data into database.
	 * @param results Retrieved results from wikiData
	 * 
	 * UPDATE !! Tried to insert all the raw data into db and pulling data from there.
	 * Insert is taking too long for big data. ABANDONED!
	 */
	private void insertRawDataToDB(ResultSet results) throws ClassNotFoundException
	{
		Connection conn = null;
		Statement statement = null;
		String sqlInsertQuery = "TRUNCATE TABLE group5db.umut_rawData ";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = conn.createStatement();
			while (results.hasNext())
			{
				QuerySolution currentSolution = results.nextSolution();
				String musicianName = currentSolution.getLiteral("?musicianLabel").toString().replace("@en", "");
				String musicianURI = currentSolution.getResource("?musician").toString().replace("http://www.wikidata.org/entity/", "");
				String genreName = currentSolution.getLiteral("?genreLabel").toString().replace("@en", "");
				String genreURI = currentSolution.getResource("?genre").toString().replace("http://www.wikidata.org/entity/", "");
				

				sqlInsertQuery += "INSERT INTO group5db.umut_rawData " + 
						"(musicianid,musician,genreid,genre) " + 
						"VALUES ('" + musicianURI + "', '" + musicianName + "', '" + genreURI + "', '" + genreName + "') ";

			}
            statement.execute(sqlInsertQuery);

		} 
		catch (SQLException exc) 
		{
			exc.printStackTrace();
		}
	}
	
	/*
	 * Inserts selected values to database.
	 */
	private void insertSelectedValuesToDB(HttpServletRequest request)
	{
		Connection conn = null;
		Statement statement = null;
		String sqlInsertQuery = "";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = conn.createStatement();
			String[] itemsToAdd = request.getParameterValues("check");
			for(String item : itemsToAdd)
			{
				String[] itemValues = item.split(valueSeperatorChar);
				String musicianURI = itemValues[0];
				String musicianName = itemValues[1];
				String genreURI = itemValues[2];
				String genreName = itemValues[3];
				
				sqlInsertQuery += "INSERT INTO group5db.umut_savedData (musicianid,musician,genreid,genre) VALUES ('" 
						+ musicianURI + "', '" + musicianName + "', '" + genreURI + "', '" + genreName + "') ";
			};
            statement.execute(sqlInsertQuery);
		} 
		catch (SQLException se) 
		{
			se.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null) conn.close();
			} 
			catch (SQLException exc) 
			{
				exc.printStackTrace();
			}
		}
	}
	
	private String retrieveSavedResults()
	{
		String table = "";
		Connection conn = null;
		Statement statement = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = conn.createStatement();
			java.sql.ResultSet savedResults = statement.executeQuery("SELECT * FROM umut_savedData");
			
			
			table = "<form method=\"post\" name=\"resulttable\" action=\"/TestWebProject/umut-dabager\">";
			table += "<table border=\"1\" style=\"width:100%\">\n" + "<tr>\n" + "<th>Musician</th>\n"
					+ "<th>Genre</th>\n";

			while (savedResults.next()) {
				String musician = savedResults.getString("musician");
				String musicianURI = savedResults.getString("musicianid");
				String genre = savedResults.getString("genre");
				String genreURI = savedResults.getString("genreid");
				table += "<tr>\n";

				table += "<td>\n" + "<a href=\"" + musicianURI + "\">" + musician + "</td>\n";

				table += "<td>\n" + "<a href=\"" + genreURI + "\">" + genre + "</td></tr>\n";
			}
			table += "</table></form>";
		} 
		catch (SQLException se) 
		{
			se.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null) conn.close();
			} 
			catch (SQLException exc) 
			{
				exc.printStackTrace();
			}
		}

		return table;
	}
	
	private void clearSavedResults()
	{
		Connection conn = null;
		Statement statement = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = conn.createStatement();
            statement.execute("TRUNCATE TABLE umut_savedData");
		} 
		catch (SQLException exc) 
		{
			exc.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null) conn.close();
			} 
			catch (SQLException exc) 
			{
				exc.printStackTrace();
			}
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
