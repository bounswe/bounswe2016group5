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

    private boolean isVisible;
    protected View rootView;

    public RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;

    public RegisteredHomeHomeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        Log.d("TT","On createview is passed - home.");
        rootView = inflater.inflate(R.layout.fragment_home_home, container, false);

        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        homeLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(homeLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        if(isVisible) {
            homeRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    // TODO Do not show recent topics. Show recommended settings.
                    if (CacheTopiclist.getInstance().getRecentTopics() == null) {
                        APIHandler.getInstance().getRecentTopics(15, ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Home", homeRecyclerView));

                    } else {
                        ((ViewRegisteredHomeActivity) getActivity()).loadTopics(homeRecyclerView, CacheTopiclist.getInstance().getRecentTopics());

                    }
                }
            });
        }
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            Log.d("TT","Yes - home.");
            CacheTopiclist.getInstance().setCurrentFragment("Home");
            ViewRegisteredHomeActivity.viewPager.setCurrentItem(0);
            if(homeRecyclerView != null) {
                if (CacheTopiclist.getInstance().getRecentTopics() == null) {
                    APIHandler.getInstance().getRecentTopics(15, ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Home", homeRecyclerView));

                } else {
                    ((ViewRegisteredHomeActivity) getActivity()).loadTopics(homeRecyclerView, CacheTopiclist.getInstance().getRecentTopics());

                }
            }
            // load data here
        }else{

            Log.d("TT","No - home.");
            // fragment is no longer visible
        }
    }

    //--------------------------  ABOVE IS OVERWRITE FUNCTIONS  ------------------------------------------
    //--------------------------  BELOW IS FRAGMENT FUNCTIONS  -------------------------------------------

}
