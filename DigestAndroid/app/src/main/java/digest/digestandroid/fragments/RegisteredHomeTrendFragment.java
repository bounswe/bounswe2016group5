package digest.digestandroid.fragments;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TextView;

import digest.digestandroid.Cache;
import digest.digestandroid.CacheTopiclist;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.R;
import digest.digestandroid.ViewRegisteredHomeActivity;
import digest.digestandroid.api.APIHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicGeneralFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicGeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisteredHomeTrendFragment extends Fragment {

    private boolean isVisible;
    protected View rootView;

    public RecyclerView trendingRecyclerView;
    private RecyclerView.LayoutManager trendingLayoutManager;

    public RegisteredHomeTrendFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {



        rootView = inflater.inflate(R.layout.fragment_home_trend, container, false);

        trendingRecyclerView = (RecyclerView) rootView.findViewById(R.id.trending_recycler_view);
        trendingLayoutManager = new LinearLayoutManager(getActivity());
        trendingRecyclerView.setLayoutManager(trendingLayoutManager);
        trendingRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        if(isVisible) {

            trendingRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (CacheTopiclist.getInstance().getTrendingTopics() == null) {
                        APIHandler.getInstance().getTrendingTopics(Cache.getInstance().getUser(), ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Trending", trendingRecyclerView));

                    } else {
                        ((ViewRegisteredHomeActivity) getActivity()).loadTopics(trendingRecyclerView, CacheTopiclist.getInstance().getTrendingTopics());

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
            Log.d("TT","Yes - trend.");
            CacheTopiclist.getInstance().setCurrentFragment("Trending");
            ViewRegisteredHomeActivity.viewPager.setCurrentItem(1);
            if(trendingRecyclerView != null) {
                if (CacheTopiclist.getInstance().getTrendingTopics() == null) {
                    APIHandler.getInstance().getTrendingTopics(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity)getActivity()).topicListQueryListenerAndLoader("Trending",trendingRecyclerView));

                } else {
                    ((ViewRegisteredHomeActivity)getActivity()).loadTopics(trendingRecyclerView,CacheTopiclist.getInstance().getTrendingTopics());

                }
            }
            // load data here
        }else{

            Log.d("TT","No - trend.");
            // fragment is no longer visible
        }
    }
}
