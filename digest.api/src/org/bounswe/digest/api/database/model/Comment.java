package org.bounswe.digest.api.database.model;

public class Comment {
	private int id;
	private int uid;
	private String username;
	private String text;
	private int rate;
	
	public Comment(int id,int uid,String username,String text,int rate){
		this.id=id;
		this.setUid(uid);
		this.setText(text);
		this.setUsername(username);
		this.rate=rate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
}
