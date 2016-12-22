package org.bounswe.digest.api.database.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Comment Object.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class Comment extends Model {
	private int id;
	private int uid;
	private int ucid;
	private int tid;
	private String body;
	private int rate;
	private int type;
	private Timestamp timestamp;

	/**
	 * Generates comment with given parameters.
	 * 
	 * @param id
	 * @param body
	 * @param uid
	 * @param tid
	 * @param ucid
	 * @param rate
	 * @param type
	 * @param timestamp
	 */
	public Comment(int id, String body, int uid, int tid, int ucid, int rate, int type, Timestamp timestamp) {
		this.id = id;
		this.body = body;
		this.uid = uid;
		this.tid = tid;
		this.ucid = ucid;
		this.rate = rate;
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
