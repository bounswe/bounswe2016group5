package digest.digestandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import digest.digestandroid.Models.QuizQuestion;

/**
 * Created by oykuyilmaz on 09/12/2016.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<QuizQuestion> questionList;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView question;
        public CheckBox choice_one, choice_two, choice_three;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.text_view_question);
            choice_one = (CheckBox) view.findViewById(R.id.checkbox_choice_one);
            choice_two = (CheckBox) view.findViewById(R.id.checkbox_choice_two);
            choice_three = (CheckBox) view.findViewById(R.id.checkbox_choice_three);

            //itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {

        }
    }

    public QuestionAdapter(List<QuizQuestion> questionList) {
        this.questionList = questionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_row, parent, false);
        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QuizQuestion question = questionList.get(position);

        holder.choice_one.setClickable(false);
        holder.choice_two.setClickable(false);
        holder.choice_three.setClickable(false);

        holder.question.setText(question.getText());
        holder.choice_one.setText(question.getChoices().get(0));
        if(question.getChoices().size()>1)
            holder.choice_two.setText(question.getChoices().get(1));
        if (question.getChoices().size()>2)
            holder.choice_three.setText(question.getChoices().get(2));
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }




}
