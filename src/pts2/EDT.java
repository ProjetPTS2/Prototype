package pts2;

import pts2.donnees.Jours;
import pts2.bdd.BDD;
import java.io.File;
import java.util.Calendar;
import pts2.donnees.Creneau;
import pts2.donnees.Salle;
import pts2.donnees.Matiere;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pts2.donnees.Cours;
import pts2.donnees.Enseignant;
import pts2.donnees.TypeCours;
import pts2.ihm.AccueilController;
import pts2.ihm.Fenetre;

public class EDT extends Application {
    
    private static AccueilController accueil;
    private final BDD bdd;
    
    public EDT() {        
        this.bdd = new BDD();
        
        this.creationDonnees();
    }
    
    /**
     * Créer un nouvel emploi du temps vide.
     */
    public void sauvegarderEDT() {
        if(this.bdd.getRepertoire() == null) {
            DirectoryChooser fileChooser = new DirectoryChooser();
            fileChooser.setTitle("Sauvegarder l'emploi du temps");
            File dossier = fileChooser.showDialog(accueil.getStage());
            if(dossier != null) {
                this.bdd.setRepertoire(dossier);
                this.bdd.sauvegarder();
            }
        } else
            this.bdd.sauvegarder();
    }
    
    /**
     * Ouvre une boite de dialogue pour charger un emploi du temps.
     */
    public void chargerEDT() {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Charger un emploi du temps");
        File dossier = fileChooser.showDialog(accueil.getStage());
        if(dossier != null) {
            this.bdd.setRepertoire(dossier);
            this.bdd.charger();
        }
    }
    
    /**
     * Fonction temporaire qui ajoute des données.
     */
    private void creationDonnees() {
        this.bdd.getBaseTypeCours().ajouter(new TypeCours("TP"));
        this.bdd.getBaseTypeCours().ajouter(new TypeCours("TD"));
        this.bdd.getBaseTypeCours().ajouter(new TypeCours("CM"));
        
        this.bdd.getBaseSalles().ajouter(new Salle("D201", 15));
        this.bdd.getBaseSalles().ajouter(new Salle("D301", 20));
        this.bdd.getBaseSalles().ajouter(new Salle("D302", 30));
        
        this.bdd.getBaseMatieres().ajouter(new Matiere("POO", "Programmation Orientée Objet", Color.BLUE));
        this.bdd.getBaseMatieres().ajouter(new Matiere("ANG", "Anglais", Color.BLUEVIOLET));
        this.bdd.getBaseMatieres().ajouter(new Matiere("BD", "Base de données", Color.AQUAMARINE));
        
        Enseignant bebien = new Enseignant("BEBIEN", "Vincent");
        Enseignant doucet = new Enseignant("DOUCET", "Antoine");
        this.bdd.getBaseEnseignants().ajouter(bebien);       
        this.bdd.getBaseEnseignants().ajouter(doucet);       
        
        
        bebien.ajouterMatiere(this.bdd.getBaseMatieres().rechercher("ANG"));
        bebien.ajouterMatiere(this.bdd.getBaseMatieres().rechercher("POO"));   
        
        Cours testCours1 = new Cours(new Creneau(Jours.JEUDI, 10, 10, 120), bebien, this.bdd.getBaseMatieres().rechercher("POO"), this.bdd.getBaseSalles().rechercher("D201"), this.bdd.getBaseTypeCours().rechercher("TD"));
        
        
        
        this.bdd.getBaseSemaines().rechercher(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)).ajouterCours(testCours1);
    }
    
    /**
     * Permet d'afficher une fenêtre.
     * @param fenetre La fenêtre à afficher.
     * @return Retourne le stage de la fenêtre.
     */
    public static Stage afficherFenetre(Fenetre fenetre) {
        Stage stage = new Stage();
        fenetre.setStage(stage);
        stage.setTitle(fenetre.getNom());
        stage.setScene(new Scene(fenetre.getRacine()));
        if(fenetre instanceof AccueilController == false) {
            stage.initOwner(accueil.getStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnCloseRequest((WindowEvent t) -> {
                accueil.actualiser();
            });
        }
        stage.show();
        return stage;
    }
    
    public BDD getBDD() {
        return this.bdd;
    }
    
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        accueil = new AccueilController(this);
        afficherFenetre(accueil);
        accueil.actualiser();
    }
}
