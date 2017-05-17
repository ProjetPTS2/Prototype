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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pts2.EDT;
import pts2.donnees.Matiere;
import pts2.ihm.AccueilController;
import pts2.ihm.IFenetre;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class EditerMatieresController implements IFenetre, Initializable {
    
    
    private AccueilController accueilController;
    private Parent racine;
    
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


    @Override
    public void initialiser(AccueilController accueilController, Stage stage) {
        try {
            this.accueilController = accueilController;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditerMatieres.fxml"));
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
        return "Editer matières";
    }
    
    public void actualiserListeMatieres() {
        this.actualiserListeMatieres(this.choixMatiere.getSelectionModel().getSelectedIndex());
    }
    
    public void actualiserListeMatieres(int index) {
        List<String> listeNomsMatieres = new ArrayList<>();
        for(Matiere matiere : EDT.getInstance().getBDD().getBaseMatieres().getListeDonnees())
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
                Matiere matiere = EDT.getInstance().getBDD().getBaseMatieres().rechercher(nouvelleValeur);
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
        if(EDT.getInstance().getBDD().getBaseMatieres().ajouter(new Matiere(this.ajouterDiminutifMatiere.getText(), this.ajouterNomMatiere.getText(), this.ajouterCouleurMatiere.getValue()))) {
            this.ajouterDiminutifMatiere.setText("");
            this.ajouterNomMatiere.setText("");
            this.ajouterCouleurMatiere.setValue(Color.WHITE);
            this.actualiserListeMatieres();
        }
    }
    
    public void sauvegarderMatiere() {
        Matiere matiere = EDT.getInstance().getBDD().getBaseMatieres().rechercher((String)this.choixMatiere.getSelectionModel().getSelectedItem());
        matiere.setDiminutif(this.editerDiminutifMatiere.getText());
        matiere.setNom(this.editerNomMatiere.getText());
        matiere.setCouleur(this.editerCouleurMatiere.getValue());
        this.actualiserListeMatieres();
        this.accueilController.actualiser();
    }
}
