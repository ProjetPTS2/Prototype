package pts2.bdd;

import java.io.File;


public class BDD {
    
    private File repertoire;
    private final BaseEnseignants baseEnseignants;
    private final BaseMatieres baseMatieres;
    private final BaseSemaines baseSemaines;
    private final BaseTypeCours baseTypeCours;
    private final BaseSalles baseSalles;
    private final BaseGroupe baseGroupes;
    
    /**
     * Constructeur de la Base de données sans fichier de sauvegarde.
     */
    public BDD() {
        this(null);
    }
    
    /**
     * Constructeur de la Base de données
     * @param repertoire Le dossier de l'emploi du temps.
     */
    public BDD(File repertoire) {
        this.repertoire = repertoire;
        this.baseEnseignants = new BaseEnseignants(this);
        this.baseMatieres = new BaseMatieres(this);
        this.baseSemaines = new BaseSemaines(this);
        this.baseTypeCours = new BaseTypeCours(this);
        this.baseSalles = new BaseSalles(this);
        this.baseGroupes = new BaseGroupe(this);
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
    
    public BaseGroupe getBaseGroupe() {
        return this.baseGroupes;
    }
    
    public void setRepertoire(File dossier) {
        this.repertoire = dossier;
    }
    
    /**
     * Sauvegarde la base de données dans le fichier nomFichier.
     */
    public void sauvegarder() {
        this.getBaseEnseignants().sauvegarder();
        this.getBaseMatieres().sauvegarder();
        this.getBaseSalles().sauvegarder();
        this.getBaseSemaines().sauvegarder();
        this.getBaseTypeCours().sauvegarder();
        this.getBaseGroupe().sauvegarder();
    }
    
    /**
     * Vide la base de données.
     */
    public void vider() {
        this.getBaseEnseignants().vider();
        this.getBaseMatieres().vider();
        this.getBaseSalles().vider();
        this.getBaseTypeCours().vider();
        this.getBaseSemaines().vider();
        this.getBaseGroupe().vider();
    }
    
    /**
     * Charge la base de données.
     */
    public void charger() {
        if(this.repertoire == null) {
            System.out.println("repertoire non défini !");
            return;
        }
        this.vider();
        this.getBaseMatieres().charger();
        this.getBaseGroupe().charger();
        this.getBaseEnseignants().charger();
        this.getBaseSalles().charger();
        this.getBaseTypeCours().charger();
        this.getBaseSemaines().charger();
    }
}
