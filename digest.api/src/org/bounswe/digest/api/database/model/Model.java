package org.bounswe.digest.api.database.model;

import com.google.gson.Gson;

public class Model {
	public String printable() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
