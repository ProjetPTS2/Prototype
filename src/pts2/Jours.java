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
    LUNDI(0, "Lundi"), MARDI(1, "Mardi"), MERCREDI(2, "Mercredi"), JEUDI(3, "Jeudi"), VENDREDI(4, "Vendredi"), SAMEDI(5, "Samedi");
    
    private final String nom;
    private final int numero;
    
    private Jours(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }
     
    public String getNom() {
        return this.nom;
    }
    
    public int getNumero() {
        return this.numero;
    }
}
