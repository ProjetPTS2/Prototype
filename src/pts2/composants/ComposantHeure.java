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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pts2.Constantes;
import pts2.EDT;
import pts2.donnees.Jours;
import pts2.donnees.Cours;
import pts2.donnees.HeureEDT;
import pts2.donnees.Semaine;
import pts2.ihm.AccueilController;
import pts2.ihm.edition.EditerCoursController;

/**
 *
 * @author tgallard
 */
public class ComposantHeure extends ComposantTexte {

    private final ComposantEDT edt;
    private final Semaine semaine;
    private final HeureEDT heure;
    private final Cours cours;
    private boolean sourisSurvol;
    
    // DRAG & DROP
    private Jours joursPlacement;
    private HeureEDT heurePlacement;
    private boolean placementInvalide, placementEnCours;
    private double decalageX; // Position de la souris dans le composant lors du drag & drop
    private double anciennePositionX, anciennePositionY;
    
    public ComposantHeure(ComposantEDT edt, Semaine semaine, Cours cours, int x, int y) {
        super(cours.getMatiere().getDiminutif() + " - " + cours.getTypeCours().getNom(), x + Constantes.LARGEUR_HEURES*cours.getHeure().getMinute()/60, y, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_JOURS);
        this.edt = edt;
        this.semaine = semaine;
        this.heure = cours.getHeure();
        this.cours = cours;
        this.setLayoutX(x + Constantes.LARGEUR_HEURES*heure.getMinute()/60);
        
        this.rectangle.setWidth(Constantes.LARGEUR_HEURES * heure.getDuree()/60);
        this.rectangle.setFill(cours.getMatiere().getCouleur());
        
        this.texte.setFill(Color.WHITE);
        
        this.sourisSurvol = false;
    }
    
    public void initialiserEvents(Scene scene, Node parent, ComposantSurvol survol) {
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!placementInvalide)
                    setCouleurFond(cours.getMatiere().getCouleur().interpolate(Color.WHITE, 0.2));
                scene.setCursor(Cursor.HAND);
                survol.setTexte(cours.getMatiere().getNom() + 
                        "\nType: " + cours.getTypeCours().getNom() + 
                        "\nSalle: " + cours.getSalle().getNom() +
                        "\nEnseignant: " + cours.getEnseignant().toString() +
                        "\n" + heure.getHeureDebutString() + " - " + heure.getHeureFinString());
                
                sourisSurvol = true;
            }
        });
        
        
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(sourisSurvol) {
                    survol.setTexte(cours.getMatiere().getNom() + 
                        "\nType: " + cours.getTypeCours().getNom() + 
                        "\nSalle: " + cours.getSalle().getNom() +
                        "\nEnseignant: " + cours.getEnseignant().toString() +
                        "\n" + heure.getHeureDebutString() + " - " + heure.getHeureFinString());
                    survol.setVisible(true);
                }
            }
        });
        
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!placementInvalide)
                    setCouleurFond(cours.getMatiere().getCouleur());
                scene.setCursor(Cursor.DEFAULT);
                survol.setVisible(false);
               sourisSurvol = false;
            }
        });
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown()) {
                    if(!placementEnCours)
                        edt.setCoursEnDeplacement(ComposantHeure.this);
                    
                    else
                        edt.placerCours(event);
                    
                }
                if(event.isSecondaryButtonDown() && !placementEnCours)
                    EDT.getInstance().afficherFenetre(new EditerCoursController(cours, heure));
            }
        });
    }
    
    public Cours getCours() {
        return this.cours;
    }
    
    
    public boolean placer(MouseEvent event) {
        boolean retour = !placementInvalide;
        if(placementInvalide) {
            setLayoutX(anciennePositionX);
            setLayoutY(anciennePositionY);
            cours.setJours(joursPlacement);
            cours.setHeure(heurePlacement);
            texte.setText(texteParDefaut);
            placementInvalide = false;
            sourisSurvol = false;
        }
        this.placementEnCours = false;
        setCouleurFond(cours.getMatiere().getCouleur());
        return retour;
    }
    
    
    public void deplacement(double sourisX, double sourisY, Node parent) {  
        this.toFront();

        int x = (int)(sourisX - parent.getLayoutX());
        int y = (int)(sourisY - parent.getLayoutY());

        if(!placementEnCours) {
            joursPlacement = cours.getJours();
            heurePlacement = cours.getHeure().dupliquer();
            anciennePositionX = getLayoutX();
            anciennePositionY = getLayoutY();
            decalageX = x - getLayoutX();
            placementEnCours = true;
        }

        
        x -= 20;


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

        cours.setJours(jours);
        cours.getHeure().setHeure(heure);
        cours.getHeure().setMinute(minute);

        placementInvalide = semaine.intersectionHeure(cours.getJours(), cours.getHeure());

        if(placementInvalide) {
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
