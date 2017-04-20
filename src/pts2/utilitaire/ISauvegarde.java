/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import pts2.BDD;

/**
 *
 * @author tgallard
 */
public interface ISauvegarde {
    
    /**
     * Permet de gérer la sauvegarde d'une classe.
     * @param xml L'objet XML à modifier.
     */
    public void sauvegarder(XMLEcriture xml);
    
    /**
     * Permet de charger une classe à partir d'un fichier XML.
     * @param xml L'objet XML à charger.
     * @param bdd La base de données.
     */
    public void charger(XMLObjet xml, BDD bdd);
}
