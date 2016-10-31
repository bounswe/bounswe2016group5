package org.bounswe.digest.api.database.model;

public class Role extends Model {
	public Role(int id, String name) {
		this.id = id;
		this.name = name;
	}
	private int id;
	private String name;
}
