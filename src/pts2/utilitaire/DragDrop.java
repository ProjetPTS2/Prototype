/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.utilitaire;

import pts2.donnees.Creneau;

/**
 *
 * @author Théo-PC
 */
public class DragDrop {
    
    private Creneau creneauPlacement;
    private boolean placementInvalide, placementEnCours;
    private double anciennePositionX, anciennePositionY, decalage;
    
    public DragDrop() {}
    
    public void setDecalage(double valeur) {
        this.decalage = valeur;
    }
    
    public void setAnciennePosition(double x, double y) {
        this.anciennePositionX = x;
        this.anciennePositionY = y;
    }
    
    public void setPlacementInvalide(boolean valeur) {
        this.placementInvalide = valeur;
    }
    
    public void setPlacementEnCours(boolean valeur) {
        this.placementEnCours = valeur;
    }
    
    public void setCreneauPlacement(Creneau creneau) {
        this.creneauPlacement = creneau;
    }
    
    public double getAnciennePositionX() {
        return this.anciennePositionX;
    }
    
    public double getAnciennePositionY() {
        return this.anciennePositionY;
    }
    
    public double getDecalage() {
        return this.decalage;
    }
    
    public Creneau getCreneauPlacement() {
        return this.creneauPlacement;
    }
    
    public boolean placementInvalide() {
        return this.placementInvalide;
    }
    
    public boolean placementEnCours() {
        return this.placementEnCours;
    }
}
