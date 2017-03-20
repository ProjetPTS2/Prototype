package pts2.donnees;


public class Cours {
    
    private final Enseignant enseignant;
    private final String typeCours;
    private final Matiere matiere;
    private Salle salle;
    
    public Cours(Enseignant enseignant, Matiere matiere, Salle salle, String typeCours) {
        this.enseignant = enseignant;
        this.matiere = matiere;
        this.typeCours = typeCours;
        this.salle = salle;
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
    
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
