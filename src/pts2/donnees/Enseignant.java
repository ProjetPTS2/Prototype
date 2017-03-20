/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.ArrayList;

/**
 *
 * @author tgallard
 */
public class Enseignant {
    
    private final String nom, prenom, diminutif;
    private final ArrayList<Matiere> listeMatieres;
    
    public Enseignant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.diminutif = prenom.charAt(0) + "" +  nom.charAt(0) + "" + nom.toLowerCase().charAt(1);
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
}
