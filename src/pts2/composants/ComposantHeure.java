/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import pts2.Constantes;
import pts2.EDT;
import pts2.Jours;
import pts2.donnees.Cours;
import pts2.donnees.HeureEDT;
import pts2.ihm.edition.EditerCoursController;

/**
 *
 * @author tgallard
 */
public class ComposantHeure extends ComposantTexte {

    private final HeureEDT heure;
    private final Cours cours;
    private boolean sourisSurvol;
    
    public ComposantHeure(HeureEDT heure, Cours cours, String str, int x, int y) {
        super(str, x + Constantes.LARGEUR_HEURES*heure.getMinute()/60, y, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_JOURS);
        this.heure = heure;
        this.cours = cours;
        this.setLayoutX(x + Constantes.LARGEUR_HEURES*heure.getMinute()/60);
        
        this.rectangle.setWidth(Constantes.LARGEUR_HEURES * heure.getDuree());
        this.rectangle.setFill(cours.getMatiere().getCouleur());
        
        this.texte.setFill(Color.WHITE);
        
        this.sourisSurvol = false;
    }
    
    public void initialiserEvents(Scene scene, Node parent, ComposantSurvol survol) {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCouleurFond(cours.getMatiere().getCouleur().interpolate(Color.WHITE, 0.2));
                scene.setCursor(Cursor.HAND);
                survol.setTexte(cours.getMatiere().getNom() + 
                        "\nType: " + cours.getTypeCours() + 
                        "\nSalle: " + cours.getSalle().getNom() +
                        "\nEnseignant: " + cours.getEnseignant().toString() +
                        "\n" + heure.getHeureDebutString() + " - " + heure.getHeureFinString());
                
                sourisSurvol = true;
            }
        });
        
        
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(sourisSurvol) {
                    //survol.setPosition(event.getSceneX(), event.getSceneY());
                    survol.setPosition(event.getSceneX(), event.getSceneY());
                    survol.setVisible(true);
                }
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCouleurFond(cours.getMatiere().getCouleur());
                scene.setCursor(Cursor.DEFAULT);
                survol.setVisible(false);
               sourisSurvol = false;
            }
        });
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EDT.getInstance().afficherFenetre(new EditerCoursController(cours, heure));
            }
        });
        
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int)(event.getSceneX() - parent.getLayoutX());
                int y = (int)(event.getSceneY() - parent.getLayoutY());
                
                int origineX = Constantes.LARGEUR_JOURS - Constantes.MARGE_HORIZONTAL;
                int origineY = Constantes.HAUTEUR_JOURS - Constantes.MARGE_VERTICAL;
                
                int colonne = (x - Constantes.LARGEUR_JOURS - Constantes.MARGE_HORIZONTAL)/Constantes.LARGEUR_HEURES;
                int ligne = (y + Constantes.MARGE_VERTICAL)/Constantes.HAUTEUR_JOURS - 1;
                if(ligne < 0)
                    ligne = 0;
                if(ligne >= Jours.values().length)
                    ligne = Jours.values().length-1;
                
                
                int heure = colonne + Constantes.HEURE_DEBUT;
                Jours jours = Jours.values()[ligne];
                
                int minute = Math.abs(60*(colonne * Constantes.LARGEUR_HEURES + (Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL) - x)/150);
                
                System.out.println(heure + "h" + minute + " - " + jours.getNom());
                
                
                int compX = x;
                int compY = ligne * Constantes.HAUTEUR_JOURS + origineY;
                if(compX < origineX)
                    compX = origineX;
                
                
                setLayoutX(compX);
                setLayoutY(compY);
                event.consume();
            }
            
        });
    }
}