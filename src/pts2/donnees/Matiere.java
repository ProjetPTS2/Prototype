package pts2.donnees;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;



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
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("diminutif", this.getDiminutif());
        attributs.put("nom", this.getNom());
        attributs.put("couleur", String.format( "#%02X%02X%02X",
            (int)( this.couleur.getRed() * 255 ),
            (int)( this.couleur.getGreen() * 255 ),
            (int)( this.couleur.getBlue() * 255 )));
        xml.ouvrirBalise("Matiere", attributs, false);
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.diminutif = element.getAttribute("diminutif");
        this.nom = element.getAttribute("nom");
        this.couleur = Color.web(element.getAttribute("couleur"));
    }
}
