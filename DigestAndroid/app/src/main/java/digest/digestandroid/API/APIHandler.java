package digest.digestandroid.API;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.Response;

import digest.digestandroid.Models.User;

/**
 * Created by Suyunu on 10.11.2016.
 */

public class APIHandler extends Application{
    private static APIHandler mInstance = new APIHandler();

    public static synchronized APIHandler getInstance() { return mInstance; }

    private String mainURL = "digest.us-east-1.elasticbeanstalk.com/digest.api";

    public String getMainURL() {
        return mainURL;
    }


    // Sample Login Handler
    // Not exactly correct atm
    public void login(String tag, User user, Response.Listener<User> successListener,
                             Response.ErrorListener failureListener) {

        GsonRequest<User> myReq = new GsonRequest<User>(Request.Method.GET,
                mainURL + "/?f=login",
                User.class,
                successListener,
                failureListener);

        VolleySingleton.getInstance(this).addToRequestQueue(myReq);
    }
}
