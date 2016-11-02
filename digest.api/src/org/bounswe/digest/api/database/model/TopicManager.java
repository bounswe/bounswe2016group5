package org.bounswe.digest.api.database.model;

public class TopicManager extends Model{
	public static final int ADMIN = 1;
	public static final int MODERATOR = 2;
	
	private int id;
	private int uid;
	private int type;
	
	
	public TopicManager(int id, int uid, int type) {
		this.id = id;
		this.uid = uid;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isAdmin(){
		return type == ADMIN;
	}
	public boolean isModerator(){
		return type == MODERATOR;
	}
	
	
	
}
