/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm.edition;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pts2.EDT;
import pts2.Jours;
import pts2.donnees.Cours;
import pts2.donnees.Enseignant;
import pts2.donnees.HeureEDT;
import pts2.donnees.Matiere;
import pts2.donnees.Salle;
import pts2.ihm.AccueilController;
import pts2.ihm.IFenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class AjouterCoursController implements IFenetre, Initializable {

    private AccueilController accueilController;
    private Stage stage;
    private Parent racine;
    
    @FXML
    private ComboBox typeCours, enseignant, matiere, salle;
    @FXML
    private TextField dureeTexte;
    
    public AjouterCoursController() {
    }

    @Override
    public void initialiser(AccueilController accueilController, Stage stage) {
        try {
            this.accueilController = accueilController;
            this.stage = stage;
             
             FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterCours.fxml"));
             loader.setController(this);
             this.racine = loader.load();
        } catch(IOException e) { e.printStackTrace(); }     
    }

    @Override
    public Parent getRacine() {
        return this.racine;
    }

    @Override
    public String getNom() {
        return "Ajouter un cours";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> liste = new ArrayList<>();
        for(Matiere _matiere : EDT.getInstance().getBDD().getListeMatieres()) {
            liste.add(_matiere.getDiminutif());
        }
        this.matiere.setItems(FXCollections.observableList(liste));
        
        liste = new ArrayList<>();
        for(Salle _salle : EDT.getInstance().getBDD().getListeSalles()) {
            liste.add(_salle.getNom());
        }
        this.salle.setItems(FXCollections.observableList(liste));
        
        
        
        liste = new ArrayList<>();
        for(Enseignant _enseignant : EDT.getInstance().getBDD().getListeEnseignants()) {
            liste.add(_enseignant.getDiminutif());
        }
        this.enseignant.setItems(FXCollections.observableList(liste));
        
        
        liste = new ArrayList<>();
        for(String _typeCours : EDT.getInstance().getBDD().getListeTypeCours()) {
            liste.add(_typeCours);
        }
        this.typeCours.setItems(FXCollections.observableList(liste));
        
        
        this.dureeTexte.setText("60");
        
        this.dureeTexte.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                this.dureeTexte.setText(oldValue);
        });
        
    }   
    
    
    
    public void placerCours() {
        Enseignant _enseignant = EDT.getInstance().getBDD().getEnseignant((String)this.enseignant.getSelectionModel().getSelectedItem());
        Matiere _matiere = EDT.getInstance().getBDD().getMatiere((String)this.matiere.getSelectionModel().getSelectedItem());
        Salle _salle = EDT.getInstance().getBDD().getSalle((String)this.salle.getSelectionModel().getSelectedItem());
        String _typeCours = (String)this.typeCours.getSelectionModel().getSelectedItem();
        
        Cours cours = new Cours(Jours.LUNDI, new HeureEDT(8, 0, 60), _enseignant, _matiere, _salle, _typeCours);
        this.accueilController.getComposantEDT().ajouterCoursTemporaire(cours);
        this.stage.close();
    }
}
