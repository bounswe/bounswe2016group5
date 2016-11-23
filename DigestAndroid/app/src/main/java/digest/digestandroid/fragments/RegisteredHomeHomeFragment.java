package digest.digestandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import digest.digestandroid.CacheTopiclist;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;
import digest.digestandroid.R;
import digest.digestandroid.HomeAdapter;
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

        homeAdapter = new HomeAdapter(CacheTopiclist.getInstance().getUserTopics());
        homeRecyclerView.setAdapter(homeAdapter);

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


               //Intent intent = new Intent(getApplicationContext(), CreateTopicFragmentsActivity.class);
               //startActivity(intent);


               Log.i("Test1","test");
           }
        });
    }



}
