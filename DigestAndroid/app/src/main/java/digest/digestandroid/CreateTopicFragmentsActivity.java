package digest.digestandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;
import digest.digestandroid.api.APIHandler;
import digest.digestandroid.fragments.TopicAddDescriptionFragment;
import digest.digestandroid.fragments.TopicAddMaterialFragment;
import digest.digestandroid.fragments.TopicAddQuizFragment;
import digest.digestandroid.fragments.TopicCommentFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;

public class CreateTopicFragmentsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private myViewPagerAdapter myPagerAdapter;

    private EditText edit_text_title;
    private EditText edit_text_body;
    private EditText edit_text_tags;
    private EditText edit_text_imageurl;
    private Button button_image_upload;
    private NetworkImageView image_view_topic;

    private Topic topic;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_fragments);

        //Set up toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_create_topic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up ViewPager for fragments
        viewPager = (ViewPager) findViewById(R.id.viewpager_create_topic);
        setupViewPager(viewPager);

        //Get current user and set the topic owner
        currentUser = Cache.getInstance().getUser();
        topic = new Topic();


        edit_text_title = (EditText) findViewById(R.id.topic_create_title);
        edit_text_body = (EditText) findViewById(R.id.topic_create_body);
        edit_text_tags = (EditText) findViewById(R.id.topic_create_tags);
        // = (EditText) findViewById(R.id.topic_create_imageurl);
        image_view_topic = (NetworkImageView) findViewById(R.id.topic_create_image_view);

        //Log.v("USERRRRRRRR", Cache.getInstance().getUser().toString());



    }

    //Next Fragment in view pager
    public void jumptoAddMaterial(View view) {
        viewPager.setCurrentItem(1);
    }

    //Next Fragment in view pager
    public void jumptoAddQuiz(View view) {
        viewPager.setCurrentItem(2);
    }


    //Sends topic request for created topic
    public void createTopicRequest(View view) {

        //topic.setOwner(currentUser.getId());
        topic.setOwner(currentUser.getId());

        TopicAddDescriptionFragment topicAddDescriptionFragment = (TopicAddDescriptionFragment)((myViewPagerAdapter)viewPager.getAdapter()).getItem(0);
        topicAddDescriptionFragment.fillInfo(topic);
        TopicAddMaterialFragment topicAddMaterialFragment = (TopicAddMaterialFragment) ((myViewPagerAdapter)viewPager.getAdapter()).getItem(1);
        topicAddMaterialFragment.fillMaterial(topic);
        APIHandler.getInstance().createTopic("top", topic);

        //Back to dashboard, since home redirects to create topic, is finishing this activity enough?
        Intent intent = new Intent(getBaseContext(), ViewRegisteredHomeActivity.class);
        //intent.putExtra()
        startActivity(intent);
        this.finish();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.createtopic_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        myViewPagerAdapter adapter = new myViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopicAddDescriptionFragment());
        adapter.addFragment(new TopicAddMaterialFragment());
        adapter.addFragment(new TopicAddQuizFragment());
        viewPager.setAdapter(adapter);

    }


    public void onFragmentInteraction() {
    }


    class myViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public myViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
