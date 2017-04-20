package pts2.donnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import pts2.BDD;
import pts2.Jours;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;


public class Journee implements ISauvegarde {
    
    private final HashMap<HeureEDT, Cours> listeCours;
    private Jours jour;
    
    public Journee(Jours jour) {
        this.jour = jour;
        this.listeCours = new HashMap<>();
    }
    
    /**
     * Ajoute un cours à la journée.
     * @param heure L'heure du cours.
     * @param cours Le cours à ajouter.
     */
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

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Journee");
        xml.ecrireValeur("Jour", jour.getNom());
        xml.ouvrirBalise("ListeCours");
        for(Entry<HeureEDT, Cours> entree : this.listeCours.entrySet()) {
            xml.ouvrirBalise("Entree");
            entree.getKey().sauvegarder(xml);
            entree.getValue().sauvegarder(xml);
            xml.fermerBalise();
        }
        xml.fermerBalise();
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        this.jour = this.convertirStringEnJours(xml.getPremiereValeur("Jour"));
        for(XMLObjet _listeCours : xml.getSousCategories()) {
            if(!_listeCours.getSousCategories().isEmpty()) {
                XMLObjet entree = _listeCours.getSousCategories().get(0);
                if(!entree.getSousCategories().isEmpty()) {
                    HeureEDT heure = new HeureEDT(-1, -1);
                    heure.charger(entree.getSousCategories().get(0), bdd);
                    Cours c = new Cours(null, null, null, null, null);
                    c.charger(entree.getSousCategories().get(1), bdd);
                    System.out.println("[Cours] Ajout: " + c.getTypeCours() + " " + c.getMatiere().getNom() + " à " + heure.getHeure() + "h" + heure.getMinute());
                    this.listeCours.put(heure, c);
                }
            }
        }
    }
    
    /**
     * Permet de convertir un string en jours.s
     * @param str Le string à convertir.
     * @return Retourne le Jours correspondant.
     */
    private Jours convertirStringEnJours(String str) {
        str = str.toLowerCase();
        Jours jours = Jours.LUNDI;
        switch(str) {
            case "lundi":
                jours = Jours.LUNDI;
                break;
            case "mardi":
                jours = Jours.MARDI;
                break;
            case "mercredi":
                jours = Jours.MERCREDI;
                break;
            case "jeudi":
                jours = Jours.JEUDI;
                break;
            case "vendredi":
                jours = Jours.VENDREDI;
                break;
            case "samedi":
                jours = Jours.SAMEDI;
                break;
            default:break;
        }
        return jours;
    }
}
