/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author tgallard
 */
public class XMLSauvegarde {
    
    private final Stack stack;
    private final PrintWriter writer;
    
    public XMLSauvegarde(PrintWriter writer) {
        this.stack = new Stack();
        this.writer = writer;
    }
    
    
    /**
     * Ouvre une balise <nom> dans le fichier XML (sans attributs).
     * @param nom Le nom de la balise.
     */
    public void ouvrirBalise(String nom) {
        this.ouvrirBalise(nom, null, true);
    }
    
    /**
     * Ouvre une balise <nom> dans le fichier XML.
     * @param nom Le nom de la balise.
     * @param attributs Les attributs de la balise.
     * @param estElement Determine si la balise peut contenir d'autres élements.
     */
    public void ouvrirBalise(String nom, Map<String, String> attributs, boolean estElement) {
        this.faireTabulation();
        String ligne = "<"+nom;
        if(attributs != null) {
            for(Entry<String, String> entry : attributs.entrySet())
                ligne += " " + entry.getKey() + "=\"" + entry.getValue() + "\"";
        }
        if(estElement) {
            ligne += ">";
            this.stack.add(nom);
            
        } else
            ligne += "/>";
        this.writer.print(ligne);
        this.writer.println();
    }
    
    /**
     * Ferme la dernière balise ouverte.
     */
    public void fermerBalise() {
        String nom = (String)this.stack.pop();
        this.faireTabulation();
        this.writer.print("</"+nom+">");
        this.writer.println();
    }  
    
    /**
     * Effectue la tabulation dans le fichier XML.
     */
    private void faireTabulation() {
        for(int i = 0; i < this.stack.size(); i++)
            this.writer.print("\t");
    }
    
    
}
