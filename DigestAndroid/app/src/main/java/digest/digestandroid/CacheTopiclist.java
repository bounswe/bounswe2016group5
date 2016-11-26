package digest.digestandroid;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;


public class CacheTopiclist {
    private static CacheTopiclist ourInstance = new CacheTopiclist();
    public static CacheTopiclist getInstance() {
        return ourInstance;
    }
    private CacheTopiclist() {}


    ArrayList<Topic> recentTopics;
    ArrayList<Topic> userTopics;
    ArrayList<Topic> tagTopics;



    public ArrayList<Topic> getRecentTopics() {
        return recentTopics;
    }

    public void setRecentTopics(ArrayList<Topic> x) {recentTopics = new ArrayList<Topic>(x);    }

    public ArrayList<Topic> getUserTopics() {
        return userTopics;
    }

    public void setUserTopics(ArrayList<Topic> x) {tagTopics = new ArrayList<Topic>(x);   }

    public ArrayList<Topic> getTAGTopics() {
        return userTopics;
    }

    public void setTagTopics(ArrayList<Topic> x, RecyclerView.Adapter homeAdapter, RecyclerView homeRecyclerView) {
        tagTopics = new ArrayList<Topic>(x);

        homeAdapter = new HomeAdapter(tagTopics);
        homeRecyclerView.setAdapter(homeAdapter);

    }


}
