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
/*
$table->increments('id');
            $table->string('nom');
            $table->double('prix', 6,2)->default(0)->nullable(false)->unsigned();
            $table->boolean('disponibilite')->default(true)->nullable(false);
            $table->integer('categorie_id')->unsigned();
            $table->integer('delais');
            $table->timestamps();
*/

public class Produit {
    
    private int id;
    private String nom;
    private double prix;
    private boolean disponibilite;
    private String categorie;
    private int delais;
    
//*********************************************************************************************************
    public Produit(int id, String nom, double prix, boolean disponibilite, String categorie, int delais)
    {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.disponibilite = disponibilite;
        this.categorie = categorie;
        this.delais = delais;
    }
        public Produit(String nom, double prix, boolean disponibilite, String categorie, int delais)
    {
       // this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.disponibilite = disponibilite;
        this.categorie = categorie;
        this.delais = delais;
    }
    
    public Produit(JSONObject obj){
       this.id =((Number)obj.get("id")).intValue();
       this.nom = (String)(obj.get("nom"));
       this.prix = ((Number)(obj.get("prix"))).doubleValue();
       this.disponibilite =  ((Number)obj.get("disponibilite")).intValue() ==1;
       this.categorie = (String) (obj.get("categorie"));
       this.delais = ((Number)obj.get("delais")).intValue(); 
        
    }
    public int getId() {
        return id;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }
     public boolean getDisponibilite() {
       return this.disponibilite;
    }
    

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getDelais() {
        return delais;
    }

    public void setDelais(int delais) {
        this.delais = delais;
    }

    @Override
    public String toString() 
    {
        return "{ \"nom\":" + "\""+nom + "\"" + ", \"prix\":" +prix +
                ", \"dispo\":" + (disponibilite?1:0) + ", \"categorie\":" + 
                "\""+ categorie +"\""  + ", \"delais\":" + delais + '}';
    }
//*********************************************************************************    
    
    
    
}
