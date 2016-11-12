package org.bounswe.digest.api.database.model;

import java.util.ArrayList;

public class Quiz extends Model{
	private String name;
	private String question;
	
	private ArrayList<String> choices;
	private ArrayList<Integer> answers;
	
	public Quiz(String name,String question,ArrayList<String> choices,ArrayList<Integer> answers){
		this.name=name;
		this.question=question;
		this.choices=choices;
		this.setAnswers(answers);
	}
	
	public ArrayList<String> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Integer> answers) {
		this.answers = answers;
	}
	
}
