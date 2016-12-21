package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

/**
 * Creates and finalizes database connections
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class ConnectionPool {
	/**
	 * DataSource object for the database
	 */
	private static final BasicDataSource dataSource = new BasicDataSource();
	/**
	 * Database settings.
	 */
	static {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://digest-db.c7pdwrhsbu6p.us-east-1.rds.amazonaws.com:3306/digest");
		dataSource.setUsername("digest");
		dataSource.setPassword("digEST352451.");
		dataSource.setInitialSize(8);
		dataSource.setMaxTotal(15);
	}

	/**
	 * Retrieves a connection from datasource.
	 * 
	 * @return Connection Object
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/*
	 * public static Connection getConnection() throws SQLException{ String url
	 * = ""; String username = ""; String password=""; String driver
	 * ="com.mysql.cj.jdbc.Driver"; try { Class.forName(driver); } catch
	 * (ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return DriverManager.getConnection(url, username,
	 * password);
	 * 
	 * 
	 * }
	 */
	/*
	 * public static Connection getConnection() { Connection conn = null; if
	 * (dataSource == null) { Properties prop = new Properties(); try {
	 * prop.load(new FileInputStream("database.properties")); } catch
	 * (IOException e) { // TODO Auto-generated catch block //logger.error(new
	 * File(".").getAbsolutePath()); e.printStackTrace(); //System.exit(-1); }
	 * 
	 * String driver = prop.getProperty(DRIVER); String url =
	 * prop.getProperty(URL); String username = prop.getProperty(USERNAME);
	 * String password = prop.getProperty(PASSWORD);
	 * 
	 * 
	 * if ((null == driver) || (null == url) || (null == username)) { // Error
	 * System.out.println("error"); System.exit(-99); }
	 * 
	 * dataSource = new BasicDataSource();
	 * dataSource.setDriverClassName(driver); dataSource.setUrl(url);
	 * dataSource.setUsername(username); dataSource.setPassword(password);
	 * dataSource.setTestOnBorrow(false); dataSource.setTestWhileIdle(true);
	 * dataSource.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
	 * dataSource.setTimeBetweenEvictionRunsMillis(30 * 60 * 1000); }
	 * 
	 * try { conn = dataSource.getConnection(); } catch (SQLException e) {
	 * System.err.println(e.getMessage());
	 * 
	 * } return conn;
	 * 
	 * }
	 */
	/**
	 * Closes database connection.
	 * 
	 * @param con
	 *            Connection object.
	 * @param statement
	 *            MySQL statement.
	 * @param resultSet
	 *            Result set.
	 */
	public static void close(Connection con, PreparedStatement statement, ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(con, statement);
	}

	/**
	 * Closes database connection.
	 * 
	 * @param con
	 *            Connection object.
	 * @param statement
	 *            MySQL statement.
	 * 
	 */
	public static void close(Connection con, PreparedStatement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		close(con);
	}

	/**
	 * Closes database connection.
	 * 
	 * @param con
	 *            Connection object..
	 */
	public static void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}