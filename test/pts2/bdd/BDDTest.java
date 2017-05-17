/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import java.io.File;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pts2.donnees.Enseignant;
import pts2.donnees.HeureEDT;
import pts2.donnees.Jours;
import pts2.donnees.Matiere;
import pts2.donnees.Salle;
import pts2.donnees.Semaine;
import pts2.donnees.TypeCours;

/**
 *
 * @author vbebien
 */
public class BDDTest {
    private BDD bdd;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bdd = new BDD();
        this.bdd.getBaseMatieres().ajouter(new Matiere("POO", "Programmation Orientée Objet", Color.BLACK));
        this.bdd.getBaseEnseignants().ajouter(new Enseignant("DOUCET", "Antoine", "ADo"));
        this.bdd.getBaseSemaines().ajouter(new Semaine(20));
        this.bdd.getBaseSalles().ajouter(new Salle("D206", 20));
        this.bdd.getBaseTypeCours().ajouter(new TypeCours("TD"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of placerCours method, of class BDD.
     */
    @Test
    public void testPlacerCours() {
        HeureEDT heure = new HeureEDT(14, 0, 2);
        System.out.println("placerCours");
        bdd.placerCours(20, "DOUCET", heure, Jours.MERCREDI, "POO", "D206", "TD");
        assertEquals(this.bdd.getBaseSemaines().rechercher(20).getListeCours().size(), 1);
    }
}

  
