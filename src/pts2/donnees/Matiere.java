package pts2.donnees;

import javafx.scene.paint.Color;



public class Matiere {
    
    private final String diminutif;
    private final String nom;
    private final Color couleur;
    
    public Matiere(String diminutif, String nom, Color couleur) {
        this.diminutif = diminutif;
        this.nom = nom;
        this.couleur = couleur;
    }
    
    public String getDiminutif() {
        return this.diminutif;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public Color getCouleur() {
        return this.couleur;
    }
}
