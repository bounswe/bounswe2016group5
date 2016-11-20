package com.digest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
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

	JSONArray questions;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();
		questions = new JSONArray();
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

		if (request.getParameter("f").contentEquals("add-question")) {

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

			}
			request.setAttribute("questions", questions);

			request.getRequestDispatcher("/quiz-add.jsp").forward(request, response);

		} else if (request.getParameter("f").contentEquals("add-quiz")) {
			if (request.getParameter("quiz-name") != null && !request.getParameter("quiz-name").contentEquals("")) {
				String quizName = "quiz";
				JSONObject quiz = new JSONObject();
				quiz.put("name", quizName);

				if (questions != null && questions.length() > 0) {

					quiz.put("questions", questions);

					for (int i = 0; i < questions.length(); i++) {

						JSONObject question = questions.getJSONObject(i);

						JSONArray answers = new JSONArray();

						if (request.getParameter("q" + i + "answer0") != null) {
							answers.put(0);

						}

						if (request.getParameter("q" + i + "answer1") != null) {
							answers.put(1);

						}

						if (request.getParameter("q" + i + "answer2") != null) {
							answers.put(2);

						}

						question.put("answers", answers);

					}

					String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=quiz_add&tid=2";
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json");
					String postBody = quiz.toString();
					
					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());

					wr.writeBytes(postBody);
					wr.flush();
					wr.close();
					System.out.println(wr.toString());

					int responseCod = con.getResponseCode();

					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer res = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						res.append(inputLine);
					}
					in.close();

					// print result
					System.out.println(res.toString());

					if (responseCod == 200) {
						System.out.println(quiz);
						request.setAttribute("success", "Quiz Addded Successfully!");
						request.removeAttribute("questions");
						questions = new JSONArray();
						request.getRequestDispatcher("/quiz-add.jsp").forward(request, response);
					} else {
						String err = "Unexpected error occured!!";
						request.setAttribute("error", err);
						request.getRequestDispatcher("/quiz-add.jsp").forward(request, response);
					}

				} else {
					String err = "At least 1 question should be added to quiz!!";
					request.setAttribute("error", err);
					request.getRequestDispatcher("/quiz-add.jsp").forward(request, response);
				}
			} else {
				String err = "Quiz must be named!!";
				request.setAttribute("error", err);
				request.getRequestDispatcher("/quiz-add.jsp").forward(request, response);
			}
		}
	}

}
