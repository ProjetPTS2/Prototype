package pts2;

import pts2.donnees.Salle;
import pts2.donnees.Matiere;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pts2.donnees.Enseignant;

public class EDT extends Application{
    
    private final FenetreEDT fenetre;
    private final BDD bdd;
    private Stage stage;
    
    public EDT() {
        this.bdd = new BDD();
        this.fenetre = new FenetreEDT(bdd);
        
        this.bdd.ajouterTypeCours("TP");
        this.bdd.ajouterTypeCours("TD");
        this.bdd.ajouterTypeCours("CM");
        
        this.bdd.ajouterSalle(new Salle("D201"));
        this.bdd.ajouterSalle(new Salle("D301"));
        
        this.bdd.ajouterMatiere(new Matiere("POO", "Programmation Orientée Objet", Color.BLUE));
        this.bdd.ajouterMatiere(new Matiere("ANG", "Anglais", Color.BLUEVIOLET));
        
        Enseignant bebien = new Enseignant("BEBIEN", "Vincent");
        bebien.ajouterMatiere(this.bdd.getMatiere("ANG"));
        bebien.ajouterMatiere(this.bdd.getMatiere("POO"));
        
        this.bdd.ajouterEnseignant(bebien);       
        
        
        
        
        this.bdd.placerCours(11, bebien, new HeureEDT(10, 10, 12, 0), Jours.JEUDI, "POO", "D301", "TP");
        this.bdd.placerCours(11, bebien, new HeureEDT(15, 0, 17, 45), Jours.LUNDI, "ANG", "D201", "TD");
        
        this.bdd.placerCours(2, bebien, new HeureEDT(8, 15, 9, 50), Jours.MARDI, "POO", "D201", "TD");
        
        this.bdd.sauvegarder();
        
        try {
            this.start(this.fenetre);
        } catch (Exception ex) { ex.printStackTrace(); }       
    }
    
    public static void main(String args[]) {
        launch(args);
        new EDT();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }
}
