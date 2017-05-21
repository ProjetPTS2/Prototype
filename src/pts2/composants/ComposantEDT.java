/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import java.util.Calendar;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pts2.utilitaire.Constantes;
import pts2.bdd.BDD;
import pts2.donnees.Jours;
import pts2.donnees.Cours;
import pts2.donnees.Semaine;

/**
 *
 * @author Theo
 */
public class ComposantEDT extends Pane {
    
    private BDD bdd;
    private ScrollPane scrollPane;
    private Group edtCours;
    private ComposantSurvol texteSurvol;
    private Semaine semaineActuelle;
    
    private ComposantHeure coursTemporaire;
    private ComposantHeure coursEnDeplacement;
    
    private double sourisX, sourisY;
    
    public ComposantEDT(BDD bdd, Node parent) {
        this.bdd = bdd;
        this.scrollPane = (ScrollPane)parent;
        this.edtCours = new Group();
        this.texteSurvol = new ComposantSurvol();
        this.texteSurvol.setVisible(false);
        this.getChildren().add(this.texteSurvol);
        
        EventHandler<MouseEvent> gestionSouris = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sourisX = event.getSceneX();
                sourisY = event.getSceneY();
                texteSurvol.actualiserPosition(scrollPane, ComposantEDT.this, event.getSceneX(), event.getSceneY() - parent.getLayoutY());
                deplacementCours(event);
            }
        };
        
        
        this.setOnMouseMoved(gestionSouris);
        this.setOnMouseDragged(gestionSouris);
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
    
    public void actualiser() {
        this.actualiser(this.bdd.getBaseSemaines().rechercher(this.semaineActuelle.getNoSemaine()));
    }
    
    public void actualiser(Semaine semaine) {
        this.semaineActuelle = semaine;
        if(this.getChildren().isEmpty())
            this.initaliserEDT(semaine);
        
        this.edtCours.getChildren().clear();
        
        // AFFICHAGE DES COURS
        for(Cours cours : semaine.getListeCours()) {
                ComposantHeure coursComposant = new ComposantHeure(this, semaine, cours, (cours.getCreneau().getHeure()-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, cours.getCreneau().getJours().getNumero() * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL);
                coursComposant.initialiserEvents(this.getScene(), this.scrollPane, this.texteSurvol);
                this.edtCours.getChildren().add(coursComposant);
        }
        this.texteSurvol.toFront();
    }
    
    
    
    
    
    
    // DRAG & DROP
    
    public void ajouterCoursTemporaire(Cours cours) {
        this.coursTemporaire = new ComposantHeure(this, this.semaineActuelle, cours, (int)sourisX, (int)sourisY);
        this.coursTemporaire.initialiserEvents(this.getScene(), this.scrollPane, this.texteSurvol);
        this.setCoursEnDeplacement(this.coursTemporaire);
        this.edtCours.getChildren().add(this.coursTemporaire);
        this.setCoursEnDeplacement(this.coursTemporaire);
    }
    
    public void setCoursEnDeplacement(ComposantHeure composant) {
        if(this.coursEnDeplacement == null)
            this.coursEnDeplacement = composant;
    }
    
    public void placerCours(MouseEvent event) {
        if(this.coursEnDeplacement != null) {
            boolean succes = this.coursEnDeplacement.placer(event);
            if(this.coursEnDeplacement.equals(this.coursTemporaire)) {
                if(!succes)
                    this.edtCours.getChildren().remove(this.coursEnDeplacement);
                else
                    this.semaineActuelle.ajouterCours(this.coursTemporaire.getCours());
                this.coursTemporaire = null;
            }
            this.coursEnDeplacement = null;
        }
        event.consume();
    }
    
    private void deplacementCours(MouseEvent event) {
        if(this.coursEnDeplacement != null) {
            this.coursEnDeplacement.deplacement(sourisX, sourisY, this.scrollPane);
        }
        
        event.consume();
    }
    
    public BDD getBDD() {
        return this.bdd;
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
