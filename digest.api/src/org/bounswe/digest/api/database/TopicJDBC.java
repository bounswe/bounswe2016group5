package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.Comment;
import org.bounswe.digest.api.database.model.Question;
import org.bounswe.digest.api.database.model.Quiz;
import org.bounswe.digest.api.database.model.Topic;
import org.bounswe.digest.api.database.model.TopicManager;
import org.bounswe.digest.api.database.model.TopicTag;
import org.bounswe.digest.api.database.model.User;

import com.google.gson.Gson;
//import com.mysql.cj.api.jdbc.Statement;
import java.sql.Statement;
public class TopicJDBC {
	public static int createTopic(Topic topic) {
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO topic (header, image, url, body, owner, status) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, topic.getHeader());
			// statement.setString(2, type);
			statement.setString(2, topic.getImage());
			statement.setString(3, topic.getUrl());
			statement.setString(4, topic.getBody());
			statement.setInt(5, topic.getOwner());
			statement.setInt(6, 0); // Doktor bu ne?
			statement.executeUpdate();

			int tid = -1;
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				tid = resultSet.getInt(1);
			}

			ArrayList<TopicTag> tags = topic.getTags();
			for (TopicTag tag : tags) {
				PreparedStatement tagStatement = null;
				String tagQuery = "INSERT INTO topic_tag (tid,tag) VALUES (?,?)";
				try {
					connection.setAutoCommit(false);
					tagStatement = connection.prepareStatement(tagQuery);
					tagStatement.setInt(1, tid);
					tagStatement.setString(2, tag.getTag());
					tagStatement.executeUpdate();
					
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

	public static String getTopicsWithUser(int uid) {
		String query = "SELECT * FROM digest.topic WHERE topic.owner=(?)";
		String query2 = "SELECT * FROM digest.topic_tag  WHERE topic_tag.id=?";
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ArrayList<Topic> result = new ArrayList<Topic>();
		ResultSet resultSet,resultSet2;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();
			
			// public Topic(int id, String header, String type, String image,
			// String url, String body,
			// int owner, int status,ArrayList<TopicManager> topicManagers,
			// ArrayList<TopicTag> tags)
			while (resultSet.next()) {
				int tid=resultSet.getInt(1);
				statement=connection.prepareStatement(query2);
				statement.setInt(1, tid);
				resultSet2=statement.executeQuery();
				ArrayList<TopicTag> tags=new ArrayList<TopicTag>();
				while(resultSet2.next()){
					tags.add(new TopicTag(resultSet2.getInt(1), resultSet2.getInt(2), resultSet2.getString(3)));
				}
				result.add(new Topic(tid, resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getString(5)/* ,resultSet.getString(6) */, resultSet.getInt(6), resultSet.getInt(7),
						tags, null, null));
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
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	private static int addQuiz(Quiz quiz) {
		Connection connection = ConnectionPool.getConnection();
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

	private static int addQuizQuestion(int quizId, int questionId) {
		Connection connection = ConnectionPool.getConnection();
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
		Connection connection = ConnectionPool.getConnection();
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

	private static int addQuestion(String text) {
		Connection connection = ConnectionPool.getConnection();
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
		Connection connection = ConnectionPool.getConnection();
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

	public static int addQuizToTopic(int tid, Quiz quiz) {
		int qid = addQuiz(quiz);
		return addTopicQuiz(tid, qid);
	}

	private static int addTopicQuiz(int tid, int qid) {
		Connection connection = ConnectionPool.getConnection();
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
	// give -1 if there is no upper comment of this comment.
	public static int addComment(String body, int uid,  int upperCommentId, int tid) {
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		// what is the default value of rate?
		String query = "INSERT INTO comment (body, uid, ucid, tid) VALUES (?, ?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, body);
			statement.setInt(2, uid);
			statement.setInt(3, upperCommentId);
			statement.setInt(4, tid);
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
			
		}finally {
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
	public static String getCommentsOfTopic(int tid){
		String query="SELECT id, body, uid, tid, ucid, rate, timestamp FROM comment WHERE comment.tid=(?)";
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		ArrayList<Comment> result=new ArrayList<Comment>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1,tid);
			resultSet=statement.executeQuery();
			//public Topic(int id, String header, String type, String image, String url, String body, 
			//int owner, int status,ArrayList<TopicManager> topicManagers, ArrayList<TopicTag> tags)
			while(resultSet.next()){
				result.add(new Comment(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),
						resultSet.getInt(4),resultSet.getInt(5), resultSet.getInt(6), resultSet.getTimestamp(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				connection.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();	
			}
		}finally {
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
		Gson gson=new Gson();
		return gson.toJson(result);
	}

	public static String getComment(int tid) {
		String query = "SELECT * FROM digest.topic WHERE topic.id=?";
		String query2 = "SELECT * FROM digest.topic_tag  WHERE topic_tag.id=?";
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		String result=null;
		ResultSet resultSet,resultSet2;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			// public Topic(int id, String header, String type, String image,
			// String url, String body,
			// int owner, int status,ArrayList<TopicManager> topicManagers,
			// ArrayList<TopicTag> tags)
			if (resultSet.next()) {
				statement = connection.prepareStatement(query2);
				statement.setInt(1, tid);
				resultSet2 = statement.executeQuery();
				ArrayList<TopicTag> tags= new ArrayList<TopicTag>();
				while(resultSet2.next()){
					tags.add(new TopicTag(resultSet2.getInt(1), resultSet2.getInt(2), resultSet2.getString(3)));
				}
				result=(new Topic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getString(5)/* ,resultSet.getString(6) */, resultSet.getInt(6), resultSet.getInt(7),
						tags, null, null)).printable();
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
}
