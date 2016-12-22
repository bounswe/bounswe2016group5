package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.Comment;

import com.google.gson.Gson;

/**
 * Handles database transactions about topic comments.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class CommentJDBC {
	/**
	 * Flag indicating whether the comment is question or not.
	 */
	public static final int QUESTION = 1;
	/**
	 * Flag indicating whether the comment is instructive or not.
	 */
	public static final int INSTRUCTIVE = 2;

	/**
	 * Adds comment to a topic.
	 * 
	 * @param body
	 *            comment body
	 * @param uid
	 *            user id
	 * @param upperCommentId
	 *            Id of the upper comment, -1 if there is no upper comment
	 * @param tid
	 *            topic id
	 * @return
	 */
	public static int addComment(String body, int uid, int upperCommentId, int tid, int type) {
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
		String query = "INSERT INTO comment (body, uid, ucid, rate, tid, type) VALUES (?, ?, ?, 0, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, body);
			statement.setInt(2, uid);
			statement.setInt(3, upperCommentId);
			statement.setInt(4, tid);
			statement.setInt(5, type);
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
	 * Get all comments of the topic
	 * 
	 * @param tid
	 *            topic id
	 * @return Array of Comments in JSON format
	 */
	public static String getCommentsOfTopic(int tid) {
		Gson gson = new Gson();
		return gson.toJson(getCommentsArrayOfTopic(tid));
	}

	/**
	 * Get all comments of the topic
	 * 
	 * @param tid
	 *            topic id
	 * @return ArrayList of Comments
	 */
	protected static ArrayList<Comment> getCommentsArrayOfTopic(int tid) {
		String query = "SELECT id, body, uid, tid, ucid, rate, type, timestamp FROM comment WHERE comment.tid=(?)";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ArrayList<Comment>();
		}
		PreparedStatement statement = null;
		ArrayList<Comment> result = new ArrayList<Comment>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7),
						resultSet.getTimestamp(8)));
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
	 * Upvote Comment
	 * 
	 * @param uid
	 *            user id
	 * @param cid
	 *            comment id
	 * @return 0 if successful
	 */
	public static int rateComment(int uid, int cid) {
		int result = removeUnrateComment(uid, cid);
		if (result == 0) {
			result = updateRate(cid, true);
			return 0;
		}
		result = addRateComment(uid, cid);
		if (result == 0) {
			result = updateRate(cid, true);
		}
		return result;
	}

	/**
	 * Downvote Comment
	 * 
	 * @param uid
	 *            user id
	 * @param cid
	 *            comment id
	 * @return 0 if successful
	 */
	public static int unrateComment(int uid, int cid) {
		int result = removeRateComment(uid, cid);
		if (result == 0) {
			result = updateRate(cid, false);
		}
		result = addUnrateComment(uid, cid);
		if (result == 0) {
			result = updateRate(cid, false);
		}
		return result;
	}

	/**
	 * Unrates a comment.
	 * 
	 * @param uid
	 *            User ID.
	 * @param cid
	 *            Comment ID.
	 * @return <code>0</code> succeed <code>-1</code> otherwise.
	 */
	private static int addUnrateComment(int uid, int cid) {
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
		String query = "INSERT INTO unrate_comment (uid,cid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
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
	 * Removes rate from a comment.
	 * 
	 * @param uid
	 *            User ID.
	 * @param cid
	 *            Comment ID.
	 * @return <code>0</code> succeed <code>-1</code> otherwise.
	 */
	private static int removeRateComment(int uid, int cid) {
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
		String query = "DELETE FROM rate_comment WHERE uid=? and cid=?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
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
	 * Removes unrate from comment.
	 * 
	 * @param uid
	 *            User ID.
	 * @param cid
	 *            Comment ID.
	 * @return <code>0</code> succeed <code>-1</code> otherwise.
	 */
	private static int removeUnrateComment(int uid, int cid) {
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
		String query = "DELETE FROM unrate_comment WHERE uid=? and cid=?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
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
	 * Updates rate value of a comment
	 * 
	 * @param cid
	 *            Comment ID.
	 * @param up
	 *            Indicating whether the vote is up or down.
	 * @return <code>0</code> succeed <code>-1</code> otherwise.
	 */
	private static int updateRate(int cid, boolean up) {
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
		String query = "UPDATE comment SET rate=rate" + (up ? "+1" : "-1") + " WHERE id=?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
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
	 * Adds rate to a comment.
	 * 
	 * @param uid
	 *            User ID.
	 * @param cid
	 *            Comment ID.
	 * @return <code>0</code> succeed <code>-1</code> otherwise.
	 */
	private static int addRateComment(int uid, int cid) {
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
		String query = "INSERT INTO rate_comment (uid,cid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
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
	 * Updates comment type as question or instructive
	 * 
	 * @param cid
	 *            Comment id
	 * @param type
	 *            Type number
	 * @return
	 */
	public static int updateType(int cid, int type) {
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
		String query = "UPDATE comment SET type= ? WHERE id=?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, type);
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

}
