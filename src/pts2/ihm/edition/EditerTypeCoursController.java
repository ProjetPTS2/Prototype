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
import pts2.donnees.TypeCours;
import pts2.ihm.Fenetre;

/**
 *
 * @author roheix
 */
public class EditerTypeCoursController extends Fenetre implements Initializable {

    @FXML
    private Tab tab;
    @FXML
    private TextField ajouterTypeCours_nomTypeCours;
    @FXML
    private Label ajouterTypeCours_resultat;
    @FXML
    private TextField editerTypeCours_nomTypeCours;
    @FXML
    private ComboBox editerTypeCours_choixCours;
    @FXML
    private TextField editerSalles_nomSalle,  editerSalles_capacite;
    @FXML
    private Label editerTypeCours_resultat;
    @FXML
    private Pane editerTypeCours_pane;
    
    public EditerTypeCoursController(BDD bdd, ComposantEDT edt) {
        super("Editer les types de cours", "EditerTypeCours.fxml", bdd, edt);
        super.chargerIHM();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ajouterTypeCours_resultat.setText("");
        
        this.editerTypeCours_choixCours.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String ancienneValeur, String nouvelleValeur) {
                TypeCours tp = bdd.getBaseTypeCours().rechercher(nouvelleValeur);
                if(tp != null) {
                    editerTypeCours_nomTypeCours.setText(tp.getNom());
                }
            }
        });
        this.editerTypeCours_resultat.setText("");
        
    }
    
    public void changementTab() {
        this.changementTab(0);
    }
    
    private void changementTab(int index) {
        if(tab.getTabPane().getSelectionModel().isSelected(1)) {
            List<String> listeNomsTypesCours = new ArrayList<>();
            for(TypeCours tp : this.bdd.getBaseTypeCours().getListeDonnees())
                listeNomsTypesCours.add(tp.getNom());
            this.editerTypeCours_choixCours.setItems(FXCollections.observableList(listeNomsTypesCours));
            this.editerTypeCours_choixCours.getSelectionModel().select(index);
            this.editerTypeCours_pane.setDisable(listeNomsTypesCours.isEmpty());
        }
    }

    public void ajouterTypeCours_valider() {
        if(this.ajouterTypeCours_nomTypeCours.getText().equals("")){
            this.ajouterTypeCours_resultat.setText("Veuillez remplir les champs.");
            this.ajouterTypeCours_resultat.setTextFill(Color.RED);
        } 
        else if(!this.bdd.getBaseTypeCours().ajouter(new TypeCours(this.ajouterTypeCours_nomTypeCours.getText()))) {
            this.ajouterTypeCours_resultat.setText("Ce nom de Type de Cours est déjà utilisé.");
            this.ajouterTypeCours_resultat.setTextFill(Color.RED);
        } else {
             this.ajouterTypeCours_nomTypeCours.setText("");
            this.ajouterTypeCours_resultat.setText("Le type de cours a bien été ajouté.");
            this.ajouterTypeCours_resultat.setTextFill(Color.GREEN);
        }
    }
    
    public void editerTypeCours_sauvegarde() {
        TypeCours tp = this.bdd.getBaseTypeCours().rechercher((String)this.editerTypeCours_choixCours.getSelectionModel().getSelectedItem());
        tp.setNom(this.editerTypeCours_nomTypeCours.getText());
        this.editerTypeCours_resultat.setTextFill(Color.GREEN);
        this.editerTypeCours_resultat.setText("Changements sauvegardés.");
        this.changementTab(this.editerTypeCours_choixCours.getSelectionModel().getSelectedIndex());
    }
    
    public void editerTypeCours_supprimer() {
        TypeCours tp = this.bdd.getBaseTypeCours().rechercher((String)this.editerTypeCours_choixCours.getSelectionModel().getSelectedItem());
        this.bdd.getBaseTypeCours().supprimer(tp);
        tp.setNom(this.editerTypeCours_nomTypeCours.getText());
        this.editerTypeCours_resultat.setTextFill(Color.GREEN);
        this.editerTypeCours_resultat.setText("Type de cours supprimé.");
        this.changementTab(this.editerTypeCours_choixCours.getSelectionModel().getSelectedIndex());
        
    }
    
}
