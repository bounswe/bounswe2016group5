package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.TopicTag;
/**
 * @deprecated
 * 
 *
 */
public class TagJDBC {
	
	/**
	 * @deprecated
	 * @param tid
	 * @param tags
	 * @param entities
	 * @param relatedEntities
	 * @return
	 */
	public static int addTags(int tid, ArrayList<String> tags, ArrayList<String> entities, ArrayList<ArrayList<String>> relatedEntities){
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
		String query = "";
		return 0;
	}

}
