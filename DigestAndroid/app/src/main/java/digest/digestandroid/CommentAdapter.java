package digest.digestandroid;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import digest.digestandroid.Models.Comment;

/**
 * Created by Burki on 19.11.2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comment> commentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView body, owner, rate;

        public MyViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.comment_body);
            owner = (TextView) view.findViewById(R.id.comment_owner);
            rate = (TextView) view.findViewById(R.id.comment_rate);
        }
    }


    public CommentAdapter(List<Comment> commentList) {
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
        Comment comment = commentList.get(position);
        holder.body.setText(comment.getBody());
        holder.owner.setText(Integer.toString(comment.getUid()));
        holder.rate.setText(String.valueOf(comment.getRate()));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
