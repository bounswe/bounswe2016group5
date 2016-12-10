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

import digest.digestandroid.Cache;
import digest.digestandroid.CacheTopiclist;
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
public class RegisteredHomeFollowedFragment extends Fragment {
    protected View rootView;

    public RecyclerView followedRecyclerView;
    private RecyclerView.LayoutManager followedLayoutManager;

    public RegisteredHomeFollowedFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_followed, container, false);

        followedRecyclerView = (RecyclerView) rootView.findViewById(R.id.followed_recycler_view);
        followedLayoutManager = new LinearLayoutManager(getActivity());
        followedRecyclerView.setLayoutManager(followedLayoutManager);
        followedRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Log.d("TT","4");
        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        CacheTopiclist.getInstance().setCurrentFragment("Followed");
        followedRecyclerView.post(new Runnable() {
            @Override
            public void run() {
            Log.d("TT","5");

            ((ViewRegisteredHomeActivity)getActivity()).loadTopics(followedRecyclerView,CacheTopiclist.getInstance().getFollowedTopics());

            }
        });
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        CacheTopiclist.getInstance().setCurrentFragment("Followed");
        Log.d("TT","On start is passed - followed.");
    }


}
