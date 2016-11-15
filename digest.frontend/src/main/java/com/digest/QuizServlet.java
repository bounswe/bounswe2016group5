package com.digest;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class QuizServlet
 */
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// TODO Auto-generated method stub
		JSONObject questions;

		if (session.getAttribute("questions") == null) {
			
			questions = new JSONObject();
			session.setAttribute("questions", questions);

		} else {
			//String jsonString =  request.getParameter("questions");
			questions = (JSONObject) session.getAttribute("questions");//new JSONObject(jsonString);
		}

		JSONArray options = new JSONArray();

		if (request.getParameter("question") != null && !request.getParameter("question").contentEquals("")) {

			String question = request.getParameter("question");

			if (!request.getParameter("option1").contentEquals("")) {
				String option1 = request.getParameter("option1");
				options.put(option1);

			}

			if (!request.getParameter("option2").contentEquals("")) {
				String option2 = request.getParameter("option2");
				options.put(option2);
			}

			if (!request.getParameter("option3").contentEquals("")) {
				String option3 = request.getParameter("option3");
				options.put(option3);
			}

			questions.put(question, options);

			session.setAttribute("questions", questions);
			

		}

		System.out.println(questions);
		response.sendRedirect("quiz-add.jsp");
		//RequestDispatcher dispatcher = request.getRequestDispatcher("quiz-add.jsp");
		//dispatcher.forward(request, response);
		
	}

}
