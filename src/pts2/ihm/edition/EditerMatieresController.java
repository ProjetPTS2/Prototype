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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import pts2.bdd.BDD;
import pts2.composants.ComposantEDT;
import pts2.donnees.Matiere;
import pts2.ihm.Fenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class EditerMatieresController extends Fenetre implements Initializable {
    
    @FXML
    private ComboBox choixMatiere;
    @FXML
    private TextField editerDiminutifMatiere;
    @FXML
    private TextField editerNomMatiere;
    @FXML
    private ColorPicker editerCouleurMatiere;
    
    @FXML
    private TextField ajouterDiminutifMatiere;
    @FXML
    private TextField ajouterNomMatiere;
    @FXML
    private ColorPicker ajouterCouleurMatiere;
    @FXML
    private AnchorPane editerMatierePane;

    public EditerMatieresController(BDD bdd, ComposantEDT edt) {
        super("Editer les matières", "EditerMatieres.fxml", bdd, edt);
        super.chargerIHM();
    }
    
    public void actualiserListeMatieres() {
        this.actualiserListeMatieres(this.choixMatiere.getSelectionModel().getSelectedIndex());
    }
    
    public void actualiserListeMatieres(int index) {
        List<String> listeNomsMatieres = new ArrayList<>();
        for(Matiere matiere : this.bdd.getBaseMatieres().getListeDonnees())
            listeNomsMatieres.add(matiere.getDiminutif());
        this.choixMatiere.setItems(FXCollections.observableList(listeNomsMatieres));
        this.choixMatiere.getSelectionModel().select(index);
        this.editerMatierePane.setDisable(listeNomsMatieres.isEmpty());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.choixMatiere.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                Matiere matiere = bdd.getBaseMatieres().rechercher(nouvelleValeur);
                if(matiere != null) {
                    editerDiminutifMatiere.setText(matiere.getDiminutif());
                    editerNomMatiere.setText(matiere.getNom());
                    editerCouleurMatiere.setValue(matiere.getCouleur());
                }
            }
        });
        
        this.actualiserListeMatieres(0);
    }   
    
    
    
    
    
    public void ajouterMatiere() {
        if(this.bdd.getBaseMatieres().ajouter(new Matiere(this.ajouterDiminutifMatiere.getText(), this.ajouterNomMatiere.getText(), this.ajouterCouleurMatiere.getValue()))) {
            this.ajouterDiminutifMatiere.setText("");
            this.ajouterNomMatiere.setText("");
            this.ajouterCouleurMatiere.setValue(Color.WHITE);
            this.actualiserListeMatieres();
        }
    }
    
    public void sauvegarderMatiere() {
        Matiere matiere = this.bdd.getBaseMatieres().rechercher((String)this.choixMatiere.getSelectionModel().getSelectedItem());
        matiere.setDiminutif(this.editerDiminutifMatiere.getText());
        matiere.setNom(this.editerNomMatiere.getText());
        matiere.setCouleur(this.editerCouleurMatiere.getValue());
        this.actualiserListeMatieres();
        this.composantEDT.actualiser();
    }
    
    public void supprimerMatiere() {
        Matiere matiere = this.bdd.getBaseMatieres().rechercher((String)this.choixMatiere.getSelectionModel().getSelectedItem());
        this.bdd.getBaseMatieres().supprimer(matiere);
        matiere.setDiminutif("");
        matiere.setNom("");
        this.actualiserListeMatieres();
        this.composantEDT.actualiser();
    }
}
