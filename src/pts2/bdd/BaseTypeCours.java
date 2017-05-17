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
import pts2.donnees.TypeCours;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author Theo
 */
public class BaseTypeCours extends Base<TypeCours> {

    public BaseTypeCours(BDD bdd) {
        super(bdd, "type_cours.xml");
    }
    
    public TypeCours rechercher(Object o) {
        TypeCours retour = null;
        if(o instanceof String == false)
            return null;
        String nomTypeCours = ((String)o);
        for(TypeCours typeCours : this.getListeDonnees()) {
            if(typeCours.getNom().equals(nomTypeCours)) {
                retour = typeCours;
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
            xml.ouvrirBalise("TypeCours");
            for(TypeCours typeCours : this.getListeDonnees())
                typeCours.sauvegarder(xml);
            xml.fermerBalise();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseTypeCours.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void charger() {
        XMLLecture xml = new XMLLecture(this.getCheminAbsolue());
        xml.lire();
        for(XMLObjet typeCours : xml.getRacine().getSousCategories()) {
            TypeCours s = new TypeCours(null);
            s.charger(typeCours);
            System.out.println("[TypeCours] Ajout: " + s.getNom());
            this.ajouter(s);
        }
    }
    
}
