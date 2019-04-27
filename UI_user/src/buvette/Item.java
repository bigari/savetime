/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

import org.json.simple.JSONObject;

/**
 *
 * @author neron
 */
public class Item {
    private int produit_id;
    private String remarques;
    private int quantite;


    public Item(JSONObject obj) {
       this.produit_id =((Number)obj.get("produit_id")).intValue();
       this.remarques = (String)(obj.get("remarques"));
       this.quantite =((Number)obj.get("quantite")).intValue();
    }
      public Item(int id, String rmq,int qte) {
       this.produit_id =id;
       this.remarques = rmq;
       this.quantite =qte;
    }
    
    public String getRemarq() {
        return remarques;
    }

    public void setRemarq(String remarques) {
        this.remarques = remarques;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "{ \"produit_id\":" + this.produit_id + ", \"quantite\":" + this.quantite +
                ", \"remarques\":" + "\""+this.remarques + "\"}";
    }
    
    
    
}
