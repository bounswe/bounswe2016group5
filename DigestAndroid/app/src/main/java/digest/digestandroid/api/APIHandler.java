package digest.digestandroid.api;

import android.app.Application;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import digest.digestandroid.Models.User;

/**
 * Created by Suyunu on 10.11.2016.
 */

public class APIHandler extends Application{
    private static APIHandler mInstance = new APIHandler();

    public static synchronized APIHandler getInstance() { return mInstance; }

    private String mainURL = "http://digest.us-east-1.elasticbeanstalk.com/digest.api";

    public String getMainURL() {
        return mainURL;
    }


    // Sample Login Handler
    // Not exactly correct atm
    // <API_PATH>/?f=login&username=<USERNAME>&password=<PASSWORD>
    // returns:
    // {"id":32,"username":"android","password":"1234","email":"asdf@asdf.com",
    // "first_name":"android","last_name":"android","status":1,
    // "role":{"id":2,"name":"user"},"session":"1c01360fd1a2aa97"}
    public void login(String tag, User user) {
        Log.d( "11111", "11111");
        GsonRequest<User> myReq = new GsonRequest<User>(Request.Method.GET,
                mainURL + "/?f=login&username=" + user.getUsername() +  "&password=" + user.getPassword(),
                User.class,
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        Log.d("Success", "Success");
                        Log.d("Success", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        Log.d("222222", "222222");
        VolleySingleton.getInstance().addToRequestQueue(myReq);
        Log.d("333333", "333333");
    }

    public void loginPOST(String tag, final User user) {

        Log.d("222222", "222222");

        GsonRequest<User> myReq = new GsonRequest<User> (Request.Method.POST,
                mainURL + "/?f=login",
                User.class,
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        Log.d("Success", "Success");
                        Log.d("Success", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",user.getUsername());
                params.put("password",user.getPassword());
                return params;
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(myReq);

        Log.d("333333", "333333");

    }


    /*
    *Sign Up Handler
    *<API_PATH>/?f=register&username=<USERNAME>&password=<PASSWORD>&email=<EMAIL>&first_name=<FIRST_NAME>&<LAST_NAME>=<LAST_NAME>&status=<STATUS_INT>
    * returns 200 or 400
    *
    */
    public void signup(String tag, final User user) {
        Log.d( "process", "Signup ");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainURL + "/?f=register",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", "onResponse: "+ response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Log.d("Response","Failed");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", user.getUsername());
                params.put("password", user.getPassword());
                params.put("email", user.getEmail());
                params.put("first_name", user.getFirst_name());
                params.put("last_name", user.getLast_name());
                params.put("status", Integer.toString(user.getStatus()));
                return params;
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest);

    }
}
