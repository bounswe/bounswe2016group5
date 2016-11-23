package digest.digestandroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Cache;
import digest.digestandroid.CommentAdapter;
import digest.digestandroid.DividerItemDecoration;
import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.R;


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

    private List<Comment> commentList = new ArrayList<Comment>();
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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        mAdapter = new CommentAdapter(commentList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareCommentData();

        return rootView;
    }

    public void thumbsUp() {
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.thumbsUp);
        // TODO: 23.11.2016   hangi commente dokunuldugunu ve hangi commentin degistirilecegini bul adapter ve comment lsit yardimiyla
        TextView textView = (TextView) rootView.findViewById(R.id.comment_rate);
        if(imageButton.isEnabled()){
            topic.setRating(topic.getRating()+1);
            textView.setText(String.valueOf(topic.getRating()));
            imageButton.setEnabled(false);
        }
        else{
            topic.setRating(topic.getRating()-1);
            textView.setText(String.valueOf(topic.getRating()));
            imageButton.setEnabled(true);
        }

    }


    private void prepareCommentData() {
        Comment comment = new Comment(0,20,"FIRST","This is a good first comment for my belloved people i think iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(1,23,"SECOND","asdasfasf",-98);
        commentList.add(comment);

        comment = new Comment(2,26,"THIRD","1231242354236346a",1);
        commentList.add(comment);

        comment = new Comment(4,534,"FORTH","This is a good fir24534535hink iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(2,12,"FIFTH","This is a good first comment fafdafddasfd people i think iwwnat to make it long so aslkdjaslkfha",0);
        commentList.add(comment);

        comment = new Comment(6,12,"SICNT AHSNFHUI","This is a good fdfgdhse i think iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(2,20,"IHEFSD ASD AFD","This is a good first comment for my belloved people i think iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(1,20,"123f asf","This is a good first comm5235235wwnat to make it long so aslkdjaslkfha",222);
        commentList.add(comment);

        comment = new Comment(0,20,"e1dsa 32f","This is a good firdfhhsdmake it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(0,20,"dsa 2r1df ","This is a good first comment for my belloved people i think iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        comment = new Comment(0,20,"qweasd asd","This is a good first comment for my belloved people i think iwwnat to make it long so aslkdjaslkfha",52);
        commentList.add(comment);

        mAdapter.notifyDataSetChanged();
    }

    // new comment added
    public void addComment(Comment comment){
        commentList.add(comment);
        mAdapter.notifyDataSetChanged();
    }


    public void initializeInfo(Topic topic){
        this.topic.setTitle(topic.getTitle());
        this.topic.setBody(topic.getBody());
        this.topic.setOwner(topic.getOwner());
        this.topic.setImage_url(topic.getImage_url());
        this.topic.setRating(topic.getRating());
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
