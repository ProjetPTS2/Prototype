package pts2.donnees;

import java.util.ArrayList;
import java.util.List;
import pts2.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;


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

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Semaine");
        xml.ecrireValeur("NoSemaine", this.noSemaine+"");
        for(Cours cours : this.listeCours) {
            cours.sauvegarder(xml);
        }
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        this.noSemaine = Integer.parseInt(xml.getPremiereValeur("NoSemaine"));
        for(XMLObjet _cours : xml.getSousCategories()) {
            Cours cours = new Cours(null, null, null, null, null, null);
            cours.charger(_cours, bdd);
            this.listeCours.add(cours);
        }
    }
}
