package org.bounswe.digest.api.database.model;

import java.util.ArrayList;
/**
 * Quiz Object.
 * 
 * @author Kerim Gokarslan 
 * @author Ozan Bulut 
 *
 */
public class Quiz extends Model{
	private String name;
	private ArrayList<Question> questions;
	
	public Quiz(String name,ArrayList<Question> questions){
		this.name=name;
		this.setQuestions(questions);
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

}
