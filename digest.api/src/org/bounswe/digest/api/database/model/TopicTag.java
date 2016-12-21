package org.bounswe.digest.api.database.model;

public class TopicTag extends Model{
	private String tag;
	
	public String getTag(){
		int indexOfParanthesis = tag.indexOf('(');
		return tag.substring(0, indexOfParanthesis);
		
	}
	public String getEntity(){
		int indexOfParanthesis = tag.indexOf('(');
		return tag.substring(indexOfParanthesis).replace("(", "").replace(")", "");
	}
	public String getFullTag(){
		return tag;
		
	}
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
