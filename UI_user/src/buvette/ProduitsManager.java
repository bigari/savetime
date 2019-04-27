/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

import java.io.IOException;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author neron
 */
public class ProduitsManager extends Manager {
    
    public ArrayList<Produit> produits;
   // private Web w;
    private HashMap<Integer,Integer> idToIndex;

    public ProduitsManager() 
    {
        super();
        this.produits = new ArrayList();
        idToIndex=new HashMap();
      //  w= new Web();
       
    }
    public Produit getProduitById(int id) {
        System.out.println(id);
        System.out.println(idToIndex);
        Integer ind = idToIndex.get(id);
        return produits.get(ind);
    }
    
    
    public void addProduit(Produit p)
    {
        idToIndex.put(p.getId(), produits.size());
        this.produits.add(p);
        
        
    }
    
    public void addNewProduit(Produit p) throws IOException
    {
        
       /* JSONParser parser = new JSONParser();
		
        try{
            Object obj = parser.parse(p.toString());
            JSONObject mes = (JSONObject)obj;
            String res= w.post("api/produits", mes);
            this.produits.add(p);
            System.out.println(res); 
            
      }
      catch(ParseException pe){
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
      }*/
        
        String res= w.post("produits", p.toString(),true);
        idToIndex.put(p.getId(), produits.size());
        this.produits.add(p);
        System.out.println(res);
        
        
        
        
       
    }
    
    
    public void getProduits() throws IOException{
        
        String res= w.get("produits");
        this.produits = new ArrayList();
        
        
        // code to parse JSON to meaningful data
        
        JSONParser parser = new JSONParser();
		
        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;
            
            for(Object jproduit : array)
            {
                Produit p = new Produit((JSONObject)jproduit);
                idToIndex.put(p.getId(), produits.size());
                this.produits.add(p);
            }
            
      }
      catch(ParseException pe){
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
      }
        
        //****************************************
        
      //  System.out.println(this.produits);
        
    }
    
    public void modifierProduit(Produit p) throws IOException{
        
        String res = w.put("produits/"+p.getId(), p.toString());
        
    }
    public void supprimerProduit(Produit p) throws IOException{
        
        String res = w.delete("produits/"+p.getId());
        
    }
    
    
}
