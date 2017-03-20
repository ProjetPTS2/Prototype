/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2;

/**
 *
 * @author local192
 */
public enum Jours {
    LUNDI("Lundi"), MARDI("Mardi"), MERCREDI("Mercredi"), JEUDI("Jeudi"), VENDREDI("Vendredi"), SAMEDI("Samedi");
    
    private final String nom;
    
    private Jours(String nom) {
        this.nom = nom;
    }
     
    public String getNom() {
        return this.nom;
    }
}
