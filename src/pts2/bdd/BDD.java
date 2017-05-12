package pts2.bdd;

import pts2.donnees.HeureEDT;
import java.io.File;
import pts2.EDT;
import pts2.donnees.Jours;

import pts2.donnees.Cours;
import pts2.donnees.Enseignant;
import pts2.donnees.Matiere;
import pts2.donnees.Salle;
import pts2.donnees.Semaine;
import pts2.donnees.TypeCours;


public class BDD {
    
    private File repertoire;
    private final BaseEnseignants baseEnseignants;
    private final BaseMatieres baseMatieres;
    private final BaseSemaines baseSemaines;
    private final BaseTypeCours baseTypeCours;
    private final BaseSalles baseSalles;
    
    /**
     * Constructeur de la Base de données sans fichier de sauvegarde.
     */
    public BDD() {
        this(null);
    }
    
    /**
     * Constructeur de la Base de données
     * @param fichier Le fichier de l'emploi du temps.
     */
    public BDD(File repertoire) {
        this.repertoire = repertoire;
        this.baseEnseignants = new BaseEnseignants(this);
        this.baseMatieres = new BaseMatieres(this);
        this.baseSemaines = new BaseSemaines(this);
        this.baseTypeCours = new BaseTypeCours(this);
        this.baseSalles = new BaseSalles(this);
    }
    
    /**
     * Ajoute un cours à l'emploi du temps.
     * @param noSemaine Le numéro de la semaine.
     * @param enseignant Le diminutif de l'enseignant.
     * @param heure L'heure du cours.
     * @param jour Le jour du cours.
     * @param matiere La matière du cours.
     * @param nomSalle Le nom de la salle.
     * @param typeCours Le type de cours.
     */
    public void placerCours(int noSemaine, String enseignant, HeureEDT heure, Jours jour, String matiere, String nomSalle, String typeCours) {
        Semaine semaine = this.baseSemaines.rechercher(noSemaine);
        Enseignant e = this.baseEnseignants.rechercher(enseignant);
        if(e == null) {
            System.out.println("Enseignant null");
            return;
        }
        Matiere m = this.baseMatieres.rechercher(matiere);
        if(m == null) {
            System.out.println("Matiere null");
            return;
        }
        Salle s = this.baseSalles.rechercher(nomSalle);
        if(s == null) {
            System.out.println("Salle null");
            return;
        }
        TypeCours tc = this.baseTypeCours.rechercher(typeCours);
        if(tc == null) {
            System.out.println("TypeCours null");
            return;
        }
        semaine.ajouterCours(new Cours(jour, heure, e, m, s, tc));
    }
    
    public File getRepertoire() {
        return this.repertoire;
    }
 
    public String getCheminRepertoire() {
        return this.repertoire.getAbsolutePath();
    }
    
    public BaseEnseignants getBaseEnseignants() {
        return this.baseEnseignants;
    }
    
    public BaseSemaines getBaseSemaines() {
        return this.baseSemaines;
    }
    
    public BaseSalles getBaseSalles() {
        return this.baseSalles;
    }
    
    public BaseTypeCours getBaseTypeCours() {
        return this.baseTypeCours;
    }
    
    public BaseMatieres getBaseMatieres() {
        return this.baseMatieres;
    }
    
    public void setRepertoire(File dossier) {
        this.repertoire = dossier;
    }
    
    /**
     * Sauvegarde la base de données dans le fichier nomFichier.
     */
    public void sauvegarder() {
        if(this.repertoire == null) {
            System.out.println("repertoire non défini !");
            EDT.getInstance().sauvegarderEDT(false);
            return;
        }
        this.getBaseEnseignants().sauvegarder();
        this.getBaseMatieres().sauvegarder();
        this.getBaseSalles().sauvegarder();
        this.getBaseSemaines().sauvegarder();
        this.getBaseTypeCours().sauvegarder();
    }
    
    /**
     * Charge la base de données à partir d'un fichier.
     * @param fichier Le fichier à charger.
     */
    public void charger(File fichier) {
        this.repertoire = fichier;
        if(this.repertoire == null) {
            System.out.println("repertoire non défini !");
            return;
        }
        this.getBaseEnseignants().charger();
        this.getBaseMatieres().charger();
        this.getBaseSalles().charger();
        this.getBaseSemaines().charger();
        this.getBaseTypeCours().charger();
    }
}
