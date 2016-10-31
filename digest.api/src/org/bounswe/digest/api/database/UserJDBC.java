package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bounswe.digest.api.database.model.Model;
import org.bounswe.digest.api.database.model.Role;
import org.bounswe.digest.api.database.model.User;
import org.bounswe.digest.api.database.model.Error;

public class UserJDBC {
	/**
	 * Handles login job into database
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String login(String username, String password) {
		Connection connection = ConnectionPool.getConnection();
		Model user = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String query = "SELECT user.id," + "user.username," + "user.password," + "user.email," + "user.first_name,"
				+ "user.last_name," + "user.status," + "user_role.id, " + "user_role.name  " + "FROM digest.user "
				+ "WHERE (user.username = ? OR user.email = ?) AND user.password = ? AND user_role.id = user.rid";

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
					user = new Error("database_error", "An error occured in the database");
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

		return user.printable();
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private static int writeSessionInformation(User user) {
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO user_session (uid, sid, duration) VALUES ?, ?, ?";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.setString(2, user.getSession());
			statement.setInt(3, 0);
			statement.executeQuery();
		} catch (SQLException e) {
			result = -1;
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
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO user (username, password, email, first_name, last_name, status, rid) VALUES ?, ?, ?, ?, ?, ?, ?";
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
			statement.executeQuery();
		} catch (SQLException e) {
			result = -1;
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
		return result;
	}

}
