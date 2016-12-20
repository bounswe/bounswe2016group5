package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.TopicTag;

public class TagJDBC {
	
	/**
	 * Vaktim yoktu yazamadým da açýklayayým bunun ne yapmasý gerektiðini.
	 * Diyelim user 2 tag ekledi topic açarken tag:entity formatýnda eklemesi zorunlu olacak
	 * python : programming_language
	 * java : programming_language
	 * Þimdi biz onlardan tagi string olarak alacaðýz, user'ýn seçtiði entity'i de string olarak alacaðýz
	 * sonra DigestAPIServlet'ta ADD_TOPIC'te ConceptNetAPI.getRelatedEntities'i çalýþtýrýp entity'i yollayarak da
	 * relatedEntitites'i alacaðýz. sonra þu hale gelecek 
	 * python : programming_language : [computer_science,artificial_intelligence..] gibi
	 * yine ADD_TOPIC'te bu metodu çaðýrarak bu elimdeki bilgiyi database'e aktarmam lazým.
	 * 
	 * þu önemli ki, tag-entity unique olmalý tablomuzda
	 * tag-entity daha önce eklenmiþ ise eklememe gerek yok tabii, eklenmediyse de key generate edecek
	 * topic id de bu metod çaðrýlmadan önce belli olmalý ki tag tablosunda ilgili tag-entity'nin columnuna ekleyebilelim topic idyi
	 * 
	 * tag tablosunda en azýndan þunlar olmalý 
	 * tag_entity_id; topic_id; tag_text; entity_text; related_entity_text_array
	 * 
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
