package digest.digestandroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Cache;
import digest.digestandroid.CommentAdapter;
import digest.digestandroid.DividerItemDecoration;
import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.CommentUser;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.R;
import digest.digestandroid.api.APIHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicCommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicCommentFragment extends Fragment {
    protected View rootView;
    protected Topic topic = new Topic();

    private List<CommentUser> commentList;
    private RecyclerView recyclerView;
    private CommentAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TopicCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicCommentFragment newInstance(String param1, String param2) {
        TopicCommentFragment fragment = new TopicCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootView = inflater.inflate(R.layout.fragment_topic_comment, container, false);

        commentList = new ArrayList<CommentUser>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        mAdapter = new CommentAdapter(commentList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareCommentData();

        mAdapter.setOnItemClickListener(new CommentAdapter.CommentClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                CommentUser cu = commentList.get(position);
            }

            @Override
            public void onRateClick(int position, View v) {
                // TODO: 1.12.2016 tiklandigi zaman gercekten rate artir
                // TODO: 1.12.2016 suan tilaninca sekil degistiginde obje de degismis gibi oluyor. Oylama icin enabled yerine bi degisken tut ve ona gore ciz.

                if(v.isEnabled()){
                    commentList.get(position).setRate(1);
                    v.setEnabled(false);
                }
                else{
                    commentList.get(position).setRate(0);;
                    v.setEnabled(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }


    int ctr = 0;
    private void prepareCommentData() {
        ctr = 0;
        commentList.clear();
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Get username", "Success " + response);
                CommentUser commentUser = new CommentUser();
                commentUser.setBody(Cache.getInstance().getTopic().getComments().get(ctr).getBody());
                commentUser.setUsername(response);
                commentUser.setRate(Cache.getInstance().getTopic().getComments().get(ctr).getRate());

                commentList.add(commentUser);

                ctr++;
                mAdapter.notifyDataSetChanged();
                // Writing data to SharedPreferences
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Failed", "Login Failed");
            }
        };

        for(int i = 0 ; i < Cache.getInstance().getTopic().getComments().size() ; i++){
            APIHandler.getInstance().getUsername("GetUsername", Cache.getInstance().getTopic().getComments().get(i).getUid(),response,errorListener);
        }

        mAdapter.notifyDataSetChanged();
    }

    // new comment added
    public void addComment(){
        Log.e("ADDCOMMENTTT", "ADDDDDD");

        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Get username", "Success " + response);

                // Writing data to SharedPreferences
                if(response.equals("200")){
                    Cache.getInstance().getTopic().getComments().add(Cache.getInstance().getComment());
                    prepareCommentData();
                    /*
                    CommentUser commentUser = new CommentUser();
                    commentUser.setBody(Cache.getInstance().getTopic().getComments().get(ctr).getBody());
                    commentUser.setUsername(response);
                    commentUser.setRate(Cache.getInstance().getTopic().getComments().get(ctr).getRate());

                    commentList.add(commentUser);
                    */
                    //commentList.add(Cache.getInstance().getComment());
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Log.d("Failed", "Comment Failed ");
                    Toast.makeText(getActivity(), "Comment Add Failed", Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Failed", "Comment Failed");
            }
        };

        APIHandler.getInstance().addComment("Add Comment", Cache.getInstance().getComment(), response, errorListener);

    }


    public void setTopicInfo(){



    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
