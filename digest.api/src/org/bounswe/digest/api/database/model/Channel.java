package org.bounswe.digest.api.database.model;

/**
 * Channel Object
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class Channel extends Model {
	/**
	 * Id of channel.
	 */
	private int id;
	/**
	 * User id of channel.
	 */
	private int uid;
	/**
	 * Name of channel.
	 */
	private String name;
	/**
	 * Status of channel.
	 */
	private int status;

	/**
	 * Creates channel with given parameters.
	 * 
	 * @param id
	 * @param uid
	 * @param name
	 * @param status
	 */
	public Channel(int id, int uid, String name, int status) {
		this.setId(id);
		this.setUid(uid);
		this.setName(name);
		this.setStatus(status);
	}

	/**
	 * Returns id.
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns user id of channel.
	 * 
	 * @return
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Sets user id of channel.
	 * 
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * Returns channel name.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets channel name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns status.
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets status.
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
