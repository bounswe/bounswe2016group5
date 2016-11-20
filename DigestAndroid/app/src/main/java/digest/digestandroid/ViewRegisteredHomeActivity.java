package digest.digestandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.fragments.TopicCommentFragment;
import digest.digestandroid.fragments.TopicGeneralFragment;
import digest.digestandroid.fragments.TopicMaterialFragment;
import digest.digestandroid.fragments.TopicQuizFragment;

/**
 * Created by Sahin on 11/1/2016.
 */

public class ViewRegisteredHomeActivity extends AppCompatActivity {

    private Cache cache = Cache.getInstance();

    private Toolbar toolbar;
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // TODO : Adjust sizes of items in the menu
        inflater.inflate(R.menu.registered_home_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_create_topic:
                Intent intent = new Intent(getApplicationContext(), CreateTopicFragmentsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_message:
                // TODO messages
                return true;

            case R.id.action_followed:
                // TODO followed topics
                return true;

            case R.id.action_notifications:
                // TODO notifications
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO :  Use cache.getUser().getUsername() when cache is ready
        setTitle("@"+"uskudarli");

        setContentView(R.layout.activity_view_registered_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.search_view_home);
        searchView.setQueryHint("Enter a topic name..");
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        // TODO implement the functionality
                        setTitle(query);

                        searchView.setQuery("",true);
                        searchView.clearFocus();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );

        viewPager = (ViewPager) findViewById(R.id.viewpager_home);
//        setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs_home);
//        tabLayout.setupWithViewPager(viewPager);
    }











    private void setupViewPager(ViewPager viewPager) {
        ViewRegisteredHomeActivity.ViewPagerAdapter adapter = new ViewRegisteredHomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopicGeneralFragment(), "General");
        adapter.addFragment(new TopicCommentFragment(), "Comment");
        adapter.addFragment(new TopicMaterialFragment(), "Material");
        adapter.addFragment(new TopicQuizFragment(), "Quiz");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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
