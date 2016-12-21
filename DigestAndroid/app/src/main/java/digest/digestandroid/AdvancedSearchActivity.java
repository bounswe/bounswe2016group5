package digest.digestandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;

/**
 * Created by sahin on 17.12.2016.
 */

public class AdvancedSearchActivity extends AppCompatActivity {

    private int year, month, day;
    private TextView initDate;
    private TextView finishDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO width value of the layout is hard coded. !!
        setContentView(R.layout.activity_advanced_search);

        final String previous = getIntent().getStringExtra("previous");

        Button addButton = ((Button)findViewById(R.id.advanced_search_search));


        initDate = (TextView)findViewById(R.id.Adv_sch_initial_date_button);
        finishDate = (TextView)findViewById(R.id.Adv_sch_finish_date_button);
        (initDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "Select starting date",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        (finishDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(998);
                Toast.makeText(getApplicationContext(), "Select ending date",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String init_date = String.valueOf(((TextView)findViewById(R.id.Adv_sch_initial_date_button)).getText().toString());
                String end_date = String.valueOf(((TextView)findViewById(R.id.Adv_sch_finish_date_button)).getText().toString());


                boolean hasChannel = Boolean.valueOf(((CheckBox)findViewById(R.id.Adv_sch_channel_button)).isChecked());
                boolean hasMaterial = Boolean.valueOf(((CheckBox)findViewById(R.id.Adv_sch_material_button)).isChecked());
                boolean hasQuiz = Boolean.valueOf(((CheckBox)findViewById(R.id.Adv_sch_quiz_button)).isChecked());

                int minComment = Integer.parseInt(((EditText)findViewById(R.id.Adv_sch_comment_number_button)).getText().toString());

                Log.d("Adv_sch",""+init_date+"-"+end_date+"-"+hasChannel+"-"+hasMaterial+"-"+hasQuiz+"-"+minComment);


                String searchText =  String.valueOf(((EditText)findViewById(R.id.advanced_search_text)).getText());

                final Response.Listener<String> tagListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<Topic> arrayList = ViewRegisteredHomeActivity.serializeTopicsFromJson(response);
                        CacheTopiclist.getInstance().setTagTopics(arrayList);
                        finish();

                        if(previous.equals("home")) {
                            Intent intent = new Intent(getApplicationContext(), ViewSearchActivity.class);
                            startActivity(intent);
                        }else if(previous.equals("search")){
                            loadTopics(ViewSearchActivity.searchRecyclerView,arrayList);
                        }
                    }
                };

                APIHandler.getInstance().advancedSearchApi(init_date,end_date,hasChannel,hasMaterial,hasQuiz,minComment,searchText,tagListener);

            }
        });

    }

    public void loadTopics(RecyclerView currentRecyclerView, final ArrayList<Topic> currentTopicList){
        Log.d("TT","9");
        RecyclerView.Adapter homeAdapter = new HomeAdapter(currentTopicList);
        currentRecyclerView.setAdapter(homeAdapter);

        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("" + pos, v.toString());

                final int clickedTopicId = currentTopicList.get(pos).getId();
                Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", response.toString());
                        Cache.getInstance().setTopic(response);

                        Response.Listener<String> getRelatedTopicsListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                final ArrayList<Topic> arrayList = ViewRegisteredHomeActivity.serializeTopicsFromJson(response);
                                CacheTopiclist.getInstance().setRelatedTopicsOfaTopic(arrayList);

                                Intent intent = new Intent(getApplicationContext(), ViewTopicActivity.class);
                                startActivity(intent);
                            }
                        };

                        APIHandler.getInstance().getRelatedTopicsOfaTopic(clickedTopicId,getRelatedTopicsListener);
                    }
                };
                APIHandler.getInstance().getTopic("", clickedTopicId, getTopicListener);
            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }else if (id == 998) {
            return new DatePickerDialog(this,
                    myDateListener1, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        Log.d("aadate",(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)).toString());
        initDate.setText((new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)));
    }

    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate1(arg1, arg2+1, arg3);
                }
            };

    private void showDate1(int year, int month, int day) {
        Log.d("aadate",(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)).toString());
        finishDate.setText((new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year)));
    }

}
