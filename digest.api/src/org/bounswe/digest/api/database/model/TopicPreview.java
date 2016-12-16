package org.bounswe.digest.api.database.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TopicPreview extends Model {
	private int id;
	private String header;
	private String image;
	private int owner;
	private int status;
	private Timestamp timestamp;


	public TopicPreview(int id, String header, String image, int owner, int status,
			 Timestamp timestamp) {
		this.id = id;
		this.header = header;
		this.image = image;
		this.owner = owner;
		this.status = status;
		this.timestamp = timestamp;
	}


}
