package digest.digestandroid.fragments;

import android.app.Activity;
import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicAddQuizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicAddQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicAddQuizFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View rootView;

    private List<QuizQuestion> questionList;
    private RecyclerView recyclerView;
    private QuestionAdapter mAdapter;

    public TopicAddQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicAddQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicAddQuizFragment newInstance(String param1, String param2) {
        TopicAddQuizFragment fragment = new TopicAddQuizFragment();
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
        rootView = inflater.inflate(R.layout.fragment_topic_add_quiz, container, false);

        questionList = new ArrayList<QuizQuestion>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.question_recycler_view);
        mAdapter = new QuestionAdapter(questionList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);



        Button button_more_question = (Button) rootView.findViewById(R.id.button_more_question);
        button_more_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_more_question:

                        Intent intent = new Intent(rootView.getContext(), AddQuestionActivity.class);
                        startActivityForResult(intent, 2); //activity started with request code 2

                        break;
                }
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If add quiz activity is finished and the question is saved to cache
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            QuizQuestion currentQuestion = Cache.getInstance().getQuestion();
            questionList.add(currentQuestion);
            mAdapter.notifyDataSetChanged();
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void fillQuiz(Topic topic) {
        Quiz quiz = new Quiz((ArrayList<QuizQuestion>) questionList);
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        quizzes.add(quiz);
        topic.setQuizzes(quizzes);
    }

    /*
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
