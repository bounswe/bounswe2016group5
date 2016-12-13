package org.bounswe.digest.api.database.model;

import java.util.ArrayList;

public class Question extends Model {

	private String text;
	private ArrayList<String> choices;
	private ArrayList<Integer> answers;
	
	public Question(String text,ArrayList<String> choices,ArrayList<Integer> answers){
		this.setText(text);
		this.setChoices(choices);
		this.setAnswers(answers);
	}

	public ArrayList<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Integer> answers) {
		this.answers = answers;
	}

	public ArrayList<String> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
