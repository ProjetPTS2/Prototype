package pts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Semaine {
    
    private final HashMap<Jours, Journee> listeJours;
    private final int noSemaine;
    
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
}
