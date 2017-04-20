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
import pts2.BDD;
import pts2.Constantes;
import pts2.EDT;
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
public class EditerCoursController implements IFenetre, Initializable {

    private AccueilController accueilController;
    private Stage stage;
    private Parent racine;
    
    private final Cours cours;
    private final HeureEDT heure;
    
    @FXML
    private ComboBox typeCours, enseignant, matiere, salle;
    @FXML
    private TextField heureDebut, heureFin, minuteDebut, minuteFin;
    
    public EditerCoursController(Cours cours, HeureEDT heure) {
        this.cours = cours;
        this.heure = heure;
    }

    @Override
    public void initialiser(AccueilController accueilController, Stage stage) {
        try {
            this.accueilController = accueilController;
            this.stage = stage;
             
             FXMLLoader loader = new FXMLLoader(getClass().getResource("EditerCours.fxml"));
             loader.setController(this);
             this.racine = loader.load();
        } catch(IOException e) {}     
    }

    @Override
    public Parent getRacine() {
        return this.racine;
    }

    @Override
    public String getNom() {
        return "Editer cours";
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int index = 0, i = 0;
        List<String> liste = new ArrayList<>();
        for(Matiere _matiere : EDT.getInstance().getBDD().getListeMatieres()) {
            if(_matiere.getDiminutif().equals(this.cours.getMatiere().getDiminutif()))
                index = i;
            liste.add(_matiere.getDiminutif());
            i++;
        }
        this.matiere.setItems(FXCollections.observableList(liste));
        this.matiere.getSelectionModel().select(index);
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(Salle _salle : EDT.getInstance().getBDD().getListeSalles()) {
            if(_salle.getNom().equals(this.cours.getSalle().getNom()))
                index = i;
            liste.add(_salle.getNom());
            i++;
        }
        this.salle.setItems(FXCollections.observableList(liste));
        this.salle.getSelectionModel().select(index);
        
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(Enseignant _enseignant : EDT.getInstance().getBDD().getListeEnseignants()) {
            if(_enseignant.getDiminutif().equals(this.cours.getEnseignant().getDiminutif()))
                index = i;
            liste.add(_enseignant.getDiminutif());
            i++;
        }
        this.enseignant.setItems(FXCollections.observableList(liste));
        this.enseignant.getSelectionModel().select(index);
        
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(String _typeCours : EDT.getInstance().getBDD().getListeTypeCours()) {
            if(_typeCours.equals(this.cours.getTypeCours()))
                index = i;
            liste.add(_typeCours);
            i++;
        }
        this.typeCours.setItems(FXCollections.observableList(liste));
        this.typeCours.getSelectionModel().select(index);
        
        this.heureDebut.setText(this.heure.getHeure() + "");
        this.heureFin.setText(this.heure.getHeureFin() + "");
        this.minuteDebut.setText(this.heure.getMinute() + "");
        this.minuteFin.setText(this.heure.getMinuteFin() + "");
        
        
        this.heureDebut.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.heureDebut.setText(oldValue);
        });
        
        this.heureFin.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.heureFin.setText(oldValue);
        });
        
        this.minuteDebut.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.minuteDebut.setText(oldValue);
        });
        
        this.minuteFin.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.minuteFin.setText(oldValue);
        });
        
    }   
    
    
    
    
    
    public void sauvegarder() {
        this.cours.setSalle(EDT.getInstance().getBDD().getSalle((String)this.salle.getSelectionModel().getSelectedItem()));
        this.cours.setMatiere(EDT.getInstance().getBDD().getMatiere((String)this.matiere.getSelectionModel().getSelectedItem()));
        this.cours.setEnseignant(EDT.getInstance().getBDD().getEnseignant((String)this.enseignant.getSelectionModel().getSelectedItem()));
        this.cours.setTypeCours((String)this.typeCours.getSelectionModel().getSelectedItem());
        
        this.heure.setHeure(Integer.parseInt(this.heureDebut.getText()));
        this.heure.setHeureFin(Integer.parseInt(this.heureFin.getText()));
        this.heure.setMinute(Integer.parseInt(this.minuteDebut.getText()));
        this.heure.setMinuteFin(Integer.parseInt(this.minuteFin.getText()));
        
        
        this.accueilController.actualiser();
    }
    
}
