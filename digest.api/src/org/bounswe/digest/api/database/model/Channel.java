package org.bounswe.digest.api.database.model;

public class Channel extends Model {
	private int id;
	private int uid;
	private String name;
	private int status;
	
	public Channel(int id,int uid,String name,int status){
		this.setId(id);
		this.setUid(uid);
		this.setName(name);
		this.setStatus(status);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
