package digest.digestandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.fragments.TopicCommentFragment;

public class AddCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        // TODO: 19.11.2016 Comment ekleyince recylerview e ekle -- mAdapter.notifyDataSetChanged();--

    }

    public void save(View view) {
        Comment comment = new Comment();

        comment.setText( String.valueOf(((EditText)findViewById(R.id.CommentBox)).getText()) );

        Cache.getInstance().setComment(comment);

        finish();

    }
}