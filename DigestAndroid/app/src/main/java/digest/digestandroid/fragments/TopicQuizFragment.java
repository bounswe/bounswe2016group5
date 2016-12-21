package digest.digestandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.AddQuestionActivity;
import digest.digestandroid.Cache;
import digest.digestandroid.DividerItemDecoration;
import digest.digestandroid.Models.Quiz;
import digest.digestandroid.Models.QuizQuestion;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.QuestionAdapter;
import digest.digestandroid.R;
import digest.digestandroid.api.APIHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicQuizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicQuizFragment extends Fragment {
    protected View rootView;
    protected Topic topic = new Topic();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<QuizQuestion> questionList;
    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;

    public TopicQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicQuizFragment newInstance(String param1, String param2) {
        TopicQuizFragment fragment = new TopicQuizFragment();
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
        rootView = inflater.inflate(R.layout.fragment_topic_quiz, container, false);


        questionList = new ArrayList<QuizQuestion>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.question_recycler_view2);
        mAdapter = new QuestionAdapter(questionList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        prepareQuiz();


        return rootView;
    }

    public void initializeInfo(Topic topic){
        this.topic.setHeader(topic.getHeader());
        this.topic.setBody(topic.getBody());
        this.topic.setOwner(topic.getOwner());
        this.topic.setImage(topic.getImage());
        this.topic.setRating(topic.getRating());
    }

    public void setTopicInfo(){


    }

    public void prepareQuiz() {

        //If there is a quiz to view
        if(Cache.getInstance().getTopic().getQuizzes().size()>0) {
            for(Quiz eachQuiz : Cache.getInstance().getTopic().getQuizzes()){
                for (QuizQuestion q : eachQuiz.getQuestions()) {
                    questionList.add(q);
                    mAdapter.updateAnswerList();
                }
                mAdapter.notifyDataSetChanged();
            }
            //Quiz quiz = Cache.getInstance().getTopic().getQuizzes().get(0); // Assumed there is only one quiz

        }
    }

    //Adds the question in the cache to the topic
    public void addQuestion() {
        QuizQuestion currentQuestion;

        if((currentQuestion = Cache.getInstance().getQuestion()) != null) {
            Log.d("CURRENT ANSWERS", Cache.getInstance().getQuestion().getAnswers().toString());
            questionList.add(currentQuestion);
            mAdapter.updateAnswerList();
            mAdapter.notifyDataSetChanged();


            Response.Listener<String> response = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Get username", "Success " + response);

                    // Writing data to SharedPreferences
                    if(response.equals("200")){
                        //Cache.getInstance().getTopic().getComments().add(Cache.getInstance().getComment());
                        //prepareCommentData();
                        //Cache.getInstance().setQuestion(null);

                    }
                    else {
                        Log.d("Failed", "Question Failed ");
                        Toast.makeText(getActivity(), "Question Add Failed", Toast.LENGTH_LONG).show();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Failed", "Question Failed");
                }
            };


            ArrayList<QuizQuestion> requestQuestions = new ArrayList<QuizQuestion>();
            requestQuestions.add(Cache.getInstance().getQuestion());
            Quiz requestQuiz = new Quiz(requestQuestions);
            requestQuiz.setName(""+Cache.getInstance().getTopic().getHeader()+" Quiz");
            Log.d("--QUIZ",requestQuiz.getName());
            APIHandler.getInstance().addQuiz("Add Quiz", requestQuiz, response, errorListener);

        }

    }

    public void solve() {
        int nRightAnswers = mAdapter.matchAnswers();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Your score is: "+nRightAnswers+"/"+questionList.size());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*questionList.clear();
                        mAdapter.notifyDataSetChanged();

                        prepareQuiz();*/
                        recyclerView.setAdapter(null);
                        mAdapter = new QuestionAdapter(questionList);
                        mAdapter.setAnswerList();
                        recyclerView.setAdapter(mAdapter);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
