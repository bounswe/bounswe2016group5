package digest.digestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;

/**
 * Created by sahin on 19.12.2016.
 */

public class ViewChannelTopicsActivity extends AppCompatActivity{

    RecyclerView viewChannelTopicsRecyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;


            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_channel_topics);

        viewChannelTopicsRecyclerView = (RecyclerView) findViewById(R.id.view_channels__recycler_view);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar_view_channels));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewChannelTopicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewChannelTopicsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ((TextView)(findViewById(R.id.view_channels_channel_name))).setText(getIntent().getStringExtra("cname"));

        loadTopics(viewChannelTopicsRecyclerView,CacheTopiclist.getInstance().getChannelTopics());

    }

    public void loadTopics(RecyclerView currentRecyclerView, final ArrayList<Topic> currentTopicList){
        Log.d("TT","9");
        RecyclerView.Adapter homeAdapter = new HomeAdapter(currentTopicList);
        currentRecyclerView.setAdapter(homeAdapter);

        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("" + pos, v.toString());

                int clickedTopicId = currentTopicList.get(pos).getId();
                Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", response.toString());
                        Cache.getInstance().setTopic(response);

                        Intent intent = new Intent(getApplicationContext(), ViewTopicActivity.class);
                        startActivity(intent);
                    }
                };
                APIHandler.getInstance().getTopic("", clickedTopicId, getTopicListener);
            }
        });
    }
}
