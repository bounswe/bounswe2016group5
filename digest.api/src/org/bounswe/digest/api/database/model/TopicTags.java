package org.bounswe.digest.api.database.model;

import java.util.ArrayList;

public class TopicTags extends Model{
	private ArrayList<Integer> ids;
	private ArrayList<Integer> tids;
	private ArrayList<String> tags;
	private ArrayList<String> entities;
	private ArrayList<ArrayList<String>> relatedEntities;
	
	// Topic a�arken eklemesi gerekti�inde id ve tid sonra gelecek
	public TopicTags(ArrayList<String> tags, ArrayList<String> entities, ArrayList<ArrayList<String>> relatedEntities) {

		this.tags = tags;
		this.entities = entities;
		this.relatedEntities = relatedEntities;
	}
	public TopicTags(ArrayList<Integer> ids, ArrayList<Integer> tids, ArrayList<String> tags, ArrayList<String> entities, ArrayList<ArrayList<String>> relatedEntities) {
		this.ids = ids;
		this.tids = tids;
		this.tags = tags;
		this.entities = entities;
		this.relatedEntities = relatedEntities;
	}
	public ArrayList<Integer> getIds() {
		return ids;
	}
	public void setIds(ArrayList<Integer> ids) {
		this.ids = ids;
	}
	public ArrayList<Integer> getTids() {
		return tids;
	}
	public void setTids(ArrayList<Integer> tids) {
		this.tids = tids;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public ArrayList<String> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<String> entities) {
		this.entities = entities;
	}
	public ArrayList<ArrayList<String>> getRelatedEntities() {
		return relatedEntities;
	}
	public void setRelatedEntities(ArrayList<ArrayList<String>> relatedEntities) {
		this.relatedEntities = new ArrayList<ArrayList<String>>();
		this.relatedEntities = relatedEntities;
	}
}
