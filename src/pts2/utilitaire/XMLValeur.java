/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

/**
 *
 * @author tgallard
 */
public class XMLValeur {
    
    private final String nom, valeur;
    
    /**
     * Constructeur de la classe XMLValeur.
     * @param nom Le nom de la valeur.
     * @param valeur La valeur.
     */
    public XMLValeur(String nom, String valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public String getValeur() {
        return this.valeur;
    }
}
