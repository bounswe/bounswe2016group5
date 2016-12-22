package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bounswe.digest.api.database.model.Model;
import org.bounswe.digest.api.database.model.Role;
import org.bounswe.digest.api.database.model.User;
import org.bounswe.digest.api.database.model.Error;
/**
 * Handles database transactions about users.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class UserJDBC {
	/**
	 * Handles login job into database
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String login(String username, String password) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		Model user = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String query = "SELECT user.id," + "user.username," + "user.password," + "user.email," + "user.first_name,"
				+ "user.last_name," + "user.status," + "role.id, " + "role.name  " + "FROM digest.user, digest.role "
				+ "WHERE (user.username = ? OR user.email = ?) AND user.password = ? AND role.id = user.rid";

		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, username);
			statement.setString(3, password);
			results = statement.executeQuery();
			if (results != null && results.next()) {
				Role role = new Role(results.getInt(8), results.getString(9));
				user = new User(results.getInt(1), results.getString(2), results.getString(3), results.getString(4),
						results.getString(5), results.getString(6), results.getInt(7), role);
				if (writeSessionInformation((User)user) != 0) {
					user = new Error("database_error", "An error occured in the database session");
				}
			} else {
				user = new Error("incorrect_credentials", "Incorrect username/email or password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					System.err.print("Transaction is being rolled back");
					connection.rollback();
				} catch (SQLException excep) {
					excep.printStackTrace();
					user = new Error("database_error", "An error occured in the database");
				}
			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return user.printable();
	}
	/**
	 * 
	 * @param uid
	 * @param session
	 * @return
	 */
	public static boolean isSessionValid(int uid, String session) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		PreparedStatement statement = null;
		ResultSet results = null;
		boolean result = false;
		String query = "SELECT * " + "FROM digest.user_session "
				+ "WHERE session.uid = ? AND session.sid = ?";

		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			statement.setString(2, session);
			results = statement.executeQuery();
			if (results != null && results.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					System.err.print("Transaction is being rolled back");
					connection.rollback();
				} catch (SQLException excep) {
					excep.printStackTrace();
					
				}
			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}
	
	

	/**
	 * 
	 * @param user
	 * @return
	 */
	private static int writeSessionInformation(User user) {
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		};
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO user_session (uid, sid, duration) VALUES (?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setString(2, user.getSession());
			statement.setInt(3, 0);
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
					// TODO Auto-generated catch block
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
		

	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param first_name
	 * @param last_name
	 * @param status
	 * @param role
	 * @return
	 */
	public static int register(String username, String password, String email, String first_name, String last_name, int status,
			Role role) {
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
		String query = "INSERT INTO user (username, password, email, first_name, last_name, status, rid) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, email);
			statement.setString(4, first_name);
			statement.setString(5, last_name);
			statement.setInt(6, status);
			statement.setInt(7, role.getId());
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
					// TODO Auto-generated catch block
					result = -1;
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = -1;
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return result;
	}
	/**
	 * Returns the username of a user.
	 * @param uid User ID.
	 * @return Username as string.
	 */
	public static String getUserName(int uid){
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}
		String user = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String query = "SELECT username from digest.user where user.id=?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			results = statement.executeQuery();
			if (results.next()) {
				user=(results.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					System.err.print("Transaction is being rolled back");
					connection.rollback();
				} catch (SQLException excep) {
					excep.printStackTrace();
					//user = new Error("database_error", "An error occured in the database");
				}
			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ConnectionPool.close(connection);
		return user;
	}

}
