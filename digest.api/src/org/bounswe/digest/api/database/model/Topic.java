package org.bounswe.digest.api.database.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Topic extends Model {
	private int id;
	private String header;
	// private String type;
	private String image;
	private String body;
	private int owner;
	private int status;
	private Timestamp timestamp;

	private ArrayList<TopicTag> tags;
	private ArrayList<Quiz> quizzes;
	private ArrayList<String> media;
	private ArrayList<Comment> comments;

	public Topic(int id, String header, String image, String body, int owner, int status,
			ArrayList<TopicTag> tags, ArrayList<Quiz> quizzes, ArrayList<String> media, ArrayList<Comment> comments, Timestamp timestamp) {
		this.id = id;
		this.header = header;
		this.image = image;
		this.body = body;
		this.owner = owner;
		this.status = status;
		this.tags = tags;
		this.quizzes = quizzes;
		this.media = media;
		this.comments = comments;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	/*
	 * public String getType() { return type; } public void setType(String type)
	 * { this.type = type; }
	 */
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<TopicTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<TopicTag> tags) {
		this.tags = tags;
	}

	public ArrayList<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(ArrayList<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public ArrayList<String> getMedia() {
		return media;
	}

	public void setMedia(ArrayList<String> media) {
		this.media = media;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

}
