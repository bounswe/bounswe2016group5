package org.bounswe.digest.api.database.model;
/**
 * Role object for user.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class Role extends Model {
	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private int id;
	private String name;
}
