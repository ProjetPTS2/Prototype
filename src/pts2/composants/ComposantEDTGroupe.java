/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.composants;

import java.util.Calendar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import pts2.bdd.BDD;
import pts2.donnees.Cours;
import pts2.donnees.Groupe;
import pts2.donnees.Jours;
import pts2.donnees.Semaine;
import pts2.ihm.AccueilController;
import pts2.utilitaire.Constantes;

/**
 *
 * @author Theo
 */
public class ComposantEDTGroupe extends ComposantEDT {
    
    private int nombreGroupes;
    
    public ComposantEDTGroupe(BDD bdd, ScrollPane parent) {
        super(bdd, parent);
    }
    
    @Override
    public void initialiserEDT(Semaine semaine) {
        nombreGroupes = 0;
        for(Groupe groupe : this.bdd.getBaseGroupe().getListeDonnees()) {
            if(groupe.getSousGroupes().size() != 0)
                nombreGroupes += groupe.getSousGroupes().size();
            else
                nombreGroupes++;
        }
        this.semaineActuelle = semaine;
        int hauteurJours = nombreGroupes * Constantes.HAUTEUR_JOURS;
        // AFFICHAGE DES JOURS
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, semaine.getNoSemaine());
        for(int idJour = 0; idJour < Jours.values().length; idJour++) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + idJour);
            String mois = this.convertirMoisCalendarEnString(calendar.get(Calendar.MONTH));
            ComposantTexte comp = new ComposantTexte(Jours.values()[idJour].getNom() + " " + calendar.get(Calendar.DATE) + " " + mois, 0, 
                    idJour * hauteurJours + Constantes.HAUTEUR_HEURES, Constantes.LARGEUR_JOURS, hauteurJours);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.getChildren().add(comp);
        }
        
        // AFFICHAGE DES HEURES
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            ComposantTexte comp = new ComposantTexte(heure + "h", (heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS, 
                    0, Constantes.LARGEUR_HEURES, Constantes.HAUTEUR_HEURES);
            comp.setCouleurFond(Constantes.COULEUR_FOND_EDT);
            comp.setCouleurTexte(Constantes.COULEUR_TEXTE_EDT);
            this.getChildren().add(comp);
        }
        
        // AFFICHAGE DES CASES COURS
        for(int heure = Constantes.HEURE_DEBUT; heure <= Constantes.HEURE_FIN; heure++) {
            for(int jour = 0; jour < Jours.values().length * nombreGroupes; jour++) {
                ComposantCaseEDT comp = new ComposantCaseEDT((heure-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS,
                        jour * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES);
                this.getChildren().add(comp);
            }
        }
        super.getChildren().add(this.edtCours);
    }
    
    
    @Override
    public void actualiser(Semaine semaine) {
        this.semaineActuelle = semaine;
        if(this.getChildren().isEmpty())
            this.initialiserEDT(semaine);
        
        this.edtCours.getChildren().clear();
        int hauteurJours = nombreGroupes * Constantes.HAUTEUR_JOURS;
        
        
        // AFFICHAGE DES COURS
        for(Cours cours : semaine.getListeCours()) {
            int index = 0;
            boolean trouve = false;
            for(Groupe groupe : this.bdd.getBaseGroupe().getListeDonnees()) {
                if(trouve)
                    break;
                if(groupe.getNom().equals(cours.getGroupe().getNom())) {
                    trouve = true;
                    break;
                }
                
                if(!groupe.getSousGroupes().isEmpty()) {
                    for(Groupe sg : groupe.getSousGroupes()) {
                        if(sg.getNom().equals(cours.getGroupe().getNom())) {
                            trouve = true;
                            break;
                        } else
                            index++;
                    }
                } else
                    index++;
                                    
            }
            ComposantHeure coursComposant = new ComposantHeure(this, semaine, cours, 
                    (cours.getCreneau().getHeure()-Constantes.HEURE_DEBUT) * Constantes.LARGEUR_HEURES + Constantes.LARGEUR_JOURS, 
                    cours.getCreneau().getJours().getNumero() * hauteurJours + index * Constantes.HAUTEUR_JOURS + Constantes.HAUTEUR_HEURES);
            int n = (!cours.getGroupe().getSousGroupes().isEmpty()) ? cours.getGroupe().getSousGroupes().size() : 1;
            coursComposant.rectangle.setHeight(Constantes.HAUTEUR_JOURS*n);
            coursComposant.initialiserEvents(this.getScene(), this.scrollPane);
            this.edtCours.getChildren().add(coursComposant);
        }
        this.texteSurvol.toFront();
    }
    
    @Override
    protected void deplacementCours(MouseEvent event) {
        if(this.coursEnDeplacement != null) {
            this.coursEnDeplacement.getCours();
            double OFFSET_X = -this.scrollPane.getLayoutX() - Constantes.LARGEUR_JOURS;
            double DECALAGE_SCROLL_X = -1* this.scrollPane.getViewportBounds().getMinX() + 1;
            double DECALAGE_SCROLL_Y = -1* this.scrollPane.getViewportBounds().getMinY() + 1;
            double OFFSET_Y = -this.scrollPane.getLayoutY() - Constantes.HAUTEUR_HEURES;
            double eventX = event.getSceneX() + OFFSET_X - this.coursEnDeplacement.getDragDrop().getDecalage() + DECALAGE_SCROLL_X;
            double eventY = event.getSceneY() + OFFSET_Y + DECALAGE_SCROLL_Y;
            
            int hauteurJours = nombreGroupes * Constantes.HAUTEUR_JOURS;
            
            int heure = (int) (eventX / Constantes.LARGEUR_HEURES); // Heure à partir de 0
            int heure_reel = heure + Constantes.HEURE_DEBUT;
            int minute = (int) (eventX - heure*Constantes.LARGEUR_HEURES) *60/Constantes.LARGEUR_HEURES;
            minute /= AccueilController.SNAP;
            minute *= AccueilController.SNAP;
            int jours = (int) (eventY/ hauteurJours); //ID du jour
            int groupe = (int)(eventY/Constantes.HAUTEUR_JOURS)%nombreGroupes; //Id du groupe (à partir de 0)
            
            boolean sousGroupe = this.coursEnDeplacement.getCours().getGroupe().getSousGroupes().isEmpty();
            
            if(eventX < 0) {
                heure = 0;
                heure_reel = Constantes.HEURE_DEBUT;
                minute = 0;
            }
            
            int index = 0;
            Groupe groupeObj = null;
            boolean trouve = false;
            for(Groupe gr : this.bdd.getBaseGroupe().getListeDonnees()) {
                if(trouve)
                    break;
                if(index == groupe && !sousGroupe) {
                    groupeObj = gr;
                    trouve = true;
                    break;
                }
                
                if(!gr.getSousGroupes().isEmpty()) {
                    for(Groupe sg : gr.getSousGroupes()) {
                        if(index == groupe) {
                            groupeObj = sg;
                            trouve = true;
                            break;
                        } else
                            index++;
                    }
                } else if(sousGroupe)
                    index++;
                                    
            }
            
            boolean valide = groupeObj.getSousGroupes().isEmpty() == sousGroupe;
            
            Cours cours = this.coursEnDeplacement.getCours();
            if(valide)
                cours.setGroupe(groupeObj);
            cours.getCreneau().setHeure(heure_reel);
            cours.getCreneau().setMinute(minute);
            cours.getCreneau().setJours(Jours.values()[jours]);
            
            int n = (!cours.getGroupe().getSousGroupes().isEmpty()) ? cours.getGroupe().getSousGroupes().size() : 1;
            coursEnDeplacement.rectangle.setHeight(Constantes.HAUTEUR_JOURS*n);
            
            double m = minute*Constantes.LARGEUR_HEURES/60;
            double compX = heure*Constantes.LARGEUR_HEURES + m + Constantes.LARGEUR_JOURS;
            double compY = (valide) ? groupe * Constantes.HAUTEUR_JOURS + jours * hauteurJours + Constantes.HAUTEUR_HEURES : this.coursEnDeplacement.getLayoutY();
            this.coursEnDeplacement.deplacement(compX, compY, this.scrollPane);
        }
        
        event.consume();
    }
}
