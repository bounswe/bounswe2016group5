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
    protected View rootView;

    public RecyclerView profileRecyclerView;
    private RecyclerView.LayoutManager profileLayoutManager;

    public RegisteredHomeProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home_profile, container, false);

        profileRecyclerView = (RecyclerView) rootView.findViewById(R.id.profile_recycler_view);
        profileLayoutManager = new LinearLayoutManager(getActivity());
        profileRecyclerView.setLayoutManager(profileLayoutManager);
        profileRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Be sure that current fragment is being updated properly and the query is sent when user opens the tab
        CacheTopiclist.getInstance().setCurrentFragment("Profile");
        profileRecyclerView.post(new Runnable() {
            @Override
            public void run() {

                if(CacheTopiclist.getInstance().getUserTopics() == null){
                    APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(),((ViewRegisteredHomeActivity)getActivity()).topicListQueryListenerAndLoader("Profile",profileRecyclerView));
                    Log.d("TT","2");
                }else{
                    ((ViewRegisteredHomeActivity)getActivity()).loadTopics(profileRecyclerView,CacheTopiclist.getInstance().getUserTopics());
                    Log.d("TT","3");
                }

            }
        });
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        CacheTopiclist.getInstance().setCurrentFragment("Profile");
        Log.d("TT","On start is passed - profile.");

    }


}
