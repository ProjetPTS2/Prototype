package pts2;


public class Cours {
    
    private final String typeCours;
    private final Matiere matiere;
    private Salle salle;
    
    public Cours(Matiere matiere, Salle salle, String typeCours) {
        this.matiere = matiere;
        this.typeCours = typeCours;
        this.salle = salle;
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
