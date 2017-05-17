/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Theo
 */
public abstract class Base<T> {
    
    private final List<T> liste;
    protected BDD bdd;
    private String nomFichier;
    
    public Base(BDD bdd, String nomFichier) {
        this.liste = new ArrayList<T>();
        this.bdd = bdd;
        this.nomFichier = nomFichier;
    }
    
    public boolean ajouter(T e) {
        if(this.liste.contains(e))
            return false;
        else
            this.liste.add(e);
        return true;
    }
    
    
    /**
     * Rechercher un objet dans la base de données.
     * @param o L'identificateur de l'objet (exemple: le nom d'une salle)
     * @return Retourne l'objet trouvé, sinon null.
     */
    public abstract T rechercher(Object o);
    
    /**
     * Vérifie si un objet existe dans la base.
     * @param o L'identificateur de l'objet (exemple: le nom d'une salle)
     * @return Retourne true si l'objet a été trouvé, false sinon.
     */
    public boolean existe(Object o) {
        return this.rechercher(o) != null;
    }
    
    /**
     * Supprime un objet dans la base de données.
     * @param e L'objet à supprimer.
     * @return Retourne true si l'objet a bien été supprimé, false sinon.
     */
    public boolean supprimer(T e) {
        return this.liste.remove(e);
    }
    
    /**
     * Renvoie toutes les données contenues dans la base.
     * @return Renvoie toutes les données contenues dans la base.
     */
    public List<T> getListeDonnees() {
        return this.liste;
    }
    
    public String getNomFichier() {
        return this.nomFichier;
    }
    
    public String getCheminAbsolue() {
        return this.bdd.getCheminRepertoire() + '\\' + this.nomFichier;
    }
    
    public abstract void sauvegarder();
    public abstract void charger();
}
