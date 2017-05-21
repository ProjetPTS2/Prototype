/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm.edition;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pts2.bdd.BDD;
import pts2.composants.ComposantEDT;
import pts2.donnees.Cours;
import pts2.donnees.Enseignant;
import pts2.donnees.Creneau;
import pts2.donnees.Matiere;
import pts2.donnees.Salle;
import pts2.donnees.TypeCours;
import pts2.ihm.Fenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class EditerCoursController extends Fenetre implements Initializable {
    
    private final Cours cours;
    private final Creneau heure;
    
    @FXML
    private ComboBox typeCours, enseignant, matiere, salle;
    @FXML
    private TextField heureTexte, minuteTexte, dureeTexte;
    
    public EditerCoursController(BDD bdd, ComposantEDT edt, Cours cours, Creneau heure) {
        super("Editer un cours", "EditerCours.fxml", bdd, edt);
        this.cours = cours;
        this.heure = heure;
        super.chargerIHM();
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int index = 0, i = 0;
        List<String> liste = new ArrayList<>();
        for(Matiere _matiere : this.bdd.getBaseMatieres().getListeDonnees()) {
            if(_matiere.getDiminutif().equals(this.cours.getMatiere().getDiminutif()))
                index = i;
            liste.add(_matiere.getDiminutif());
            i++;
        }
        this.matiere.setItems(FXCollections.observableList(liste));
        this.matiere.getSelectionModel().select(index);
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(Salle _salle : this.bdd.getBaseSalles().getListeDonnees()) {
            if(_salle.getNom().equals(this.cours.getSalle().getNom()))
                index = i;
            liste.add(_salle.getNom());
            i++;
        }
        this.salle.setItems(FXCollections.observableList(liste));
        this.salle.getSelectionModel().select(index);
        
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(Enseignant _enseignant : this.bdd.getBaseEnseignants().getListeDonnees()) {
            if(_enseignant.getDiminutif().equals(this.cours.getEnseignant().getDiminutif()))
                index = i;
            liste.add(_enseignant.getDiminutif());
            i++;
        }
        this.enseignant.setItems(FXCollections.observableList(liste));
        this.enseignant.getSelectionModel().select(index);
        
        
        
        index = 0; i = 0;
        liste = new ArrayList<>();
        for(TypeCours _typeCours : this.bdd.getBaseTypeCours().getListeDonnees()) {
            if(_typeCours.getNom().equals(this.cours.getTypeCours().getNom()))
                index = i;
            liste.add(_typeCours.getNom());
            i++;
        }
        this.typeCours.setItems(FXCollections.observableList(liste));
        this.typeCours.getSelectionModel().select(index);
        
        this.heureTexte.setText(this.heure.getHeure() + "");
        this.minuteTexte.setText(this.heure.getMinute() + "");
        this.dureeTexte.setText(this.heure.getDuree() + "");
        
        
        this.heureTexte.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.heureTexte.setText(oldValue);
        });
        this.minuteTexte.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 2)
                this.minuteTexte.setText(oldValue);
        });
        
        this.dureeTexte.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                this.dureeTexte.setText(oldValue);
        });
        
    }   
    
    
    
    
    
    public void sauvegarder() {
        this.cours.setSalle(this.bdd.getBaseSalles().rechercher((String)this.salle.getSelectionModel().getSelectedItem()));
        this.cours.setMatiere(this.bdd.getBaseMatieres().rechercher((String)this.matiere.getSelectionModel().getSelectedItem()));
        this.cours.setEnseignant(this.bdd.getBaseEnseignants().rechercher((String)this.enseignant.getSelectionModel().getSelectedItem()));
        this.cours.setTypeCours(this.bdd.getBaseTypeCours().rechercher((String)this.typeCours.getSelectionModel().getSelectedItem()));
        
        this.heure.setHeure(Integer.parseInt(this.heureTexte.getText()));
        this.heure.setMinute(Integer.parseInt(this.minuteTexte.getText()));
        this.heure.setDuree(Integer.parseInt(this.dureeTexte.getText()));
        
        this.cours.setCreneau(this.heure);
        
        
        this.composantEDT.actualiser();
    }
    
}
