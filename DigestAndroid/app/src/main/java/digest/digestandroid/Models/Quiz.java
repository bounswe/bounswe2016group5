package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by oykuyilmaz on 15/11/16.
 */

public class Quiz {
    private String name;
    private ArrayList<QuizQuestion> questions;

    public Quiz(String name, ArrayList<QuizQuestion> questions){
        this.name=name;
        this.setQuestions(questions);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }


    public void setQuestions(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
    }
}
