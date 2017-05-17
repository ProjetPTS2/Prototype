/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pts2.donnees.Matiere;

/**
 *
 * @author vbebien
 */
public class BaseMatieresTest {
    
    private BaseMatieres bm;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        BDD bdd = new BDD();
        bm = new BaseMatieres(bdd);
        bm.ajouter(new Matiere("POO", "Programmation Orientée Objet", Color.BLUE));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of rechercher method, of class BaseMatieres.
     */
    @Test
    public void testRechercher() {
        System.out.println("rechercher");
        bm.ajouter(new Matiere("POO", "Programmation Orientée Objet", Color.BLUE));
        assertFalse(this.bm.rechercher("POO")==null);
    }
    
        @Test
    public void testAjouter(){
        System.out.println("ajouter");
        assertTrue(bm.ajouter(new Matiere("POO", "Programmation Orientée Objet", Color.BLUE)));
    }
    
    @Test
    public void testSupprimer(){
        System.out.println("supprimer");
        Matiere m = new Matiere("POO", "Programmation Orientée Objet", Color.BLUE);
        bm.ajouter(m);
        assertTrue(bm.supprimer(m));
    }

    @Test
    public void testExiste(){
        System.out.println("existe");
        assertTrue(bm.existe("POO"));
    }    
    
}
