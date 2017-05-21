/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;

/**
 *
 * @author tgallard
 */
public class Enseignant implements ISauvegarde {
    
    private String nom, prenom, diminutif;
    private final ArrayList<Matiere> listeMatieres;
    
    public Enseignant(String nom, String prenom) {
        this(nom, prenom, null);
    }
    
    public Enseignant(String nom, String prenom, String diminutif) {
        this.nom = nom;
        this.prenom = prenom;
        if(diminutif == null && nom != null && prenom != null)
            this.diminutif = prenom.charAt(0) + "" +  nom.charAt(0) + "" + nom.toLowerCase().charAt(1);
        else
            this.diminutif = "NULL";
        this.listeMatieres = new ArrayList<>();
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public String getPrenom() {
        return this.prenom;
    }
    
    public String getDiminutif() {
        return this.diminutif;
    }
    
    public ArrayList<Matiere> getMatieres() {
        return this.listeMatieres;
    }
    
    public void ajouterMatiere(Matiere matiere) {
        this.listeMatieres.add(matiere);
    }
    
    public void supprimerMatiere(Matiere matiere) {
        this.listeMatieres.remove(matiere);
    }
    
    @Override
    public String toString() {
        return this.nom + " " + this.prenom;
    }

    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("diminutif", this.getDiminutif());
        attributs.put("prenom", this.getPrenom());
        attributs.put("nom", this.getNom());
        xml.ouvrirBalise("Enseignant", attributs, true);
        xml.ouvrirBalise("Matieres");
        for(Matiere matiere : this.getMatieres())
            matiere.sauvegarder(xml);
        xml.fermerBalise();
        xml.fermerBalise();
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.nom = element.getAttribute("nom");
        this.prenom = element.getAttribute("prenom");
        this.diminutif = element.getAttribute("diminutif");
        
        NodeList listeMatieres = ((Element)(element.getElementsByTagName("Matieres").item(0))).getElementsByTagName("Matiere");
        for(int i = 0; i < listeMatieres.getLength(); i++) {
            Element matiere = (Element) listeMatieres.item(i);
            String nomMatiere = matiere.getAttribute("diminutif");
            Matiere m = bdd.getBaseMatieres().rechercher(nomMatiere);
            this.listeMatieres.add(m);
        }
    }
}
