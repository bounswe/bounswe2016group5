package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.Channel;
import org.bounswe.digest.api.database.model.Topic;

import com.google.gson.Gson;

/**
 * Handles database transactions about topic channels.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class ChannelJDBC {
	/**
	 * Adds a new channel of a user.
	 * 
	 * @param uid
	 *            User ID.
	 * @param name
	 *            Channel Name
	 * @return The ID of created channel <code>-1</code> if an error occurred.
	 */
	public static int addChannel(int uid, String name) {
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
		String query = "INSERT INTO channel(uid, name,status) VALUES (?, ?, 0)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			statement.setString(2, name);
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

	/**
	 * Adds topic into to given channel.
	 * 
	 * @param tid
	 *            Topic id
	 * @param cid
	 *            Channel id
	 * @return <code>0</code> if succeed <code>-1</code> if an error occurred.
	 */
	public static int addTopicToChannel(int tid, int cid) {
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
		String query = "INSERT INTO channel_topic(tid, cid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			statement.setInt(2, cid);
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

	/**
	 * Returns Channel Object in JSON Format.
	 * 
	 * @param cid
	 *            Channel ID.
	 * @return Channel object as JSON string.
	 */
	public static String getChannel(int cid) {
		return getChannelObject(cid).printable();
	}

	/**
	 * Returns channels of users.
	 * 
	 * @param uid
	 *            User ID.
	 * @return List of channels IDs of the user.
	 */
	public static String getChannelsOfUser(int uid) {
		Gson gson = new Gson();
		return gson.toJson(getChannelArrayOfUser(uid));
	}

	/**
	 * Returns channels of users as array.
	 * 
	 * @param uid
	 *            User ID.
	 * @return Array of channels IDs of the user.
	 */
	protected static ArrayList<Channel> getChannelArrayOfUser(int uid) {
		String query = "SELECT * FROM digest.channel WHERE channel.uid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		ArrayList<Channel> result = new ArrayList<Channel>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new Channel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getInt(4)));
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
		return result;

	}

	/**
	 * Returns channel object with given id.
	 * 
	 * @param cid
	 *            Channel ID.
	 * @return Channel Object. <code>null</code> if an error occurred.
	 */
	protected static Channel getChannelObject(int cid) {
		String query = "SELECT * FROM digest.channel WHERE channel.id=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		Channel result = null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = (new Channel(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getInt(4)));
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
		return result;
	}
<<<<<<< HEAD

	/**
	 * Returns channel ID of a topic.
	 * 
	 * @param tid
	 *            Topic ID.
	 * @return Channel ID of given topic.
	 */
	public static int getChannelTid(int tid) {
=======
	
	public static String getChannelTid(int tid){
>>>>>>> api
		String query = " (SELECT cid FROM channel_topic WHERE tid=? )";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
		int result = -1;
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
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
		return result;

	}

	/**
	 * Returns subscribed channels of an user.
	 * 
	 * @param uid
	 *            User ID.
	 * @return Subscribed channels of the user in JSON format.
	 */
	public static String getSubscribedChannels(int uid) {
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
		ArrayList<Integer> topics = new ArrayList<Integer>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();

			// public Topic(int id, String header, String image, String body,
			// int owner, int status,
			// ArrayList<TopicTag> tags, ArrayList<Quiz> quizzes,
			// ArrayList<String> media, ArrayList<Comment> comments, Timestamp
			// timestamp) {
			while (resultSet.next()) {
				topics.add(resultSet.getInt(1));
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
		ArrayList<Channel> result = new ArrayList<>();
		for (int i = 0; i < topics.size(); i++) {
			result.add(getChannelObjectOfTopic(topics.get(i)));
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	/**
	 * Returns the number of topics in a channel.
	 * 
	 * @param cid
	 *            Channel ID.
	 * @return The number of topics in a channel.
	 */
	public static int getNumberOfTopics(int cid) {
		String query = "SELECT COUNT(tid) from channel_topic " + "WHERE channel_topic.cid=?";
		Connection connection;
		int result = 0;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result = (resultSet.getInt(1));
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

	/**
	 * Returns topics of a channel in JSON format.
	 * 
	 * @param cid
	 *            Channel ID.
	 * @return List of topics of the channel in JSON format.
	 */
	public static String getTopicsOfChannel(int cid) {
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String query = "SELECT tid from channel_topic " + "WHERE channel_topic.cid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				topics.add(TopicJDBC.getTopicObject(resultSet.getInt(1)));
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
		return gson.toJson(topics);
	}

	/**
	 * Returns channel object of a given topic as JSON
	 * 
	 * @param tid
	 *            Topic ID.
	 * @return Channel object of the given topic as JSON
	 */
	public static String getChannelOfTopic(int tid) {
		Gson gson = new Gson();
		return gson.toJson(getChannelObjectOfTopic(tid));
	}

	/**
	 * Returns channel object of a given topic.
	 * 
	 * @param tid
	 *            Topic ID.
	 * @return Channel object of the given topic.
	 */
	public static Channel getChannelObjectOfTopic(int tid) {
		Channel channel = null;
		String query = "SELECT cid from channel_topic " + "WHERE channel_topic.tid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				channel = (getChannelObject(resultSet.getInt(1)));
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
		return (channel);
	}

}
