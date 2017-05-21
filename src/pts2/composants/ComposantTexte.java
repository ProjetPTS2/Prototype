/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author tgallard
 */
public class ComposantTexte extends StackPane {
    
    protected final Rectangle rectangle;
    protected final Text texte;
    protected final String texteParDefaut;
    
    public ComposantTexte(String texteParDefaut, int x, int y, int largeur, int hauteur) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        
        this.texteParDefaut = texteParDefaut;
        
        this.rectangle = new Rectangle(largeur, hauteur);
        this.rectangle.setFill(Color.WHITE);
        
        this.rectangle.setStroke(Color.BLACK);
        this.texte = new Text(texteParDefaut);
        
        this.texte.setFill(Color.BLACK);
        this.texte.setFont(new Font("Roboto", 18));
        this.texte.setBoundsType(TextBoundsType.VISUAL);
        
        super.getChildren().add(this.rectangle);
        super.getChildren().add(this.texte);
    }
    
    public void setTexte(String str) {
        this.texte.setText(str);
    }
    
    public void recalculerTaille() {
        this.rectangle.setWidth(this.getWidth());
        this.rectangle.setHeight(this.getHeight());
        System.out.println(rectangle.getWidth() + " - " + rectangle.getHeight());
    }
    
    public void setCouleurFond(Color couleur) {
        this.rectangle.setFill(couleur);
    }
    
    public void setCouleurTexte(Color couleur) {
        this.texte.setFill(couleur);
    }
}
