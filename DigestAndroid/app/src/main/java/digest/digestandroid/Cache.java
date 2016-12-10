package digest.digestandroid;

import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.QuizQuestion;
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
}
