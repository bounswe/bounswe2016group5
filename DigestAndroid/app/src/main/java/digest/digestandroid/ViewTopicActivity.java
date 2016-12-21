package digest.digestandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Models.QuizQuestion;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;
import digest.digestandroid.fragments.TopicCommentFragment;
import digest.digestandroid.fragments.TopicGeneralFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;
import digest.digestandroid.fragments.TopicQuizFragment;


public class ViewTopicActivity extends AppCompatActivity implements TopicGeneralFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView recyclerView1;

    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_topic);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.v("MAIN METHOD", "MAIN METHOD");


        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Get username", "Success " + response);

                // Writing data to SharedPreferences
                Cache.getInstance().setTopicCreator(response);

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);

                recyclerView1 = (RecyclerView) findViewById(R.id.related_topics_ofa_topic_recycler_view);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView1.setItemAnimator(new DefaultItemAnimator());

                loadTopics(recyclerView1,CacheTopiclist.getInstance().getRelatedTopicsOfaTopic());

            }
        };

        APIHandler.getInstance().getUsername("GET TOPIC", Cache.getInstance().getTopic().getOwner(), response);

        //topicGeneralFragment.initializeTopic(topic);

    }
/*
    public void initializeTopic(){
        TopicGeneralFragment topicGeneralFragment = (TopicGeneralFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(0);
        topicGeneralFragment.initializeInfo(topic);

        TopicCommentFragment topicCommentFragment = (TopicCommentFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(1);
        topicCommentFragment.initializeInfo(topic);

        TopicMaterialFragment topicMaterialFragment = (TopicMaterialFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(2);
        topicMaterialFragment.initializeInfo(topic);

        TopicQuizFragment topicQuizFragment = (TopicQuizFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(3);
        topicQuizFragment.initializeInfo(topic);
    }
*/



    @Override
    protected void onResume() {
        super.onResume();

        // Is there a new comment added
        // If yes, prepare comment
        if(Cache.getInstance().newComment == 1){
            Cache.getInstance().newComment = 0;
            Cache.getInstance().getComment().setUid(Cache.getInstance().getUser().getId());
            Cache.getInstance().getComment().setTid(Cache.getInstance().getTopic().getId());
            Cache.getInstance().getComment().setUcid(-1);
            Cache.getInstance().getComment().setRate(0);
            Cache.getInstance().getComment().setType(0);

            TopicCommentFragment topicCommentFragment = (TopicCommentFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(1);
            topicCommentFragment.addComment();
        }

        if(Cache.getInstance().newMaterial == 1){
            Cache.getInstance().newMaterial = 0;

            TopicMaterialFragment topicMaterialFragment = (TopicMaterialFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(2);
            topicMaterialFragment.addMaterial();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(Cache.getInstance().getTopic().getOwner() == Cache.getInstance().getUser().getId()){
            inflater.inflate(R.menu.viewtopic_creator_actionbar, menu);
        }
        else{
            inflater.inflate(R.menu.viewtopic_regular_actionbar, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_bar_title:
                intent = new Intent(getApplicationContext(), AddMaterialPopActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_comment:
                intent = new Intent(getApplicationContext(), AddCommentActivity.class);
                startActivity(intent);

                return true;

            case R.id.action_subscribe_topic:
                APIHandler.getInstance().subscribeTheTopic(Cache.getInstance().getUser(), Cache.getInstance().getTopic().getId());

                return true;

            case R.id.action_bar_quiz:
                intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
                startActivityForResult(intent, 3); //activity request id 3
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            TopicQuizFragment topicQuizFragment = (TopicQuizFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(3);
            topicQuizFragment.addQuestion();
        }

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopicGeneralFragment(), "General");
        adapter.addFragment(new TopicCommentFragment(), "Comment");
        adapter.addFragment(new TopicMaterialFragment(), "Material");
        adapter.addFragment(new TopicQuizFragment(), "Quiz");
        viewPager.setAdapter(adapter);

    }



    @Override
    public void onFragmentInteraction() {
    }

    public void solveQuiz(View view) {
        TopicQuizFragment topicQuizFragment = (TopicQuizFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(3);
        topicQuizFragment.solve();
    }

    public void loadTopics(RecyclerView currentRecyclerView,final ArrayList<Topic> currentTopicList){
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


    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
