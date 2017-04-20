package pts2;

import pts2.donnees.HeureEDT;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pts2.donnees.Salle;
import pts2.donnees.Cours;
import pts2.donnees.Semaine;
import pts2.donnees.Matiere;
import pts2.donnees.Enseignant;
import pts2.utilitaire.XMLEcriture;
import pts2.utilitaire.XMLLecture;
import pts2.utilitaire.XMLObjet;


public class BDD {
    
    private File fichier;
    
    private final HashMap<Integer, Semaine> listeSemaines;
    private final List<Matiere> listeMatieres;
    private final List<Enseignant> listeEnseignants;
    private final List<String> listeTypeCours;
    private final List<Salle> listeSalles;
    
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
    public BDD(File fichier) {
        this.fichier = fichier;
        this.listeSemaines = new HashMap<>();
        this.listeMatieres = new ArrayList<>();
        this.listeEnseignants = new ArrayList<>();
        this.listeTypeCours = new ArrayList<>();
        this.listeSalles = new ArrayList<>();
    }
    
    // AJOUTER UN COURS A L'EMPLOI DU TEMPS   
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
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        this.listeSemaines.get(noSemaine).ajouterCours(heure, new Cours(heure, this.getEnseignant(enseignant), this.getMatiere(matiere), this.getSalle(nomSalle), typeCours), jour);
    }
    
    /**
     * Ajoute un type de cours à la base de données.
     * @param nom Le nom du type de cours.
     */
    public void ajouterTypeCours(String nom) {
        this.listeTypeCours.add(nom);
    }
    
    /**
     * Ajoute une salle à la base de données.
     * @param salle La salle à ajouter.
     * @return Retourne true si la salle a bien été ajoutée, false sinon.
     */
    public boolean ajouterSalle(Salle salle) {
        if(this.getSalle(salle.getNom()) == null) {
            this.listeSalles.add(salle);
            return true;
        }
        return false;
    }
    
    /**
     * Ajoute une matière à la base de données.
     * @param matiere La matière à ajouter.
     * @return Retourne true si la matière a bien été ajoutée, false sinon.
     */
    public boolean ajouterMatiere(Matiere matiere) {
        boolean resultat = this.getMatiere(matiere.getDiminutif()) == null;
        if(resultat)
            this.listeMatieres.add(matiere);
        return resultat;
    }
    
    
    /**
     * Ajoute un enseignant à la base de données.
     * @param enseignant L'enseignant à ajouter.
     * @return Retourne true si l'enseignant a bien été ajoutée, false sinon.
     */
    public boolean ajouterEnseignant(Enseignant enseignant) {
        boolean resultat = this.getEnseignant(enseignant.getDiminutif()) == null;
        if(resultat)
            this.listeEnseignants.add(enseignant);
        return resultat;
    }
    
    public Semaine getSemaine(int noSemaine) {
        if(!this.listeSemaines.containsKey(noSemaine))
            this.listeSemaines.put(noSemaine, new Semaine(noSemaine));
        return this.listeSemaines.get(noSemaine);
    }
    
    public Matiere getMatiere(String diminutif) {
        Matiere resultat = null;
        for(Matiere matiere : this.listeMatieres) {
            if(matiere.getDiminutif().equals(diminutif)) {
                resultat = matiere;
                break;
            }
        }
        return resultat;
    }
    
    public Enseignant getEnseignant(String diminutif) {
        Enseignant resultat = null;
        for(Enseignant enseignant : this.listeEnseignants) {
            if(enseignant.getDiminutif().equals(diminutif)) {
                resultat = enseignant;
                break;
            }
        }
        return resultat;
    }
    
    public Salle getSalle(String nomSalle) {
        for(Salle salle : this.listeSalles) {
            if(salle.getNom().equals(nomSalle)) {
                return salle;
            }
        }
        return null;
    }
    
    public List<Salle> getListeSalles() {
        return this.listeSalles;
    }
    
    public List<Matiere> getListeMatieres() {
        return this.listeMatieres;
    }
    
    public List<Enseignant> getListeEnseignants() {
        return this.listeEnseignants;
    }
    
    public List<String> getListeTypeCours() {
        return this.listeTypeCours;
    }
    
    /**
     * Sauvegarde la base de données dans le fichier nomFichier.
     */
    public void sauvegarder() {
        if(this.fichier == null)
            this.fichier = EDT.getInstance().sauvegarderEDT(false);
        if(this.fichier == null)
            return;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.fichier);
            XMLEcriture xml = new XMLEcriture(writer);
            
            xml.ouvrirBalise("EDT");
            
            
            // BALISE TYPE COURS
            xml.ouvrirBalise("TypesCours");
            for(String type : this.listeTypeCours)
                xml.ecrireValeur("Type", type);
            xml.fermerBalise();
            
            // BALISE MATIERES
            xml.ouvrirBalise("Matieres");
            for(Matiere matiere : this.listeMatieres)
                matiere.sauvegarder(xml);
            xml.fermerBalise();
            
            
            
            // BALISE ENSEIGNANTS
            xml.ouvrirBalise("Enseignants");
            for(Enseignant enseignant : this.listeEnseignants)
                enseignant.sauvegarder(xml);
            xml.fermerBalise();
            
            // BALISE SALLES
            xml.ouvrirBalise("Salles");
            for(Salle salle : this.listeSalles)
                salle.sauvegarder(xml);
            xml.fermerBalise();
            
            
            // BALISE SEMAINES
            xml.ouvrirBalise("Semaines");
            for(Semaine semaine : this.listeSemaines.values())
                semaine.sauvegarder(xml);
            xml.fermerBalise();
            
            
            //FERMER BALISE EDT
            xml.fermerBalise();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            writer.close();
        }
    }
    
    /**
     * Charge la base de données à partir d'un fichier.
     * @param fichier Le fichier à charger.
     */
    public void charger(File fichier) {
        XMLLecture xml = new XMLLecture(fichier.getAbsolutePath());
        xml.lire();
        XMLObjet typeCours = xml.rechercherCategorie("TypesCours");
        for(String valeur : typeCours.getValeurs("Type")) {
            System.out.println("[TypeCours] Ajout: " + valeur);
            this.listeTypeCours.add(valeur);
        }
        
        XMLObjet matieres = xml.rechercherCategorie("Matieres");
        for(XMLObjet matiere : matieres.getSousCategories()) {
            Matiere m = new Matiere(null, null, null);
            m.charger(matiere, this);
            System.out.println("[Matiere] Ajout: " + m.getNom());
            this.listeMatieres.add(m);
        }
        
        XMLObjet enseignants = xml.rechercherCategorie("Enseignants");
        for(XMLObjet enseignant : enseignants.getSousCategories()) {
            Enseignant e = new Enseignant(null, null);
            e.charger(enseignant, this);
            System.out.println("[Enseignant] Ajout: " + e.getNom() + " " + e.getPrenom());
            this.listeEnseignants.add(e);
        }
        
        XMLObjet salles = xml.rechercherCategorie("Salles");
        for(XMLObjet salle : salles.getSousCategories()) {
            Salle s = new Salle(null, 0);
            s.charger(salle, this);
            System.out.println("[Salle] Ajout: " + s.getNom());
            this.listeSalles.add(s);
        }
        
        XMLObjet semaines = xml.rechercherCategorie("Semaines");
        for(XMLObjet semaine : semaines.getSousCategories()) {
            Semaine s = new Semaine(-1);
            s.charger(semaine, this);
            System.out.println("[Semaine] Ajout: n°" + s.getNoSemaine());
            this.listeSemaines.put(s.getNoSemaine(), s);
        }
    }
}
