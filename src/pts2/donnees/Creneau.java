/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.donnees;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;

/**
 *
 * @author local192
 */
public class Creneau implements ISauvegarde {

    private Jours jours;
    private int heure, minute, duree;
    
    public Creneau(Jours jours, int heure, int minute, int duree) {
        this.jours = jours;
        this.heure = heure;
        this.minute = minute;
        this.duree = duree;
    }
    
    public Jours getJours() {
        return this.jours;
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
    
    public void setJours(Jours jours) {
        this.jours = jours;
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

    public boolean compare(Creneau heure) {
        return this.heure == heure.heure &&
                this.minute == heure.minute &&
                this.duree == heure.duree;
    }
    
    public boolean intersection(Creneau heure) {
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
    
    public Creneau dupliquer() {
        return new Creneau(this.jours, this.heure, this.minute, this.duree);
    }
    
    
    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("jours", this.jours.getNom());
        attributs.put("heure", ""+this.heure);
        attributs.put("minute", ""+this.minute);
        attributs.put("duree", ""+this.duree);
        
        xml.ouvrirBalise("Creneau", attributs, false);
    }

    @Override
    public void charger(BDD bdd, Element element) {
        this.jours = Jours.valueOf(element.getAttribute("jours").toUpperCase());
        this.heure = Integer.parseInt(element.getAttribute("heure"));
        this.minute = Integer.parseInt(element.getAttribute("minute"));
        this.duree = Integer.parseInt(element.getAttribute("duree"));
    }
}
