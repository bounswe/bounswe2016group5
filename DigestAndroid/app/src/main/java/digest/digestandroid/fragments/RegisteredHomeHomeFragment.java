package digest.digestandroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import java.util.List;
import java.util.StringTokenizer;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;
import digest.digestandroid.R;
import digest.digestandroid.HomeAdapter;


public class RegisteredHomeHomeFragment extends Fragment {

    protected View rootView;

    private ArrayList<Topic> homeTopics = new ArrayList<Topic>();
    private RecyclerView.Adapter homeAdapter;

    private RecyclerView homeRecyclerView;
    private RecyclerView.LayoutManager homeLayoutManager;

    private static String Home_Fragment = "HomeFragment";


    public RegisteredHomeHomeFragment() {}

    public RegisteredHomeHomeFragment getInstance(){

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_home, container, false);

        homeRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        homeLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(homeLayoutManager);
        homeRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //--------------
        Topic topic;
        User user = new User();
        user.setUsername("Burki");
        topic = new Topic();
        //topic.setOwner(user);
        topic.setImage("http://i.dailymail.co.uk/i/pix/2016/04/12/23/3319F89C00000578-3536787-image-m-19_1460498410943.jpg");
        topic.setHeader("TITLEEEEEE");
        topic.setBody("HEBELE HUBELE CCOK GUZEL BI TEXT BU");
        topic.setMedia(new ArrayList<String>());
        topic.getMedia().add("111111111111111");
        topic.getMedia().add("22222222222222222");
        topic.getMedia().add("333333333333");
        homeTopics.add(topic);
        //--------------

        homeAdapter = new HomeAdapter(homeTopics);
        homeRecyclerView.setAdapter(homeAdapter);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // TODO initialize homeTopics , implement onResume, implement adapter


    @Override
    public void onResume(){
        super.onResume();
        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener(){
           @Override
            public void onItemClick(int pos, View v){

           }
        });
    }










}
