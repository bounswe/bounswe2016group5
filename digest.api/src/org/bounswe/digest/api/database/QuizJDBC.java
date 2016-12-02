package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.bounswe.digest.api.database.model.Question;
import org.bounswe.digest.api.database.model.Quiz;

import com.google.gson.Gson;

public class QuizJDBC {
	
	public static String getQuizzesOfTopic(int tid){
		Gson gson=new Gson();
		return gson.toJson(QuizJDBC.getQuizArrayOfTopic(tid));
	}
	
	public static int addQuizToTopic(int tid, Quiz quiz) {
		int qid = addQuiz(quiz);
		return addTopicQuiz(tid, qid);
	}
	
	private static int addQuiz(Quiz quiz) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int qid = -1;
		String query = "INSERT INTO quiz (name) VALUES (?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, quiz.getName());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				qid = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}
			ConnectionPool.close(connection);
			return -1;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
					ConnectionPool.close(connection);
					return -1;
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				ConnectionPool.close(connection);
				return -1;
			}
		}
		ConnectionPool.close(connection);

		for (Question q : quiz.getQuestions()) {
			ArrayList<String> choices = q.getChoices();
			ArrayList<Integer> answers = q.getAnswers();
			int questionId = addQuestion(q.getText());
			for (int i = 0; i < choices.size(); i++) {
				int cid = addChoice(choices.get(i), answers.contains(i) ? 1 : 0);
				addQuestionChoice(questionId, cid);
			}
			addQuizQuestion(qid, questionId);
		}

		return qid;
	}
	
	protected static ArrayList<Quiz> getQuizArrayOfTopic(int tid){
		String query = "SELECT quiz.*, question.*, choice.text, choice.isAnswer "
					 + "FROM quiz, question, choice, digest.quiz_question, question_choice, topic_quiz "
					 + "WHERE quiz.id=quiz_question.quiz_id AND question.id=quiz_question.question_id AND "
					 + "question.id=digest.question_choice.qid AND choice.id=question_choice.cid AND "
					 + "topic_quiz.tid=? AND topic_quiz.qid=quiz.id;";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<Quiz>();
		}
		PreparedStatement statement = null;
		ResultSet resultSet;
		ArrayList<Quiz> result=new ArrayList<Quiz>();
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			
			HashSet<Integer> quiz=new HashSet<Integer>();
			HashSet<Integer> question=new HashSet<Integer>();
			while (resultSet.next()) {
				int quizId=resultSet.getInt(1);
				if(!quiz.contains(quizId)){
					quiz.add(quizId);
					result.add(new Quiz(resultSet.getString(2), new ArrayList<Question>()));
				}
				int questionId=resultSet.getInt(3);
				ArrayList<Question> questions=result.get(result.size()-1).getQuestions();
				if(!question.contains(questionId)){
					question.add(questionId);
					questions.add(new Question(resultSet.getString(4), new ArrayList<String>(), new ArrayList<Integer>()));
				}
				questions.get(questions.size()-1).getChoices().add(resultSet.getString(5));
				if(resultSet.getInt(6)==1){
					questions.get(questions.size()-1).getAnswers().add(questions.get(questions.size()-1).getChoices().size()-1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);

		return result;
	}
	private static int addQuestion(String text) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int result = -1;
		String query = "INSERT INTO question (text) VALUES (?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, text);
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}

	private static int addChoice(String c, int isAnswer) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int result = -1;
		String query = "INSERT INTO choice (text,isAnswer) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, c);
			statement.setInt(2, isAnswer);
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}
	private static int addTopicQuiz(int tid, int qid) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO topic_quiz (tid,qid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			statement.setInt(2, qid);
			statement.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}
	private static int addQuizQuestion(int quizId, int questionId) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int result = -1;
		String query = "INSERT INTO quiz_question (quiz_id, question_id) VALUES (?,?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, quizId);
			statement.setInt(2, questionId);
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}

	private static int addQuestionChoice(int qid, int cid) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		PreparedStatement statement = null;
		int result = -1;
		String query = "INSERT INTO question_choice (qid,cid) VALUES (?,?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, qid);
			statement.setInt(2, cid);
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();

			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}
}
