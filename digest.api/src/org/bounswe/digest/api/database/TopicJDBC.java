package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.mysql.cj.api.jdbc.Statement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.bounswe.digest.api.database.model.Comment;
import org.bounswe.digest.api.database.model.Question;
import org.bounswe.digest.api.database.model.Quiz;
import org.bounswe.digest.api.database.model.Topic;
import org.bounswe.digest.api.database.model.TopicTag;
import org.bounswe.digest.api.database.model.TopicPreview;

import com.google.gson.Gson;
import com.mysql.cj.api.exceptions.StreamingNotifiable;

public class TopicJDBC {
	public static int createTopic(Topic topic) {
		int tid = -1;
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
		String query = "INSERT INTO topic (header, image, body, owner, status) VALUES (?, ?, ?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, topic.getHeader());
			// statement.setString(2, type);
			statement.setString(2, topic.getImage());
			//statement.setString(3, topic.getUrl());
			statement.setString(3, topic.getBody());
			statement.setInt(4, topic.getOwner());
			statement.setInt(5, 0); // Doktor bu ne?
			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				tid = resultSet.getInt(1);
				result = tid;
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
		ArrayList<String> media=topic.getMedia();
		for(String item : media){
			addMedia(tid, item);
		}
		ConnectionPool.close(connection);
		return result;
	}

	public static String getTopicsWithUser(int uid) {
		String query = "SELECT * FROM digest.topic WHERE topic.owner=(?)";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		ArrayList<Topic> result = new ArrayList<Topic>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new Topic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getInt(5), resultSet.getInt(6), null, null, null, null, resultSet.getTimestamp(7)));
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
		for(Topic t: result){
			int tid=t.getId();
			t.setTags(getTagsOfTopic(tid));
			t.setComments(CommentJDBC.getCommentsArrayOfTopic(tid));
			t.setQuizzes(QuizJDBC.getQuizArrayOfTopic(tid));
			t.setMedia(getMediaArray(tid));
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	public static String getRecentTopics(int count) {
		//will be timestamp
		String query = "SELECT * FROM digest.topic ORDER BY id DESC LIMIT ?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		ArrayList<Topic> result = new ArrayList<Topic>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, count);
			resultSet = statement.executeQuery();
			
			//public Topic(int id, String header, String image, String body, int owner, int status,
					//ArrayList<TopicTag> tags, ArrayList<Quiz> quizzes, ArrayList<String> media, ArrayList<Comment> comments, Timestamp timestamp) {
			while (resultSet.next()) {
				result.add(new Topic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getInt(5), resultSet.getInt(6), null, null, null, null, resultSet.getTimestamp(7)));
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
		for(Topic t: result){
			int tid = t.getId();
			t.setTags(getTagsOfTopic(tid));
			t.setComments(CommentJDBC.getCommentsArrayOfTopic(tid));
			t.setQuizzes(QuizJDBC.getQuizArrayOfTopic(tid));
			t.setMedia(getMediaArray(tid));
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	private static ArrayList<TopicTag> getTagsOfTopic(int tid){
		String query = "SELECT * FROM digest.topic_tag  WHERE topic_tag.tid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<TopicTag>();
		}
		PreparedStatement statement = null;
		ArrayList<TopicTag> tags = new ArrayList<TopicTag>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				tags.add(new TopicTag(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
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
		return tags;
	}
	
	private static ArrayList<String> getMediaArray(int tid){
		String query = "SELECT url FROM digest.media WHERE tid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		ArrayList<String> result = new ArrayList<String>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
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
	
	public static int addMedia(int tid,String url){
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
		// what is the default value of rate?
		String query = "INSERT INTO media(tid,url) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			statement.setString(2, url);
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
	
	
	public static String getTopic(int tid) {
		String query = "SELECT * FROM digest.topic WHERE topic.id=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		Topic result=null;
		ResultSet resultSet;
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
				result = (new Topic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4),
						resultSet.getInt(5), resultSet.getInt(6), null, null, null, null, resultSet.getTimestamp(7)));
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
		if(result!=null){
			result.setTags(getTagsOfTopic(tid));
			result.setComments(CommentJDBC.getCommentsArrayOfTopic(tid));
			result.setQuizzes(QuizJDBC.getQuizArrayOfTopic(tid));
			result.setMedia(getMediaArray(tid));
		}
		
		return result.printable();
	}
	public static int addSubscriberToTopic(int tid, int uid){
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
		String query = "INSERT INTO topic_subscribe (tid, uid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);// Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, tid);
			statement.setInt(2, uid);
			statement.executeUpdate();
			
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
		return 0;
	}
	public static String getSubscribedTopics(int uid) {
		String query = "SELECT topic_subscribe.tid FROM topic_subscribe WHERE uid = ?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		ArrayList<Integer> result = new ArrayList<Integer>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();
			
			//public Topic(int id, String header, String image, String body, int owner, int status,
					//ArrayList<TopicTag> tags, ArrayList<Quiz> quizzes, ArrayList<String> media, ArrayList<Comment> comments, Timestamp timestamp) {
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
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
	public static String getTopicsWithTag(String tag) {
		String query = "SELECT topic.id, topic.header, topic.image, topic.owner, topic.status, topic.timestamp FROM topic, topic_tag WHERE topic_tag.tag LIKE ? AND topic.id = topic_tag.tid";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		ArrayList<TopicPreview> result = new ArrayList<TopicPreview>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, tag);
	
			resultSet = statement.executeQuery();
			
			/*public TopicPreview(int id, String header, String image, int owner, int status,
					 Timestamp timestamp) */
			while (resultSet.next()) {
				result.add(new TopicPreview(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), resultSet.getTimestamp(6)));
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

}
