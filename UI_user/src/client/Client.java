/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import userpkg.User;
import userpkg.Web;
import buvette.Commande;
import buvette.Item;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author neron
 */
/*
  $table->increments('id');
            $table->string('name');
            $table->string('email')->unique();
            $table->string('password');
            $table->Double('solde', 8, 2)->default(0)->nullable(false)->unsigned();
            $table->rememberToken();
            $table->timestamps();
*/

public class Client extends User {
    
   // private String token;
    private int id;
    private String name;
   // private String email;
   // private String password;
    private Double solde;
    //private Web w;
    
    public Client(String email, String password){
        super.setEmail(email);
        super.setPassword(password);
        w= new Web();
    }

    public Client(int id, String name, String email, String password, Double solde) {
        this.id=id;
        this.name = name;
        super.setEmail(email);
        super.setPassword(password);
        this.solde = solde;
        w = new Web();
    }
    public Client(String name, String email, String password, Double solde) {

        this.name = name;
        super.setEmail(email);
        super.setPassword(password);
        this.solde = solde;
        w = new Web();
    }
    
    public Client(JSONObject obj){
        this.id =((Number)obj.get("id")).intValue();
        this.name = (String)(obj.get("name"));
        super.setEmail((String)(obj.get("email")));
        this.solde = ((Number)(obj.get("solde"))).doubleValue();
        w = new Web();

 }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() 
    {
        return "{\"id\":" +id +", \"name\":" +"\"" +name + "\"" + ", \"email\":" +"\"" +getEmail() + "\"" +
                 ", \"password\":" +"\"" +getPassword() +"\"" +
                ", \"solde\":" + solde +'}';
             
    }
    
    //-----------------------------------------------------------------------
    
   
     public void getInfo() throws IOException{
         String res = w.get("clients/info");
           JSONParser parser = new JSONParser();
		
        try{
            Object obj = parser.parse(res);
            JSONObject jclient = (JSONObject)obj;
            
            Client c = new Client(jclient);
            this.name= c.getName();
            this.solde= c.getSolde();
            this.id=c.getId();
                
            
      }
      catch(ParseException pe){
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
      }
     }
     public boolean commander(Commande c){
        System.out.println(c.toString());
        try {
            w.post("commandes", c.toString(),true);
            this.substract(c.getTotal());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
     }
    
    public void substract(Double cost){
        this.solde-=cost;
    }
    
    protected void writeNotif(String notif) throws IOException{
        
    
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
                File file = new File("notifications"+this.id+".txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                // true = append file
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);

                bw.write(notif);

                System.out.println("Done");

        } catch (IOException e) {

                e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                bw.close();
                if (fw != null)
                        fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }
        
    public Commande getNotification() throws IOException, ParseException {
        String res=w.get("clients/notifications");
        JSONParser parser = new JSONParser();
        
       
        /* JSONArray list_comm = (JSONArray)parser.parse(res);
        ArrayList<Commande> notifs = new ArrayList();*/
        String line;
        JSONObject jcomm = (JSONObject)parser.parse(res);
        if(  ((Number)jcomm.get("etat")).intValue() >3 )
           return null;
       // for(Object obj: list_comm) {
        //     JSONObject jcomm = (JSONObject) obj;
             Commande c =new Commande(jcomm);
             
             /* 
             String item_line = "";
             for(Item it : c.getProduits()) {
                item_line+=it.getQuantite()+it.
             }*/
             line= String.format("%s|%s|%s|%s|%s\n", c.getDate(),
                                                     c.getCode(),
                                                     c.getTotal(),
                                                     c.getEtat(),
                                                     c.getObservationsGerant());
             //notifs.add(c);
             System.out.println(line);
             writeNotif(line); 
       // }
            System.out.println(c);
            return c;
    }
   
    
}
