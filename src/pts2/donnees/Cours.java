package pts2.donnees;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLSauvegarde;


public class Cours implements ISauvegarde {
    
    private Groupe groupe;
    private Creneau creneau;
    private Enseignant enseignant;
    private TypeCours typeCours;
    private Matiere matiere;
    private Salle salle;
    
    /**
     * Constructeur de la classe Cours.
     * @param creneau Le creneau du cours.
     * @param enseignant L'enseignant assigné à ce cours.
     * @param matiere La matière de ce cours.
     * @param salle La salle de ce cours.
     * @param typeCours Le type de ce cours.
     */
    public Cours(Creneau creneau, Groupe groupe, Enseignant enseignant, Matiere matiere, Salle salle, TypeCours typeCours) {
        this.groupe = groupe;
        this.creneau = creneau;
        this.enseignant = enseignant;
        this.matiere = matiere;
        this.typeCours = typeCours;
        this.salle = salle;
    }
    
    public Creneau getCreneau() {
        return this.creneau;
    }
    
    public Groupe getGroupe() {
        return this.groupe;
    }
    
    public Enseignant getEnseignant() {
        return this.enseignant;
    }
    
    public TypeCours getTypeCours() {
        return this.typeCours;
    }
    
    public Matiere getMatiere() {
        return this.matiere;
    }
    
    public Salle getSalle() {
        return this.salle;
    }
    
    public void setCreneau(Creneau creneau) {
        if(creneau != null) {
            this.creneau.setJours(creneau.getJours());
            this.creneau.setHeure(creneau.getHeure());
            this.creneau.setMinute(creneau.getMinute());
            this.creneau.setDuree(creneau.getDuree());
        } else
            this.creneau = creneau;
    }
    
    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
    
    public void setTypeCours(TypeCours typeCours) {
        this.typeCours = typeCours;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    
    @Override
    public void sauvegarder(XMLSauvegarde xml) {
        Map<String, String> attributs = new HashMap<>();
        attributs.put("enseignant", this.enseignant.getDiminutif());
        attributs.put("matiere", this.matiere.getDiminutif());
        attributs.put("salle", this.salle.getNom());
        attributs.put("typecours", this.typeCours.getNom());
        attributs.put("groupe", this.groupe.getNom());
        xml.ouvrirBalise("Cours", attributs, true);
        this.creneau.sauvegarder(xml);
        xml.fermerBalise();
    }

    
    @Override
    public void charger(BDD bdd, Element element) {
        this.enseignant = bdd.getBaseEnseignants().rechercher(element.getAttribute("enseignant"));
        this.matiere = bdd.getBaseMatieres().rechercher(element.getAttribute("matiere"));
        this.salle = bdd.getBaseSalles().rechercher(element.getAttribute("salle"));
        this.creneau = new Creneau(Jours.LUNDI, 0, 0, 0);
        this.creneau.charger(bdd, ((Element)(element.getElementsByTagName("Creneau").item(0))));
        this.typeCours = bdd.getBaseTypeCours().rechercher(element.getAttribute("typecours"));
        this.groupe = bdd.getBaseGroupe().rechercher(element.getAttribute("groupe"));
    }
}
