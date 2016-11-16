package org.bounswe.digest.api.database.model;

import java.util.Random;

public class User extends Model {
	private int id;
	private String username;
	private String password;
	private String email;
	private String first_name;
	private String last_name;
	private int status;
	private Role role;
	private String session;

	public User(int id, String username, String password, String email, String first_name, String last_name, int status,
			Role role) {
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		this.setFirst_name(first_name);
		this.setLast_name(last_name);
		this.setStatus(status);
		this.setRole(role);
		this.setSession();

		//

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	private void setSession() {
		Random rand = new Random();
		int r1 = rand.nextInt(0xEFFF) + 0x1000;
		int r2 = rand.nextInt(0xEFFF) + 0x1000;
		int r3 = rand.nextInt(0xEFFF) + 0x1000;
		int r4 = rand.nextInt(0xEFFF) + 0x1000;
		this.session = Integer.toHexString(r1) + Integer.toHexString(r2) + Integer.toHexString(r3)
				+ Integer.toHexString(r4);
	}
	public String getSession(){
		return session;
	}

	@Override
	public String toString() {
		return this.username + " " + this.role;

	}

}
