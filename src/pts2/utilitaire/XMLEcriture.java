/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import java.io.PrintWriter;
import java.util.Stack;

/**
 *
 * @author tgallard
 */
public class XMLEcriture {
    
    private final Stack stack;
    private final PrintWriter writer;
    
    public XMLEcriture(PrintWriter writer) {
        this.stack = new Stack();
        this.writer = writer;
    }
    
    /**
     * Ouvre une balise <nom> dans le fichier XML.
     * @param nom Le nom de la balise.
     */
    public void ouvrirBalise(String nom) {
        this.faireTabulation();
        this.writer.print("<"+nom+">");
        this.stack.add(nom);
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
     * Permet d'écrire une valeur dans la dernière balise ouverte.
     * @param nomValeur Le nom de la valeur à écrire.
     * @param valeur La valeur à écrire.
     */
    public void ecrireValeur(String nomValeur, String valeur) {
        this.faireTabulation();
        this.writer.print("<"+nomValeur+">");
        this.writer.print(valeur);
        this.writer.print("</"+nomValeur+">");
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
