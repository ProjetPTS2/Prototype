/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tgallard
 */
public class XMLLecture {
    
    private Scanner reader;    
    private XMLObjet objetFinal;
    
    /**
     * Constructeur de la classe XMLLecture
     * @param chemin Le chemin du fichier XML à charger.
     */
    public XMLLecture(String chemin) {
        try {
            this.reader = new Scanner(new File(chemin));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLLecture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Charge toutes les données du fichier XML.
     */
    public void lire() {
        int nbLignes = 0;
        Stack<XMLObjet> objets = new Stack<>();
        while(this.reader.hasNextLine()) {
            String ligne = this.reader.nextLine();
            Matcher matcherCategorie = Pattern.compile("<([^<>]+)>").matcher(ligne);
            
            String nom = "";
            XMLValeur valeur = null;
            boolean finCategorie = false;
            ligne = ligne.replaceAll("\t", "");
            if(ligne.startsWith("<Valeur")) {
                String _valeur = "";
                int index = ligne.indexOf("nom")+5;
                while(ligne.charAt(index) != '"')
                    nom += ligne.charAt(index++);
                
                index = ligne.indexOf("valeur")+8;
                while(ligne.charAt(index) != '"')
                    _valeur += ligne.charAt(index++);
                valeur = new XMLValeur(nom, _valeur);
            } else if(matcherCategorie.find()) {
                nom = matcherCategorie.group(0);
                if(nom.charAt(1) != '/') {
                    nom = nom.replace("<", "").replace(">", "");
                    //System.out.println("Categorie: " + nom);
                }
                else {
                    finCategorie = true;
                    nom = nom.replace("<", "").replace(">", "").replace("/", "");
                    //System.out.println("Fin Categorie: " + nom);
                }
            } else
                System.err.println("Ligne non reconnue");
            
            if(!finCategorie) {
                if(valeur == null) {
                    XMLObjet objet = new XMLObjet(nom);
                    if(!objets.isEmpty())
                        objets.peek().ajouterSousCategorie(objet);
                    objets.add(objet);
                }
                else {
                    if(!objets.isEmpty())
                        objets.peek().ajouterValeur(valeur);
                }
            } else
                this.objetFinal = objets.pop();
            nbLignes++;
        }
        System.out.println(nbLignes + " lignes lues.");
    }
    
    /**
     * Recherche le nom de la balise.
     * @param nomCategorie Le nom de la balise à rechercher.
     * @return Retourne le XMLObjet contenant toutes les données de la balise.
     */
    public XMLObjet rechercherCategorie(String nomCategorie) {
        XMLObjet objet = null;
        for(XMLObjet obj : this.objetFinal.getSousCategories()) {
            if(objet != null)
                continue;
            if(obj.getNom().equals(nomCategorie))
                objet = obj;
        }
        return objet;
    }
    
    
}
