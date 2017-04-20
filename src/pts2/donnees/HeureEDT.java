/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import pts2.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author local192
 */
public class HeureEDT implements ISauvegarde {
    
    // Changer heureFin minuteFin en duree(min)
    private int heure, minute, heureFin, minuteFin;
    
    public HeureEDT(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
        this.heureFin = heure+1;
        this.minuteFin = minute;
    }
    
    public HeureEDT(int heure, int minute, int heureFin, int minuteFin) {
        this.heure = heure;
        this.minute = minute;
        this.heureFin = heureFin;
        this.minuteFin = minuteFin;
    }
    
    public int getHeure() {
        return this.heure;
    }
    
    public int getHeureFin() {
        return this.heureFin;
    }
    
    public int getMinute() {
        return this.minute;
    }
    
    public int getMinuteFin() {
        return this.minuteFin;
    }
    
    public double getDuree() {
        double debut = ((double)heure * 100 + ((double)minute*100/60))/100;
        double fin = ((double)heureFin * 100 + ((double)minuteFin*100/60))/100;
        return fin - debut;
    }
    
    public String getHeureDebutString() {
        String str = heure + "h";
        if(minute < 10)
            str += "0";
        str += minute;
        return str;
    }
    
    public String getHeureFinString() {
        String str = heureFin + "h";
        if(minuteFin < 10)
            str += "0";
        str += minuteFin;
        return str;
    }
    
    public void setHeure(int heure) {
        this.heure = heure;
    }
    
    public void setHeureFin(int heure) {
        this.heureFin = heure;
    }
    
    public void setMinute(int minute) {
        this.minute = minute;
    }
    
    public void setMinuteFin(int minute) {
        this.minuteFin = minute;
    }

    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Heure");
        xml.ecrireValeur("HeureDebut", ""+this.heure);
        xml.ecrireValeur("MinuteDebut", ""+this.minute);
        xml.ecrireValeur("HeureFin", ""+this.heureFin);
        xml.ecrireValeur("MinuteFin", ""+this.minuteFin);
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        this.heure = Integer.parseInt(xml.getPremiereValeur("HeureDebut"));
        this.minute = Integer.parseInt(xml.getPremiereValeur("MinuteDebut"));
        this.heureFin = Integer.parseInt(xml.getPremiereValeur("HeureFin"));
        this.minuteFin = Integer.parseInt(xml.getPremiereValeur("MinuteFin"));
    }
}
