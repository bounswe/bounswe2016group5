package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.Topic;
import org.bounswe.digest.api.database.model.TopicManager;
import org.bounswe.digest.api.database.model.TopicTag;
import org.bounswe.digest.api.database.model.User;

public class TopicJDBC {
	public static int createTopic(String header, String type, String image, String url, String body, int owner, int status,
			ArrayList<Integer> topicManager, ArrayList<String> tags) {
		Connection connection = ConnectionPool.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		String query = "INSERT INTO topic (header, type, image, url, body, owner, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setString(1, header);
			statement.setString(2, type);
			statement.setString(3, image);
			statement.setString(4, url);
			statement.setString(5, body);
			statement.setInt(6, owner);
			statement.setInt(7,0); //Doktor bu ne?
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
	private static Topic getTopicWithUser(){
		
		return null;
	}
}
