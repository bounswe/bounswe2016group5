package digest.digestandroid.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import digest.digestandroid.CacheTopiclist;
import digest.digestandroid.Models.Tagit;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.TopicTag;
import digest.digestandroid.Models.User;
import digest.digestandroid.R;
import digest.digestandroid.api.APIHandler;
import digest.digestandroid.api.VolleySingleton;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicAddDescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicAddDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicAddDescriptionFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    NetworkImageView mImageView;
    private String url="";
    EditText etTitle;
    EditText etBody;
    EditText etTags;

    private ArrayList<String> channel_list = new ArrayList<String>();
    private ArrayAdapter<String> list_adapter;
    MaterialBetterSpinner spinnerChannel;
    int cid = 0;

    private ArrayList<Tagit> tagitArrayList = new ArrayList<Tagit>();
    private TagitAdapter tagitAdapter = null;
    ListView listViewTagit;




    private Topic mtopic;
    private View rootView;


    public TopicAddDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopicAddDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopicAddDescriptionFragment newInstance(String param1, String param2) {
        TopicAddDescriptionFragment fragment = new TopicAddDescriptionFragment();
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

    int i = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_topic_add_description, container, false);

        Button button_upload = (Button) rootView.findViewById(R.id.button_image_upload);
        button_upload.setOnClickListener(this);
        mImageView = (NetworkImageView) rootView.findViewById(R.id.topic_create_image_view);
        mImageView.setDefaultImageResId(R.drawable.default_logo);

        etTitle = (EditText) rootView.findViewById(R.id.topic_create_title);
        etBody = (EditText) rootView.findViewById(R.id.topic_create_body);
        etTags = (EditText) rootView.findViewById(R.id.topic_create_tags);

        spinnerChannel = (MaterialBetterSpinner) rootView.findViewById(R.id.topic_spinner_channel);

        spinnerChannel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                         cid = digest.digestandroid.Cache.getInstance().getUserChannels().get(position).getId();
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> parent) {

                                                     }
                                                 });

        for(int i = 0; i < digest.digestandroid.Cache.getInstance().getUserChannels().size() ; i++)
            channel_list.add(digest.digestandroid.Cache.getInstance().getUserChannels().get(i).getName());


        // TODO: 19.12.2016  channel_list = digest.digestandroid.Cache.getInstance().getChannels();
        // TODO: 19.12.2016 Then submit channel of the topic to backend
        list_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, channel_list);
        list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChannel.setAdapter(list_adapter);


        Tagit country = new Tagit("AFG","Afghanistan",false);
        tagitArrayList.add(country);
        country = new Tagit("ALB","Albania",true);
        tagitArrayList.add(country);
        country = new Tagit("DZA","Algeria",false);
        tagitArrayList.add(country);
        country = new Tagit("ASM","American Samoa",true);
        tagitArrayList.add(country);
        country = new Tagit("AND","Andorra",true);
        tagitArrayList.add(country);
        country = new Tagit("AGO","Angola",false);
        tagitArrayList.add(country);
        country = new Tagit("AIA","Anguilla",false);
        tagitArrayList.add(country);


        tagitAdapter = new TagitAdapter(getContext(), R.layout.tagit_checkbox, tagitArrayList);
        listViewTagit = (ListView) rootView.findViewById(R.id.list_view_tagit);
        listViewTagit.setAdapter(tagitAdapter);

        listViewTagit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Tagit tagit = (Tagit) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),
                        "Clicked on Row: " + tagit.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });



        return rootView;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_image_upload:

                final ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
                //final String url = ((EditText) getView().findViewById(R.id.topic_create_imageurl)).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter your topic image link");

                // Set up the input
                final EditText input = new EditText(getActivity());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        url = input.getText().toString();


                /*mImageLoader.get(url, ImageLoader.getImageListener(mImageView,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));*/

                        mImageView.setImageUrl(url, mImageLoader);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                break;

            case R.id.button_tagit:

        }
    }



    //Sets topic title, body, image url and tag list
    // returns channel id
    public int fillInfo(Topic topic){
        mtopic = topic;
        String mtitle = etTitle.getText().toString();
        String mbody = etBody.getText().toString();
        String mtags = etTags.getText().toString();

        ArrayList<TopicTag> tags = new ArrayList<TopicTag>();

        List<String> tagList = Arrays.asList(mtags.split(","));
        for (String tg : tagList) {
            tags.add(new TopicTag(tg));
        }

        Log.d("tag", tagList.toString());

        mtopic.setHeader(mtitle);
        mtopic.setBody(mbody);
        mtopic.setTags(tags);
        mtopic.setImage(url);

        return cid;
    }



    private class TagitAdapter extends ArrayAdapter<Tagit> {

        private ArrayList<Tagit> tagitList;

        public TagitAdapter(Context context, int textViewResourceId,
                               ArrayList<Tagit> tagitList) {

            super(context, textViewResourceId, tagitList);
            this.tagitList = new ArrayList<Tagit>();
            this.tagitList.addAll(tagitList);
        }

        private class ViewHolder {
            CheckBox tagName;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tagit_checkbox, parent, false);

                holder = new ViewHolder();
                holder.tagName = (CheckBox) convertView.findViewById(R.id.checkbox_tagit);
                convertView.setTag(holder);

                holder.tagName.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Tagit tagit = (Tagit) cb.getTag();
                        Toast.makeText(getContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        tagit.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Tagit tagit = tagitList.get(position);
            holder.tagName.setText(tagit.getName());
            holder.tagName.setChecked(tagit.isSelected());
            holder.tagName.setTag(tagit);

            return convertView;

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
