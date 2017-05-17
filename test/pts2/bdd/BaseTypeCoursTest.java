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
import pts2.donnees.TypeCours;

/**
 *
 * @author roheix
 */
public class BaseTypeCoursTest {
    private BDD bdd;
    private BaseTypeCours basetypecours;
    private TypeCours typecours;
    
    @Before
    public void setUp() {
        bdd = new BDD();
        basetypecours = new BaseTypeCours(bdd);
        typecours = new TypeCours("TP");
    }
    
    @After
    public void tearDown() {
        typecours = null;
        basetypecours = null;
        bdd = null;
    }

    /**
     * Test of rechercher method, of class BaseTypeCours.
     */
    @Test
    public void testRechercher() {
        assertTrue(basetypecours.rechercher("TP") == null );
    }

    @Test
    public void testAjouter() {
        assertTrue(basetypecours.ajouter(typecours) == true );
        
    }
    
    @Test
    public void testExiste() {
        assertTrue(basetypecours.existe(typecours) == false);
    }
    
    @Test
    public void testSupprimer() {
        basetypecours.ajouter(typecours);
        assertTrue(basetypecours.supprimer(typecours) == true );
    }

    
}
