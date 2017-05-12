package pts2.donnees;

import pts2.EDT;
import pts2.bdd.BDD;
import pts2.utilitaire.ISauvegarde;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLObjet;


public class Cours implements ISauvegarde {
    
    private Jours jours;
    private HeureEDT heure;
    private Enseignant enseignant;
    private TypeCours typeCours;
    private Matiere matiere;
    private Salle salle;
    
    /**
     * Constructeur de la classe Cours.
     * @param enseignant L'enseignant assigné à ce cours.
     * @param matiere La matière de ce cours.
     * @param salle La salle de ce cours.
     * @param typeCours Le type de ce cours.
     */
    public Cours(Jours jours, HeureEDT heure, Enseignant enseignant, Matiere matiere, Salle salle, TypeCours typeCours) {
        this.jours = jours;
        this.heure = heure;
        this.enseignant = enseignant;
        this.matiere = matiere;
        this.typeCours = typeCours;
        this.salle = salle;
    }
    
    public Jours getJours() {
        return this.jours;
    }
    
    public HeureEDT getHeure() {
        return this.heure;
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
    
    
    public void setJours(Jours jours) {
        this.jours = jours;
    }
    
    public void setHeure(HeureEDT heure) {
        if(heure != null) {
            this.heure.setHeure(heure.getHeure());
            this.heure.setMinute(heure.getMinute());
            this.heure.setDuree(heure.getDuree());
        } else
            this.heure = heure;
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
    public void sauvegarder(XMLEcriture xml) {
        xml.ouvrirBalise("Cours");
        this.enseignant.sauvegarder(xml);
        this.typeCours.sauvegarder(xml);
        this.matiere.sauvegarder(xml);
        this.salle.sauvegarder(xml);
        this.heure.sauvegarder(xml);
        xml.ecrireValeur("Jours", this.jours.getNumero()+"");
        xml.fermerBalise();
    }

    
    @Override
    public void charger(XMLObjet xml) {
        BDD bdd = EDT.getInstance().getBDD();
        String diminutifEnseignant = xml.getSousCategories().get(0).getPremiereValeur("Diminutif");
        this.enseignant = bdd.getBaseEnseignants().rechercher(diminutifEnseignant);
        String diminutifMatiere = xml.getSousCategories().get(1).getPremiereValeur("Diminutif");
        this.matiere = bdd.getBaseMatieres().rechercher(diminutifMatiere);
        String nomSalle = xml.getSousCategories().get(2).getPremiereValeur("Nom");
        this.salle = bdd.getBaseSalles().rechercher(nomSalle);
        this.heure = new HeureEDT(0, 0, 0);
        this.heure.charger(xml.getSousCategories().get(3));
        this.jours = Jours.values()[Integer.parseInt(xml.getPremiereValeur("Jours"))];
        String typeCours = xml.getSousCategories().get(4).getPremiereValeur("TypeCours");
        this.typeCours = bdd.getBaseTypeCours().rechercher(typeCours);
    }
}
