package pts2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Journee {
    
    private final HashMap<HeureEDT, Cours> listeCours;
    private final Jours jour;
    
    public Journee(Jours jour) {
        this.jour = jour;
        this.listeCours = new HashMap<>();
    }
    
    public void ajouterCours(HeureEDT heure, Cours cours) {
        this.listeCours.put(heure, cours);
    }
    
    public Entry<HeureEDT, Cours> getCours(int heure) {
        for(Entry<HeureEDT, Cours> entree : this.listeCours.entrySet()) {
            if(entree.getKey().getHeure() == heure)
                return entree;
        }
        return null;
    }
    
    public Cours getCours(HeureEDT heure) {
        return this.listeCours.get(heure);
    }
    
    public ArrayList<Entry<HeureEDT, Cours>> getListeCours() {
        ArrayList<Entry<HeureEDT, Cours>> liste = new ArrayList<>();
        for(Entry<HeureEDT, Cours> entree : this.listeCours.entrySet())
            liste.add(entree);
        return liste;
    }
    
    public Jours getJour() {
        return this.jour;
    }
}
