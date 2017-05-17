package pts2.donnees;

import javafx.scene.paint.Color;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;



public class Matiere implements ISauvegarde {
    
    private String diminutif;
    private String nom;
    private Color couleur;
    
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
    
    public void setDiminutif(String diminutif) {
        this.diminutif = diminutif;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Matiere");
        xml.ecrireValeur("Diminutif", this.getDiminutif());
        xml.ecrireValeur("Nom", this.getNom());
        xml.ecrireValeur("Couleur", (int)(255*this.couleur.getRed())+ ";" + (int)(255*this.couleur.getGreen()) + ";" + (int)(255*this.couleur.getBlue()));
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml) {
        this.diminutif = xml.getPremiereValeur("Diminutif");
        this.nom = xml.getPremiereValeur("Nom");
        String[] clr = xml.getPremiereValeur("Couleur").split(";");
        this.couleur = Color.rgb(Integer.parseInt(clr[0]), Integer.parseInt(clr[1]), Integer.parseInt(clr[2]));
    }
}
