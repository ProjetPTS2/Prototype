/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author tgallard
 */
public class ComposantSurvol extends StackPane {
    
    private final Text texte;
    
    public static final double MARGIN_LEFT = 17, PADDING_LEFT_RIGHT = 6.0, PADDING_TOP_BOTTOM = 9.0;
    
    public ComposantSurvol() {
        this.texte = new Text();
        
        this.texte.setFill(Color.BLACK);
        this.texte.setFont(new Font("Roboto", 18));
        this.texte.setBoundsType(TextBoundsType.VISUAL);
        
        this.setCouleurFond(Color.WHITE);
        
        this.getChildren().add(this.texte);
    }
    
    public void setTexte(String str) {
        this.texte.setText(str);
    }
    
    public void setCouleurFond(Color couleur) {
        couleur = new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 0.95);
        BackgroundFill background = new BackgroundFill(couleur, new CornerRadii(0),new Insets(-PADDING_TOP_BOTTOM,-PADDING_LEFT_RIGHT,-PADDING_TOP_BOTTOM,-PADDING_LEFT_RIGHT));
        this.setBackground(new Background(background));
    }
    
    public void setPosition(double x, double y) {
        this.setLayoutX(x + PADDING_LEFT_RIGHT + MARGIN_LEFT);
        this.setLayoutY(y + PADDING_TOP_BOTTOM);
    }
    
    public void actualiserPosition(ScrollPane scrollPane, ComposantEDT edt, double x, double y) {
        double minX = -1 * scrollPane.getViewportBounds().getMinX() + 1;
        double maxX = minX + scrollPane.getViewportBounds().getWidth();
        double _x = minX + x;
        if((_x + this.getWidth() + MARGIN_LEFT + PADDING_LEFT_RIGHT) > maxX)
            _x = x - this.getWidth() - 2*MARGIN_LEFT - 2*PADDING_LEFT_RIGHT + minX;
        this.setPosition(_x, y);
    }
}
