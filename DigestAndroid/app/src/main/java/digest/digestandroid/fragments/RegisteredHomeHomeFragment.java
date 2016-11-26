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


public class RegisteredHomeHomeFragment extends Fragment {

    protected View rootView;

    private RecyclerView.Adapter homeAdapter;
    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;

    private static String Home_Fragment = "HomeFragment";
    public RegisteredHomeHomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_home, container, false);

        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        homeLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(homeLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());


        homeAdapter = new HomeAdapter(CacheTopiclist.getInstance().getRecentTopics());
        homeRecyclerView.setAdapter(homeAdapter);

        final SearchView searchView = ((ViewRegisteredHomeActivity)getActivity()).searchView;


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {


                        searchView.clearFocus();

                        final Response.Listener<String> tagListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Log.d("Here" , "heey2");
                                try{
                                    JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                                    ArrayList<Topic> arrayList = new ArrayList<Topic>();


                                    int topicNumber = obj.length();
                                    for(int i = 0 ; i < topicNumber ; i++){
                                        JSONObject tempObj = (JSONObject) obj.get(i);
                                        Topic tempTop = new Topic();

                                        Gson gson = new Gson();
                                        tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                                        arrayList.add(tempTop);
                                    }

                                    CacheTopiclist.getInstance().setTagTopics(arrayList,homeAdapter,homeRecyclerView);

                                    Log.d("Here" , "heey5");
                                }catch (JSONException e){}

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

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume(){
        super.onResume();
        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener(){
           @Override
            public void onItemClick(int pos, View v){
               Log.d(""+pos,v.toString());

               Log.i("Test1","test");

               int tid = CacheTopiclist.getInstance().getRecentTopics().get(pos).getId();

               Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                   @Override
                   public void onResponse(Topic response) {
                       Log.d("Success", "Success");
                       Log.d("Success", response.toString());
                       Cache.getInstance().setTopic(response);

                       Intent intent = new Intent(getActivity(), ViewTopicActivity.class);
                       startActivity(intent);
                   }
               };

                APIHandler.getInstance().getTopic("",tid,getTopicListener);


           }
        });
    }



}