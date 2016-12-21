package digest.digestandroid.Models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by oykuyilmaz on 15/11/16.
 *
 * Topic model
 */

public class Topic {


    private int id;
    private String header;
    private String body;
    private ArrayList<TopicTag> tags = new ArrayList<TopicTag>();
    private String image;
    private int owner;
    private long rating;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<String> media) {
        this.media = media;
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(ArrayList<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    private int status;
    private Timestamp timestamp;

    private ArrayList<Comment> comments;
    private ArrayList<String> media;
    private ArrayList<Quiz> quizzes;

    public Topic() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                ", image='" + image + '\'' +
                ", owner=" + owner +
                ", rating=" + rating +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", comments=" + comments +
                ", media=" + media +
                ", quizzes=" + quizzes +
                '}';
    }
}
