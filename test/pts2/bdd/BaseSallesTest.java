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
import pts2.donnees.Salle;

/**
 *
 * @author roheix
 */
public class BaseSallesTest {
    private BDD bdd;
    private BaseSalles basesalle;
    private Salle salle;
    @Before
    public void setUp() {
        bdd = new BDD();
        basesalle = new BaseSalles(bdd);
        salle = new Salle("D301", 20);
    }
    
    @After
    public void tearDown() {
        basesalle = null;
        bdd = null;
    }

    /**
     * Test of rechercher method, of class BaseSalles.
     */
    @Test
    public void testRechercher() {
        basesalle.ajouter(salle);
        assertFalse(basesalle.rechercher("D301") == null );
    }

    @Test
    public void testAjouter() {
        assertTrue(basesalle.ajouter(salle) == true );
        
    }
    
    @Test
    public void testExiste() {
        assertTrue(basesalle.existe(salle) == false);
    }
    
    @Test
    public void testSupprimer() {
        basesalle.ajouter(salle);
        assertTrue(basesalle.supprimer(salle) == true );
    }
    
}
