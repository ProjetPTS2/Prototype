package pts2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import pts2.donnees.Salle;
import pts2.donnees.Cours;
import pts2.donnees.Semaine;
import pts2.donnees.Matiere;
import pts2.donnees.Enseignant;


public class BDD {
    
    private final HashMap<Integer, Semaine> listeSemaines;
    private final HashMap<String, Matiere> listeMatieres;
    private final HashMap<String, Enseignant> listeEnseignants;
    private final ArrayList<String> listeTypeCours;
    private final ArrayList<Salle> listeSalles;
    
    public BDD() {
        this.listeSemaines = new HashMap<>();
        this.listeMatieres = new HashMap<>();
        this.listeEnseignants = new HashMap<>();
        this.listeTypeCours = new ArrayList<>();
        this.listeSalles = new ArrayList<>();
    }
    
    // AJOUTER UN COURS A L'EMPLOI DU TEMPS    
    public void placerCours(int noSemaine, Enseignant enseignant, HeureEDT heure, Jours jour, String matiere, String nomSalle, String typeCours) {
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        this.listeSemaines.get(noSemaine).ajouterCours(heure, new Cours(enseignant, this.getMatiere(matiere), this.getSalle(nomSalle), typeCours), jour);
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
    
    public void ajouterEnseignant(Enseignant enseignant) {
        this.listeEnseignants.put(enseignant.getDiminutif(), enseignant);
    }
    
    public Semaine getSemaine(int noSemaine) {
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        return this.listeSemaines.get(noSemaine);
    }
    
    public Matiere getMatiere(String nom) {
        return this.listeMatieres.get(nom);
    }
    
    public Enseignant getEnseignant(String diminutif) {
        return this.listeEnseignants.get(diminutif);
    }
    
    public Salle getSalle(String nomSalle) {
        for(Salle salle : this.listeSalles) {
            if(salle.getNom().equals(nomSalle)) {
                return salle;
            }
        }
        return null;
    }
    
    public void sauvegarder() {
        PrintWriter writer = null;
        try {
            File dossier = new File("sauvegardes");
            if(!dossier.exists())
                dossier.mkdir();
            File fichier = new File("sauvegardes/test.xml");
            writer = new PrintWriter(fichier);
            
            writer.println("<EDT>");
            
            writer.println("\t<Enseignants>");
            for(Enseignant enseignant : this.listeEnseignants.values()) {
                writer.println("\t\t<Enseigant>");
                writer.println("\t\t\t<Nom>"+enseignant.getNom()+"</Nom>");
                writer.println("\t\t\t<Prenom>"+enseignant.getPrenom()+"</Prenom>");
                writer.println("\t\t</Enseigant>");
            }
            writer.println("\t</Enseignants>");
            
            writer.println("</EDT>");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
