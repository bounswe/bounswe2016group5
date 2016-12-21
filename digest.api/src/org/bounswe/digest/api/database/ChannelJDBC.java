package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.Channel;
import org.bounswe.digest.api.database.model.Comment;
import org.bounswe.digest.api.database.model.Topic;

import com.google.gson.Gson;

public class ChannelJDBC {

	public static int addChannel(int uid,String name){
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
	
	public static int addTopicToChannel(int tid,int cid){
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
	
	public static String getChannel(int cid){
		return getChannelObject(cid).printable();
	}
	public static String getChannelsOfUser(int uid){
		Gson gson = new Gson();
		return gson.toJson(getChannelArrayOfUser(uid));
	}
	protected static ArrayList<Channel> getChannelArrayOfUser(int uid){
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
		ArrayList<Channel> result=new ArrayList<Channel>();
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, uid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				result.add(new Channel(resultSet.getInt(1), resultSet.getInt(2), 
						resultSet.getString(3),resultSet.getInt(4)));
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
	
	protected static Channel getChannelObject(int cid){
		String query = "SELECT * FROM digest.channel WHERE channel.id=?";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		Channel result=null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = (new Channel(resultSet.getInt(1), resultSet.getInt(2), 
						resultSet.getString(3),resultSet.getInt(4)));
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
	
	public static String getChannelTid(int tid){
		String query = "SELECT * FROM digest.channel WHERE "+
					   " id=(SELECT cid FROM channel_topic WHERE tid=? )";
		Connection connection;
		try {
			connection = ConnectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PreparedStatement statement = null;
		Channel result=null;
		ResultSet resultSet;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(query);
			statement.setInt(1, tid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				result = (new Channel(resultSet.getInt(1), resultSet.getInt(2), 
						resultSet.getString(3),resultSet.getInt(4)));
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
		Gson gson=new Gson();
		return gson.toJson(gson);
	
	}
	
	public static String getSubscribedChannels(int uid){
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
			
			//public Topic(int id, String header, String image, String body, int owner, int status,
					//ArrayList<TopicTag> tags, ArrayList<Quiz> quizzes, ArrayList<String> media, ArrayList<Comment> comments, Timestamp timestamp) {
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
		ArrayList<Channel> result=new ArrayList<>();
		for(int i=0; i<topics.size(); i++){
			result.add(getChannelObjectOfTopic(topics.get(i)));
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	public static int getNumberOfTopics(int cid){
		String query = "SELECT COUNT(tid) from channel_topic "
				+ 	"WHERE channel_topic.cid=?";
		Connection connection;
		int result=0;
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
			statement.setInt(1,cid);
			resultSet=statement.executeQuery();
			while(resultSet.next()){
				result=(resultSet.getInt(1));
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
		return result;
	}
	
	public static String getTopicsOfChannel(int cid){
		ArrayList<Topic> topics=new ArrayList<Topic>();
		String query = "SELECT tid from channel_topic "
				+ 	"WHERE channel_topic.cid=?";
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
			statement.setInt(1,cid);
			resultSet=statement.executeQuery();
			while(resultSet.next()){
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
		Gson gson = new Gson();
		return gson.toJson(topics);
	}
	public static String getChannelOfTopic(int tid){
		Gson gson= new Gson();
		return gson.toJson(getChannelObjectOfTopic(tid));
	}
	public static Channel getChannelObjectOfTopic(int tid){
		Channel channel=null;
		String query = "SELECT cid from channel_topic "
				+ 	"WHERE channel_topic.tid=?";
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
			statement.setInt(1,tid);
			resultSet=statement.executeQuery();
			if(resultSet.next()){
				channel=(getChannelObject(resultSet.getInt(1)));
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
		return (channel);
	}

}
