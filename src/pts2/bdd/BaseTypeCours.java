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
import pts2.donnees.TypeCours;
import pts2.utilitaire.XML;
import pts2.utilitaire.XMLSauvegarde;

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
            XMLSauvegarde xml = new XMLSauvegarde(writer);
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
        try {
            XML xml = new XML();
            NodeList liste = xml.lire(this.getCheminAbsolue()).item(0).getChildNodes();
            for(int i = 0; i < liste.getLength(); i++) {
                Node node = liste.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    TypeCours e = new TypeCours(null);
                    e.charger(this.bdd, element);
                    System.out.println("[TypeCours] Ajout: " + e.getNom());
                    this.ajouter(e);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(BaseEnseignants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
