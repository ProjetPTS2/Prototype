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
import pts2.donnees.Semaine;

/**
 *
 * @author roheix
 */
public class BaseSemainesTest {
    private BDD bdd;
    private BaseSemaines basesemaines;
    private Semaine semaine;
    
    @Before
    public void setUp() {
        bdd = new BDD();
        basesemaines = new BaseSemaines(bdd);
    }
    
    @After
    public void tearDown() {
        semaine = null;
        basesemaines = null;
        bdd = null;
    }

    /**
     * Test of rechercher method, of class BaseSemaines.
     */
    @Test
    public void testRechercher() {
        assertFalse(basesemaines.rechercher(1) == null );
    }

    @Test
    public void testAjouter() {
        assertTrue(basesemaines.ajouter(semaine) == true );
        
    }
    
    @Test
    public void testExiste() {
        assertTrue(basesemaines.existe(semaine) == false);
    }
    
    @Test
    public void testSupprimer() {
        basesemaines.ajouter(semaine);
        assertTrue(basesemaines.supprimer(semaine) == true );
    }

    
}
