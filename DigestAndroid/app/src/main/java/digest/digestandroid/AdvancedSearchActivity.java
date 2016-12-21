package digest.digestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;

/**
 * Created by sahin on 17.12.2016.
 */

public class AdvancedSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        final String previous = getIntent().getStringExtra("previous");

        Button addButton = ((Button)findViewById(R.id.advanced_search_search));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText =  String.valueOf(((EditText)findViewById(R.id.advanced_search_text)).getText());

                final Response.Listener<String> tagListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<Topic> arrayList = ViewRegisteredHomeActivity.serializeTopicsFromJson(response);
                        CacheTopiclist.getInstance().setTagTopics(arrayList);
                        finish();

                        if(previous.equals("home")) {
                            Intent intent = new Intent(getApplicationContext(), ViewSearchActivity.class);
                            startActivity(intent);
                        }else if(previous.equals("search")){
                            loadTopics(ViewSearchActivity.searchRecyclerView,arrayList);
                        }
                    }
                };

                APIHandler.getInstance().searchWithTag(searchText,tagListener);

            }
        });

    }

    public void loadTopics(RecyclerView currentRecyclerView, final ArrayList<Topic> currentTopicList){
        Log.d("TT","9");
        RecyclerView.Adapter homeAdapter = new HomeAdapter(currentTopicList);
        currentRecyclerView.setAdapter(homeAdapter);

        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("" + pos, v.toString());

                final int clickedTopicId = currentTopicList.get(pos).getId();
                Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", response.toString());
                        Cache.getInstance().setTopic(response);

                        Response.Listener<String> getRelatedTopicsListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                final ArrayList<Topic> arrayList = ViewRegisteredHomeActivity.serializeTopicsFromJson(response);
                                CacheTopiclist.getInstance().setRelatedTopicsOfaTopic(arrayList);

                                Intent intent = new Intent(getApplicationContext(), ViewTopicActivity.class);
                                startActivity(intent);
                            }
                        };

                        APIHandler.getInstance().getRelatedTopicsOfaTopic(clickedTopicId,getRelatedTopicsListener);
                    }
                };
                APIHandler.getInstance().getTopic("", clickedTopicId, getTopicListener);
            }
        });
    }
}
