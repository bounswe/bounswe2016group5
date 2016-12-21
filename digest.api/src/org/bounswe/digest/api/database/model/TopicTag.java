package org.bounswe.digest.api.database.model;
/**
 * Tags object.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class TopicTag extends Model{
	/** 
	 * Holds tag and its entity as "tag(entity)"
	 */
	private String tag;
	/**
	 * Returns tag without entity.
	 * @return
	 */
	public String getTag(){
		int indexOfParanthesis = tag.indexOf('(');
		return tag.substring(0, indexOfParanthesis);
		
	}
	/**
	 * Returns entity.
	 * @return
	 */
	public String getEntity(){
		int indexOfParanthesis = tag.indexOf('(');
		return tag.substring(indexOfParanthesis).replace("(", "").replace(")", "");
	}
	/**
	 * Returns both entity and tag.
	 * @return
	 */
	public String getFullTag(){
		return tag;
		
	}
	/**
	 * Creates tag object with tag and entity.
	 * @param tag
	 */
	public TopicTag(String tag){
		this.tag = tag;

	}
	
	/*private int id;
	private int tid;	
	
	public TopicTag(int id, int tid) {
		this.id = id;
		this.tid = tid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}*/
	
}
