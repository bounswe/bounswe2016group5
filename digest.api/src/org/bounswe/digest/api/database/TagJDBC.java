package org.bounswe.digest.api.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bounswe.digest.api.database.model.TopicTag;

public class TagJDBC {
	
	/**
	 * Vaktim yoktu yazamad�m da a��klayay�m bunun ne yapmas� gerekti�ini.
	 * Diyelim user 2 tag ekledi topic a�arken tag:entity format�nda eklemesi zorunlu olacak
	 * python : programming_language
	 * java : programming_language
	 * �imdi biz onlardan tagi string olarak alaca��z, user'�n se�ti�i entity'i de string olarak alaca��z
	 * sonra DigestAPIServlet'ta ADD_TOPIC'te ConceptNetAPI.getRelatedEntities'i �al��t�r�p entity'i yollayarak da
	 * relatedEntitites'i alaca��z. sonra �u hale gelecek 
	 * python : programming_language : [computer_science,artificial_intelligence..] gibi
	 * yine ADD_TOPIC'te bu metodu �a��rarak bu elimdeki bilgiyi database'e aktarmam laz�m.
	 * 
	 * �u �nemli ki, tag-entity unique olmal� tablomuzda
	 * tag-entity daha �nce eklenmi� ise eklememe gerek yok tabii, eklenmediyse de key generate edecek
	 * topic id de bu metod �a�r�lmadan �nce belli olmal� ki tag tablosunda ilgili tag-entity'nin columnuna ekleyebilelim topic idyi
	 * 
	 * tag tablosunda en az�ndan �unlar olmal� 
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
