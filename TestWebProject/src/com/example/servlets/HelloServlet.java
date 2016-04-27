package com.example.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("This is group 5!!!11!1!!");
		
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		response.getWriter().append("<head><style>table, th, td {    border: 1px solid black;border-collapse: collapse;}</style></head>"+"<table border=\"1\" style=\"width:100%\">  <tr>" +
									"<td><a href=\"atakan-guney\">Atakan Guney</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Atakan-G%C3%BCney\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"burak-suyunu\">Burak Suyunu</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Burak-Suyunu\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"sevda-copur\">Sevda Copur</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Sevda-%C3%87opur\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"kerim-gokarslan\">Kerim Gokarslan</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Kerim-G%C3%B6karslan\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"umut-dabager\">Umut M.Dabager</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Umut-M.-Dabager\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"ozer-biber\">Ozer Biber</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/%C3%96zer-Biber\"> git page</a></td> </tr>  <tr>" +
									"<td><a href=\"mbugc\">Bugra Cil</a></td><td><a href=\"https://github.com/bounswe/bounswe2016group5/wiki/Bu%C4%9Fra-%C3%87il\"> git page</a></td> </tr>  <tr>" +								    
									"</table>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
