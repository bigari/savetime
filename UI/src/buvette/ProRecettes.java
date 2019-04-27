/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buvette;

import org.json.simple.JSONObject;

/**
 *
 * @author Black_Shadow
 */
public class ProRecettes {
    
    private String nom;
    private Double total;
    
     public ProRecettes(JSONObject obj){
       
       this.nom = (String)(obj.get("nom"));
       this.total = ((Number)(obj.get("total"))).doubleValue();
        
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    
    
}
