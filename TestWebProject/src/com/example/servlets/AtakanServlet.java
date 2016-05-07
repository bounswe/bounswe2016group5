package com.example.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.Resource;

/**
 * Servlet implementation class HelloServlet
 */
@SuppressWarnings("deprecation")
@WebServlet("/atakan-guney")
public class AtakanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String query = "SELECT ?item ?itemLabel ?pic WHERE { ?item ?x1 wd:Q146 . OPTIONAL { ?item wdt:P18 ?pic } SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" } }";
		String website = "https://query.wikidata.org/sparql?query=";
		try {
			String queryResult = getHTML("https://query.wikidata.org/sparql?query=SELECT%20?h%20WHERE{?h%20wdt:P31%20wd:Q3624078}");
			out.println(queryResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getHTML(String urlToRead) throws Exception {
		URL oracle = new URL(urlToRead);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				oracle.openStream()));

		String input;
		while ((input = in.readLine()) != null)
			System.out.println(input);
		in.close();
		return input;
	}
}
