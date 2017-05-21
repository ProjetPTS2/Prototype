/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pts2.bdd.BDD;

/**
 *
 * @author tgallard
 */
public interface ISauvegarde {
    
    /**
     * Permet de gérer la sauvegarde d'une classe.
     */
    public void sauvegarder(XMLSauvegarde xml);
    
    /**
     * Permet de charger une classe à partir d'un fichier XML.
     */
    public void charger(BDD bdd, Element element);
}
