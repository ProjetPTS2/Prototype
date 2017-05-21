/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import pts2.bdd.BDD;
import pts2.composants.ComposantEDT;

/**
 *
 * @author Théo-PC
 */
public abstract class Fenetre {
    
    protected final String nom;
    protected final BDD bdd;
    protected Stage stage;
    protected Parent racine;
    protected ComposantEDT composantEDT;
    private final String fichierFXML;
    
    public Fenetre(String nom, String fichierFXML, BDD bdd, ComposantEDT composantEDT) {
        this.nom = nom;
        this.bdd = bdd;
        this.composantEDT = composantEDT;
        this.fichierFXML = fichierFXML;
    }
    
    protected void chargerIHM() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fichierFXML));
        loader.setController(this);
        try {
            this.racine = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public BDD getBDD() {
        return this.bdd;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public Parent getRacine() {
        return this.racine;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
