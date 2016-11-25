package org.bounswe.digest.semantic;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.util.Iterator;
 
public class CalaisAPI {
 
    private static final String CALAIS_URL = "https://api.thomsonreuters.com/permid/calais";
    public static String uniqueAccessKey ="ABw03VsxXSFc8w3GaoS3dPrJzLZRP29P";		
    private String input;
    private static String output;
    private HttpClient client;
 
    private PostMethod createPostMethod() {
        PostMethod method = new PostMethod(CALAIS_URL);
 
        // Set mandatory parameters
        method.setRequestHeader("X-AG-Access-Token", uniqueAccessKey);
        // Set input content type
        method.setRequestHeader("Content-Type", "text/raw");
	// Set response/output format
        method.setRequestHeader("outputformat", "application/json");
        return method;
    }
 
    private void run() {
	try {
		postFile(input, createPostMethod());
	} catch (Exception e) {
		e.printStackTrace();
	}
    }
 
    private void doRequest(String query, PostMethod method) {
        try {
            int returnCode = client.executeMethod(method);
            if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
                // still consume the response body
                method.getResponseBodyAsString();
            } else if (returnCode == HttpStatus.SC_OK) {
                saveResponse(query, method);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
    }
 
    private void saveResponse(String query, PostMethod method) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    method.getResponseBodyAsStream(), "UTF-8"));
            output = org.apache.commons.io.IOUtils.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @SuppressWarnings("deprecation")
	private void postFile(String query, PostMethod method) throws IOException {
        method.setRequestEntity(new StringRequestEntity(query));
        doRequest(query, method);
    }
 
    public static void main(String[] args) throws UnirestException {
        CalaisAPI httpClientPost = new CalaisAPI();
        httpClientPost.input = "Erdogan's plan is to reconstitute Turkey as a presidential system,that would enable him to better tackle Turkey’s internal and external threats. One of the main hurdles allegedly standing in his way is Fethullah Gulen’s movement.";
        httpClientPost.output = "";
        httpClientPost.client = new HttpClient();
        httpClientPost.client.getParams().setParameter("http.useragent", "Calais Rest Client");
        httpClientPost.run();
        System.out.println("What common traits do wolves, dogs, werewolfves share? What WOlverine shares with them and Spiderman? Stuck between being a superhero and being a member of animal kingdom, let's solve the mystery of Wolverine.");
        JSONObject jsonObj = new JSONObject(output);
        Iterator<String> i = jsonObj.keys();
		while(i.hasNext()) {
			String key = (String)i.next();
			if(key != null) {
				JSONObject item = (JSONObject)jsonObj.get(key);
				if (item.has("_typeGroup")) {
					String typeGroup = item.getString("_typeGroup");
					if(typeGroup != null && typeGroup.equals("entities")) {
						String name = item.getString("name");
						Double relevance = item.getDouble("relevance");
						System.out.println(name + " " + relevance);
						//int count = item.getJSONArray("instances").length();
					}
					if(typeGroup != null && typeGroup.equals("socialTag")) {
						String name = item.getString("name");
						Double relevance = item.getDouble("importance");
						System.out.println(name + " " + relevance);
						//int count = item.getJSONArray("instances").length();
					}
				}
        	}
       	}
    }
}