/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.File;
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
import pts2.donnees.Enseignant;
import pts2.utilitaire.XML;
import pts2.utilitaire.XMLSauvegarde;

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
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(this.getCheminAbsolue()));
            XMLSauvegarde xml = new XMLSauvegarde(pw);
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

    @Override
    public void charger() {
        try {
            XML xml = new XML();
            NodeList liste = xml.lire(this.getCheminAbsolue()).item(0).getChildNodes();
            for(int i = 0; i < liste.getLength(); i++) {
                Node node = liste.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Enseignant e = new Enseignant(null, null);
                    e.charger(this.bdd, element);
                    System.out.println("[Enseignant] Ajout: " + e.getNom() + " " + e.getPrenom());
                    this.ajouter(e);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
