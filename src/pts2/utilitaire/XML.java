/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Théo-PC
 */
public class XML {
    
    public XML() {
        
    }
    
    public NodeList lire(String chemin) throws ParserConfigurationException, IOException, SAXException {
        File fichier = new File(chemin);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc  = dBuilder.parse(fichier);
        
        doc.getDocumentElement().normalize();
        return doc.getChildNodes();
    }
}
