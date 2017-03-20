package pts2;

import java.util.ArrayList;
import java.util.HashMap;


public class BDD {
    
    private final HashMap<Integer, Semaine> listeSemaines;
    private final HashMap<String, Matiere> listeMatieres;
    private final ArrayList<String> listeTypeCours;
    private final ArrayList<Salle> listeSalles;
    
    public BDD() {
        this.listeSemaines = new HashMap<>();
        this.listeMatieres = new HashMap<>();
        this.listeTypeCours = new ArrayList<>();
        this.listeSalles = new ArrayList<>();
    }
    
    // AJOUTER UN COURS A L'EMPLOI DU TEMPS    
    public void placerCours(int noSemaine, HeureEDT heure, Jours jour, String matiere, String nomSalle, String typeCours) {
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        this.listeSemaines.get(noSemaine).ajouterCours(heure, new Cours(this.getMatiere(matiere), this.getSalle(nomSalle), typeCours), jour);
    }
    
    // AJOUTER UN TYPE DE COURS
    public void ajouterTypeCours(String nom) {
        this.listeTypeCours.add(nom);
    }
    
    public void ajouterSalle(Salle salle) {
        this.listeSalles.add(salle);
    }
    
    public void ajouterMatiere(Matiere matiere) {
        this.listeMatieres.put(matiere.getDiminutif(), matiere);
    }
    
    public Semaine getSemaine(int noSemaine) {
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        return this.listeSemaines.get(noSemaine);
    }
    
    public Matiere getMatiere(String nom) {
        return this.listeMatieres.get(nom);
    }
    
    public Salle getSalle(String nomSalle) {
        for(Salle salle : this.listeSalles) {
            if(salle.getNom().equals(nomSalle)) {
                return salle;
            }
        }
        return null;
    }
}
