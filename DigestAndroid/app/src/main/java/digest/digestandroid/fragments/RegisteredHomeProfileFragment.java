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
import android.widget.TextView;

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
public class RegisteredHomeProfileFragment extends Fragment {

    private boolean isVisible;
    protected View rootView;

    public RecyclerView profileRecyclerView;
    public RecyclerView profileChannelsRecyclerView;
    private RecyclerView.ItemAnimator profileItemAnimator;

    public RegisteredHomeProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.d("TT","On createview is passed - profile.");
        rootView = inflater.inflate(R.layout.fragment_home_profile, container, false);

        ((TextView)(rootView.findViewById(R.id.profile_user_info_2))).setText(" "+Cache.getInstance().getUser().getUsername());
        ((TextView)(rootView.findViewById(R.id.profile_user_info_4))).setText(" "+Cache.getInstance().getUser().getEmail());
        ((TextView)(rootView.findViewById(R.id.profile_user_info_6))).setText(" "+Cache.getInstance().getUser().getFirst_name());
        ((TextView)(rootView.findViewById(R.id.profile_user_info_8))).setText(" "+Cache.getInstance().getUser().getLast_name());

        profileRecyclerView = (RecyclerView) rootView.findViewById(R.id.profile_recycler_view);
        profileChannelsRecyclerView = (RecyclerView) rootView.findViewById(R.id.profile_channel_recycler_view);


        profileItemAnimator = new DefaultItemAnimator();


        profileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileRecyclerView.setItemAnimator(profileItemAnimator);

        profileChannelsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileChannelsRecyclerView.setItemAnimator(profileItemAnimator);

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        if(isVisible) {

            profileRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    if (CacheTopiclist.getInstance().getUserTopics() == null) {
                        APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(), ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Profile", profileRecyclerView));
                        APIHandler.getInstance().getChannelsOfUser(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Profile2",profileChannelsRecyclerView));
                    } else {
                        ((ViewRegisteredHomeActivity) getActivity()).loadTopics(profileRecyclerView, CacheTopiclist.getInstance().getUserTopics());
                        ((ViewRegisteredHomeActivity)getActivity()).loadChannels(profileChannelsRecyclerView,CacheTopiclist.getInstance().getUserChannels(),false);
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
            Log.d("TT","Yes - profile.");
            CacheTopiclist.getInstance().setCurrentFragment("Profile");
            ViewRegisteredHomeActivity.viewPager.setCurrentItem(3);
            if(profileRecyclerView != null){
                if(CacheTopiclist.getInstance().getUserTopics() == null){
                    APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity)getActivity()).topicListQueryListenerAndLoader("Profile",profileRecyclerView));
                    APIHandler.getInstance().getChannelsOfUser(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Profile2",profileChannelsRecyclerView));

                }else{
                    ((ViewRegisteredHomeActivity)getActivity()).loadTopics(profileRecyclerView,CacheTopiclist.getInstance().getUserTopics());
                    ((ViewRegisteredHomeActivity)getActivity()).loadChannels(profileChannelsRecyclerView,CacheTopiclist.getInstance().getUserChannels(),false);

                }

            }
            // load data here
        }else{

            Log.d("TT","No - profile.");
            // fragment is no longer visible
        }
    }

}
