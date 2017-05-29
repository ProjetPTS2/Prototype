/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;

/**
 *
 * @author Fournier Louis
 */
public class Groupe implements ISauvegarde {
    
    private String nom;
    private List<Groupe> sousGroupes;
    
    public Groupe(String nom) {
        this.nom = nom;
        this.sousGroupes = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }
    
    public List<Groupe> getSousGroupes() {
        return this.sousGroupes;
    }
    
    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("nom", this.getNom());
        xml.ouvrirBalise("Groupe", attributs, true);
        xml.fermerBalise();
    }
    
    @Override
    public void charger(BDD bdd, Element element) {
        this.nom = element.getAttribute("nom"); 
    }
}
