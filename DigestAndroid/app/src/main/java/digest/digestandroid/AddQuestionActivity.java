package digest.digestandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import digest.digestandroid.Models.QuizQuestion;

public class AddQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
    }

    public void saveQuestion(View view) {
        QuizQuestion question = new QuizQuestion();
        question.setText(String.valueOf(((EditText)findViewById(R.id.edit_text_question_box)).getText()) );

        ArrayList<String> choices = new ArrayList<String>();
        choices.add(String.valueOf(((EditText)findViewById(R.id.edit_text_choice_one)).getText()));
        choices.add(String.valueOf(((EditText)findViewById(R.id.edit_text_choice_two)).getText()));
        choices.add(String.valueOf(((EditText)findViewById(R.id.edit_text_choice_three)).getText()));
        question.setChoices(choices);

        ArrayList<Integer> answers = new ArrayList<Integer>();
        if ( ((CheckBox)findViewById(R.id.checkbox_choice_one)).isChecked() ) {
            answers.add(1);
        }
        if ( ((CheckBox)findViewById(R.id.checkbox_choice_two)).isChecked() ) {
            answers.add(2);
        }
        if ( ((CheckBox)findViewById(R.id.checkbox_choice_three)).isChecked() ) {
            answers.add(3);
        }
        question.setAnswers(answers);

        Cache.getInstance().setQuestion(question);
        Log.d("Cache", Cache.getInstance().getQuestion().getText());
        setResult(RESULT_OK);
        finish();

    }
}
