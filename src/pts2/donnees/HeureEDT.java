/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;

/**
 *
 * @author local192
 */
public class HeureEDT implements ISauvegarde {

    private int heure, minute, duree;
    
    public HeureEDT(int heure, int minute) {
        this(heure, minute, 0);
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
    
    public boolean intersection(HeureEDT heure) {
        if(this.equals(heure))
            return false;
        if(this.compare(heure))
            return true;
        int totalMinute = this.heure * 60 + this.minute;
        int totalMinute_fin = totalMinute + this.duree;
        int totalMinute2 = heure.getHeure() * 60 + heure.minute;
        int totalMinute2_fin = totalMinute2 + heure.getDuree();
        
        return  ((totalMinute > totalMinute2 && totalMinute < totalMinute2_fin) ||
                (totalMinute_fin > totalMinute2 && totalMinute_fin < totalMinute2_fin) ||
                (totalMinute2 > totalMinute && totalMinute2 < totalMinute_fin) ||
                (totalMinute2_fin > totalMinute && totalMinute2_fin < totalMinute_fin));
    }
    
    public HeureEDT dupliquer() {
        return new HeureEDT(this.heure, this.minute, this.duree);
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
    public void charger(XMLObjet xml) {
        this.heure = Integer.parseInt(xml.getPremiereValeur("HeureDebut"));
        this.minute = Integer.parseInt(xml.getPremiereValeur("MinuteDebut"));
        this.duree = Integer.parseInt(xml.getPremiereValeur("Duree"));
    }
}
