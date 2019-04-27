    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

//import java.text.SimpleDateFormat;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    public ArrayList<Commande> commandes;
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

        String  res = w.get("commandes/nouvelles/get");

        JSONParser parser = new JSONParser();
        commandes = new ArrayList();

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

        String  res = w.get("commandes/anciennes/get");
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
        
        System.out.println("{\"etat\":"+ etat+ ", \"id\":"+ comm.getId()+ "}");

        String  res = w.put("commandes/setEtat/set"

               , "{\"etat\":"+ etat+ ", \"id\":"+ comm.getId()+", \"observations\":"+ "\""+comm.getObservations()+"\""+  "}");
    }
     public void resetEtat(Commande comm) throws IOException{

        String  res = w.put("commandes/resetEtat/reset"

                , "{\"etat\":"+ comm.getEtat()+ ", \"id\":"+ comm.getId()+"}");
    }
     
    public void resetAll() throws IOException{
        for(Commande c: commandes){
            resetEtat(c);
        }
    }
    
    public ArrayList<ProRecettes> comptaParProduit(String date) throws IOException{
        String  res = w.post("commandes/compta/parproduit", "{\"date\":"+ "\""+ date+ "\"}", true);

        JSONParser parser = new JSONParser();
        ArrayList<ProRecettes> lpr = new ArrayList();

        try{
            Object obj = parser.parse(res);
            JSONArray array = (JSONArray)obj;

            for(Object jpror : array)
            {
                ProRecettes pr = new ProRecettes((JSONObject)jpror);
                lpr.add(pr);
            }

        } catch(ParseException pe)
        {

         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }
        
        return lpr;
    }
      public Double comptaTotal(String date) throws IOException{
        String  res = w.post("commandes/compta/total", "{\"date\":"+ "\""+ date+ "\"}", true);

        JSONParser parser = new JSONParser();
        
        Double total =0.;
       

        try{
            Object obj = parser.parse(res);
            JSONObject jobj = (JSONObject)obj;
            total = ((Number)(jobj.get("total"))).doubleValue();
           

        } catch(ParseException pe)
        {

         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
        }
        
        return total;
    }
    
     private void archiver(String archive) throws IOException{
        
    
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
                File file = new File("commandes.txt");

                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                // true = append file
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);

                bw.write(archive);

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
        
    public void formatComm(Commande c, ProduitsManager pro) throws IOException, ParseException {
       
        /* if(  c.getEtat() !=2 || c.getEtat() !=4 )
        return;*/
        String item_lines = "";
        
        for(Item it: c.getProduits()) {

            item_lines+="+"+pro.getProduitById(it.getProduit_id()).getNom()+
                    ": "+it.getQuantite()+" "+((it.getRemarq()==null ||it.getRemarq()==null)?" ":it.getRemarq().replace("|","").replace("\n", "@#%"))+"@#%";
         }
        
        String line= String.format("%s|%s|%s|%s|%s\n", c.getDate(),
                                                 c.getCode(), //1
                                                 c.getTotal(), //2
                                                 c.getEtat(), //3
                                                 item_lines //4
                                                 );
         //notifs.add(c);
        System.out.println(line);
        archiver(line); 
   // }
        System.out.println(c);
        //return c;
    }
   
}
