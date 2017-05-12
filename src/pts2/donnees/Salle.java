/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author tgallard
 */
public class Salle implements ISauvegarde {
    
    private String nom;
    private int capacite;
    
    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public int getCapacite() {
        return this.capacite;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    
    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Salle");
        xml.ecrireValeur("Nom", this.getNom());
        xml.ecrireValeur("Capacite", this.capacite + "");
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml) {
        this.nom = xml.getPremiereValeur("Nom");
        this.capacite = Integer.parseInt(xml.getPremiereValeur("Capacite"));
    }
}
