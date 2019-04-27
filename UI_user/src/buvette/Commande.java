/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

  
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import userpkg.Web;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author neron
 */
public class Commande {
    
   private ArrayList<Item> produits;
   private String observations;
   private String observationsGerant;
   private int id;
   private int client_id;
   private double total;
   private long deadline;
   private String date;
   private int etat;
   private String code;
   
    public Commande(ArrayList<Item> produits, String observations, int id,long deadline, String date) {
        this.produits = produits;
        this.observations = observations;
        this.id = id;
        this.total = 0;
        this.deadline = deadline;
        this.date = date;
    }
        public Commande(ArrayList<Item> produits, int client_id, double total, long deadline) {
        this.produits = produits;
        this.total = total;
        this.deadline = deadline;
        this.client_id=client_id;
    }
    
    public Commande(JSONObject obj) {
       this.id =((Number)obj.get("id")).intValue();
       this.client_id =((Number)obj.get("client_id")).intValue();
       this.observations = (String)(obj.get("observationsGerant"));
       this.total = ((Number)(obj.get("total"))).doubleValue();
       this.deadline =  ((Number)obj.get("deadline")).longValue();
       this.date = (String) (obj.get("updated_at"));
       this.code = (String) (obj.get("code"));
       this.etat = ((Number)obj.get("etat")).intValue();
       this.produits = new ArrayList();
       try {
           // Parceque parfois le champs "produits" n'est pas accessible
            JSONArray jcomm = (JSONArray)obj.get("produits");
            for(Object oitem : jcomm){
                JSONObject jitem = (JSONObject) oitem;
                this.produits.add(new Item(jitem));
            }
       }
       catch (Exception e){
           
       }
    }
   
  /* public double evalTotal(){
       
       for(Item ite:produits){
           total+= ite.getPrix();
       }
    
       return this.total;
   }
*/
    public ArrayList<Item> getProduits() {
        return produits;
    }

    public void addProduits(ArrayList<Item> produits) {
        this.produits.addAll(produits);
    }
    public void setProduits(ArrayList<Item> produits) {
        this.produits=produits;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    

    @Override
    public String toString() {
        
        JSONArray listprod = new JSONArray();
         JSONParser parser = new JSONParser();
         for (Item prod : produits) {
            try {
                JSONObject obj = (JSONObject)parser.parse(prod.toString());
                listprod.add(obj);
                
            } catch (ParseException ex) {
                Logger.getLogger(Commande.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         JSONObject jcomm = new JSONObject();
         jcomm.put("client_id", client_id);
         jcomm.put("total", total);
         jcomm.put("deadline", deadline);
         jcomm.put("produits", listprod);
         return jcomm.toJSONString();     
    }
  

    public int getClient_id() {
        return client_id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    

    public String getCode() {
        return code;
    }
    
    public String getObservationsGerant() throws IOException{
        Web w = new Web();
        this.observationsGerant=w.get("commandes/observationsGerant/"+getId());
        
        return observationsGerant;
        
    }
    
   
    
}
