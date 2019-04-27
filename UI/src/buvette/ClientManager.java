/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;


import client.Client;
import java.io.IOException;
import java.util.*;
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
public class ClientManager extends Manager{
    
    public ArrayList<Client> clients;
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
    
    public void incrementerSolde(String email, Double increment){
        
        try {
            w.put("clients/solde/incr", 
                    "{\"email\":"+"\""+email+"\""+", \"increment\":"+increment+ "}"
                    );
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void setPass(String email, String password){
        
        try {
            w.put("clients/password/mod", 
                    "{\"email\":"+"\""+email+"\""+", \"password\":"+password+ "}"
                    );
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
    //Make sure to call getClients first
    public Double getTotalSolde(){
        Double total=0.;
        try {
            getClients();
                  
            for(Client c : clients){
                total+=c.getSolde();
            }
                    
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
    public ArrayList<Client> filtre(String name, String email, double solde_min, double solde_max)
    {
        ArrayList<Client> filtrat = new ArrayList();
        try {
            getClients();
            for(Client c : clients){
                
              //  System.out.println("");
                if(((name==null||name.length()==0)?true:c.getName().equalsIgnoreCase(name)) && ((email==null||email.length()==0)?true:c.getEmail().equals(email))
                   && (c.getSolde()>=solde_min) && (c.getSolde()<=solde_max))
                    filtrat.add(c);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return filtrat;
    }
    
    
}
