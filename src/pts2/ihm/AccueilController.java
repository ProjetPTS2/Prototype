/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm;

import java.io.File;
import pts2.ihm.edition.EditerMatieresController;
import pts2.ihm.edition.EditerSallesController;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pts2.EDT;
import pts2.composants.ComposantEDT;
import pts2.composants.ComposantSemaines;
import pts2.composants.ComposantSurvol;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class AccueilController implements IFenetre, Initializable {
    
    private Stage stage;
    private Parent racine;
    
    private ComposantSemaines composantSemaines;
    private ComposantEDT composantEDT;
    
    @FXML
    private AnchorPane racinePane;
    @FXML
    private ScrollPane semainesScrollPane;
    @FXML
    private ScrollPane edtScrollPane;
    @FXML
    private Menu snapMenu;
    
    public static int SNAP = 1;
    
    
    
    public void actualiser() {
        this.composantEDT.actualiser(true);
    }

    
    @Override
    public void initialiser(AccueilController accueilController, Stage stage) {
         try {
            this.stage = stage;
             
             FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
             loader.setController(this);
             this.racine = loader.load();
        } catch(IOException e) {}          
         
        this.composantEDT.initaliserEDT(EDT.getInstance().getBDD().getSemaine(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)));
        this.composantSemaines.initialiserComposants(this.composantEDT);
    }

    @Override
    public Parent getRacine() {
        return this.racine;
    }
    
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public String getNom() {
        return "R3G15";
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        this.composantEDT = new ComposantEDT(this.edtScrollPane);
        this.composantSemaines = new ComposantSemaines(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        
        this.semainesScrollPane.setContent(this.composantSemaines);
        this.edtScrollPane.setContent(this.composantEDT);
    }    
    
    
    
    
    
    public void menuBar_fichier_nouveau() {
        EDT.getInstance().sauvegarderEDT(true);
    }
    
    public void menuBar_fichier_ouvrir() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un emploi du temps");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier XML", "*.xml"));
        File fichier = fileChooser.showOpenDialog(this.stage);
        EDT.getInstance().chargerEDT(fichier);
    }
    
    public void menuBar_fichier_sauvegarder() {
        EDT.getInstance().getBDD().sauvegarder();
    }
    
    
    public void menuBar_edtion_editerSalles() {
        EDT.getInstance().afficherFenetre(new EditerSallesController());
    }
    
    public void menuBar_edtion_editerMatieres() {
        EDT.getInstance().afficherFenetre(new EditerMatieresController());
    }
    
    
    
    public void menuBar_edition_snap_1() {
        this.setSnap(0);
    }
    public void menuBar_edition_snap_5() {
        this.setSnap(5);        
    }
    public void menuBar_edition_snap_10() {
        this.setSnap(10);
    }
    public void menuBar_edition_snap_15() {
        this.setSnap(15);
    }
    public void menuBar_edition_snap_30() {
        this.setSnap(30);
    }
    
    public void setSnap(int nombre) {
        SNAP = nombre;
        int i = 0;
        int itemId = 0;
        switch (nombre) {
            case 5:
                itemId = 1;
                break;
            case 10:
                itemId = 2;
                break;
            case 15:
                itemId = 3;
                break;
            case 30:
                itemId = 4;
                break;
            default:
                break;
        }
        for(MenuItem item : snapMenu.getItems()) {
            CheckMenuItem check = (CheckMenuItem)item;
            if(i != itemId)
                check.setSelected(false);
            else
                check.setSelected(true);
            i++;
        }
    }
    
}
