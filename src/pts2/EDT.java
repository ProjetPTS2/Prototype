package pts2;

import pts2.donnees.Jours;
import pts2.bdd.BDD;
import java.io.File;
import pts2.donnees.HeureEDT;
import pts2.donnees.Salle;
import pts2.donnees.Matiere;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pts2.donnees.Enseignant;
import pts2.donnees.TypeCours;
import pts2.ihm.AccueilController;
import pts2.ihm.IFenetre;

public class EDT extends Application {
    
    private static EDT instance;
    
    private AccueilController accueil;
    private BDD bdd;
    
    public EDT() {
        instance = this;
        
        this.bdd = new BDD();
        
        this.creationDonnees();
        //this.bdd.charger("test.xml");
    }
    
    /**
     * Créer un nouvel emploi du temps vide.
     * @param creerNouvelleBase Définit si la fonction doit créer une nouvelle base de données.
     * @return Retourne le fichier dans lequel la base de données a été sauvegardée.
     */
    public File sauvegarderEDT(boolean creerNouvelleBase) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Sauvegarder l'emploi du temps");
        File fichier = directoryChooser.showDialog(this.accueil.getStage());
        if(fichier != null) {
            this.bdd.setRepertoire(fichier);
            if(creerNouvelleBase)
                this.bdd = new BDD(fichier);
            this.bdd.sauvegarder();
            this.accueil.actualiser();
        }
        return fichier;
    }
    
    public void chargerEDT(File fichier) {
        this.bdd = new BDD(fichier);
        this.bdd.charger(fichier);
        this.accueil.actualiser();
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
        
        
        this.bdd.placerCours(11, "VBe", new HeureEDT(10, 10, 120), Jours.JEUDI, "POO", "D301", "TP");
        this.bdd.placerCours(11, "VBe", new HeureEDT(15, 0, 90), Jours.LUNDI, "ANG", "D201", "TD");
        this.bdd.placerCours(18, "ADo", new HeureEDT(9, 0, 60), Jours.LUNDI, "BD", "D201", "TD");
        this.bdd.placerCours(18, "ADo", new HeureEDT(8, 15, 60), Jours.MARDI, "POO", "D201", "TD");
        
        System.out.println(this.bdd.getBaseSemaines().getListeDonnees().size());
    }
    
    /**
     * Permet d'afficher une fenêtre.
     * @param fenetre La fenêtre à afficher.
     * @return Retourne le stage de la fenêtre.
     */
    public Stage afficherFenetre(IFenetre fenetre) {
        Stage stage = new Stage();
        fenetre.initialiser(this.accueil, stage);
        stage.setTitle(fenetre.getNom());
        stage.setScene(new Scene(fenetre.getRacine()));
        if(fenetre instanceof AccueilController == false) {
            stage.initOwner(this.accueil.getStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnCloseRequest((WindowEvent t) -> {
                this.accueil.actualiser();
            });
        }
        stage.show();
        return stage;
    }
    
    public BDD getBDD() {
        return this.bdd;
    }
    
    public static EDT getInstance() {
        return instance;
    }
    
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.accueil = new AccueilController();
        afficherFenetre(this.accueil);
        this.accueil.actualiser();
    }
}
