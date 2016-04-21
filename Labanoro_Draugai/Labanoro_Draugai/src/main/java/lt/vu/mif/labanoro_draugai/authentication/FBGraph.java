/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Stateless
public class FBGraph {
    
    public FBGraph() {}
      
    public String getEmail(String accessToken) {
        
        String email = null;
        
        try {
            String responseBody = getDataFromGraph("email", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                email = json.getString("email"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return email;   
    }
    
    public Map getIdEmail(String accessToken) {
        
        Map info = new HashMap();
        
        try {
            String responseBody = getDataFromGraph("email", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                info.put("id", json.getString("id")); 
                info.put("email", json.getString("email")); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return info;
    }
    
    public Map getFirstLastName(String accessToken) {
        
        Map info = new HashMap();
        
        try {
            String responseBody = getDataFromGraph("first_name,last_name", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                info.put("first_name", json.getString("first_name")); 
                info.put("last_name", json.getString("last_name"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return info;
    }
    
    public String getAge(String accessToken) {
        
        String birthday = null;
        
        try {
            String responseBody = getDataFromGraph("birthday", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                birthday = json.getString("birthday"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return birthday; 
    }
    
    public String getProfilePicture(String accessToken) {
        
        String profilePictureUrl = null;
        
        try {
            String responseBody = getDataFromGraph("picture", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                profilePictureUrl = json.getJSONObject("picture").getJSONObject("data").getString("url"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return profilePictureUrl; 
    }
    
    public String getFirstName(String accessToken) {
        
        String firstName = null;
        
        try {
            String responseBody = getDataFromGraph("first_name", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                firstName = json.getString("first_name"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return firstName; 
    }
    
    public String getLastName(String accessToken) {
        
        String lastName = null;
        
        try {
            String responseBody = getDataFromGraph("last_name", accessToken);
            if (responseBody != null) {
               JSONObject json = (JSONObject) JSONSerializer.toJSON(responseBody);
                lastName = json.getString("last_name"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return lastName; 
    }
    
    private String getDataFromGraph(String command, String accessToken) throws IOException {
        
        String responseBody = null;
        
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        
        try {
            if (accessToken != null && !"".equals(accessToken)) {
                String newUrl = "https://graph.facebook.com/v2.5/me?fields="+ command +"&access_token=" + accessToken;
                httpclient = HttpClientBuilder.create().build();

                HttpGet httpget = new HttpGet(newUrl);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                responseBody = httpclient.execute(httpget, responseHandler);

            } else {
                System.err.println("Facebook token is null");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        
        return responseBody;
    }
    
}
