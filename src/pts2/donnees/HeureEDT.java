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
    private int heure, minute, duree;
    
    public HeureEDT(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
        this.duree = 0;
    }
    
    public HeureEDT(int heure, int minute, int duree) {
        this.heure = heure;
        this.minute = minute;
        this.duree = duree;
    }
    
    public int getHeure() {
        return this.heure;
    }
    
    public int getMinute() {
        return this.minute;
    }
    
    public int getDuree() {
        return this.duree;
    }
    
    public String getHeureDebutString() {
        String str = this.heure + "h";
        if(this.minute < 10)
            str += "0";
        str += this.minute;
        return str;
    }
    
    public String getHeureFinString() {
        int minuteTotal = this.heure * 60 + this.minute + this.duree;
        String str = (minuteTotal/60) + "h";
        if((minuteTotal%60) < 10)
            str += "0";
        str += (minuteTotal%60);
        return str;
    }
    
    public void setHeure(int heure) {
        this.heure = heure;
    }
    
    public void setMinute(int minute) {
        this.minute = minute;
    }
    
    public void setDuree(int duree) {
        this.duree = duree;
    }

    public boolean compare(HeureEDT heure) {
        return this.heure == heure.heure &&
                this.minute == heure.minute &&
                this.duree == heure.duree;
    }
    
    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Heure");
        xml.ecrireValeur("HeureDebut", ""+this.heure);
        xml.ecrireValeur("MinuteDebut", ""+this.minute);
        xml.ecrireValeur("Duree", ""+this.duree);
        xml.fermerBalise();
    }

    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        this.heure = Integer.parseInt(xml.getPremiereValeur("HeureDebut"));
        this.minute = Integer.parseInt(xml.getPremiereValeur("MinuteDebut"));
        this.duree = Integer.parseInt(xml.getPremiereValeur("Duree"));
    }
}
