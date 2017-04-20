/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.ihm;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author Theo
 */
public interface IFenetre {
    
    /**
     * Initialise la classe comme un constructeur.
     * @param accueilController La fenêtre principale du programme.
     * @param stage Le stage de cette fenêtre.
     */
    public void initialiser(AccueilController accueilController, Stage stage);
    
    /**
     * Retourne le composant racine de la fenêtre.
     * @return Retourne Le composant racine(class Parent)
     */
    public Parent getRacine();
    
    /**
     * Retourne le nom de la fenêtre.
     * @return Retourne le nom de la fenêtre.
     */
    public String getNom();
}
