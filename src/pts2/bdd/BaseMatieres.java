/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import pts2.donnees.Matiere;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class BaseMatieres extends Base<Matiere>{

    public BaseMatieres(BDD bdd) {
        super(bdd, "matieres.xml");
    }
    
    public Matiere rechercher(Object o) {
        if(o instanceof String == false)
            return null;
        Matiere retour = null;
        String string = ((String)o);
        for(Matiere matiere : this.getListeDonnees()) {
            if(matiere.getNom().equals(string) || matiere.getDiminutif().equals(string)) {
                retour = matiere;
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
            xml.ouvrirBalise("Matieres");
            for(Matiere matiere : this.getListeDonnees())
                matiere.sauvegarder(xml);
            xml.fermerBalise();
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void charger() {
        XMLLecture xml = new XMLLecture(this.getCheminAbsolue());
        xml.lire();
        for(XMLObjet matiere : xml.getRacine().getSousCategories()) {
            Matiere m = new Matiere(null, null, null);
            m.charger(matiere);
            System.out.println("[Matiere] Ajout: " + m.getNom());
            this.ajouter(m);
        }
    }
    
}
