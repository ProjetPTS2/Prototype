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
 * @author Theo
 */
public class TypeCours implements ISauvegarde {
    
    private String nom;
    
    public TypeCours(String nom) {
        this.nom = nom;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("nom", this.getNom());
        xml.ouvrirBalise("TypeCours", attributs, false);
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.nom = element.getAttribute("nom");
    }
    
}
