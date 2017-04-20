/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author tgallard
 */
public class XMLObjet {
    
    private final String nom;
    private final ArrayList<XMLObjet> sousCategories;
    private final HashMap<String, ArrayList<String>> valeurs;
    
    /**
     * Constructeur de la classe XMLObjet.
     * @param nom Le nom de l'objet.
     */
    public XMLObjet(String nom) {
        this.nom = nom;
        this.sousCategories = new ArrayList<>();
        this.valeurs = new HashMap<>();
    }
    
    /**
     * Ajoute une sous catégorie à l'objet.
     * @param objet La sous catégorie à ajouter.
     */
    public void ajouterSousCategorie(XMLObjet objet) {
        this.sousCategories.add(objet);
    }
    
    /**
     * Ajoute une valeur à l'objet.
     * @param nomValeur Le nom de la valeur.
     * @param valeur La valeur.
     */
    public void ajouterValeur(String nomValeur, String valeur) {
        if(this.valeurs.containsKey(nomValeur))
            this.valeurs.get(nomValeur).add(valeur);
        else {
            ArrayList<String> liste = new ArrayList<>();
            liste.add(valeur);
            this.valeurs.put(nomValeur, liste);
        }
        //System.out.println(nom  + " Ajout " + nomValeur + "=" + valeur);
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public ArrayList<XMLObjet> getSousCategories() {
        return this.sousCategories;
    }
    
    public String getPremiereValeur(String nomValeur) {
        return valeurs.get(nomValeur).get(0);
    }
    
    public ArrayList<String> getValeurs(String nomValeur) {
        return valeurs.get(nomValeur);
    }
    
    public ArrayList<XMLObjet> getListeSousCategories() {
        return this.sousCategories;
    }
    
    public HashMap<String, ArrayList<String>> getListeValeurs() {
        return this.valeurs;
    }
}
