package test;
//
//import model.FileAccessor;
//import model.Project;
//import model.Subproject;
//import org.junit.jupiter.api.*;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * JUnit test for the Project class.
// * Some of the conditions that check for duplicated name are not covered because
// * they open message dialog.
// *
// * @author Jiameng Li
// * @version v0.1
// */
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ProjectTest {
//
//    /**
//     * Test for {@link model.Project#createProject(String, BigDecimal, String)}
//     *
//     * Check whether the method create a project and open it by assigning values to its fields.
//     */
//    @Test
//    @Order(1)
//    void testCreateProject() {
//        assertTrue(Project.createProject("Test Project", new BigDecimal("500"),
//                "Test project description"));
//        assertEquals("Test Project", Project.getProjectName());
//        assertEquals("500", Project.getBudget().toString());
//        assertEquals("Test project description", Project.getProjectDescription());
//        assertEquals(0, Project.getSubprojectsList().size());
//    }
//
//    /**
//     * Test for {@link model.Project#createProject(String, BigDecimal, String)}
//     *
//     * Check if the method create necessary folder and files and whether files are empty.
//     */
//    @Test
//    @Order(2)
//    void testCreateProjectFilesCreated() {
//        File file = new File("data/Test Project");
//        assertTrue(file.exists());
//        assertTrue(file.isDirectory());
//        for (String s : Project.BASIC_INFO_FILE) {
//            file = new File("data/Test Project" + s);
//            assertTrue(file.exists());
//            assertTrue(file.isFile());
//            assertEquals(0, file.length());
//        }
//    }
//
//    /**
//     * Test for {@link model.Project#setProjectDescription(String)}
//     *
//     * Set project description.
//     */
//    @Test
//    @Order(3)
//    void testSetProjectDescription() {
//        Project.setProjectDescription("New description.\nWith multiple lines.");
//        assertEquals("New description.\nWith multiple lines.", Project.getProjectDescription());
//    }
//
//    /**
//     * Test for {@link model.Project#createSubproject(String, BigDecimal, String)}
//     *
//     * Create a subproject and check whether it's added to the list
//     */
//    @Test
//    @Order(4)
//    void testCreateSubproject() {
//        Project.createSubproject("Test Subproject", new BigDecimal("100"),
//                "Test subproject description");
//        assertEquals(1, Project.getSubprojectsList().size());
//        assertTrue(Project.getSubprojectsList().containsKey("Test Subproject"));
//        Subproject sp = Project.getSubproject("Test Subproject");
//        assertEquals("Test Subproject", sp.getName());
//        assertEquals("100", sp.getBudget().toString());
//        assertEquals("Test subproject description", sp.getDescription());
//        assertEquals(0, sp.getOptionsList().size());
//        assertEquals(0, sp.getNotesList().size());
//        assertEquals(0, sp.getSketchesList().size());
//    }
//
//    /**
//     * Test for {@link model.Project#createSubproject(String, BigDecimal, String)}
//     *
//     * Check if the folders and text files for the subproject are created.
//     * Folder and files should remain empty at this point.
//     */
//    @Test
//    @Order(5)
//    void testCreateSubprojectFilesCreated() {
//        String path = "data/Test Project/Test Subproject";
//        File file = new File(path);
//        assertTrue(file.exists());
//        assertTrue(file.isDirectory());
//        // Check for budget and description files
//        for (String s : Project.BASIC_INFO_FILE) {
//            file = new File(path + s);
//            assertTrue(file.exists());
//            assertTrue(file.isFile());
//            assertEquals(0, file.length());
//        }
//        // Check for options, notes, and sketches folders
//        for (String s : Subproject.SUBPROJECT_FOLDERS) {
//            file = new File(path + s);
//            assertTrue(file.exists());
//            assertTrue(file.isDirectory());
//            assertEquals(0, Objects.requireNonNull(file.listFiles()).length);
//        }
//    }
//
//
//    /**
//     * Test for saveProject() in Project class.*
//     * The saveProject() method is called in the closeProject() method.
//     * Check whether the information is stored in correct files.
//     * This doesn't test whether subprojects are saved.
//     */
//    @Test
//    @Order(6)
//    void testCloseProjectCheckSave() {
//        Project.closeProject();
//        String content = FileAccessor.readTxtFile("data/Test Project/Description.txt");
//        assertEquals("New description.\nWith multiple lines.", content);
//        content = FileAccessor.readTxtFile("data/Test Project/Budget.txt");
//        assertEquals("500", content);
//    }
//
//    /**
//     * Test for {@link Project#closeProject()}
//     *
//     * Check whether the project is close.
//     */
//    @Test
//    @Order(7)
//    void testCloseProjectCheckClose() {
//        assertNull(Project.getProjectName());
//        assertNull(Project.getBudget());
//        assertNull(Project.getProjectDescription());
//        assertEquals(0, Project.getSubprojectsList().size());
//    }
//
//    /**
//     * Test for {@link model.Project#loadProject(String)}
//     *
//     * Check if the project is loaded from data files correctly.
//     */
//    @Test
//    @Order(8)
//    void testLoadProject() {
//        Project.loadProject("Test Project");
//        assertEquals("Test Project", Project.getProjectName());
//        assertEquals("500", Project.getBudget().toString());
//        assertEquals("New description.\nWith multiple lines.", Project.getProjectDescription());
//        assertEquals(1, Project.getSubprojectsList().size());
//    }
//
//    /**
//     * Test for {@link model.Project#loadProject(String)}
//     *
//     * Check if the subproject is loaded from data files correctly.
//     * The loadSubproject() method is called in the loadProject() method.
//     */
//    @Test
//    @Order(9)
//    void testLoadSubproject() {
//        Subproject sp = Project.getSubproject("Test Subproject");
//        assertEquals("Test Subproject", sp.getName());
//        assertEquals("100", sp.getBudget().toString());
//        assertEquals("Test subproject description", sp.getDescription());
//        assertEquals(0, sp.getOptionsList().size());
//        assertEquals(0, sp.getNotesList().size());
//        assertEquals(0, sp.getSketchesList().size());
//    }
//
//    /**
//     * Test for {@link model.Project#deleteSubproject(String)}
//     *
//     * Check if the subproject is removed from the list and the folder for the subproject is deleted.
//     */
//    @Test
//    @Order(10)
//    void testDeleteSubproject() {
//        Project.deleteSubproject("Test Subproject");
//        assertFalse(Project.getSubprojectsList().containsKey("Test Subproject"));
//        File file = new File("data/Test Project/Test Subproject");
//        assertFalse(file.exists());
//    }
//
//    /**
//     * Test for {@link model.Project#deleteProject(String)}
//     *
//     * Check if the folder for the project is deleted.
//     */
//    @Test
//    @Order(11)
//    void testDeleteProject() {
//        Project.deleteProject("Test Project");
//        File file = new File("data/Test Project");
//        assertFalse(file.exists());
//    }
//}