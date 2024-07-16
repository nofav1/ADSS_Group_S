package tests;

import static org.junit.jupiter.api.Assertions.*;

import DataAccess.ItemsDAO;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class ItemsDAOTest {
    private static ItemsDAO itemsDAO;
    private static final int TEST_ITEM_ID = 1;

    @BeforeAll
    static void setUp() {
        itemsDAO = ItemsDAO.getInstance();
    }

    @BeforeEach
    void prepareDatabase() {
        JsonObject itemJson = new JsonObject();
        itemJson.addProperty("id", TEST_ITEM_ID);
        itemJson.addProperty("expiring_date", "2024-12-31");
        itemJson.addProperty("section", "A");
        itemJson.addProperty("location", 101);
        itemJson.addProperty("supplier_discount", 10);
        itemJson.addProperty("cost_price", 50.0);
        itemJson.addProperty("product_number", 12345);
        itemJson.addProperty("purchase_price", 60.0);

        try {
            itemsDAO.add(itemJson);
        } catch (SQLException e) {
            fail("Failed to prepare database: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanDatabase() {
        itemsDAO.delete(TEST_ITEM_ID);
    }

    @Test
    void add() {
        JsonObject itemJson = new JsonObject();
        itemJson.addProperty("id", 3);
        itemJson.addProperty("expiring_date", "2024-12-31");
        itemJson.addProperty("section", "B");
        itemJson.addProperty("location", 102);
        itemJson.addProperty("supplier_discount", 15);
        itemJson.addProperty("cost_price", 60.0);
        itemJson.addProperty("product_number", 12345);
        itemJson.addProperty("purchase_price", 60.0);

        assertDoesNotThrow(() -> itemsDAO.add(itemJson));

        JsonObject result = null;
        try {
            result = itemsDAO.search(3);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(3, result.get("item_id").getAsInt());

        // Clean up after this specific test
        itemsDAO.delete(3);
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> itemsDAO.delete(TEST_ITEM_ID));

        JsonObject result = null;
        try {
            result = itemsDAO.search(TEST_ITEM_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNull(result);
    }

    @Test
    void update() {
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>();
        fieldsAndValuesConditions.put("item_id", TEST_ITEM_ID);

        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>();
        fieldsAndValuesToUpdates.put("section", "B");

        assertDoesNotThrow(() -> itemsDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates));

        JsonObject result = null;
        try {
            result = itemsDAO.search(TEST_ITEM_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals("B", result.get("section").getAsString());
    }

    @Test
    void search() {
        JsonObject result = null;
        try {
            result = itemsDAO.search(TEST_ITEM_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(TEST_ITEM_ID, result.get("item_id").getAsInt());
    }

}