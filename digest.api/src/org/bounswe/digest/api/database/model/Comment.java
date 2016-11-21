package org.bounswe.digest.api.database.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Comment {
	private int id;
	private int uid;
	private int ucid;
	private int tid;
	private String body;
	private int rate;
	private Timestamp timestamp;
	// private String username; maybe we can use.
	public Comment(int id, String body, int uid, int tid, int ucid, int rate, Timestamp timestamp) {
		this.id = id;
		this.body = body;
		this.uid = uid;
		this.tid = tid;
		this.ucid = ucid;
		this.rate = rate;
		this.timestamp = timestamp;
		
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
