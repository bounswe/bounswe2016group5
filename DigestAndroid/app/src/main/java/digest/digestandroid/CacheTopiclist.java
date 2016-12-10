package digest.digestandroid;

import android.util.Log;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;


public class CacheTopiclist {
    private static CacheTopiclist ourInstance = new CacheTopiclist();
    public static CacheTopiclist getInstance() {
        return ourInstance;
    }
    private CacheTopiclist() {
        Log.d("CC","Topic lists are initialized as null.");
        recentTopics = null;
        userTopics = null;
        tagTopics = null;
    }


    ArrayList<Topic> recentTopics;
    ArrayList<Topic> userTopics;
    ArrayList<Topic> tagTopics;

    String currentFragment;

    public String getCurrentFragment(){return currentFragment;}

    public void setCurrentFragment(String x){ currentFragment=x;}

    public ArrayList<Topic> getRecentTopics() {
        return recentTopics;
    }

    public void setRecentTopics(ArrayList<Topic> x) {recentTopics = new ArrayList<Topic>(x);    }

    public ArrayList<Topic> getUserTopics() {
        return userTopics;
    }

    public void setUserTopics(ArrayList<Topic> x) {tagTopics = new ArrayList<Topic>(x);   }

    public ArrayList<Topic> getTagTopics() {
        return tagTopics;
    }

    public void setTagTopics(ArrayList<Topic> x) {tagTopics = new ArrayList<Topic>(x);}


}
