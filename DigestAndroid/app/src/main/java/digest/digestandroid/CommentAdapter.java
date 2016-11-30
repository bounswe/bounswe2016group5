package digest.digestandroid;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.CommentUser;

/**
 * Created by Burki on 19.11.2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<CommentUser> commentList;
    private static CommentClickListener commentClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView body, owner, rate;
        public ImageButton rateButton;

        public MyViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.comment_body);
            owner = (TextView) view.findViewById(R.id.comment_owner);
            rate = (TextView) view.findViewById(R.id.comment_rate);
            rateButton = (ImageButton) view.findViewById(R.id.thumbsUp);

            itemView.setOnClickListener(this);
            rateButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){

            if (v.getId() == rateButton.getId()){
                commentClickListener.onRateClick(getAdapterPosition(), v);
            } else {
                commentClickListener.onItemClick(getAdapterPosition(), v);
            }

        }
    }

    public void setOnItemClickListener(CommentClickListener commentClickListener){
        this.commentClickListener = commentClickListener;
    }

    public CommentAdapter(List<CommentUser> commentList) {
        this.commentList = commentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentUser comment = commentList.get(position);
        holder.body.setText(comment.getBody());
        holder.owner.setText(comment.getUsername());
        holder.rate.setText(String.valueOf(comment.getRate()));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public interface CommentClickListener {
        public void onItemClick(int position, View v);
        public void onRateClick(int position, View v);
    }
}
