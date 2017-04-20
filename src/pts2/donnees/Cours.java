package pts2.donnees;

import pts2.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;


public class Cours implements ISauvegarde {
    
    // FAIRE SAUVEGARDER/CHARGE HEURE
    
    private HeureEDT heure;
    private Enseignant enseignant;
    private String typeCours;
    private Matiere matiere;
    private Salle salle;
    
    /**
     * Constructeur de la classe Cours.
     * @param enseignant L'enseignant assigné à ce cours.
     * @param matiere La matière de ce cours.
     * @param salle La salle de ce cours.
     * @param typeCours Le type de ce cours.
     */
    public Cours(HeureEDT heure, Enseignant enseignant, Matiere matiere, Salle salle, String typeCours) {
        this.heure = heure;
        this.enseignant = enseignant;
        this.matiere = matiere;
        this.typeCours = typeCours;
        this.salle = salle;
    }
    
    public HeureEDT getHeure() {
        return this.heure;
    }
    
    public Enseignant getEnseignant() {
        return this.enseignant;
    }
    
    public String getTypeCours() {
        return this.typeCours;
    }
    
    public Matiere getMatiere() {
        return this.matiere;
    }
    
    public Salle getSalle() {
        return this.salle;
    }
    
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }
    
    public void setTypeCours(String typeCours) {
        this.typeCours = typeCours;
    }
    
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    
    @Override
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Cours");
        this.enseignant.sauvegarder(xml);
        xml.ecrireValeur("TypeCours", this.typeCours);
        this.matiere.sauvegarder(xml);
        this.salle.sauvegarder(xml);
        xml.fermerBalise();
    }

    
    @Override
    public void charger(XMLObjet xml, BDD bdd) {
        String diminutifEnseignant = xml.getSousCategories().get(0).getPremiereValeur("Diminutif");
        this.enseignant = bdd.getEnseignant(diminutifEnseignant);
        this.typeCours = xml.getPremiereValeur("TypeCours");
        String diminutifMatiere = xml.getSousCategories().get(1).getPremiereValeur("Diminutif");
        this.matiere = bdd.getMatiere(diminutifMatiere);
        String nomSalle = xml.getSousCategories().get(2).getPremiereValeur("Nom");
        this.salle = bdd.getSalle(nomSalle);
    }
}