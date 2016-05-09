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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String inputTerm = request.getParameter("input");
		String s1 = 
					"PREFIX wd: <http://www.wikidata.org/entity/>\n"+
					"PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"+
					"PREFIX wikibase: <http://wikiba.se/ontology#>\n"+
					"PREFIX p: <http://www.wikidata.org/prop/>\n"+
					"PREFIX ps: <http://www.wikidata.org/prop/statement/>\n"+
					"PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n"+
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
					"PREFIX bd: <http://www.bigdata.com/rdf#>\n"+

					"#Most eponymous mathematicians\n"+
					"SELECT ?eponym ?eponymLabel ?count ?sample ?sampleLabel\n"+
					"WHERE\n"+
					"{\n"+
						"{\n"+
						"SELECT ?eponym (COUNT(?item) as ?count) (SAMPLE(?item) AS ?sample)\n"+
						"WHERE\n"+
						"{\n"+
							"?item wdt:P138 ?eponym.\n"+
							"?eponym wdt:P106 wd:Q170790.\n"+
						"}\n"+
						"GROUP BY ?eponym\n"+
						"}\n"+
						"SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }\n"+
					"}\n"+
					"ORDER BY DESC(?count)\n";
		Query query = QueryFactory.create(s1); 
		QueryExecution qExe = QueryExecutionFactory.sparqlService( "https://query.wikidata.org/sparql", query );
		ResultSet results = qExe.execSelect();
		
		PrintWriter out = response.getWriter();
		out.println(results.getResultVars().toString());
		while(results.hasNext()){
			out.println(results.nextSolution());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}