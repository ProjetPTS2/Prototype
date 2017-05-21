/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pts2.utilitaire.Constantes;
import pts2.EDT;
import pts2.donnees.Jours;
import pts2.donnees.Cours;
import pts2.donnees.Semaine;
import pts2.ihm.AccueilController;
import pts2.ihm.edition.EditerCoursController;
import pts2.utilitaire.DragDrop;

/**
 *
 * @author tgallard
 */
public class ComposantHeure extends ComposantTexte {

    private final ComposantEDT edt;
    private final Semaine semaine;
    private final Cours cours;
    private boolean sourisSurvol;
    
    private final DragDrop dragDrop;
    
    public ComposantHeure(ComposantEDT edt, Semaine semaine, Cours cours, int x, int y) {
        super(cours.getMatiere().getDiminutif() + " - " + cours.getTypeCours().getNom(), x + Constantes.LARGEUR_HEURES*cours.getCreneau().getMinute()/60, y, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_JOURS);
        this.edt = edt;
        this.semaine = semaine;
        this.cours = cours;
        this.setLayoutX(x + Constantes.LARGEUR_HEURES*cours.getCreneau().getMinute()/60);
        
        this.rectangle.setWidth(Constantes.LARGEUR_HEURES * cours.getCreneau().getDuree()/60);
        this.rectangle.setFill(cours.getMatiere().getCouleur());
        
        this.texte.setFill(Color.WHITE);
        
        this.sourisSurvol = false;
        this.dragDrop = new DragDrop();
    }
    
    public void initialiserEvents(Scene scene, Node parent) {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!dragDrop.placementInvalide())
                    setCouleurFond(cours.getMatiere().getCouleur().interpolate(Color.WHITE, 0.2));
                scene.setCursor(Cursor.HAND);
                updateTexteSurvol();
                edt.getTexteSurvol().setVisible(true);
                sourisSurvol = true;
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!dragDrop.placementInvalide())
                    setCouleurFond(cours.getMatiere().getCouleur());
                scene.setCursor(Cursor.DEFAULT);
                edt.getTexteSurvol().setVisible(false);
                sourisSurvol = false;
            }
        });
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && !dragDrop.placementEnCours()) {
                    edt.setCoursEnDeplacement(ComposantHeure.this);
                    dragDrop.setDecalage(event.getSceneX() - getLayoutX() + -1 * (int)((ScrollPane)(parent)).getViewportBounds().getMinX() + 1);
                    dragDrop.setCreneauPlacement(cours.getCreneau().dupliquer());
                    dragDrop.setAnciennePosition(getLayoutX(), getLayoutY());
                    dragDrop.setPlacementEnCours(true);
                }
                if(event.isSecondaryButtonDown() && !dragDrop.placementEnCours())
                    EDT.afficherFenetre(new EditerCoursController(edt.getBDD(), edt, cours));
            }
        });
        
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!event.isPrimaryButtonDown() && dragDrop.placementEnCours()) 
                    edt.placerCours(event);
            }
        });
    }
    
    private void updateTexteSurvol() {
        edt.getTexteSurvol().setTexte(cours.getMatiere().getNom() + 
                        "\nType: " + cours.getTypeCours().getNom() + 
                        "\nSalle: " + cours.getSalle().getNom() +
                        "\nEnseignant: " + cours.getEnseignant().toString() +
                        "\n" + cours.getCreneau().getJours().getNom() + " - " + cours.getCreneau().getHeureDebutString() + " - " + cours.getCreneau().getHeureFinString());
    }
    
    public Cours getCours() {
        return this.cours;
    }
    
    
    public boolean placer(MouseEvent event) {
        this.dragDrop.setPlacementEnCours(false);
        boolean retour = !this.dragDrop.placementInvalide();
        if(!retour) {
            setLayoutX(this.dragDrop.getAnciennePositionX());
            setLayoutY(this.dragDrop.getAnciennePositionY());
            cours.setCreneau(this.dragDrop.getCreneauPlacement());
            texte.setText(texteParDefaut);
            this.dragDrop.setPlacementInvalide(false);
            sourisSurvol = false;
        }
        super.setCouleurFond(cours.getMatiere().getCouleur());
        return retour;
    }
    
    
    public void deplacement(double sourisX, double sourisY, Node parent) {  
        this.toFront();

        int x = (int)(sourisX - parent.getLayoutX());
        int y = (int)(sourisY - parent.getLayoutY());

        
        x -= this.dragDrop.getDecalage();


        // Ajout du décalage du ScrollPane
        if(parent instanceof ScrollPane)
            x += -1 * (int)((ScrollPane)(parent)).getViewportBounds().getMinX() + 1;

        int origineX = Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL;
        int origineY = Constantes.HAUTEUR_JOURS - Constantes.MARGE_VERTICAL;

        int colonne = (x - Constantes.LARGEUR_JOURS - Constantes.MARGE_HORIZONTAL)/Constantes.LARGEUR_HEURES;
        int ligne = (y + Constantes.MARGE_VERTICAL)/Constantes.HAUTEUR_JOURS - 1;
        if(ligne < 0)
            ligne = 0;
        if(ligne >= Jours.values().length)
            ligne = Jours.values().length-1;


        int heure = colonne + Constantes.HEURE_DEBUT;
        Jours jours = Jours.values()[ligne];

        int minute = Math.abs(60*(colonne * Constantes.LARGEUR_HEURES + (Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL) - x)/Constantes.LARGEUR_HEURES);
        minute /= AccueilController.SNAP;
        minute *= AccueilController.SNAP;

        int compX = Constantes.MARGE_HORIZONTAL + Constantes.LARGEUR_JOURS 
                + (heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES
                + (int)(Constantes.LARGEUR_HEURES * (float)minute/60f);
        int compY = ligne * Constantes.HAUTEUR_JOURS + origineY;
        if(x < origineX) {
            compX = origineX;
            minute = 0;
        }

        cours.getCreneau().setJours(jours);
        cours.getCreneau().setHeure(heure);
        cours.getCreneau().setMinute(minute);
        
        this.updateTexteSurvol();

        this.dragDrop.setPlacementInvalide(this.semaine.intersectionHeure(this.cours.getCreneau()));

        if(this.dragDrop.placementInvalide()) {
            setCouleurFond(Color.RED);
            texte.setText("INVALIDE");
        }
        else {
            setCouleurFond(cours.getMatiere().getCouleur());
            texte.setText(texteParDefaut);
        }
        setLayoutX(compX);
        setLayoutY(compY);
    }
}
