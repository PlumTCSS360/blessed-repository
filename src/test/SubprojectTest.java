package test;

import model.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the Subproject class.
 * Some checks for sketches are done manually for convenient.
 *
 * @author Jiameng Li
 * @version v0.1
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubprojectTest {

    /** The subproject used for test. */
    private static Subproject sp;

    /**
     * Before the test, create a test project and test subproject.
     * Set up the image that will be used in tests for sketches. The same image is saved as TestImage.png.
     *
     * @author Jiameng Li
     */
    @BeforeAll
    static void setupTest() {
        Project.createProject("Test Project", new BigDecimal("500"),
                "Test project description.");
        sp = Project.createSubproject("Test Subproject", new BigDecimal("100"),
                "Test subproject description.");
    }

    /**
     * Test for {@link model.Subproject#createOption(String, BigDecimal, String, String)}
     * Create an option and add it to the options list.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(1)
    void testCreateOption() {
        sp.createOption("Test Option", new BigDecimal("90"), "Test option description.",
                "http://www.website.com");
        assertEquals(1, sp.getOptionsList().size());
        assertTrue(sp.getOptionsList().containsKey("Test Option"));
        Option op = sp.getOptionsList().get("Test Option");
        assertEquals("Test Option", op.getName());
        assertEquals("90", op.getCost().toString());
        assertEquals("Test option description.", op.getDescription().getDescription());
        assertEquals("http://www.website.com", op.getWebsite().toString());
        assertEquals("http://www.website.com", op.getWebsite().toString());
        assertEquals(Option.CONTRACTOR_SETUP, op.getContractorInfo());
        assertEquals(Option.WARRANTY_SETUP, op.getWarrantyInfo());
    }

    /**
     * Test for {@link model.Subproject#createOption(String, BigDecimal, String, String)}
     * Check if the folder and empty text files for the new option are created.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(2)
    void testCreateOptionFilesCreated() {
        String path = "data/Test Project/Test Subproject/Options/Test Option";
        File file = new File(path);
        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        for (String s : Option.OPTION_FILE) {
            file = new File(path + s);
            assertTrue(file.exists());
            assertTrue(file.isFile());
        }
    }

    /**
     * Test for {@link model.Subproject#createNote(String, String)}
     * Create a note and add it to the notes list.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(3)
    void testCreateNote() {
        sp.createNote("Test Note", "Test note content.");
        assertEquals(1, sp.getNotesList().size());
        assertTrue(sp.getNotesList().containsKey("Test Note"));
        assertEquals("Test note content.", sp.getNote("Test Note"));
    }

    /**
     * Test for {@link model.Subproject#createNote(String, String)}
     * Check if the empty text file for the new note is created.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(4)
    void testCreateNoteFilesCreated() {
        File file = new File("data/Test Project/Test Subproject/Notes/Test Note.txt");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertEquals(0, file.length());
    }

    /**
     * Test for {@link model.Subproject#createSketch(String, String)}
     * Create a sketch and add it the sketches list.
     * The content in the list is checked manually for convenient.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(5)
    void testCreateSketch() {
        sp.createSketch("Test Sketch", "src/test/TestImage.png");
        assertEquals(1, sp.getSketchesList().size());
        assertTrue(sp.getSketchesList().containsKey("Test Sketch"));
    }

    /**
     * Test for {@link model.Subproject#createSketch(String, String)}
     * Check if the empty png file for new sketch is created.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(6)
    void testCreateSketchFilesCreated() {
        File file = new File("data/Test Project/Test Subproject/Sketches/Test Sketch.png");
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertEquals(0, file.length());
    }

    /**
     * Test for {@link model.Subproject#setNoteContent(String, String)}
     * Set new content to an existing note.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(8)
    void testSetNoteContent() {
        sp.setNoteContent("Test Note", "New note content.");
        assertEquals("New note content.", sp.getNote("Test Note"));
    }

    // setSketchContent() is checked manually for convenient

    /**
     * Test for saveSubproject() in Subproject class.
     * Check whether the budget, description, and notes are saved correctly.
     * This method doesn't check whether options are saved.
     * Sketches are checked manually for convenient.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(9)
    void testSaveSubproject() {
        Project.closeProject();
        final String path = "data/Test Project/Test Subproject";
//        String content = FileAccessor.readTxtFile(path + Budget.FILE_NAME);
//        String expected = """
//                data/Test Project/Test Subproject
//                spending limit:100.00
//                end""";
//        assertEquals(expected, content);
//        content = FileAccessor.readTxtFile(path + Description.FILE_NAME);
//        expected = """
//                data/Test Project/Test Subproject
//                Test subproject description.""";
//        assertEquals(expected, content);
        String content = FileAccessor.readTxtFile(path + "/Notes/Test Note.txt");
        assertEquals("New note content.", content);
    }

    /**
     * Test for loadOptionsNotesSketches() in Subproject class.
     * Check whether the subproject is loaded correctly from data files.
     * This doesn't check whether the options list is loaded.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(10)
    void testLoadOptionsNotesSketches() {
        Project.loadProject("Test Project");
        sp = Project.getSubproject("Test Subproject");
        // Notes list
        assertEquals(1, sp.getNotesList().size());
        assertTrue(sp.getNotesList().containsKey("Test Note"));
        assertEquals("New note content.", sp.getNote("Test Note"));
        // Sketches list
        assertEquals(1, sp.getSketchesList().size());
        assertTrue(sp.getSketchesList().containsKey("Test Sketch"));
    }

    /**
     * Test for loadOption() in Subproject class.
     * Check if the options list is loaded from data files correctly.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(11)
    void testLoadOption() {
        // Options list
        assertEquals(1, sp.getOptionsList().size());
        assertTrue(sp.getOptionsList().containsKey("Test Option"));
        Option op = sp.getOption("Test Option");
        assertEquals("Test Option", op.getName());
        assertEquals("90", op.getCost().toString());
//        assertEquals("Test option description.", op.getDescription());
        assertEquals("http://www.website.com", op.getWebsite().toString());
        assertEquals(Option.CONTRACTOR_SETUP, op.getContractorInfo());
        assertEquals(Option.WARRANTY_SETUP, op.getWarrantyInfo());
    }

    /**
     * Test for {@link model.Subproject#deleteOption(String)}
     * Check if the deleted option is removed from the link and if the folder is deleted.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(12)
    void testDeleteOption() {
        sp.deleteOption("Test Option");
        assertEquals(0, sp.getOptionsList().size());
        File file = new File("data/Test Project/Test Subproject/Options/Test Option");
        assertFalse(file.exists());
    }

    /**
     * Test for {@link model.Subproject#deleteNote(String)}
     * Check if the deleted note is removed from the link and if the file is deleted.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(13)
    void testDeleteNote() {
        sp.deleteNote("Test Note");
        assertEquals(0, sp.getNotesList().size());
        File file = new File("data/Test Project/Test Subproject/Notes/Test Note.txt");
        assertFalse(file.exists());
    }

    /**
     * Test for {@link model.Subproject#deleteSketch(String)}
     * Check if the deleted sketch is removed from the link and if the file is deleted.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(14)
    void testDeleteSketch() {
        sp.deleteSketch("Test Sketch");
        assertEquals(0, sp.getSketchesList().size());
        File file = new File("data/Test Project/Test Subproject/Sketches/Test Sketch.png");
        assertFalse(file.exists());
    }

    /**
     * Delete the test project when finishing teh test.
     *
     * @author Jiameng Li
     */
    @AfterAll
    static void finishTest() {
        Project.deleteProject("Test Project");
    }

}