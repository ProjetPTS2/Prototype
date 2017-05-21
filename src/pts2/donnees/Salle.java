/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;

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
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("nom", this.getNom());
        attributs.put("capacite", this.capacite + "");
        xml.ouvrirBalise("Salle", attributs, false);
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.nom = element.getAttribute("nom");
        this.capacite = Integer.parseInt(element.getAttribute("capacite"));
    }
}
