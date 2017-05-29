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
import pts2.donnees.Jours;
import pts2.donnees.Cours;
import pts2.donnees.Enseignant;
import pts2.donnees.Creneau;
import pts2.donnees.Groupe;
import pts2.donnees.Matiere;
import pts2.donnees.Salle;
import pts2.donnees.TypeCours;
import pts2.ihm.Fenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class AjouterCoursController extends Fenetre implements Initializable {
    
    @FXML
    private ComboBox typeCours, enseignant, matiere, salle;
    @FXML
    private TextField dureeTexte;
    
    public AjouterCoursController(BDD bdd, ComposantEDT edt) {
        super("Ajouter un cours", "AjouterCours.fxml", bdd, edt);
        super.chargerIHM();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> liste = new ArrayList<>();
        for(Matiere _matiere : this.bdd.getBaseMatieres().getListeDonnees()) {
            liste.add(_matiere.getDiminutif());
        }
        this.matiere.setItems(FXCollections.observableList(liste));
        
        liste = new ArrayList<>();
        for(Salle _salle : this.bdd.getBaseSalles().getListeDonnees()) {
            liste.add(_salle.getNom());
        }
        this.salle.setItems(FXCollections.observableList(liste));
        
        
        
        liste = new ArrayList<>();
        for(Enseignant _enseignant : this.bdd.getBaseEnseignants().getListeDonnees()) {
            liste.add(_enseignant.getDiminutif());
        }
        this.enseignant.setItems(FXCollections.observableList(liste));
        
        
        liste = new ArrayList<>();
        for(TypeCours _typeCours : this.bdd.getBaseTypeCours().getListeDonnees()) {
            liste.add(_typeCours.getNom());
        }
        this.typeCours.setItems(FXCollections.observableList(liste));
        
        
        this.dureeTexte.setText("60");
        
        this.dureeTexte.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                this.dureeTexte.setText(oldValue);
        });
        
    }   
    
    
    
    public void placerCours() {
        Enseignant _enseignant = this.bdd.getBaseEnseignants().rechercher((String)this.enseignant.getSelectionModel().getSelectedItem());
        Matiere _matiere = this.bdd.getBaseMatieres().rechercher((String)this.matiere.getSelectionModel().getSelectedItem());
        Salle _salle = this.bdd.getBaseSalles().rechercher((String)this.salle.getSelectionModel().getSelectedItem());
        TypeCours _typeCours = this.bdd.getBaseTypeCours().rechercher((String)this.typeCours.getSelectionModel().getSelectedItem());
        int _duree = Integer.parseInt(dureeTexte.getText());
        Groupe _groupe = this.bdd.getBaseGroupe().rechercher("C1");
        
        Cours cours = new Cours(new Creneau(Jours.LUNDI, 8, 0, _duree), _groupe, _enseignant, _matiere, _salle, _typeCours);
        this.composantEDT.ajouterCoursTemporaire(cours);
        this.stage.close();
    }
}
