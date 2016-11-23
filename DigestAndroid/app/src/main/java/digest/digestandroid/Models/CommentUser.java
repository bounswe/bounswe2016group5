package digest.digestandroid.Models;

import java.sql.Timestamp;

/**
 * Created by Burki on 24.11.2016.
 */

public class CommentUser extends Comment{
    private String username;

    public CommentUser() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
