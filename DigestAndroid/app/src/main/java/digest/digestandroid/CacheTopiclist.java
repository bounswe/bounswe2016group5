package digest.digestandroid;

import java.util.ArrayList;

import digest.digestandroid.Models.Topic;


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

    public ArrayList<Topic> getTagTopics() {
        return tagTopics;
    }

    public void setTagTopics(ArrayList<Topic> x) {tagTopics = new ArrayList<Topic>(x);}


}
