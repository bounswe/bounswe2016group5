package digest.digestandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import digest.digestandroid.Cache;
import digest.digestandroid.CacheTopiclist;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;
import digest.digestandroid.R;
import digest.digestandroid.HomeAdapter;
import digest.digestandroid.ViewRegisteredHomeActivity;
import digest.digestandroid.ViewTopicActivity;
import digest.digestandroid.api.APIHandler;
import digest.digestandroid.api.GsonRequest;


public class RegisteredHomeHomeFragment extends Fragment {

    protected View rootView;

    private RecyclerView.Adapter homeAdapter;
    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;

    public RegisteredHomeHomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_home, container, false);

        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        homeLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(homeLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        homeRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Do not show recent topics. Show recommended settings.
                if(CacheTopiclist.getInstance().getRecentTopics() == null){
                    APIHandler.getInstance().getRecentTopics(15, homeQueryListener());
                }else{
                    loadTopics();
                }
            }
        });
        return rootView;
    }

    public Response.Listener<String> homeQueryListener(){
        return
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Topic> arrayList = serializeTopics(response);
                CacheTopiclist.getInstance().setRecentTopics(arrayList);
                Log.d("DD", "Home fragment topic list is taken from database.");

                loadTopics();
            }
        };
    }
    public ArrayList<Topic> serializeTopics(String resp){

        final ArrayList<Topic> resultArrayList = new ArrayList<>();

        try {
            JSONArray obj = (JSONArray) new JSONTokener(resp).nextValue();
            int topicNumber = obj.length();
            for (int i = 0; i < topicNumber; i++) {
                JSONObject tempObj = (JSONObject) obj.get(i);
                Topic tempTop = (new Gson()).fromJson(tempObj.toString(), Topic.class);
                resultArrayList.add(tempTop);
            }
        } catch (JSONException e) {}


        return resultArrayList;
    }
    public void loadTopics(){
        homeAdapter = new HomeAdapter(CacheTopiclist.getInstance().getRecentTopics());
        homeRecyclerView.setAdapter(homeAdapter);

        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("" + pos, v.toString());

                int clickedTopicId = CacheTopiclist.getInstance().getRecentTopics().get(pos).getId();
                Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", response.toString());
                        Cache.getInstance().setTopic(response);

                        Intent intent = new Intent(getActivity(), ViewTopicActivity.class);
                        startActivity(intent);
                    }
                };
                APIHandler.getInstance().getTopic("", clickedTopicId, getTopicListener);
            }
        });
    }
}
