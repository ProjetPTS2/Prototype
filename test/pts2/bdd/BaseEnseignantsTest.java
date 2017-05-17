/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pts2.bdd;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pts2.donnees.Enseignant;

/**
 *
 * @author vbebien
 */
public class BaseEnseignantsTest {
    private BaseEnseignants be;
    private BDD bdd = new BDD();
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        be = new BaseEnseignants(bdd);
        be.ajouter(new Enseignant("MARCHAND", "Sylvain", "SMa"));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of rechercher method, of class BaseEnseignants.
     */
    @Test
    public void testRechercher() {
        System.out.println("rechercher");
        assertFalse(this.be.rechercher("MARCHAND")==null);
    }
    
    @Test
    public void testAjouter(){
        System.out.println("ajouter");
        assertTrue(be.ajouter(new Enseignant("GOMEZ", "Petra", "PGo")));
    }
    
    @Test
    public void testSupprimer(){
        System.out.println("supprimer");
        Enseignant e = new Enseignant("RABAH", "Mourad", "MRa");
        be.ajouter(e);
        assertTrue(be.supprimer(e));
    }

    @Test
    public void testExiste(){
        System.out.println("existe");
        assertTrue(be.existe("MARCHAND"));
    }    
}
    /**
     * Test of sauvegarder method, of class BaseEnseignants.
     */
 
