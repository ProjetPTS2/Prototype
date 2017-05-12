/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import pts2.donnees.Salle;
import pts2.donnees.Semaine;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class BaseSalles extends Base<Salle> {

    public BaseSalles(BDD bdd) {
        super(bdd, "salles.xml");
    }
    
    public Salle rechercher(Object o) {
        if(o instanceof String == false)
            return null;
        Salle retour = null;
        String nomSalle = ((String)o);
        for(Salle salle : this.getListeDonnees()) {
            if(salle.getNom().equals(nomSalle)) {
                retour = salle;
                break;
            }
        }
        return retour;
    }

    @Override
    public void sauvegarder() {
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(this.getCheminAbsolue());
            XMLEcriture xml = new XMLEcriture(writer);
            xml.ouvrirBalise("Salles");
            for(Salle salle : this.getListeDonnees())
                salle.sauvegarder(xml);
            xml.fermerBalise();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseSalles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void charger() {
        XMLLecture xml = new XMLLecture(this.getCheminAbsolue());
        xml.lire();
        XMLObjet semaines = xml.rechercherCategorie("Salles");
        for(XMLObjet semaine : semaines.getSousCategories()) {
            Salle s = new Salle(null, 0);
            s.charger(semaine);
            System.out.println("[Salle] Ajout: " + s.getNom());
            this.ajouter(s);
        }
    }
    
}
