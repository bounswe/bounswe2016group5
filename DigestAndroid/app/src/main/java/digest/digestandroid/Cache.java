package digest.digestandroid;

import digest.digestandroid.Models.Comment;
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
    User user;

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        newComment = 1;
    }
}
