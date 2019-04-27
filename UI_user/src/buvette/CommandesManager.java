    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

//import java.text.SimpleDateFormat;  
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
public class CommandesManager extends Manager {
    
    //private Web w;
    private ArrayList<Commande> commandes;
    //private ArrayList<Commande> pastComm;

    public CommandesManager() {
        super();
        //this.w = new Web();
    }

    public void setListeCommandes(ArrayList<Commande> commandes) {
        this.commandes = commandes;
    }
    

    public ArrayList<Item> getProduitsCommandes(int id) throws IOException {
        String res= w.get("produits_commandes/"+id);

        ArrayList<Item> items = new ArrayList();
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;

            for(Object jitem : array)
            {
                Item it = new Item((JSONObject)jitem);
                items.add(it); 
            }

        }
        catch(ParseException pe){

           System.out.println("position: " + pe.getPosition());
           System.out.println(pe);
        }



        return items;

    }

    //Beaucoup de donnees
    public void getCommandes() throws IOException {

        String  res = w.get("commandes" );

        // Exploiting JSON here
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;

            for(Object jcommande : array)
            {
                Commande c = new Commande((JSONObject)jcommande);
                c.addProduits(this.getProduitsCommandes(c.getId())); 
                this.commandes.add(c);
            }

        } catch(ParseException pe)
        {

         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }
    }

    public void getNewComm() throws IOException{

        String  res = w.get("commandes/nouvelles");

        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;

            for(Object jcommande : array)
            {
                Commande c = new Commande((JSONObject)jcommande);
                c.addProduits(this.getProduitsCommandes(c.getId())); 
                this.commandes.add(c);
            }

        } catch(ParseException pe)
        {

         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }


    }

    public void getPastComm() throws IOException{

        String  res = w.get("commandes/anciennes");
        JSONParser parser = new JSONParser();
		
        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;
            
            for(Object jcommande : array)
            {
                Commande c = new Commande((JSONObject)jcommande);
                c.addProduits(this.getProduitsCommandes(c.getId())); 
                this.commandes.add(0,c);
            }
            
        } catch(ParseException pe)
        {
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }

    }
    
    public ArrayList<Commande> getRangeCommande(Date from, Date to) throws IOException{
        ArrayList<Commande> comm = new ArrayList();
        
        String res = w.post("commandes/range", "{\"from:\""+from+",\"to:\""+to+"}",true);
        
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;

            for(Object jcommande : array)
            {
                Commande c = new Commande((JSONObject)jcommande);
                c.addProduits(this.getProduitsCommandes(c.getId())); 
                comm.add(c);
            }

        } catch(ParseException pe)
        {

         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }
        
        return comm;
        
    }

    public void setEtat(Commande comm,int etat) throws IOException{

        String  res = w.put("commandes/setEtat/"+

                comm.getId(), "{\"etat\":"+ etat+ "+}");
    }
}
