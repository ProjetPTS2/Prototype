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
import pts2.donnees.Semaine;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class BaseSemaines extends Base<Semaine> {

    public BaseSemaines(BDD bdd) {
        super(bdd, "semaines.xml");
    }
    
    public Semaine rechercher(Object o) {
        if(o instanceof Integer == false)
            return null;
        Semaine retour = null;
        int noSemaine = ((Integer)o);
        for(Semaine semaine : this.getListeDonnees()) {
            if(semaine.getNoSemaine() == noSemaine) {
                retour = semaine;
                break;
            }
        }
        
        if(retour == null) {
            retour = new Semaine(noSemaine);
            this.ajouter(retour);
        }
        return retour;
    }

    @Override
    public void sauvegarder() {
        try {
            PrintWriter writer = null;
            writer = new PrintWriter(this.getCheminAbsolue());
            XMLEcriture xml = new XMLEcriture(writer);
            xml.ouvrirBalise("Semaines");
            for(Semaine semaine : this.getListeDonnees())
                semaine.sauvegarder(xml);
            xml.fermerBalise();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseSemaines.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void charger() {
        XMLLecture xml = new XMLLecture(this.getCheminAbsolue());
        xml.lire();
        XMLObjet semaines = xml.rechercherCategorie("Semaines");
        for(XMLObjet semaine : xml.getRacine().getSousCategories()) {
            Semaine s = new Semaine(-1);
            s.charger(semaine);
            System.out.println("[Semaine] Ajout: n°" + s.getNoSemaine());
            this.ajouter(s);
        }
    }
    
}
