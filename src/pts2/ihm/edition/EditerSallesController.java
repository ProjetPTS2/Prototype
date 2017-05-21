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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pts2.bdd.BDD;
import pts2.composants.ComposantEDT;
import pts2.donnees.Salle;
import pts2.ihm.Fenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class EditerSallesController extends Fenetre implements Initializable {
    
    @FXML
    private Tab tab;
    @FXML
    private TextField ajouterSalle_nomSalle, ajouterSalle_capacite;
    @FXML
    private Label ajouterSalle_resultat;
    
    @FXML
    private ComboBox editerSalles_choixSalle;
    @FXML
    private TextField editerSalles_nomSalle,  editerSalles_capacite;
    @FXML
    private Label editerSalles_resultat;
    @FXML
    private Pane editerSalles_pane;
    
    public EditerSallesController(BDD bdd, ComposantEDT edt) {
        super("Editer les salles", "EditerSalles.fxml", bdd, edt);
        super.chargerIHM();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ajouterSalle_capacite.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                this.ajouterSalle_capacite.setText(oldValue);
        });
        this.ajouterSalle_resultat.setText("");
        
        this.editerSalles_choixSalle.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                Salle salle = bdd.getBaseSalles().rechercher(nouvelleValeur);
                if(salle != null) {
                    editerSalles_nomSalle.setText(salle.getNom());
                    editerSalles_capacite.setText(salle.getCapacite() + "");
                }
            }
        });
        this.editerSalles_resultat.setText("");
        
    }
    
    public void changementTab() {
        this.changementTab(0);
    }
    
    private void changementTab(int index) {
        if(tab.getTabPane().getSelectionModel().isSelected(1)) {
            List<String> listeNomsSalles = new ArrayList<>();
            for(Salle salle : this.bdd.getBaseSalles().getListeDonnees())
                listeNomsSalles.add(salle.getNom());
            this.editerSalles_choixSalle.setItems(FXCollections.observableList(listeNomsSalles));
            this.editerSalles_choixSalle.getSelectionModel().select(index);
            this.editerSalles_pane.setDisable(listeNomsSalles.isEmpty());
        }
    }

    public void ajouterSalle_valider() {
        if(this.ajouterSalle_nomSalle.getText().equals("") || this.ajouterSalle_capacite.getText().equals("")){
            this.ajouterSalle_resultat.setText("Veuillez remplir les champs.");
            this.ajouterSalle_resultat.setTextFill(Color.RED);
        } else if(!this.bdd.getBaseSalles().ajouter(new Salle(this.ajouterSalle_nomSalle.getText(), Integer.parseInt(this.ajouterSalle_capacite.getText())))) {
            this.ajouterSalle_resultat.setText("Ce nom de salle est déjà utilisé.");
            this.ajouterSalle_resultat.setTextFill(Color.RED);
        } else {
             this.ajouterSalle_nomSalle.setText("");
            this.ajouterSalle_capacite.setText("");
            this.ajouterSalle_resultat.setText("La salle a bien été ajoutée.");
            this.ajouterSalle_resultat.setTextFill(Color.GREEN);
        }
    }
    
    public void editerSalles_sauvegarde() {
        Salle salle = this.bdd.getBaseSalles().rechercher((String)this.editerSalles_choixSalle.getSelectionModel().getSelectedItem());
        salle.setNom(this.editerSalles_nomSalle.getText());
        salle.setCapacite(Integer.parseInt(this.editerSalles_capacite.getText()));
        this.editerSalles_resultat.setTextFill(Color.GREEN);
        this.editerSalles_resultat.setText("Changements sauvegardés.");
        this.changementTab(this.editerSalles_choixSalle.getSelectionModel().getSelectedIndex());
    }
}
