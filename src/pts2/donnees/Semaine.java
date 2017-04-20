package pts2.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import pts2.BDD;
import pts2.Jours;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;


public class Semaine implements ISauvegarde {
    
    private final HashMap<Jours, Journee> listeJours;
    private int noSemaine;
    
    public Semaine(int noSemaine) {
        this.noSemaine = noSemaine;
        this.listeJours = new HashMap<>();
        for(Jours jour : Jours.values())
            listeJours.put(jour, new Journee(jour));
    }
    
    public void ajouterCours(HeureEDT heure, Cours cours, Jours jour) {
        this.listeJours.get(jour).ajouterCours(heure, cours);
    }
    
    public int getNoSemaine() {
        return this.noSemaine;
    }
    
    public Entry<HeureEDT, Cours> getCours(int heure, Jours jours) {
        return this.listeJours.get(jours).getCours(heure);
    }
    
    public Cours getCours(HeureEDT heure, Jours jour) {
        return this.listeJours.get(jour).getCours(heure);
    }
    
    public ArrayList<Entry<HeureEDT, Cours>> getListeCours(Jours jours) {
        return this.listeJours.get(jours).getListeCours();
    }

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Semaine");
        xml.ecrireValeur("NoSemaine", this.noSemaine+"");
        for(Entry<Jours, Journee> entree : this.listeJours.entrySet()) {
            xml.ouvrirBalise(entree.getKey().getNom());
            entree.getValue().sauvegarder(xml);
            xml.fermerBalise();
        }
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        this.noSemaine = Integer.parseInt(xml.getPremiereValeur("NoSemaine"));
        for(XMLObjet jour : xml.getSousCategories()) {
            Journee journee = new Journee(null);
            journee.charger(jour.getSousCategories().get(0), bdd);
            this.listeJours.put(journee.getJour(), journee);
        }
    }
}
