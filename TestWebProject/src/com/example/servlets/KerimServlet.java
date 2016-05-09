package com.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
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
		resp.getWriter().append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"+
"<html>"+
"<head>"+
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"+
"<title>CmpE Group5 - Assignment6 - Kerim Gokarslan</title>"+
"</head><body>");
		if (req.getParameter("query") == null) {
			// save();
			resp.sendRedirect("/TestWebProject/kerim-gokarslan.jsp");
		} else {
			String userQuery = req.getParameter("query");
			resp.getWriter().append(userQuery);
			String query = "https://query.wikidata.org/sparql?query=";
			query += "SELECT%20?item%20?itemLabel%20?commons%20?manufacturer%20?manufacturerLabel%20?type%20?typeLabel%20"
					+

					"WHERE{%20"
					+

					"%20%20?item%20wdt:P31%20wd:Q1420%20.%20"
					+

					"%20%20?item%20wdt:P373%20?commons%20.%20"
					+

					"%20%20OPTIONAL{%20"
					+

					"%20%20%20%20?item%20wdt:P176%20?manufacturer%20.%20"
					+

					"%20%20}%20"
					+

					"%20%20OPTIONAL{%20"
					+

					"%20%20?item%20wdt:P279%20?type%20"
					+

					"%20%20}%20"
					+

					"%20%20SERVICE%20wikibase:label%20{%20bd:serviceParam%20wikibase:language%20\"en\"%20}%20"
					+

					"}";

			/*URL url = new URL(query);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String xmlResult = "";
			String line;
			while ((line = in.readLine()) != null)
				xmlResult += line;
			in.close();*/
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().append("Internal error.");
				return;
			}
			Document doc;
			try {
				doc = dBuilder.parse(query);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.getWriter().append("parsing error");
				return;
			}
			NodeList nList = doc.getElementsByTagName("result");
			//resp.getWriter().append("len:" + nList.getLength());
			String[] item = new String[nList.getLength()];
			String[] itemLabel = new String[nList.getLength()];
			String[] commons = new String[nList.getLength()];
			String[] manufacturer = new String[nList.getLength()];
			String[] manufacturerLabel = new String[nList.getLength()];
			String[] type = new String[nList.getLength()];
			String[] typeLabel = new String[nList.getLength()];
			
			for(int i=0;i<nList.getLength();++i){
				Node node = (Node)nList.item(i);
				Element element = (Element) node;
				NodeList nl = element.getElementsByTagName("binding");
				for(int j=0;j<nl.getLength();++j){
					Element e = (Element) nl.item(j);
					if(e.getAttribute("name").equalsIgnoreCase("item")){
						item[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("itemLabel")){
						itemLabel[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("itemLabel")){
						itemLabel[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("commons")){
						commons[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("manufacturer")){
						manufacturer[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("manufacturerLabel")){
						manufacturerLabel[i] = e.getTextContent();
					}else if(e.getAttribute("name").equalsIgnoreCase("type")){
						type[i] = e.getTextContent();
					}
					else if(e.getAttribute("name").equalsIgnoreCase("typeLabel")){
						typeLabel[i] = e.getTextContent();
					}
				}
				
			}
			String table = "<table border=\"1\" style=\"width:100%\">\n"+ "<tr>\n"+
			"<th>Item</th>\n"+
			"<th>Commons</th>\n"+
			"<th>Manufacturer</th>\n"+
			"<th>Type</th>\n"+"</tr>\n";
			for(int i=0;i<nList.getLength();++i){
				String row = "<tr>\n";
				row+="<td>";
				row+="<a href=\""+item[i]+"\">" + itemLabel[i];
				row+="</td>\n";
				if(commons[i]!=null){
				row+="<td>";
				row+=commons[i];
				row+="</td>\n";
				row+="<td>";}else{
					row+="<td>";
					row+="-";
					row+="</td>\n";
				}
				if(manufacturer[i]!=null){
				row+="<a href=\""+manufacturer[i]+"\">" + manufacturerLabel[i];
				row+="</td>\n";
				row+="<td>";}else{
					row+="<td>";
					row+="-";
					row+="</td>\n";
				}if(type[i]!=null){
				row+="<a href=\""+type[i]+"\">" + typeLabel[i];
				row+="</td>\n";
				row+="<td>";
				row+="</td>\n";
				}else{
					row+="<td>";
					row+="-";
					row+="</td>\n";
				}
	
				row+="</tr>\n";
				table+=row;
			}
			table+="</table>";
			resp.getWriter().append(table);
			resp.getWriter().append("</body></html>");
		
		}

	}

	public Connection mysqlConnection() {
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

	public void save() {
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
