/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pts2.utilitaire.Constantes;

/**
 *
 * @author Theo
 */
public class ComposantCaseEDT extends StackPane {
    
    private final Rectangle rectangle;
    
    public ComposantCaseEDT(int x, int y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        
        this.rectangle = new Rectangle(Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_JOURS);
        this.rectangle.setFill(Color.WHITE);
        
        this.rectangle.setStroke(Color.BLACK);    
        
        super.getChildren().add(this.rectangle);
    }
}
