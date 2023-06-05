package test;

import model.FileAccessor;
import model.Option;
import model.Project;
import model.Subproject;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the Option class.
 *
 * @author Jiameng Li
 * @version 0.3
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OptionTest {

    /** The Option used for test. */
    private static Option op;

    /**
     * Before the test, create a test project, a test subproject, and a test option.
     *
     * @author Jiameng Li
     */
    @BeforeAll
    static void setupTest() {
        Project.createProject("Test Project", new BigDecimal("500"),
                "Test project description");
        final Subproject sp = Project.createSubproject("Test Subproject", new BigDecimal("100"),
                "Test subproject description");
        op = sp.createOption("Test Option", new BigDecimal("90"), "Test option description",
                "http://www.website.com");
    }

    /**
     * Test for {@link model.Option#setCost(BigDecimal)}
     *
     * @author Jiameng Li
     */
    @Test
    @Order(1)
    void setCost() {
        op.setCost(new BigDecimal("80"));
        assertEquals("80", op.getCost().toString());
    }

    /**
     * Test for {@link model.Option#setWebsite(String)}
     *
     * @author Jiameng Li
     */
    @Test
    @Order(2)
    void setWebsite() {
        op.setWebsite("https://www.anotherwebsite.com");
        assertEquals("https://www.anotherwebsite.com", op.getWebsite().toString());
    }

    /**
     * Test for {@link model.Option#setContractorInfo(String)}
     *
     * @author Jiameng Li
     */
    @Test
    @Order(3)
    void setContractorInfo() {
        op.setContractorInfo("Test contractor information");
        assertEquals("Test contractor information", op.getContractorInfo());
    }

    /**
     * Test for {@link model.Option#setWarrantyInfo(String)}
     *
     * @author Jiameng Li
     */
    @Test
    @Order(4)
    void setWarrantyInfo() {
        op.setWarrantyInfo("Test warranty information");
        assertEquals("Test warranty information", op.getWarrantyInfo());
    }

    /**
     * Test for saveOption() in Option class.
     * Check if information about the option are saved correctly.
     *
     * @author Jiameng Li
     */
    @Test
    @Order(5)
    void saveOption() {
        Project.saveProject();
        final String path = "data/Test Project/Test Subproject/Options/Test Option";
        String content = FileAccessor.readTxtFile(path + "/cost.txt");
        assertEquals("80", content);
        content = FileAccessor.readTxtFile(path + "/website.txt");
        assertEquals("https://www.anotherwebsite.com", content);
        content = FileAccessor.readTxtFile(path + "/contractor.txt");
        assertEquals("Test contractor information", content);
        content = FileAccessor.readTxtFile(path + "/warranty.txt");
        assertEquals("Test warranty information", content);
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