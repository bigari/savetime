/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;


import client.Client;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author neron
 */
public class ClientManager extends Manager{
    
    private ArrayList<Client> clients;
   // private Web w;

    public ClientManager() {
        super();
        this.clients = new ArrayList();
       // this.w = new Web();
    }
    

    
    public void addClient(Client c){
        this.clients.add(c);
    }
    
    public void addNewClient(Client c) throws IOException{
        
        String res=w.post("clientsRegister", c.toString(),true);
        
        System.out.println(res);  
        this.clients.add(c);
        
    }
    
    public void getClients() throws IOException{

        String res= w.get("clients");
        this.clients = new ArrayList();
        
        
        // code to parse JSON to meaningful data
        
        JSONParser parser = new JSONParser();
		
        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;
            
            for(Object jclient : array)
            {
                Client c = new Client((JSONObject)jclient);
                this.clients.add(c);
            }
            
      }
      catch(ParseException pe){
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
      }
        
    }
    
    public void modifierClient(Client c) throws IOException, ParseException{
     
        String res = w.put("clients/"+c.getId(), c.toString());
        
    }
    
    public void supprimerClient(Client c) throws IOException{
        
        String res = w.delete("clients/"+c.getId());
        
    }
    
   
    //Make sure to call getClients first
    public float getTotalSolde(){
        float total=0;
        for(Client c : clients){
            total+=c.getSolde();
        }
        return total;
        
    }
    
    
}
