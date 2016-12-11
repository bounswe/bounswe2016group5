package com.digest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SubmitQuizServlet
 */
@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmitQuizServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Enumeration<String> names = request.getParameterNames();
		
		int tid = -1;
		//ArrayList<String> quizNames = new ArrayList<String>(); //Only one quiz
		ArrayList<ArrayList<String>> userAnswers = new ArrayList<ArrayList<String>>();
		while (names.hasMoreElements()) {
			String attr = names.nextElement();
			String value = request.getParameter(attr);
			if(attr.equals("tid"))
				tid = Integer.parseInt(value);
			else{
				String question = attr.substring(attr.indexOf('&')+1, attr.indexOf('&')+2);
				try{
					ArrayList<String> answers = userAnswers.get(Integer.parseInt(question));
					answers.add(value);
				}catch(IndexOutOfBoundsException e){
					ArrayList<String> answers = new ArrayList<String>();
					answers.add(value);
					userAnswers.add(Integer.parseInt(question), answers);
				}
					
			}
		}
		System.out.println(userAnswers.toString());

		String url = "http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_quiz&tid=" +tid;
		URL jsonpage = new URL(url);
		HttpURLConnection urlcon = (HttpURLConnection) jsonpage.openConnection();
		BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));

		String recv = "";
		String recvbuff = "";
		while ((recv = buffread.readLine()) != null)
			recvbuff += recv;
		buffread.close();
		int correct = 0;
		int wrong = 0;
		int total = 0;
		try {
			JSONArray quizzes = new JSONArray(recvbuff);
			JSONObject quiz = quizzes.getJSONObject(0);
			Set<String> sattr = quiz.keySet();
			for (String attribute : sattr) {
				if(attribute.equalsIgnoreCase("questions")){
					JSONArray questions = (JSONArray) quiz.get(attribute);
					for(int i=0; i<questions.length(); i++){
						JSONObject question = questions.getJSONObject(i);
						Set<String> sattr2 = question.keySet();
						int c=0;
						for (String attribute2 : sattr2) {
							if(attribute2.equalsIgnoreCase("answers")){
								JSONArray realAnswers = (JSONArray)question.get(attribute2);
								ArrayList<String> uAnswers = userAnswers.get(i);
								total += realAnswers.length();
								for(int r=0; r<realAnswers.length(); r++){
									for(int u=0; u<uAnswers.size(); u++){
										if(uAnswers.get(u).equalsIgnoreCase(realAnswers.get(r).toString())){
											c++;
										}
									}
								}
								wrong += (uAnswers.size() - c);
							}
						}
						correct += c;
						
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double progress = (double)correct/total - wrong*0.2;
		System.out.println( progress);
	}

}
