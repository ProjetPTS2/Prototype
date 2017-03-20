/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2;

/**
 *
 * @author local192
 */
public class HeureEDT {
    
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
}
