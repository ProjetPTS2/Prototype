/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import java.util.Calendar;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pts2.Constantes;
import pts2.EDT;
import pts2.Jours;
import pts2.donnees.Cours;
import pts2.donnees.HeureEDT;
import pts2.donnees.Semaine;

/**
 *
 * @author Theo
 */
public class ComposantEDT extends Pane {
    
    private Node parent;
    private Group edtCours;
    private ComposantSurvol texteSurvol;
    private Semaine semaineActuelle;
    
    public ComposantEDT(Node parent, ComposantSurvol texteSurvol) {
        this.parent = parent;
        this.edtCours = new Group();
        this.texteSurvol = texteSurvol;
        this.texteSurvol.setVisible(false);
    }
    
    public void initaliserEDT(Semaine semaine) {
        this.semaineActuelle = semaine;
        // AFFICHAGE DES JOURS
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, semaine.getNoSemaine());
        for(int idJour = 0; idJour < Jours.values().length; idJour++) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + idJour);
            String mois = this.convertirMoisCalendarEnString(calendar.get(Calendar.MONTH));
            ComposantTexte comp = new ComposantTexte(Jours.values()[idJour].getNom() + " " + calendar.get(Calendar.DATE) + " " + mois, Constantes.MARGE_HORIZONTAL, idJour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL, Constantes.LARGEUR_JOURS, Constantes.HAUTEUR_JOURS);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.getChildren().add(comp);
        }
        
        // AFFICHAGE DES HEURES
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            ComposantTexte comp = new ComposantTexte(heure + "h", (heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, Constantes.MARGE_VERTICAL, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_HEURES);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.getChildren().add(comp);
        }
        
        // AFFICHAGE DES CASES COURS
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            for(int jour = 0; jour < Jours.values().length; jour++) {
                ComposantCaseEDT comp = new ComposantCaseEDT((heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, jour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL);
                this.getChildren().add(comp);
            }
        }
        
        this.getChildren().add(this.edtCours);
    }   
    
    public void actualiser(boolean actualiserSemaine) {
        if(!actualiserSemaine)
            this.actualiser(this.semaineActuelle);
        else
            this.actualiser(EDT.getInstance().getBDD().getSemaine(this.semaineActuelle.getNoSemaine()));
    }
    
    public void actualiser(Semaine semaine) {
        this.semaineActuelle = semaine;
        if(this.getChildren().isEmpty())
            this.initaliserEDT(semaine);
        
        this.edtCours.getChildren().clear();
        
        // AFFICHAGE DES COURS
        for(int jour = 0; jour < Jours.values().length; jour++) {
            for(Map.Entry<HeureEDT, Cours> cours : semaine.getListeCours(Jours.values()[jour])) {
                    ComposantHeure coursComposant = new ComposantHeure(cours.getKey(), cours.getValue(), "", (cours.getKey().getHeure()-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, jour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL);
                    coursComposant.setTexte(cours.getValue().getMatiere().getDiminutif() + " - " + cours.getValue().getTypeCours());
                    coursComposant.initialiserEvents(this.getScene(), this.parent, this.texteSurvol);
                    this.edtCours.getChildren().add(coursComposant);
            }
        }
        this.texteSurvol.toFront();
    }
    
    private String convertirMoisCalendarEnString(int idMois) {
        String mois;
        switch(idMois) {
            case 0:
                mois = "Janvier";
                break;
            case 1:
                mois = "Février";
                break;
            case 2:
                mois = "Mars";
                break;
            case 3:
                mois = "Avril";
                break;
            case 4:
                mois = "Mai";
                break;
            case 5:
                mois = "Juin";
                break;
            case 6:
                mois = "Juillet";
                break;
            case 7:
                mois = "Aout";
                break;
            case 8:
                mois = "Septembre";
                break;
            case 9:
                mois = "Octobre";
                break;
            case 10:
                mois = "Novembre";
                break;
            case 11:
                mois = "Décembre";
                break;
            default:
                mois = "erreur";
        }
        return mois;
    }
}