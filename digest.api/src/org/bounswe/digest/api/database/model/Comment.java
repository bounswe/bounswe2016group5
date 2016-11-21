package org.bounswe.digest.api.database.model;

public class Comment {
	private int id;
	private int uid;
	private int ucid;
	private int tid;
	// private String username;
	private String body;
	private int rate;

	public Comment(int id, int uid, int ucid, int tid, String body, int rate) {
		this.id = id;
		this.uid = uid;
		this.ucid = ucid;
		this.body = body;
		this.rate = rate;
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
