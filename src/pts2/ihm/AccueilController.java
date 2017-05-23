/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm;

import pts2.ihm.edition.EditerMatieresController;
import pts2.ihm.edition.EditerSallesController;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import pts2.EDT;
import pts2.composants.ComposantEDT;
import pts2.composants.ComposantSemaines;
import pts2.ihm.edition.AjouterCoursController;
import pts2.ihm.edition.EditerTypeCoursController;

/**
 * FXML Controller class
 *
 * @author Theo
 */
public class AccueilController extends Fenetre implements Initializable {
    
    private final EDT edt;
    private ComposantSemaines composantSemaines;
    
    @FXML
    private AnchorPane racinePane;
    @FXML
    private ScrollPane semainesScrollPane;
    @FXML
    private ScrollPane edtScrollPane;
    @FXML
    private Menu snapMenu;
    
    public static int SNAP = 1;
    
    public AccueilController(EDT edt) {
        super("R3G15", "Accueil.fxml", edt.getBDD(), null);
        super.chargerIHM();
        this.edt = edt;
        this.composantEDT.initaliserEDT(this.bdd.getBaseSemaines().rechercher(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)));
        this.composantSemaines.initialiserComposants(this.bdd.getBaseSemaines(), this.composantEDT);
    }    
    
    
    public void actualiser() {
        this.composantEDT.actualiser();
    }
    
    public ComposantEDT getComposantEDT() {
        return this.composantEDT;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        this.composantEDT = new ComposantEDT(this.bdd, this.edtScrollPane);
        this.composantSemaines = new ComposantSemaines(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        
        this.semainesScrollPane.setContent(this.composantSemaines);
        this.edtScrollPane.setContent(this.composantEDT);
    }    
    
    
    
    
    
    public void menuBar_fichier_nouveau() {
        this.bdd.vider();
        this.actualiser();
    }
    
    public void menuBar_fichier_ouvrir() {
        this.edt.chargerEDT();
        this.actualiser();
    }
    
    public void menuBar_fichier_sauvegarder() {
        this.edt.sauvegarderEDT();
    }
    
    
    public void menuBar_edtion_ajouterCours() {
        EDT.afficherFenetre(new AjouterCoursController(this.edt.getBDD(), this.composantEDT));
    }
    
    
    public void menuBar_edtion_editerSalles() {
        EDT.afficherFenetre(new EditerSallesController(this.edt.getBDD(), this.composantEDT));
    }
    
    public void menuBar_edtion_editerMatieres() {
        EDT.afficherFenetre(new EditerMatieresController(this.edt.getBDD(), this.composantEDT));
    }
    
    public void menuBar_edtion_editerTypeCours() {
        EDT.afficherFenetre(new EditerTypeCoursController(this.edt.getBDD(), this.composantEDT));
    }
    
    
    
    public void menuBar_edition_snap_1() {
        this.setSnap(1);
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
