package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressJDBC {
	
	public static int addProgres(int uid,int tid){
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
		String query = "INSERT INTO progress(tid, uid) VALUES (?, ?)";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			statement.setInt(2, uid);
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
	
	public static int setProgressTopic(int tid,int uid,int val){
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
		String query = "UPDATE progress prog=? WHERE tid=? and uid=? ";
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, val);
			statement.setInt(2, tid);
			statement.setInt(3, uid);
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
	
	public static int getProgressOnTopic(int tid,int uid){
		String query = "SELECT prog FROM digest.progress WHERE progress.tid=? and progress.uid=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		PreparedStatement statement = null;
		int result=0;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			statement.setInt(2, uid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result += resultSet.getInt(1);
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
	
	public static int getProgressOnChannel(int cid,int uid){
		String query = "SELECT prog FROM digest.progress WHERE"
				+ " progress.uid=? and (SELECT tid FROM digest.channel_topic WHERE "
				+ " channel_topic.cid=?  and progress.tid=channel_topic.tid)";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
		PreparedStatement statement = null;
		int result=0;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
			statement.setInt(2, uid);
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
		return result/ChannelJDBC.getNumberOfTopics(cid);
	}
}
