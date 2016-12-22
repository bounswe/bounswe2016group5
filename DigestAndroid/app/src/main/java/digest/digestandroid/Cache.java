package digest.digestandroid;

import java.util.ArrayList;

import digest.digestandroid.Models.Channel;
import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.QuizQuestion;
import digest.digestandroid.Models.Tagit;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.User;

/**
 * Created by Burki on 20.11.2016.
 */

/* A singleton class
 * Holds the current user
 * Holds if there is a new comment
 *
 * All in all we can pass any temporary object to pass between activities
 */
public class Cache {
    private static Cache ourInstance = new Cache();
    Comment comment;
    int newComment = 0;
    String material;
    int newMaterial = 0;
    User user;
    Topic topic;
    String topicCreator; // username of topic creator

    QuizQuestion question; //

    ArrayList<Channel> userChannels;
    int cid;  // selected channel id
    int cidd;

    ArrayList<String> entityList;
    ArrayList<Tagit> chosenTags = new ArrayList<Tagit>();

    //ArrayList<Tagit> selectBoxTags = new ArrayList<Tagit>();

    String tag;
    int newTag = 0;


    public static Cache getInstance() {
        return ourInstance;
    }

    private Cache() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getTopicCreator() {
        return topicCreator;
    }

    public void setTopicCreator(String topicCreator) {
        this.topicCreator = topicCreator;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        newComment = 1;
    }

    public String getMaterial() {
        return material;
    }

    public QuizQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }

    public void setMaterial(String material) {
        this.material = material;
        newMaterial = 1;
    }

    public ArrayList<Channel> getUserChannels() {
        return userChannels;
    }

    public void setUserChannels(ArrayList<Channel> userChannels) {
        this.userChannels = new ArrayList<Channel>(userChannels);
    }

    public ArrayList<String> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<String> entityList) {
        this.entityList = entityList;
    }

    public ArrayList<Tagit> getChosenTags() {
        return chosenTags;
    }

    public void setChosenTags(ArrayList<Tagit> chosenTags) {
        this.chosenTags = chosenTags;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getNewTag() {
        return newTag;
    }

    public void setNewTag(int newTag) {
        this.newTag = newTag;
    }

    public int getCidd() {
        return cidd;
    }

    public void setCidd(int cidd) {
        this.cidd = cidd;
    }
}
