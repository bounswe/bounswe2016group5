package com.example.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
	private static String jsonData = null;

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

			PrintWriter out = response.getWriter();
			// out.println(results.getResultVars().toString());
			/*
			 * while(results.hasNext()){
			 * out.println(results.nextSolution().getResource("?eponym").
			 * toString()) ; }
			 */
			response.setContentType("text/html");

			String query1 = request.getParameter("query");
			createResultTable(results, out, query1);
		} else {
			response.sendRedirect("/TestWebProject/atakan-guney.jsp");
		}
	}

	public void createResultTable(ResultSet results, PrintWriter out, String searchQuery) {
		String table = "<form name=\"ftable\" method=\"post\" action=\"/TestWebProject/kerim-gokarslan\">";
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
			table += "<tr>\n" + "<td>\n" + "<input type=\"checkbox\" name=\"selected\" value=\"\" />" + "</td>\n"
					+ "<td>\n";
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