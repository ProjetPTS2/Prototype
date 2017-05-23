/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.HashMap;
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
   public Groupe (String nom)
    {
        this.nom=nom;
    }

    public String getNom() {
        return nom;
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
