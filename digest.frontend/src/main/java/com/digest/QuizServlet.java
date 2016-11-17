package com.digest;

import java.io.IOException;
import java.util.Enumeration;

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
		if (request.getParameter("f").contentEquals("add-question")) {

			JSONArray questions;

			if (session.getAttribute("questions") == null) {

				questions = new JSONArray();
				session.setAttribute("questions", questions);

			} else {
				questions = (JSONArray) session.getAttribute("questions");
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

				JSONObject quest = new JSONObject();
				quest.put("text", question);
				quest.put("choices", options);

				questions.put(quest);

				session.setAttribute("questions", questions);

			}

			System.out.println(questions);
			response.sendRedirect("quiz-add.jsp");
		} else if (request.getParameter("f").contentEquals("add-quiz")) {

			String quizName = "quiz";
			JSONObject quiz = new JSONObject();
			quiz.put("name", quizName);

			JSONArray questions = (JSONArray) session.getAttribute("questions");

			if (questions != null && questions.length() > 0) {

				quiz.put("questions", questions);

				for (int i = 0; i < questions.length(); i++) {
					
					JSONObject question = questions.getJSONObject(i);

					JSONArray answers = new JSONArray();

					if (request.getParameter("q"+i+"answer0") != null) {
						answers.put(0);

					}

					if (request.getParameter("q"+i+"answer1") != null) {
						answers.put(1);

					}

					if (request.getParameter("q"+i+"answer2") != null) {
						answers.put(2);

					}
					
					question.put("answers", answers);

				}

				System.out.println(quiz);
				response.sendRedirect("quiz-add.jsp");

			}

		}
	}

}
