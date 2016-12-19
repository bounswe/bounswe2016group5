package digest.digestandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
import digest.digestandroid.fragments.RegisteredHomeFollowedFragment;
import digest.digestandroid.fragments.RegisteredHomeHomeFragment;
import digest.digestandroid.fragments.RegisteredHomeProfileFragment;
import digest.digestandroid.fragments.RegisteredHomeTrendFragment;
/**
 * Created by Sahin on 11/1/2016.
 */

public class ViewRegisteredHomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private ImageView advancedSearchView;
    private TabLayout tabLayout;
    public static ViewPager viewPager;

    private static RegisteredHomeHomeFragment homeHomeFragment;
    private static RegisteredHomeTrendFragment homeTrendFragment ;
    private static RegisteredHomeFollowedFragment homeFollowedFragment;
    private static RegisteredHomeProfileFragment homeProfileFragment;

    //--------------------------  ABOVE IS FIELD VARIABLES  -------------------------------------------
    //--------------------------  BELOW IS OVERRIDE-CREATE FUNCTIONS  ---------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registered_home_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTitle("@"+Cache.getInstance().getUser().getUsername());

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

                            ArrayList<Topic> arrayList = serializeTopicsFromJson(response);
                            CacheTopiclist.getInstance().setTagTopics(arrayList);

                            Intent intent = new Intent( getApplicationContext(), ViewSearchActivity.class);
                            startActivity(intent);
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


        advancedSearchView = (ImageView) findViewById(R.id.advanced_search_button_inhome);
        advancedSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                intent.putExtra("previous", "home");
                startActivity(intent);
            }
        });

        //--------------------------  ABOVE IS TOOLBAR  ------------------------------------
        //--------------------------  BELOW IS RECYCLERVIEW  -------------------------------------------


        viewPager = (ViewPager) findViewById(R.id.viewpager_home);
        defineViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_home);
        tabLayout.setupWithViewPager(viewPager);
        loadViewPager();

    }

    //--------------------------  ABOVE IS OVERRIDE-CREATE FUNCTIONS  ------------------------------------
    //--------------------------  BELOW IS FRAGMENT FUNCTIONS  -------------------------------------------

    private void defineViewPager(ViewPager viewPager) {
        ViewRegisteredHomeActivity.HomePagerAdapter adapter = new ViewRegisteredHomeActivity.HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisteredHomeHomeFragment(), "HOME");
        adapter.addFragment(new RegisteredHomeTrendFragment(), "TRENDING");
        adapter.addFragment(new RegisteredHomeFollowedFragment(), "FOLLOWED");
        adapter.addFragment(new RegisteredHomeProfileFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }

    private void loadViewPager(){
        homeHomeFragment = (RegisteredHomeHomeFragment)((HomePagerAdapter)viewPager.getAdapter()).getItem(0);
        //homeHomeFragment.initializeInfo();
        homeTrendFragment = (RegisteredHomeTrendFragment)((ViewRegisteredHomeActivity.HomePagerAdapter)ViewRegisteredHomeActivity.viewPager.getAdapter()).getItem(1);
        homeFollowedFragment = (RegisteredHomeFollowedFragment)((ViewRegisteredHomeActivity.HomePagerAdapter)ViewRegisteredHomeActivity.viewPager.getAdapter()).getItem(2);
        homeProfileFragment = (RegisteredHomeProfileFragment)((ViewRegisteredHomeActivity.HomePagerAdapter)ViewRegisteredHomeActivity.viewPager.getAdapter()).getItem(3);
    }

    //--------------------------  ABOVE IS FRAGMENT FUNCTIONS  ------------------------------------------
    //--------------------------  BELOW IS OVERRIDE-OPTIONS FUNCTION  -----------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_create_topic:
                intent = new Intent(getApplicationContext(), CreateTopicFragmentsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_add_channel:
                intent = new Intent(getApplicationContext(), AddChannelActivity.class);
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                String currentFragment = CacheTopiclist.getInstance().getCurrentFragment();
                Log.d("Refresh is pressed","Current fragment is"+ currentFragment);

                if(currentFragment.equals("Home")){
                    APIHandler.getInstance().getRecentTopics(15,topicListQueryListenerAndLoader(currentFragment,homeHomeFragment.homeRecyclerView));
                }else if(currentFragment.equals("Trending")){
                    APIHandler.getInstance().getTrendingTopics(Cache.getInstance().getUser(),topicListQueryListenerAndLoader(currentFragment,homeTrendFragment.trendingRecyclerView));
                }else if(currentFragment.equals("Followed")){
                    APIHandler.getInstance().getFollowedTopics(Cache.getInstance().getUser(),topicListQueryListenerAndLoader(currentFragment,homeFollowedFragment.followedRecyclerView));
                }else if(currentFragment.equals("Profile")){
                    APIHandler.getInstance().getAllTopicsOfAUser(Cache.getInstance().getUser(),topicListQueryListenerAndLoader(currentFragment,homeProfileFragment.profileRecyclerView));
                }else{
                    Log.d("HEEY","This fragment name is not expected !!! ");
                }
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //--------------------------  ABOVE IS OVERRIDE-OPTIONS FUNCTION  -----------------------------------
    //--------------------------  BELOW IS LISTENER FUNCTIONS  ------------------------------------------

    public Response.Listener<String> topicListQueryListenerAndLoader(final String currentFragment,final RecyclerView currentRecyclerView){
        return
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AA",""+response.length());

                if(currentFragment.equals("Home")){
                    final ArrayList<Topic> arrayList = serializeTopicsFromJson(response);
                    CacheTopiclist.getInstance().setRecentTopics(arrayList);
                    loadTopics(currentRecyclerView,arrayList);


                }else if(currentFragment.equals("Trending")){
                    final ArrayList<Topic> arrayList = serializeTopicsFromJson(response);
                    CacheTopiclist.getInstance().setTrendingTopics(arrayList);
                    loadTopics(currentRecyclerView,arrayList);


                }else if(currentFragment.equals("Followed")){

                    final ArrayList<Topic> arrayList = new ArrayList<Topic>();

                    response = response.substring(1,response.length()-1);
                    String[] topicIds = response.split(",");

                    final int numberOfFollowedTopics = topicIds.length;
                    Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                        @Override
                        public void onResponse(Topic response) {
                            arrayList.add(response);
                            if(arrayList.size() == numberOfFollowedTopics ) {
                                CacheTopiclist.getInstance().setFollowedTopics(arrayList);
                                loadTopics(currentRecyclerView, arrayList);
                            }
                        }
                    };

                    for(int i = 0; i < numberOfFollowedTopics; i++){
                        APIHandler.getInstance().getTopic("", Integer.parseInt(topicIds[i]), getTopicListener);
                    }


                }else if(currentFragment.equals("Profile")){
                    final ArrayList<Topic> arrayList = serializeTopicsFromJson(response);
                    Log.d("AA",""+arrayList.toString());
                    CacheTopiclist.getInstance().setUserTopics(arrayList);
                    loadTopics(currentRecyclerView,arrayList);


                }else{
                    Log.d("HEEY","This fragment name is not expected !!! ");
                }
            }
        };
    }

    public void loadTopics(RecyclerView currentRecyclerView,final ArrayList<Topic> currentTopicList){
        RecyclerView.Adapter homeAdapter = new HomeAdapter(currentTopicList);
        currentRecyclerView.setAdapter(homeAdapter);

        ((HomeAdapter) homeAdapter).setOnItemClickListener(new HomeAdapter.HomeClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Log.d("" + pos, v.toString());

                int clickedTopicId = currentTopicList.get(pos).getId();
                Response.Listener<Topic> getTopicListener = new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", response.toString());
                        Cache.getInstance().setTopic(response);

                        Intent intent = new Intent(getApplicationContext(), ViewTopicActivity.class);
                        startActivity(intent);
                    }
                };
                APIHandler.getInstance().getTopic("", clickedTopicId, getTopicListener);
                // TODO: 19.12.2016 Get channels of user 
            }
        });
    }
    public static ArrayList<Topic> serializeTopicsFromJson(String resp){

        final ArrayList<Topic> resultArrayList = new ArrayList<>();

        try {
            JSONArray obj = (JSONArray) new JSONTokener(resp).nextValue();
            int topicNumber = obj.length();
            for (int i = 0; i < topicNumber; i++) {
                JSONObject tempObj = (JSONObject) obj.get(i);
                Topic tempTop = (new Gson()).fromJson(tempObj.toString(), Topic.class);
                resultArrayList.add(tempTop);
            }
        } catch (JSONException e) {}


        return resultArrayList;
    }


    //--------------------------  ABOVE IS LISTENER FUNCTIONS  ------------------------------------------
    //--------------------------  BELOW IS ADAPTER CLASS  ------------------------------------------


    public class HomePagerAdapter extends FragmentPagerAdapter {
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
