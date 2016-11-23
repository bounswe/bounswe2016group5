package digest.digestandroid.api;

import android.app.Application;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import digest.digestandroid.Cache;
import digest.digestandroid.Models.Topic;
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

        GsonRequest<User> myReq = new GsonRequest<User> (Request.Method.POST,
                mainURL + "/?f=login", User.class,
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        Log.d("Success", "Success");
                        Log.d("Success", "+" + response.toString());
                        //Cache.getInstance().setUser(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);

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

    /*
    * Create Topic Handler
    *<API_PATH>/?f=register&username=<USERNAME>&password=<PASSWORD>&email=<EMAIL>&first_name=<FIRST_NAME>&<LAST_NAME>=<LAST_NAME>&status=<STATUS_INT>
    * returns 200 or 400
    *
    */
    public void createTopic(String tag, final Topic topic) {
        Log.d( "process", "Topic Creation ");

        Gson gson = new Gson();
        String jsonString = gson.toJson(topic);
        JSONObject json = null;
        try {
             json = new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mainURL + "/?f=create_topic",
                json,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", "onResponse: "+ response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response","Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    // Get A Topic Handler
    // <API_PATH>/?f=get_topics_of_user&uid=<REQUEST_OWNER_USER_ID>&session=<SESSION>&tid=<TOPIC_ID>
    public void getATopic(String tag, Topic topic) {
        GsonRequest<Topic> myReq = new GsonRequest<Topic>(Request.Method.GET,
                mainURL + "/?f=tid=" + topic.getId(),
                Topic.class,
                new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
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

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // Get all topics of a user
    // http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_of_user&ruid=25

    public void getAllTopicsOfAUser(User user) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_topics_of_user&ruid=" + user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Success", "Success");
                        Log.d("Success", response.toString());




                        try{
                            JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                            ArrayList<Topic> arrayList = new ArrayList<Topic>();

                            Log.d("Suc", obj.get(0).toString());

                            int topicNumber = obj.length();
                            for(int i = 0 ; i < topicNumber ; i++){
                                JSONObject tempObj = (JSONObject) obj.get(i);
                                Topic tempTop = new Topic();

                                GsonRequest<Topic> tempGson = new GsonRequest<Topic>(Request.Method.GET,"",Topic.class,null,null);
                                tempTop = tempGson.getGson().fromJson(tempObj.toString(),Topic.class);

                                Log.d("DOOOOOOOONE -------", tempTop.toString());
//                                tempTop.setId(Integer.parseInt(tempObj.getString("id")));
//                                tempTop.setImage_url(tempObj.getString("image"));
//                                tempTop.setBody(tempObj.getString("body"));
//                                tempTop.setOwner(Integer.parseInt(tempObj.getString("owner")));
//                                tempTop.setStatus(Integer.parseInt(tempObj.getString("status")));
//                                tempTop.setTimestamp(new Timestamp( (new Date()).getTime() ));
//                                tempTop.setTags(null);
//                                tempTop.setQuiz(null);
//                                tempTop.setMaterials(null); // media?
//                                tempTop.setComments(null);
                            }



                        }catch (JSONException e){}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }


}
