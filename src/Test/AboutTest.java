package Test;

import static org.junit.jupiter.api.Assertions.*;

import model.About;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Owner
 *
 */
class AboutTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        About testAbout = new About();
        Person[] testDevelopers = new Person[5];
        Person testPerson = testAbout.getUser();
        testDevelopers = testAbout.getDevelopers();
        assertEquals(0.1, testAbout.getVersion());
        assertEquals("JJH", testPerson.getName());
        assertEquals("JJH@gmail.com", testPerson, testPerson.getEmail());
        assertEquals("Devin Peevy", testDevelopers[0].getName());
        assertEquals("Junghyon Jo", testDevelopers[1].getName());
        assertEquals("Taylor Merwin", testDevelopers[2].getName());
        assertEquals("Jiameng Li", testDevelopers[3].getName());
        assertEquals("Cameron Gregoire", testDevelopers[4].getName());
        assertEquals("dpeevy@uw.edu", testDevelopers[0].getEmail());
        assertEquals("whwheoeo2015@gmail.com", testDevelopers[1].getEmail());
        assertEquals("tmerwin@uw.edu", testDevelopers[2].getEmail());
        assertEquals("jli39@uw.edu", testDevelopers[3].getEmail());
        assertEquals("cgrego2@uw.edu", testDevelopers[4].getEmail());
    }

    /**
     * Test method for {@link model.About#About()}.
     */
    @Test
    void testAbout() {
        About testAbout = new About();
    }

    /**
     * Test method for {@link model.About#getUser()}.
     */
    @Test
    void testGetUser() {
        About testAbout = new About();
        Person testPerson = testAbout.getUser();
        assertEquals("JJH", testPerson.getName());
        assertEquals("JJH@gmail.com", testPerson.getEmail());
    }

    /**
     * Test method for {@link model.About#getDevelopers()}.
     */
    @Test
    void testGetDevelopers() {
        About testAbout = new About();
        Person[] testDevelopers = new Person[5];
        testDevelopers = testAbout.getDevelopers();
        assertEquals("Devin Peevy", testDevelopers[0].getName());
        assertEquals("Junghyon Jo", testDevelopers[1].getName());
        assertEquals("Taylor Merwin", testDevelopers[2].getName());
        assertEquals("Jiameng Li", testDevelopers[3].getName());
        assertEquals("Cameron Gregoire", testDevelopers[4].getName());
        assertEquals("dpeevy@uw.edu", testDevelopers[0].getEmail());
        assertEquals("whwheoeo2015@gmail.com", testDevelopers[1].getEmail());
        assertEquals("tmerwin@uw.edu", testDevelopers[2].getEmail());
        assertEquals("jli39@uw.edu", testDevelopers[3].getEmail());
        assertEquals("cgrego2@uw.edu", testDevelopers[4].getEmail());
    }

    /**
     * Test method for {@link model.About#getVersion()}.
     */
    @Test
    void testGetVersion() {
        About testAbout = new About();
        assertEquals(0.1, testAbout.getVersion());
    }

}
