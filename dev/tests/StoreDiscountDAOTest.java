package tests;

import static org.junit.jupiter.api.Assertions.*;

import DataAccess.StoreDiscountDAO;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class StoreDiscountDAOTest {
    private static StoreDiscountDAO storeDiscountDAO;
    private static final int TEST_DISCOUNT_ID = 0;

    @BeforeAll
    static void setUp() {
        storeDiscountDAO = StoreDiscountDAO.getInstance();
    }

    @BeforeEach
    void prepareDatabase() {
        JsonObject discountJson = new JsonObject();
        discountJson.addProperty("discount_id", TEST_DISCOUNT_ID);
        discountJson.addProperty("start_date", "2024-01-01");
        discountJson.addProperty("end_date", "2024-12-31");
        discountJson.addProperty("discount", 20);

        try {
            storeDiscountDAO.add(discountJson);
        } catch (SQLException e) {
            fail("Failed to prepare database: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanDatabase() {
        try {
            storeDiscountDAO.delete(TEST_DISCOUNT_ID);
        } catch (Exception e) {
            // Ignore exceptions thrown by delete
        }
    }

    @Test
    //@Order(1)
    void add() {
        JsonObject discountJson = new JsonObject();
        discountJson.addProperty("discount_id", 100);
        discountJson.addProperty("start_date", "2024-01-01");
        discountJson.addProperty("end_date", "2024-12-30");
        discountJson.addProperty("discount", 15);

        assertDoesNotThrow(() -> storeDiscountDAO.add(discountJson));

        JsonObject result = null;
        try {
            result = storeDiscountDAO.search(100);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(100, result.get("discount_id").getAsInt());

        // Clean up after this specific test
        storeDiscountDAO.delete(100);
    }

    @Test
    //@Order(2)
    void delete() {
        assertDoesNotThrow(() -> storeDiscountDAO.delete(TEST_DISCOUNT_ID));

        JsonObject result = null;
        try {
            result = storeDiscountDAO.search(TEST_DISCOUNT_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNull(result);
    }

    @Test
    //@Order(3)
    void update() {
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>();
        fieldsAndValuesConditions.put("discount_id", TEST_DISCOUNT_ID);

        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>();
        fieldsAndValuesToUpdates.put("discount", 25);

        assertDoesNotThrow(() -> storeDiscountDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates));

        JsonObject result = null;
        try {
            result = storeDiscountDAO.search(TEST_DISCOUNT_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(25, result.get("discount").getAsInt());
    }

    @Test
    //@Order(4)
    void search() {
        JsonObject result = null;
        try {
            result = storeDiscountDAO.search(TEST_DISCOUNT_ID);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(TEST_DISCOUNT_ID, result.get("discount_id").getAsInt());
    }

}