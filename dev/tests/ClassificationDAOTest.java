package tests;

import static org.junit.jupiter.api.Assertions.*;

import DataAccess.ClassificationDAO;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(OrderAnnotation.class)
class ClassificationDAOTest {
    private static ClassificationDAO classificationDAO;
    private static final int TEST_CATALOG_NUM = 1;

    @BeforeAll
    static void setUp() {
        classificationDAO = ClassificationDAO.getInstance();
    }

    @BeforeEach
    void prepareDatabase() {
        JsonObject classificationJson = new JsonObject();
        classificationJson.addProperty("catalog_num", TEST_CATALOG_NUM);
        classificationJson.addProperty("category", "Test Category");
        classificationJson.addProperty("subcategory", "Test Subcategory");
        classificationJson.addProperty("size", 10);
        classificationJson.addProperty("product_number", 1);

        try {
            classificationDAO.add(classificationJson);
        } catch (SQLException e) {
            fail("Failed to prepare database: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanDatabase() {
        try {
            classificationDAO.delete(TEST_CATALOG_NUM);
        } catch (Exception e) {
            // Ignore exceptions thrown by delete
        }
    }

    @Test
    //@Order(1)
    void add() {
        JsonObject classificationJson = new JsonObject();
        classificationJson.addProperty("catalog_num", 2);
        classificationJson.addProperty("category", "New Category");
        classificationJson.addProperty("subcategory", "New Subcategory");
        classificationJson.addProperty("size", 20);
        classificationJson.addProperty("product_number", 2);

        assertDoesNotThrow(() -> classificationDAO.add(classificationJson));

        JsonObject result = null;
        try {
            result = classificationDAO.search(2);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(2, result.get("catalog_num").getAsInt());

        // Clean up after this specific test
        classificationDAO.delete(2);
    }

    @Test
    //@Order(2)
    void delete() {
        assertDoesNotThrow(() -> classificationDAO.delete(TEST_CATALOG_NUM));

        JsonObject result = null;
        try {
            result = classificationDAO.search(TEST_CATALOG_NUM);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNull(result);
    }

    @Test
    //@Order(3)
    void update() {
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>();
        fieldsAndValuesConditions.put("catalog_num", TEST_CATALOG_NUM);

        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>();
        fieldsAndValuesToUpdates.put("category", "Updated Category");

        assertDoesNotThrow(() -> classificationDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates));

        JsonObject result = null;
        try {
            result = classificationDAO.search(TEST_CATALOG_NUM);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals("Updated Category", result.get("category").getAsString());
    }

    @Test
    //@Order(4)
    void search() {
        JsonObject result = null;
        try {
            result = classificationDAO.search(TEST_CATALOG_NUM);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(TEST_CATALOG_NUM, result.get("catalog_num").getAsInt());
    }

}