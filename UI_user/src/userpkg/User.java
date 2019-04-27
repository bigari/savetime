/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userpkg;


import java.io.IOException;
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Black_Shadow
 */
public abstract class User {
    private String email;
    private String password;
    private String token;
    protected Web w;
    
    public User() {
        w=new Web();
    }
    
     public User(String email, String password){
        this.email=email;
        this.password=password;
        w= new Web();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        w.setToken(token);
    }

    public Web getW() {
        return w;
    }

    public void setW(Web w) {
        this.w = w;
    }
    
    public boolean login() throws IOException{

       String postData=URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+
              URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

       String res;
       res= w.post("login", postData,false);

       JSONParser parser = new JSONParser();

       try{
           Object obj = parser.parse(res);
           JSONObject code = (JSONObject)obj;

           JSONObject succ=(JSONObject)code.get("success");
           setToken((String)succ.get("token"));
           return true;
        }
        catch(ParseException pe){

           System.out.println("position: " + pe.getPosition());
           System.out.println(pe);
           return false;
        }
    }
    
}
