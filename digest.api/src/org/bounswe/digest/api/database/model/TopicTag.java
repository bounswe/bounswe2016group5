package org.bounswe.digest.api.database.model;

public class TopicTag extends Model{
	private int id;
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
	}
	
}
