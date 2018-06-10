package DAO;

import Entites.Igrac;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Filip
 */
public class IgracDAOTest {
    
    public IgracDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addIgrac method, of class IgracDAO.
     */
    @Test
    public void testAddIgrac() {
        System.out.println("addIgrac");
        Igrac i = new Igrac("Test", "Test", new Date(1997, 07, 29), "abcd");
        IgracDAO instance = new IgracDAO();
        instance.addIgrac(i);
        List<Igrac> igraci = instance.readFromIgrac();
        assertEquals(igraci.get(igraci.size()-1).getKvalifikacija(), i.getKvalifikacija());
    }

    /**
     * Test of readFromIgrac method, of class IgracDAO.
     */
    @Test
    public void testReadFromIgrac() {
        System.out.println("readFromIgrac");
        IgracDAO instance = new IgracDAO();
        boolean expResult = false;
        List<Igrac> result = instance.readFromIgrac();
        assertEquals(expResult, result.isEmpty());
    }
    
}
