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
public class RegisteredHomeProfileFragment extends Fragment {

    private boolean isVisible;
    protected View rootView;

    public RecyclerView profileRecyclerView;
    private RecyclerView.LayoutManager profileLayoutManager;

    public RegisteredHomeProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        Log.d("TT","On createview is passed - profile.");
        rootView = inflater.inflate(R.layout.fragment_home_profile, container, false);

        profileRecyclerView = (RecyclerView) rootView.findViewById(R.id.profile_recycler_view);
        profileLayoutManager = new LinearLayoutManager(getActivity());
        profileRecyclerView.setLayoutManager(profileLayoutManager);
        profileRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        if(isVisible) {

            profileRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    if (CacheTopiclist.getInstance().getUserTopics() == null) {
                        APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(), ((ViewRegisteredHomeActivity) getActivity()).topicListQueryListenerAndLoader("Profile", profileRecyclerView));

                    } else {
                        ((ViewRegisteredHomeActivity) getActivity()).loadTopics(profileRecyclerView, CacheTopiclist.getInstance().getUserTopics());

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

                }else{
                    ((ViewRegisteredHomeActivity)getActivity()).loadTopics(profileRecyclerView,CacheTopiclist.getInstance().getUserTopics());

                }

            }
            // load data here
        }else{

            Log.d("TT","No - profile.");
            // fragment is no longer visible
        }
    }

}
