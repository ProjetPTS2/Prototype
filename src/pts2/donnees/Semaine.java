package pts2.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pts2.EDT;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;


public class Semaine implements ISauvegarde {
    
    private final List<Cours> listeCours;
    private int noSemaine;
    
    public Semaine(int noSemaine) {
        this.noSemaine = noSemaine;
        this.listeCours = new ArrayList<>();
    }
    
    public void ajouterCours(Cours cours) {
        this.listeCours.add(cours);
    }
    
    public int getNoSemaine() {
        return this.noSemaine;
    }
    
    public List<Cours> getListeCours() {
        return this.listeCours;
    }
    
    public List<Cours> getListeCours(Jours jours) {
        List<Cours> cours = new ArrayList<>();
        for(Cours c : this.listeCours) {
            if(c.getCreneau().getJours().equals(jours))
                cours.add(c);
        }
        return cours;
    }
    
    public boolean intersectionHeure(Creneau creneau) {
        boolean resultat = false;
        for(Cours cours : this.getListeCours(creneau.getJours())) {
            if(cours.getCreneau().intersection(creneau)) {
                resultat = true;
                break;
            }
        }
        return resultat;
    }

    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("numero", this.noSemaine+"");
        xml.ouvrirBalise("Semaine", attributs, true);
        for(Cours cours : this.listeCours)
            cours.sauvegarder(xml);
        xml.fermerBalise();
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.noSemaine = Integer.parseInt(element.getAttribute("numero"));
        NodeList listeCours = element.getElementsByTagName("Cours");
        for(int i = 0; i < listeCours.getLength(); i++) {
            Element e = ((Element)listeCours.item(i));
            Cours cours = new Cours(null, null, null, null, null);
            cours.charger(bdd, e);
            this.listeCours.add(cours);
        }
    }
}
