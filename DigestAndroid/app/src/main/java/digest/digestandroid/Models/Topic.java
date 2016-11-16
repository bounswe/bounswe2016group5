package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by oykuyilmaz on 15/11/16.
 *
 * Topic model
 */

public class Topic {


    private int id;
    private String topic_title;
    private String topic_body;
    private ArrayList<TopicTag> topic_tags;
    private String image_url;
    private User topic_owner;
    private long topic_rating;
    private int topic_status;

    private ArrayList<Comment> topic_comments;

    private ArrayList<String> topic_materials;
    private ArrayList<QuizQuestion> topic_quiz;

    public Topic() {
    }

    public Topic(int id, String topic_title, String topic_body, ArrayList<TopicTag> topic_tags, String image_url,
                 User topic_owner, long topic_rating, int topic_status, ArrayList<Comment> topic_comments, ArrayList<String> topic_materials,
                 ArrayList<QuizQuestion> topic_quiz) {
        this.id = id;
        this.topic_title = topic_title;
        this.topic_body = topic_body;
        this.topic_tags = topic_tags;
        this.image_url = image_url;
        this.topic_owner = topic_owner;
        this.topic_rating = topic_rating;
        this.topic_status = topic_status;
        this.topic_comments = topic_comments;
        this.topic_materials = topic_materials;
        this.topic_quiz = topic_quiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    public String getTopic_body() {
        return topic_body;
    }

    public void setTopic_body(String topic_body) {
        this.topic_body = topic_body;
    }

    public ArrayList<TopicTag> getTopic_tags() {
        return topic_tags;
    }

    public void setTopic_tags(ArrayList<TopicTag> topic_tags) {
        this.topic_tags = topic_tags;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public User getTopic_owner() {
        return topic_owner;
    }

    public void setTopic_owner(User topic_owner) {
        this.topic_owner = topic_owner;
    }

    public long getTopic_rating() {
        return topic_rating;
    }

    public void setTopic_rating(long topic_rating) {
        this.topic_rating = topic_rating;
    }

    public int getTopic_status() {
        return topic_status;
    }

    public void setTopic_status(int topic_status) {
        this.topic_status = topic_status;
    }

    public ArrayList<Comment> getTopic_comments() {
        return topic_comments;
    }

    public void setTopic_comments(ArrayList<Comment> topic_comments) {
        this.topic_comments = topic_comments;
    }

    public ArrayList<String> getTopic_materials() {
        return topic_materials;
    }

    public void setTopic_materials(ArrayList<String> topic_materials) {
        this.topic_materials = topic_materials;
    }

    public ArrayList<QuizQuestion> getTopic_quiz() {
        return topic_quiz;
    }

    public void setTopic_quiz(ArrayList<QuizQuestion> topic_quiz) {
        this.topic_quiz = topic_quiz;
    }
}
