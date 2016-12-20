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
import com.google.gson.GsonBuilder;
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
import digest.digestandroid.Models.Channel;
import digest.digestandroid.Models.Comment;
import digest.digestandroid.Models.QuizQuestion;
import digest.digestandroid.Models.Tagit;
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
    public void login(String tag, User user, Response.Listener<User> successListener,
                      Response.ErrorListener failureListener) {

        GsonRequest<User> myReq = new GsonRequest<User>(Request.Method.GET,
                mainURL + "/?f=login&username=" + user.getUsername() +  "&password=" + user.getPassword(),
                User.class, successListener, failureListener);

        VolleySingleton.getInstance().addToRequestQueue(myReq);
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
    public void signup(String tag, final User user, Response.Listener<String> successListener,
                       Response.ErrorListener errorListener ) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainURL + "/?f=register",
                successListener, errorListener){
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

    public void getTopic(String tag, int topicId, Response.Listener<Topic> getTopicListener) {
        Log.d( "process", "Get Topic ");

        GsonRequest<Topic> myReq = new GsonRequest<Topic>(Request.Method.GET,
                mainURL + "/?f=get_topic&tid=" + topicId,
                Topic.class,
                getTopicListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);

    }


    public void createTopic(String tag, final Topic topic, Response.Listener<String> successListener)  {

        //Gson gson = new Gson();
        final String jsonInString = new GsonBuilder().setExclusionStrategies().create().toJson(topic, Topic.class);
        Log.d("---Json in string",jsonInString);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainURL+"/?f=create_topic", successListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json" ;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                Log.d("---Json in string",jsonInString);
                return jsonInString == null ? null : jsonInString.getBytes();

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


    //f=add_channel
    //uid=<user_id> // owner of the channel
    //name=<channel_name>

    public void addChannel(User user, String channelName, Response.Listener<String> successListener) {


        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=add_channel&uid=" + user.getId() + "&name=" + channelName ,
                successListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Failed", "Channel is not added");
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse (NetworkResponse response){
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        VolleySingleton.getInstance().addToRequestQueue(myReq);

    }

    public void getChannelsOfSubscribedTopics(User user,Response.Listener<String> successListener){
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_subscribed_channels&uid=" + user.getId() ,
                successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Log.d("Failed", "Channel list from subscribed topics did not arrive");}
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    public void getChannelsOfUser(User user, Response.Listener<String> successListener){
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_channels_of_user&uid=" + user.getId() ,
                successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Log.d("Failed", "Channel can not return topics");}
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // ?f=add_subscriber&uid=<USER_ID>&tid=<TOPIC_ID>
    public void subscribeTheTopic(User user, int tid){
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=add_subscriber&uid="+user.getId()+"&tid="+tid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Result","Topic is subscribed");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Channel can not return topics");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // MAY NOT BE NEEDED AT ALL
    public void getProgressOfChannel(int channelId, User user, Response.Listener<String> successListener){
        StringRequest myReq = new StringRequest(Request.Method.GET,
                // TODO waiting for backend api.
                mainURL + "/?f=get_subscribed_channels&uid=" + user.getId() ,
                successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Log.d("Failed", "Progress did not arrive");}
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    //  http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_from_channel&cid=8
    public void getTopicsFromChannel(int channelId, Response.Listener<String> successListener) {

        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_topics_from_channel&cid=" + channelId ,
                successListener,
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {Log.d("Failed", "Channel can not return topics");}
        });

        VolleySingleton.getInstance().addToRequestQueue(myReq);

    }


    //http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_trending_topics
    public void getTrendingTopics(User user, Response.Listener<String> trendingTopLis) {
        Log.d("FFSS","We entered getting topics");
        StringRequest myReq = new StringRequest(Request.Method.GET,
                // TODO change the api for trending topics !!!!!!!!!!!!!!!!!!!!!!!!!! ------------------ Change api when it comes !!!!!!!!!!!!!!!!!!!!!!!
                mainURL + "/?f=get_trending_topics",
                trendingTopLis,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "No trending topics");
                    }
                });

        Log.d("FFSS","We sent request");
        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // Get all topics of a user
    // http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_topics_of_user&ruid=25

    public void getAllTopicsOfAUser(User user, Response.Listener<String> userTopLis) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_topics_of_user&ruid=" + user.getId(),
                userTopLis,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "No user topics");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    //?f=get_recent_topics&count=<MAX LIMIT OF TOPICS>
    public void getRecentTopics(int limit,Response.Listener<String> recentTopLis) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_recent_topics&count=" + limit,
                recentTopLis,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "No recent topics");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // <API_PATH>/?f=get_subscribed_topics&uid=<USER_ID>
    public void getFollowedTopics(User user,Response.Listener<String> recentTopLis) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_subscribed_topics&uid=" + user.getId() ,
                recentTopLis,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "No followed topics");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    public void getUsername(String tag, int userID, Response.Listener<String> successListener) {

        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_username&uid=" + userID,
                successListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Failed", "Login Failed");
            }
        });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }


    public void addComment(String tag, Comment comment, Response.Listener<String> successListener,
                            Response.ErrorListener failureListener) {

        String commentBody;
        commentBody = comment.getBody().replace(" ", "%20");
        Log.e("commentBody:", commentBody);

        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=add_comment&body=" + commentBody + "&uid=" + comment.getUid() + "&ucid=" + comment.getUcid() + "&tid=" + comment.getTid() ,
                successListener, failureListener){
                @Override
                protected Response<String> parseNetworkResponse (NetworkResponse response){
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
        };


        // TODO: 5.12.2016 Bu satir comment out yapilmisti??? Kim neden yapti ki -- Bilemiyorum Altan...
        VolleySingleton.getInstance().addToRequestQueue(myReq);

    }


    public void addMaterial(String tag, String material, int tid, Response.Listener<String> successListener,
                           Response.ErrorListener failureListener) {

        String materialUrl;
        materialUrl = material.replace(" ", "%20");
        Log.e("materialBody:", materialUrl);

        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=add_media&tid=" + tid + "&url=" + materialUrl,
                successListener, failureListener){
            @Override
            protected Response<String> parseNetworkResponse (NetworkResponse response){
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }



    //<API_PATH>?f=get_topics_with_tag&tag=<TAG>
    public void searchWithTag (String tag, Response.Listener<String> tagListener) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_topics_with_tag&tag=" + tag,
                tagListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }


    //http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=add_topic_to_channel&cid=8&tid=24
    public void addTopicToChannel (int cid, int tid) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=add_topic_to_channel&cid=" + cid + "&tid=" + tid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
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

    // http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_suggestions&body=some_text
    // result will be : [{"artificial_intelligence":["computer science","computing","artificial"]},
    // {"syntax":["linguistics","grammar","structure","system"]},{"grammar":["linguistics"]},
    // {"cognitive_science":["science","cognitive psychology"]},{"information_science":["science","lysis"]}]
    public void getTagSuggestions (String body, Response.Listener<String> successListener) {
        StringRequest myReq = new StringRequest(Request.Method.GET,
                mainURL + "/?f=get_tag_suggestions&body=" + body,
                successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }

    // http://digest.us-east-1.elasticbeanstalk.com/digest.api/?f=get_tag_entities&tag=computer_science
    // result will be : {"myArrayList":["programming language","an animal","boa","spirit","prototype based language"]}
    public void getTagEntities (String tag, Response.Listener<Tagit> successListener) {
        GsonRequest<Tagit> myReq = new GsonRequest<Tagit>(Request.Method.GET,
                mainURL + "/?f=get_tag_entities&tag=" + tag,
                Tagit.class,
                successListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Failed", "Login Failed");
                    }
                });

        VolleySingleton.getInstance().addToRequestQueue(myReq);
    }



}
