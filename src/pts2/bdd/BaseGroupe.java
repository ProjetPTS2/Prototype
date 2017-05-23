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
import pts2.donnees.Groupe;
import pts2.utilitaire.XML;
import pts2.utilitaire.XMLSauvegarde;
/**
 *
 * @author Fournier Louis
 */
public class BaseGroupe extends Base<Groupe> {
     public BaseGroupe(BDD bdd) {
        super(bdd, "groupe.xml");
    }
    
    public Groupe rechercher(Object o) {
        if(o instanceof String == false)
            return null;
        Groupe retour = null;
        String string = ((String)o);
        for(Groupe groupe : this.getListeDonnees()) {
            if( groupe.getNom().equals(string)) {
                retour = groupe;
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
            for(Groupe groupe : this.getListeDonnees())
                groupe.sauvegarder(xml);
            xml.fermerBalise();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BaseGroupe.class.getName()).log(Level.SEVERE, null, ex);
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
                    Groupe e = new Groupe(null);
                    e.charger(this.bdd, element);
                    System.out.println("[Groupe] Ajout: " + e.getNom());
                    this.ajouter(e);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

