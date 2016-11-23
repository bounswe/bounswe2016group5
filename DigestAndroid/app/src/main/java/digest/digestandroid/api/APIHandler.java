package digest.digestandroid.api;

import android.app.Application;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import org.json.JSONArray;
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
import digest.digestandroid.CacheTopiclist;
import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.QuizQuestion;
import digest.digestandroid.Models.Topic;
import digest.digestandroid.Models.TopicTag;
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

    public void getTopic(String tag, int topicId) {
        Log.d( "process", "Get Topic ");

        GsonRequest<Topic> myReq = new GsonRequest<Topic>(Request.Method.GET,
                mainURL + "/?f=get_topic&tid=" + topicId,
                Topic.class,
                new Response.Listener<Topic>() {
                    @Override
                    public void onResponse(Topic response) {
                        Log.d("Success", "Success");
                        Log.d("Success", response.toString());
                        // TODO: 23.11.2016 Comment out next statement when click listener on HomePage Topic objects implements and directs here 
                        //Cache.getInstance().setTopic(response);
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



    public void createTopic(String tag, final Topic topic) throws JSONException {

        Gson gson = new Gson();
        Topic top = new Topic();
        top.setOwner(32);
        top.setHeader("andeneme2");
        top.setBody("andeneme");
        top.setImage("");
        //top.setComments(new ArrayList<Comment>());
        //top.setQuizzes(new ArrayList<QuizQuestion>());
        //top.setMedia(new ArrayList<String>());
        //top.setTimestamp(new Timestamp());
        top.setId(-1);
        top.setTags(new ArrayList<TopicTag>());
        top.setRating(0);
        top.setStatus(-1);

        final String jsonInString = gson.toJson(top);




        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainURL+"/?f=create_topic", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    Log.d("---Json in string",jsonInString);
                    return jsonInString == null ? null : jsonInString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonInString, "utf-8");
                    return null;
                }
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest);
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
                                arrayList.add(tempTop);
                            }

                            CacheTopiclist.getInstance().setUserTopics(arrayList);

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

    //?f=get_recent_topics&count=<MAX LIMIT OF TOPICS>
    public void getRecentTopics(int limit) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_recent_topics&count=" + limit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Success", "Success");
                        Log.d("Success", response.toString());


                        try{
                            JSONArray obj = (JSONArray) new JSONTokener(response).nextValue();
                            ArrayList<Topic> arrayList = new ArrayList<Topic>();


                            int topicNumber = obj.length();
                            for(int i = 0 ; i < topicNumber ; i++){
                                JSONObject tempObj = (JSONObject) obj.get(i);
                                Topic tempTop = new Topic();

                                GsonRequest<Topic> tempGson = new GsonRequest<Topic>(Request.Method.GET,"",Topic.class,null,null);
                                tempTop = tempGson.getGson().fromJson(tempObj.toString(),Topic.class);
                                arrayList.add(tempTop);
                            }

                            Log.d("SucArray", ""+arrayList.size());
                            Log.d("SucArray", arrayList.toString());

                            CacheTopiclist.getInstance().setRecentTopics(arrayList);

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
