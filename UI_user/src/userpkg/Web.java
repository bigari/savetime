/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpkg;
//import buvette.*;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author neron
 */
public class Web {
  
private String baseURL = "http://192.168.122.1/savetime/public/api/";
private String token;

public Web(){
    token="";
}
private HttpURLConnection connect(String urlString, String mode, Boolean useJson) throws
        MalformedURLException, IOException{
    
        URL url = new URL(baseURL+urlString);
        HttpURLConnection con =(HttpURLConnection) url.openConnection();
        con.setRequestMethod(mode); 
        if(useJson)
        {
            con.setRequestProperty ("Authorization", "Bearer "+token);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
        }
        else {
             con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }
        
        HttpURLConnection.setFollowRedirects(true);
        con.setInstanceFollowRedirects(false);
        
        
        return con;
   
    
}

public String get(String urlString) throws IOException{
    try{
        HttpURLConnection con =this.connect(urlString, "GET" ,true);
        
        con.connect();
        
        
        
        InputStream ip = con.getInputStream();
        BufferedReader br1 = 
        new BufferedReader(new InputStreamReader(ip));

         StringBuilder response = new StringBuilder();
         String responseSingle;
         while ((responseSingle = br1.readLine()) != null) 
         {
                 response.append(responseSingle);
         }
         return response.toString();
    }
    catch (Exception e) 
    {
            System.out.println(e.getMessage());
            return "";
    }
} 

public String post(String urlString, String postString, boolean useJson) throws IOException{
    byte[] postData = postString.getBytes( StandardCharsets.UTF_8 );
    try{
 
       HttpURLConnection con = this.connect(urlString, "POST",useJson);
       con.setDoOutput(true);
       if (!useJson){
            
            con.setRequestProperty("Content-Length", String.valueOf(postData.length));
            con.setRequestProperty("charset", "utf-8");
            con.connect();
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write( postData );
            }
       }
       else{
            con.connect();
            OutputStream out = con.getOutputStream();
            try (OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8")) {
                osw.write(postString);
                osw.flush();
            }
       }


        System.out.println("Response Code:"
         + con.getResponseCode());
        boolean redirect = false;

	// normally, 3xx is redirect
	int status = con.getResponseCode();
	if (status != HttpURLConnection.HTTP_OK) {
		if (status == HttpURLConnection.HTTP_MOVED_TEMP
			|| status == HttpURLConnection.HTTP_MOVED_PERM
				|| status == HttpURLConnection.HTTP_SEE_OTHER)
		redirect = true;
	}

	System.out.println("Response Code ... " + status+" redirect: "+ redirect+ " "+ postString);

	if (redirect) {

		// get redirect url from "location" header field
		String newUrl = con.getHeaderField("Location");

		// get the cookie if need, for login
		//String cookies = con.getHeaderField("Set-Cookie");
                Map<String, List<String>> hdrs = con.getHeaderFields();
                Set<String> hdrKeys = hdrs.keySet();

                for (String k : hdrKeys)
                  System.out.println("Key: " + k + "  Value: " + hdrs.get(k));


		// open the new connnection again
                con = (HttpURLConnection) new URL(newUrl).openConnection();
                con.setRequestMethod("POST");
                
                hdrs = con.getHeaderFields();
                hdrKeys = hdrs.keySet();
                 System.out.println("Redirection");
                for (String k : hdrKeys)
                  System.out.println("Key: " + k + "  Value: " + hdrs.get(k));
                
		///String res = post(newUrl);						
		System.out.println("Redirect to URL : " + newUrl);
               // return res;

	}
        InputStream ip = con.getInputStream();
        BufferedReader br1 = 
        new BufferedReader(new InputStreamReader(ip));

         StringBuilder response = new StringBuilder();
         String responseSingle;
         while ((responseSingle = br1.readLine()) != null) 
         {
                 response.append(responseSingle);
         }
         return response.toString();
    }
    catch (Exception e) 
    {
            System.out.println(e.getMessage());
            return "[ERROR]";
    }
} 

public String put(String urlString, String putString) throws IOException{
    try{
 
       HttpURLConnection con = this.connect(urlString, "PUT",true);
        
       con.setDoOutput(true);
       con.connect();
        OutputStream out = con.getOutputStream();
        out.write(putString.getBytes());
        out.flush();
        out.close();
        
        InputStream ip = con.getInputStream();
        BufferedReader br1 = 
        new BufferedReader(new InputStreamReader(ip));

         StringBuilder response = new StringBuilder();
         String responseSingle;
         while ((responseSingle = br1.readLine()) != null) 
         {
                 response.append(responseSingle);
         }
         return response.toString();
    }
    catch (Exception e) 
    {
            System.out.println(e.getMessage());
            return "[]";
    }
} 


public String delete(String urlString) throws IOException{
    try{
        HttpURLConnection con =this.connect(urlString, "DELETE",true );
        
       
        con.connect();
        InputStream ip = con.getInputStream();

        BufferedReader br1 = 
        new BufferedReader(new InputStreamReader(ip));

        /* System.out.println("Response Code:"
         + con.getResponseCode());
         System.out.println("Response Message:"
         + con.getResponseMessage());*/

         
         // System.out.println("FollowRedirects:" 
         //			 + HttpURLConnection.getFollowRedirects());

         //System.out.println("Using proxy:" + con.usingProxy());

         StringBuilder response = new StringBuilder();
         String responseSingle;
         while ((responseSingle = br1.readLine()) != null) 
         {
                 response.append(responseSingle);
         }
         return response.toString();
    }
    catch (Exception e) 
    {
            System.out.println(e.getMessage());
            return "[]";
    }
} 


public String getBaseURL() {
    return baseURL;    
}

public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
