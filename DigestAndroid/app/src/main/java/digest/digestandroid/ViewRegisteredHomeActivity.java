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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import digest.digestandroid.Models.Topic;
import digest.digestandroid.api.APIHandler;
import digest.digestandroid.fragments.RegisteredHomeChannelFragment;
import digest.digestandroid.fragments.RegisteredHomeHomeFragment;
import digest.digestandroid.fragments.RegisteredHomeProfileFragment;
import digest.digestandroid.fragments.RegisteredHomeTrendFragment;
/**
 * Created by Sahin on 11/1/2016.
 */

public class ViewRegisteredHomeActivity extends AppCompatActivity {

    private Cache cache = Cache.getInstance();

    // TODO adjust the heights and sizes
    private Toolbar toolbar;
    public SearchView searchView;
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
                // TODO fix the create topic transition problem
                Intent intent = new Intent(getApplicationContext(), CreateTopicFragmentsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_refresh:

                final Response.Listener<String> userTopicsResponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                            ArrayList<Topic> arrayList = new ArrayList<Topic>();


                            int topicNumber = obj.length();
                            for(int i = 0 ; i < topicNumber ; i++){
                                JSONObject tempObj = (JSONObject) obj.get(i);
                                Topic tempTop = new Topic();

                                Gson gson = new Gson();
                                tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                                arrayList.add(tempTop);
                            }

                            CacheTopiclist.getInstance().setUserTopics(arrayList);





                            viewPager = (ViewPager) findViewById(R.id.viewpager_home);
                            defineViewPager(viewPager);

                            tabLayout = (TabLayout) findViewById(R.id.tabs_home);
                            tabLayout.setupWithViewPager(viewPager);
                            loadViewPager();






                        }catch (JSONException e){}

                    }
                };
                Response.Listener<String> recentTopicsResponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                            ArrayList<Topic> arrayList = new ArrayList<Topic>();


                            int topicNumber = obj.length();
                            for(int i = 0 ; i < topicNumber ; i++){
                                JSONObject tempObj = (JSONObject) obj.get(i);
                                Topic tempTop = new Topic();

                                Gson gson = new Gson();
                                tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                                arrayList.add(tempTop);
                            }

                            CacheTopiclist.getInstance().setRecentTopics(arrayList);





                            APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(),userTopicsResponseListener);







                        }catch (JSONException e){}

                    }
                };

                APIHandler.getInstance().getRecentTopics(15,recentTopicsResponseListener);


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

        setTitle("@"+cache.getUser().getUsername());

        setContentView(R.layout.activity_view_registered_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.search_view_home);
        searchView.setQueryHint("Enter a topic name..");

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {


                        searchView.clearFocus();

                        final Response.Listener<String> tagListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try{
                                    JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                                    ArrayList<Topic> arrayList = new ArrayList<Topic>();


                                    int topicNumber = obj.length();
                                    for(int i = 0 ; i < topicNumber ; i++){
                                        JSONObject tempObj = (JSONObject) obj.get(i);
                                        Topic tempTop = new Topic();

                                        Gson gson = new Gson();
                                        tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                                        arrayList.add(tempTop);
                                    }

                                    CacheTopiclist.getInstance().setTagTopics(arrayList);

                                    Intent intent = new Intent( getApplicationContext(), ViewSearchActivity.class);
                                    startActivity(intent);

                                }catch (JSONException e){}

                            }
                        };

                        APIHandler.getInstance().searchWithTag(query,tagListener);

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );




        final Response.Listener<String> userTopicsResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                    ArrayList<Topic> arrayList = new ArrayList<Topic>();


                    int topicNumber = obj.length();
                    for(int i = 0 ; i < topicNumber ; i++){
                        JSONObject tempObj = (JSONObject) obj.get(i);
                        Topic tempTop = new Topic();

                        Gson gson = new Gson();
                        tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                        arrayList.add(tempTop);
                    }

                    CacheTopiclist.getInstance().setUserTopics(arrayList);





                    viewPager = (ViewPager) findViewById(R.id.viewpager_home);
                    defineViewPager(viewPager);

                    tabLayout = (TabLayout) findViewById(R.id.tabs_home);
                    tabLayout.setupWithViewPager(viewPager);
                    loadViewPager();






                }catch (JSONException e){}

            }
        };
        Response.Listener<String> recentTopicsResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                    ArrayList<Topic> arrayList = new ArrayList<Topic>();


                    int topicNumber = obj.length();
                    for(int i = 0 ; i < topicNumber ; i++){
                        JSONObject tempObj = (JSONObject) obj.get(i);
                        Topic tempTop = new Topic();

                        Gson gson = new Gson();
                        tempTop = gson.fromJson(tempObj.toString(),Topic.class);
                        arrayList.add(tempTop);
                    }

                    CacheTopiclist.getInstance().setRecentTopics(arrayList);





                    APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(),userTopicsResponseListener);







                }catch (JSONException e){}

            }
        };

        APIHandler.getInstance().getRecentTopics(15,recentTopicsResponseListener);

    }


    private void defineViewPager(ViewPager viewPager) {
        ViewRegisteredHomeActivity.HomePagerAdapter adapter = new ViewRegisteredHomeActivity.HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredHomeHomeFragment(), "Home");
        adapter.addFragment(new RegisteredHomeTrendFragment(), "Trending");
        adapter.addFragment(new RegisteredHomeChannelFragment(), "Channels");
        adapter.addFragment(new RegisteredHomeProfileFragment(), "Profile");
        viewPager.setAdapter(adapter);
    }

    private void loadViewPager(){
        // TODO implement fragments and come back
        RegisteredHomeHomeFragment homeHomeFragment = (RegisteredHomeHomeFragment)((HomePagerAdapter)viewPager.getAdapter()).getItem(0);
        //homeHomeFragment.initializeInfo();
        RegisteredHomeTrendFragment homeTrendFragment = (RegisteredHomeTrendFragment)((HomePagerAdapter)viewPager.getAdapter()).getItem(1);
        RegisteredHomeChannelFragment homeChannelFragment = (RegisteredHomeChannelFragment)((HomePagerAdapter)viewPager.getAdapter()).getItem(2);
        RegisteredHomeProfileFragment homeProfileFragment = (RegisteredHomeProfileFragment)((HomePagerAdapter)viewPager.getAdapter()).getItem(3);
    }

    class HomePagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> homeFragments = new ArrayList<>();
        private final List<String> homeFragmentTitles = new ArrayList<>();

        public HomePagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return homeFragments.get(position);
        }

        @Override
        public int getCount() {
            return homeFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            homeFragments.add(fragment);
            homeFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return homeFragmentTitles.get(position);
        }
    }

}
