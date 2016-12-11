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

        Log.d("TT","On createview is passed - followed.");
        rootView = inflater.inflate(R.layout.fragment_home_followed, container, false);

        followedRecyclerView = (RecyclerView) rootView.findViewById(R.id.followed_recycler_view);
        followedLayoutManager = new LinearLayoutManager(getActivity());
        followedRecyclerView.setLayoutManager(followedLayoutManager);
        followedRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        followedRecyclerView.post(new Runnable() {
            @Override
            public void run() {


            if(CacheTopiclist.getInstance().getFollowedTopics() == null){
                APIHandler.getInstance().getFollowedTopics(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity)getActivity()).topicListQueryListenerAndLoader("Followed",followedRecyclerView));

            }else{
                ((ViewRegisteredHomeActivity)getActivity()).loadTopics(followedRecyclerView,CacheTopiclist.getInstance().getFollowedTopics());

            }

            }
        });
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            CacheTopiclist.getInstance().setCurrentFragment("Followed");
            ViewRegisteredHomeActivity.viewPager.setCurrentItem(2);
            Log.d("TT","Yes - followed.");
            // load data here
        }else{

            Log.d("TT","No - followed.");
            // fragment is no longer visible
        }
    }


}
