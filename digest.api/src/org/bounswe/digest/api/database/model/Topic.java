package org.bounswe.digest.api.database.model;

import java.util.ArrayList;

public class Topic extends Model{
	private int id;
	private String header;
	private String type;
	private String image;
	private String url;
	private String body;
	private int owner;
	private int status;
	
	private ArrayList<TopicManager> topicManagers;
	private ArrayList<TopicTag> tags;
	
	
	public Topic(int id, String header, String type, String image, String url, String body, int owner, int status,
			ArrayList<TopicManager> topicManagers, ArrayList<TopicTag> tags) {
		this.id = id;
		this.header = header;
		this.type = type;
		this.image = image;
		this.url = url;
		this.body = body;
		this.owner = owner;
		this.status = status;
		this.topicManagers = topicManagers;
		this.tags = tags;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public ArrayList<TopicManager> getTopicManagers() {
		return topicManagers;
	}
	public void setTopicManagers(ArrayList<TopicManager> topicManagers) {
		this.topicManagers = topicManagers;
	}
	public ArrayList<TopicTag> getTags() {
		return tags;
	}
	public void setTags(ArrayList<TopicTag> tags) {
		this.tags = tags;
	}
	
	
	

}
