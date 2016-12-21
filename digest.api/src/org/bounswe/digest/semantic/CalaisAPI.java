package org.bounswe.digest.semantic;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.text.Normalizer;
import java.util.Iterator;
 
public class CalaisAPI {
 
    private static final String CALAIS_URL = "https://api.thomsonreuters.com/permid/calais";
    public static final String uniqueAccessKey ="ABw03VsxXSFc8w3GaoS3dPrJzLZRP29P";		
    public String input;
    public String output;
    public HttpClient client;
 
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
 
    public void run() {
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
    
    public String getOutput(){
    	return output;
    }
 
    @SuppressWarnings("deprecation")
	private void postFile(String query, PostMethod method) throws IOException {
        method.setRequestEntity(new StringRequestEntity(query));
        doRequest(query, method);
    }
    
    /**
     * Extracts entities with respect to given text.
     * @param queryText
     * @return JSONArray : JSONObject : JSONArray | {"label":["labels"]}
     */
    public JSONObject extractTags(String queryText){
    	ConceptNetAPI httpClientPost = new ConceptNetAPI();
		input = queryText;
		client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Calais Rest Client");
		run();
		JSONObject jsonObj = new JSONObject(getOutput());
        Iterator<String> i = jsonObj.keys();
        JSONArray ja = new JSONArray();
        JSONArray cnet = new JSONArray();
        JSONObject jo = new JSONObject();
		while(i.hasNext()) {
			String key = (String)i.next();
			if(key != null) {
				JSONObject item = (JSONObject)jsonObj.get(key);
				if (item.has("_typeGroup")) {
					String typeGroup = item.getString("_typeGroup");
					/*if(typeGroup != null && typeGroup.equals("entities")) {
						String name = item.getString("name");
						Double relevance = item.getDouble("relevance");
						jo.put(name, relevance);
						ja.put(jo);
						jo = new JSONObject();
					}*/
					if(typeGroup != null && typeGroup.equals("socialTag")) {
						String name = item.getString("name");
						name = name.replaceAll(" ", "_").toLowerCase();
						name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{Mn}", "");
				    	if(name.contains("�")) name = name.replaceAll("�", "i");
						Double relevance = item.getDouble("importance");
						cnet = httpClientPost.extractEntities(name).getJSONArray("entities");
						if(cnet.length()!=0){
							jo.put(name, cnet);
							ja.put(jo);
							jo = new JSONObject();
						}
					}
				}
        	}
       	}
		JSONObject o = new JSONObject();
		o.put("suggestions", ja);
		return o;
    }
}