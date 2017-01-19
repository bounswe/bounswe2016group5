package org.bounswe.digest.api.database.model;

import com.google.gson.Gson;
/**
 * Model superclass of database objects. Holds JSONify function.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class Model {
	/**
	 * Generates printable string from itself.
	 * @return Printable JSON string.
	 */
	public String printable() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
