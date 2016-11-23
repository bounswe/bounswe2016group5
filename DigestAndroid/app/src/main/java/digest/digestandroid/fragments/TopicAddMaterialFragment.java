package digest.digestandroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicAddMaterialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicAddMaterialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicAddMaterialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Topic mtopic;
    private View rootView;
    private ListView mListView;


    private ArrayList<String> material_list = new ArrayList<String>();
    private ArrayAdapter<String> list_adapter;




    public TopicAddMaterialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicAddMaterialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicAddMaterialFragment newInstance(String param1, String param2) {
        TopicAddMaterialFragment fragment = new TopicAddMaterialFragment();
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
        rootView = inflater.inflate(R.layout.fragment_topic_add_material, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_material);
        list_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, material_list);
        mListView.setAdapter(list_adapter);


        Button button_more_material = (Button) rootView.findViewById(R.id.button_more_material);
        button_more_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_more_material:

                        String mmaterial = ((EditText) rootView.findViewById(R.id.topic_create_material)).getText().toString();
                        addtoMaterialList(mmaterial);
                        ((EditText) rootView.findViewById(R.id.topic_create_material)).setText("");

                        break;
                }
            }
        });


        return rootView;
    }

    public void fillMaterial(Topic topic) {
        mtopic = topic;
        mtopic.setMedia(material_list);
    }

    protected void addtoMaterialList(String material) {
        if(!material.isEmpty()) {
            material_list.add(0, material);
            list_adapter.notifyDataSetChanged();
        }
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

    //private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter>
}
