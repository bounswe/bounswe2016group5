package org.bounswe.digest.api.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;


public class ConnectionPool {
	private static final BasicDataSource dataSource = new BasicDataSource();

	static {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("");
		dataSource.setUsername("");
		dataSource.setPassword("");
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/*public static Connection getConnection(){
		String url = "jdbc:mysql://";
		String username = "";
		String password="";
		String driver ="com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	/*
	public static Connection getConnection() {
		Connection conn = null;
		if (dataSource == null) {
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream("database.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//logger.error(new File(".").getAbsolutePath());
				e.printStackTrace();
				//System.exit(-1);
			}

			String driver = prop.getProperty(DRIVER);
			String url = prop.getProperty(URL);
			String username = prop.getProperty(USERNAME);
			String password = prop.getProperty(PASSWORD);
			

			if ((null == driver) || (null == url) || (null == username)) {
				// Error
				System.out.println("error");
				System.exit(-99);
			}

			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(driver);
			dataSource.setUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setTestOnBorrow(false);
			dataSource.setTestWhileIdle(true);
			dataSource.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
			dataSource.setTimeBetweenEvictionRunsMillis(30 * 60 * 1000);
		}

		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			System.err.println(e.getMessage());

		}
		return conn;

	}
*/
	public static void close(Connection connection) {
		/* do nothing*/
		/*// TODO fix
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}