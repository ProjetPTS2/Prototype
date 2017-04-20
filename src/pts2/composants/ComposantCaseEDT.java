/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pts2.Constantes;

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
        
        this.getChildren().add(this.rectangle);
    }
}
