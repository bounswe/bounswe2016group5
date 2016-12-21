package org.bounswe.digest.api.database.model;

public class TopicTag extends Model{
	private int id;
	private int tid;
	private String tag;
	
	
	public TopicTag(int id, int tid, String tag) {
		this.id = id;
		this.tid = tid;
		this.tag = tag;
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
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
}
