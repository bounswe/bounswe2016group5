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
    private boolean isVisible;
    protected View rootView;

    public RecyclerView followedRecyclerView;
    public RecyclerView followedChannelsRecyclerView;
    private RecyclerView.ItemAnimator followedItemAnimator;

    private RecyclerView.LayoutManager followedLayoutManager;

    public RegisteredHomeFollowedFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.d("TT","On createview is passed - followed.");
        rootView = inflater.inflate(R.layout.fragment_home_followed, container, false);

        followedRecyclerView = (RecyclerView) rootView.findViewById(R.id.followed_recycler_view);
        followedChannelsRecyclerView = (RecyclerView) rootView.findViewById(R.id.followed_channel_recycler_view);


        followedItemAnimator = new DefaultItemAnimator();

        followedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followedRecyclerView.setItemAnimator(followedItemAnimator);

        followedChannelsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followedChannelsRecyclerView.setItemAnimator(followedItemAnimator);

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        if(isVisible) {

            followedRecyclerView.post(new Runnable() {
                @Override
                public void run() {


                    if (CacheTopiclist.getInstance().getFollowedTopics() == null) {
                        APIHandler.getInstance().getFollowedTopics(Cache.getInstance().getUser(), ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Followed", followedRecyclerView));
                        APIHandler.getInstance().getChannelsOfSubscribedTopics(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Followed2",followedChannelsRecyclerView));

                    } else {
                        ((ViewRegisteredHomeActivity) getActivity()).loadTopics(followedRecyclerView, CacheTopiclist.getInstance().getFollowedTopics());
                        ((ViewRegisteredHomeActivity) getActivity()).loadChannels(followedChannelsRecyclerView, CacheTopiclist.getInstance().getFollowedChannels());

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
            CacheTopiclist.getInstance().setCurrentFragment("Followed");
            ViewRegisteredHomeActivity.viewPager.setCurrentItem(2);
            Log.d("TT","Yes - followed.");
            if(followedRecyclerView != null){
                if(CacheTopiclist.getInstance().getFollowedTopics() == null){
                    APIHandler.getInstance().getFollowedTopics(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity)getActivity()).topicListQueryListenerAndLoader("Followed",followedRecyclerView));
                    APIHandler.getInstance().getChannelsOfSubscribedTopics(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Followed2",followedChannelsRecyclerView));

                }else{
                    ((ViewRegisteredHomeActivity)getActivity()).loadTopics(followedRecyclerView,CacheTopiclist.getInstance().getFollowedTopics());
                    ((ViewRegisteredHomeActivity) getActivity()).loadChannels(followedChannelsRecyclerView, CacheTopiclist.getInstance().getFollowedChannels());

                }
            }
            // load data here
        }else{

            Log.d("TT","No - followed.");
            // fragment is no longer visible
        }
    }


}
