package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bounswe.digest.api.database.model.User;

public class UserJDBC {

	public static User login(String username, String password) throws SQLException {
		Connection connection = ConnectionPool.getConnection();
		User user = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String query = "SELECT user.id," + "user.username," + "user.password," + "user.first_name," + "user.last_name,"
				+ "user.status " + "FROM digest.user " + "WHERE user.username = ? AND user.password = ?";

		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			results = statement.executeQuery();
			if (results != null && results.next()) {
				user = new User();
				user.setId(results.getInt(1));
				user.setUsername(results.getString(2));
				user.setPassword(results.getString(3));
				user.setFirst_name(results.getString(4));
				user.setLast_name(results.getString(5));
				user.setStatus(results.getInt(6));

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
				statement.close();
			}
			connection.setAutoCommit(true);
		}
		
		return user;
	}

}
