package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by sahin on 17.12.2016.
 */

public class Channel {

    private ArrayList<Topic> channelTopics;
    private String channelName;
    private int channelId;
    private int userId;


    public ArrayList<Topic> getChannelTopics() {
        return channelTopics;
    }

    public void setChannelTopics(ArrayList<Topic> channelTopics) {
        this.channelTopics = channelTopics;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "Channel{" +
                "topics=" + channelTopics +
                ", id='" + channelId + '\'' +
                ", userid='" + userId + '\'' +
                ", name=" + channelName +
                '}';
    }

}
