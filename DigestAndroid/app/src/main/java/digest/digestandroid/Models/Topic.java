package digest.digestandroid.Models;

import java.util.ArrayList;

/**
 * Created by oykuyilmaz on 15/11/16.
 *
 * Topic model
 */

public class Topic {


    private int id;
    private String title;
    private String body;
    private ArrayList<TopicTag> tags;
    private ArrayList<String> string_tags;
    private String image_url;
    private int owner;
    private long rating;
    private int status;

    private ArrayList<Comment> comments;
    private ArrayList<String> materials;
    private ArrayList<QuizQuestion> quiz;

    public Topic() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<TopicTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TopicTag> tags) {
        this.tags = tags;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<String> materials) {
        this.materials = materials;
    }

    public ArrayList<QuizQuestion> getQuiz() {
        return quiz;
    }

    public void setQuiz(ArrayList<QuizQuestion> quiz) {
        this.quiz = quiz;
    }


    public ArrayList<String> getString_tags() {
        return string_tags;
    }

    public void setString_tags(ArrayList<String> string_tags) {
        this.string_tags = string_tags;
    }

}
