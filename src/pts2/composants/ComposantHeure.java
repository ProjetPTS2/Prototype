/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pts2.Constantes;
import pts2.donnees.Cours;
import pts2.HeureEDT;

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
    
    public void initialiserEvents(Stage stage, ComposantSurvol survol) {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCouleurFond(cours.getMatiere().getCouleur().interpolate(Color.WHITE, 0.1));
                stage.getScene().setCursor(Cursor.HAND);
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
                    survol.setPosition(event.getSceneX(), event.getSceneY());
                    survol.setVisible(true);
                }
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCouleurFond(cours.getMatiere().getCouleur());
                stage.getScene().setCursor(Cursor.DEFAULT);
                survol.setVisible(false);
               sourisSurvol = false;
            }
        });
    }
}
