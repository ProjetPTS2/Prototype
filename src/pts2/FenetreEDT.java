package pts2;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.Set;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class FenetreEDT extends Stage {
    
    private final BDD bdd;
    private final Scene scene;
    private final Group racine;
    private final Group edt, edtCours;
    private final HBox semaines;
    private final ScrollPane edtScrollPane, semainesScrollPane;
    private final ComposantSurvol texteSurvol;
    
    private final Button[] boutonSemaines;
    private int semaineSelection;
    
    public FenetreEDT(BDD bdd) {
        this.bdd = bdd;
        this.setWidth(800);
        this.setHeight(600);
        this.setTitle("PTS2 - Prototype");
        this.setResizable(true);
        
        this.racine = new Group();
        this.edt = new Group();
        this.edtCours = new Group();
        this.semaines = new HBox();
        
        this.texteSurvol = new ComposantSurvol();
        this.texteSurvol.setVisible(false);
        this.racine.getChildren().add(texteSurvol);
        
        this.edtScrollPane = new ScrollPane();
        this.edtScrollPane.setPrefSize(800, 400);
        this.edtScrollPane.setLayoutY(140);
        this.edtScrollPane.setContent(this.edt);
        
        this.semainesScrollPane = new ScrollPane();
        this.semainesScrollPane.setPrefSize(800, 60);
        this.semainesScrollPane.setLayoutY(0);
        this.semainesScrollPane.setContent(this.semaines);
        
        int boutonSemaineActuelle = -1;
        
        this.semaineSelection = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        this.boutonSemaines = new Button[52];
        for(int i = 0; i < 52; i++) {
            int semaine = (i+35)%53;
            if((i+35) > 52)
                semaine++;
            final int noSemaine = semaine;
            Button btn = new Button("S" + noSemaine);
            btn.setPrefSize(60, 40);
            
            if(this.semaineSelection == noSemaine)
                boutonSemaineActuelle = i;
            
            final int id = i;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    actualiser(bdd.getSemaine(noSemaine));
                    boutonSemaines[id].setDisable(true);
                    boutonSemaines[semaineSelection].setDisable(false);
                    semaineSelection = id;
                }
            
            });
            this.semaines.getChildren().add(btn);
            this.boutonSemaines[i] = btn;
        }
        
        this.semaineSelection = boutonSemaineActuelle;
        this.boutonSemaines[this.semaineSelection].setDisable(true);
        
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.edtScrollPane.setPrefWidth((double)newVal - 20 - 200);
            this.semainesScrollPane.setPrefWidth((double)newVal - 20);
        });

        /*this.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.scrollPane.setPrefHeight((double)newVal);
        });*/

        this.scene = new Scene(racine, 1200, 600);
        this.setScene(this.scene);
        
        this.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        
        this.racine.getChildren().add(this.edtScrollPane);
        this.racine.getChildren().add(this.semainesScrollPane);
        
        this.setMaximized(true);
        this.show();
        
        
        // Modification de la taille des ScrollBars
        Set<Node> nodes = this.edtScrollPane.lookupAll(".scroll-bar");
        for (final Node node : nodes) {
            if (node instanceof ScrollBar) {
                ScrollBar sb = (ScrollBar) node;
                if (sb.getOrientation() == Orientation.HORIZONTAL) {
                    sb.setPrefHeight(20);
                }
            }
        }
        
        this.initalizerEDT(bdd.getSemaine(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)));
    }
    
    private void initalizerEDT(Semaine semaine) {
        // AFFICHAGE DES JOURS
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, semaine.getNoSemaine());
        for(int idJour = 0; idJour < Jours.values().length; idJour++) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + idJour);
            String mois;
            switch(calendar.get(Calendar.MONTH)) {
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
                    mois = "Decembre";
                    break;
                default:
                    mois = "erreur";
            }
            ComposantTexte comp = new ComposantTexte(Jours.values()[idJour].getNom() + " " + calendar.get(Calendar.DATE) + " " + mois, Constantes.MARGE_HORIZONTAL, idJour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL, Constantes.LARGEUR_JOURS, Constantes.HAUTEUR_JOURS);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.edt.getChildren().add(comp);
        }
        
        // AFFICHAGE DES HEURES
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            ComposantTexte comp = new ComposantTexte(heure + "h", (heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, Constantes.MARGE_VERTICAL, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_HEURES);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.edt.getChildren().add(comp);
        }
        
        // AFFICHAGE DES CASES COURS
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            for(int jour = 0; jour < Jours.values().length; jour++) {
                ComposantTexte comp = new ComposantTexte("", (heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, jour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_JOURS);
                comp.setCouleurTexte(Color.WHITE);
                this.edt.getChildren().add(comp);
            }
        }
        
        this.edt.getChildren().add(this.edtCours);
    }   
    
    public void actualiser(Semaine semaine) {
        if(this.edt.getChildren().isEmpty())
            this.initalizerEDT(semaine);
        
        this.edtCours.getChildren().clear();
        
        // AFFICHAGE DES COURS
        for(int jour = 0; jour < Jours.values().length; jour++) {
            for(Entry<HeureEDT, Cours> cours : semaine.getListeCours(Jours.values()[jour])) {
                    ComposantHeure coursComposant = new ComposantHeure(cours.getKey(), cours.getValue(), "", (cours.getKey().getHeure()-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS + Constantes.MARGE_HORIZONTAL, jour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES + Constantes.MARGE_VERTICAL);
                    coursComposant.setTexte(cours.getValue().getMatiere().getDiminutif() + " - " + cours.getValue().getTypeCours());
                    coursComposant.initialiserEvents(this, this.texteSurvol);
                    this.edtCours.getChildren().add(coursComposant);
            }
        }
        this.texteSurvol.toFront();
    }
}
