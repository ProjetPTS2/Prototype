/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class TypeCours implements ISauvegarde {
    
    private String nom;
    
    public TypeCours(String nom) {
        this.nom = nom;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("TypeCours");
        xml.ecrireValeur("Nom", this.getNom());
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml) {
        this.nom = xml.getPremiereValeur("Nom");
    }
    
}
