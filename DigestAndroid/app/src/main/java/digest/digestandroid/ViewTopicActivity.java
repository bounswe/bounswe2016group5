package digest.digestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;
import digest.digestandroid.fragments.TopicCommentFragment;
import digest.digestandroid.fragments.TopicGeneralFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;
import digest.digestandroid.fragments.TopicQuizFragment;


public class ViewTopicActivity extends AppCompatActivity implements TopicGeneralFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_topic);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.v("MAIN METHOD", "MAIN METHOD");

        // TODO: 19.11.2016 Get topic info from database


        User user = new User();
        user.setUsername("Burki");
        topic = new Topic();
        topic.setOwner(user.getId());
        topic.setImage_url("http://i.dailymail.co.uk/i/pix/2016/04/12/23/3319F89C00000578-3536787-image-m-19_1460498410943.jpg");
        topic.setTitle("TITLEEEEEE");
        topic.setBody("HEBELE HUBELE CCOK GUZEL BI TEXT BU");
        topic.setMaterials(new ArrayList<String>());
        topic.getMaterials().add("111111111111111");
        topic.getMaterials().add("22222222222222222");
        topic.getMaterials().add("333333333333");

        TopicGeneralFragment topicGeneralFragment = (TopicGeneralFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(0);
        topicGeneralFragment.initializeInfo(topic);

        TopicCommentFragment topicCommentFragment = (TopicCommentFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(1);
        topicCommentFragment.initializeInfo(topic);

        TopicMaterialFragment topicMaterialFragment = (TopicMaterialFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(2);
        topicMaterialFragment.initializeInfo(topic);

        TopicQuizFragment topicQuizFragment = (TopicQuizFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(3);
        topicQuizFragment.initializeInfo(topic);




        //topicGeneralFragment.initializeTopic(topic);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Is there a new comment added
        // If yes, prepare comment
        if(Cache.getInstance().newComment == 1){
            Cache.getInstance().newComment = 0;
            Cache.getInstance().getComment().setUsername("111111111");
            Cache.getInstance().getComment().setRate(123);
            TopicCommentFragment topicCommentFragment = (TopicCommentFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getItem(1);
            topicCommentFragment.addComment(Cache.getInstance().getComment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewtopic_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_comment:
                Intent intent = new Intent(getApplicationContext(), AddCommentActivity.class);
                startActivity(intent);
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

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
