package digest.digestandroid;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Movie;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        public ImageView typeIcon;

        public MyViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.comment_body);
            owner = (TextView) view.findViewById(R.id.comment_owner);
            rate = (TextView) view.findViewById(R.id.comment_rate);
            rateButton = (ImageButton) view.findViewById(R.id.thumbsUp);
            typeIcon = (ImageView) view.findViewById(R.id.comment_type_icon);
            itemView.setOnClickListener(this);
            rateButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){

            if (v.getId() == rateButton.getId()){
                commentClickListener.onRateClick(getAdapterPosition(), v);
                Log.e("RATEE: ", "RATEDD");
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

        if(Cache.getInstance().getTopic().getOwner() == comment.getUid()){
            holder.owner.setTextColor(Color.BLUE);
        }
        else
            holder.owner.setTextColor(Color.BLACK);

        if(comment.getType()==1) {
            holder.typeIcon.setBackgroundResource(R.drawable.ic_question);
        } else if (comment.getType()==2) {
            holder.typeIcon.setBackgroundResource(R.drawable.ic_instructive);
        } else {
            //holder.typeIcon.setBackgroundResource(R.drawable.ic_instructive);
            holder.typeIcon.setVisibility(View.INVISIBLE);
        }

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
