/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pts2.donnees.Enseignant;
import pts2.donnees.Salle;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class BaseEnseignants extends Base<Enseignant> {
    
    public BaseEnseignants(BDD bdd) {
        super(bdd, "enseignants.xml");
    }
    
    public Enseignant rechercher(Object o) {
        if(o instanceof String == false)
            return null;
        Enseignant retour = null;
        String string = ((String)o);
        for(Enseignant enseignant : this.getListeDonnees()) {
            if(enseignant.getDiminutif().equals(string) || enseignant.getNom().equals(string)) {
                retour = enseignant;
                break;
            }
        }
        return retour;
    }

    public void sauvegarder() {
        System.out.println(this.getCheminAbsolue());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(this.getCheminAbsolue()));
            XMLEcriture xml = new XMLEcriture(pw);
            xml.ouvrirBalise("Enseignants");
            for(Enseignant enseignant : this.getListeDonnees())
                enseignant.sauvegarder(xml);
            xml.fermerBalise();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }

    public void charger() {
        XMLLecture xml = new XMLLecture(this.getCheminAbsolue());
        XMLObjet enseignants = xml.rechercherCategorie("Enseignants");
        for(XMLObjet enseignant : enseignants.getSousCategories()) {
            Enseignant e = new Enseignant(null, null);
            e.charger(enseignant);
            System.out.println("[Enseignant] Ajout: " + e.getNom() + " " + e.getPrenom());
            this.ajouter(e);
        }
    }
    
}
