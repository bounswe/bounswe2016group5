package digest.digestandroid;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import digest.digestandroid.Models.QuizQuestion;

/**
 * Created by oykuyilmaz on 09/12/2016.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<QuizQuestion> questionList;
    private ArrayList<Integer> answerList;




    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tw_question;
        public CheckBox choice_one, choice_two, choice_three;


        public MyViewHolder(View view) {
            super(view);
            tw_question = (TextView) view.findViewById(R.id.text_view_question);
            choice_one = (CheckBox) view.findViewById(R.id.checkbox_choice_one);
            choice_two = (CheckBox) view.findViewById(R.id.checkbox_choice_two);
            choice_three = (CheckBox) view.findViewById(R.id.checkbox_choice_three);

            //choice_one.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {

        }


    }


    public QuestionAdapter(List<QuizQuestion> questionList) {
        this.questionList = questionList;
        this.answerList = new ArrayList<Integer>();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_row, parent, false);
        return new QuestionAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final QuizQuestion question = questionList.get(position);


        /*holder.choice_one.setClickable(false);
        holder.choice_two.setClickable(false);
        holder.choice_three.setClickable(false);*/

        holder.tw_question.setText(question.getText());
        holder.choice_one.setText(question.getChoices().get(0));
        if(question.getChoices().size()>1)
            holder.choice_two.setText(question.getChoices().get(1));
        if (question.getChoices().size()>2)
            holder.choice_three.setText(question.getChoices().get(2));

        holder.choice_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    Log.d("first one", "checked");
                    answerList.set(position, 0);

                }
            }
        });

        holder.choice_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    answerList.set(position, 1);

                }
            }
        });

        holder.choice_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    answerList.set(position, 2);
                }
            }
        });




        //Log.d("----CEVAPLAR", answerList.get(0).toString());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }




    public List<Integer> getAnswerList(){
        return answerList;
    }

    public void updateAnswerList() {
        this.answerList.add(-1);
    }

    public void setAnswerList() {
        for (int i=0; i<questionList.size(); i++) {
            answerList.add(-1);
        }
    }

    public int matchAnswers(){
        int nRightAnswers = 0;

        for(QuizQuestion q: questionList) {
            //Log.d("bizim: ", answerList.get(questionList.indexOf(q)).toString());
            if(answerList.size()>questionList.indexOf(q) && q.getAnswers().contains(answerList.get(questionList.indexOf(q)))) {
                Log.d("---CEVAP", "DOGRU");
                nRightAnswers++;
            }

        }
        return nRightAnswers;
    }

    public void clearBoxes(){
        answerList.clear();
    }




}
