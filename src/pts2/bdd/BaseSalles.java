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
import pts2.donnees.Salle;
import pts2.utilitaire.XML;
import pts2.utilitaire.XMLSauvegarde;

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
            XMLSauvegarde xml = new XMLSauvegarde(writer);
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
        try {
            XML xml = new XML();
            NodeList liste = xml.lire(this.getCheminAbsolue()).item(0).getChildNodes();
            for(int i = 0; i < liste.getLength(); i++) {
                Node node = liste.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Salle e = new Salle(null, 0);
                    e.charger(this.bdd, element);
                    System.out.println("[Salle] Ajout: " + e.getNom());
                    this.ajouter(e);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
