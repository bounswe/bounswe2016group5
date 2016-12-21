package digest.digestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;

/**
 * Created by sahin on 27.11.2016.
 */

public class ViewSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private ImageView advancedSearchView;
    public static RecyclerView searchRecyclerView;
    private RecyclerView.LayoutManager searchLayoutManager;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) findViewById(R.id.search_view_search);
        searchView.setQueryHint("Enter a topic name..");


        searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchLayoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(searchLayoutManager);
        searchRecyclerView.setItemAnimator(new DefaultItemAnimator());


        loadTopics(searchRecyclerView,CacheTopiclist.getInstance().getTagTopics());


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        searchView.clearFocus();
                        final Response.Listener<String> tagListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                ArrayList<Topic> arrayList = ViewRegisteredHomeActivity.serializeTopicsFromJson(response);
                                CacheTopiclist.getInstance().setTagTopics(arrayList);
                                loadTopics(searchRecyclerView,arrayList);
                            }
                        };

                        APIHandler.getInstance().searchWithTag(query,tagListener);

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );

        advancedSearchView = (ImageView) findViewById(R.id.advanced_search_button_insearch);
        advancedSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                intent.putExtra("previous", "search");
                startActivity(intent);
            }
        });

    }


    public void loadTopics(RecyclerView currentRecyclerView,final ArrayList<Topic> currentTopicList){
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
