/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pts2.donnees.Cours;
import pts2.donnees.Semaine;
import pts2.utilitaire.XML;
import pts2.utilitaire.XMLSauvegarde;

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
            XMLSauvegarde xml = new XMLSauvegarde(writer);
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
        try {
            XML xml = new XML();
            NodeList liste = xml.lire(this.getCheminAbsolue()).item(0).getChildNodes();
            for(int i = 0; i < liste.getLength(); i++) {
                Node node = liste.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Semaine e = new Semaine(0);
                    e.charger(this.bdd, element);
                    System.out.println("[Semaine] Ajout: n°" + e.getNoSemaine());
                    for(Cours cours : e.getListeCours())
                        System.out.println("-> Cours " + cours.getCreneau().getHeureDebutString() + " " + cours.getMatiere().getDiminutif() + " " + cours.getTypeCours().getNom());
                    this.ajouter(e);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
