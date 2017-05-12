/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.ArrayList;
import pts2.EDT;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;

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
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Enseignant");
        xml.ecrireValeur("Diminutif", this.getDiminutif());
        xml.ecrireValeur("Nom", this.getNom());
        xml.ecrireValeur("Prenom", this.getPrenom());
        xml.ouvrirBalise("Matieres");
        for(Matiere matiere : this.getMatieres())
            matiere.sauvegarder(xml);
        xml.fermerBalise();
        xml.fermerBalise();
    }

    public void charger(XMLObjet xml) {
        this.nom = xml.getPremiereValeur("Nom");
        this.prenom = xml.getPremiereValeur("Prenom");
        this.diminutif = prenom.charAt(0) + "" +  nom.charAt(0) + "" + nom.toLowerCase().charAt(1);
        
        for(XMLObjet matiere : xml.getSousCategories().get(0).getSousCategories()) {
            String nomMatiere = matiere.getPremiereValeur("Diminutif");
            this.listeMatieres.add(EDT.getInstance().getBDD().getBaseMatieres().rechercher(nomMatiere));
        }
    }
}
