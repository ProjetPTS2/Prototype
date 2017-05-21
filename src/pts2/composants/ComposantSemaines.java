/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pts2.bdd.BaseSemaines;

/**
 *
 * @author tgallard
 */
public class ComposantSemaines extends HBox {
    
    private int idSemaine;
    private int semaineEnCours;
    
    private final Button[] boutonSemaines;
    
    public ComposantSemaines(int semaineEnCours) {
        this.semaineEnCours = semaineEnCours;
        this.boutonSemaines = new Button[52];
    }
    
    public void initialiserComposants(BaseSemaines baseSemaines, ComposantEDT edt) {
        for(int i = 0; i < 52; i++) {
            int semaine = this.convertirSemaineEnID(i);
            final int noSemaine = semaine;
            Button btn = new Button("S" + noSemaine);
            btn.setPrefSize(60, 40);
            
            final int id = i;
            if(semaine == this.semaineEnCours)
                this.idSemaine = id;
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    edt.actualiser(baseSemaines.rechercher(noSemaine));
                    boutonSemaines[id].setDisable(true);
                    boutonSemaines[idSemaine].setDisable(false);
                    idSemaine = id;
                    semaineEnCours = semaine;
                }
            
            });
            this.getChildren().add(btn);
            this.boutonSemaines[i] = btn;
        }
        
        this.boutonSemaines[this.idSemaine].setDisable(true);
        
        //this.setPrefSize(this.getPrefWidth(), this.getPrefHeight()+10);
        //this.setLayoutY(0);
    }
    
    private int convertirSemaineEnID(int semaine) {
        semaine = (semaine+35)%53;
        if(semaine < 34)
                semaine++;
        return semaine;
    }
    
}
