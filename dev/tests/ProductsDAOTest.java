package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

import DataAccess.ProductsDAO;
import Domain.Product;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.HashMap;
import java.util.Map;

//@TestMethodOrder(OrderAnnotation.class)
class ProductsDAOTest {
    private static ProductsDAO productsDAO;
    private static final int TEST_PRODUCT_NUMBER = 1;

    @BeforeAll
    static void setUp() {
        productsDAO = ProductsDAO.getInstance();
    }

    @BeforeEach
    void prepareDatabase() {
        JsonObject productJson = new JsonObject();
        productJson.addProperty("product_number", TEST_PRODUCT_NUMBER);
        productJson.addProperty("name", "Test Product");
        productJson.addProperty("demand", 100);
        productJson.addProperty("supply_time", 7);
        productJson.addProperty("min_amount_for_alert", 10);
        productJson.addProperty("manufacturer", "Test Manufacturer");
        productJson.addProperty("current_amount", 50);
        productJson.addProperty("discount_id", 1);

        try {
            productsDAO.add(productJson);
        } catch (SQLException e) {
            fail("Failed to prepare database: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanDatabase() {
        productsDAO.delete(TEST_PRODUCT_NUMBER);
    }

    @Test
//    @Order(1)
    void add() {
        JsonObject productJson = new JsonObject();
        productJson.addProperty("product_number", 2);
        productJson.addProperty("name", "New Product");
        productJson.addProperty("demand", 200);
        productJson.addProperty("supply_time", 5);
        productJson.addProperty("min_amount_for_alert", 15);
        productJson.addProperty("manufacturer", "New Manufacturer");
        productJson.addProperty("current_amount", 30);
        productJson.addProperty("discount_id", 2);

        assertDoesNotThrow(() -> productsDAO.add(productJson));

        JsonObject result = null;
        try {
            result = productsDAO.search(2);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(2, result.get("product_number").getAsInt());

        // Clean up after this specific test
        productsDAO.delete(2);
    }

    @Test
    //@Order(2)
    void delete() {
        assertDoesNotThrow(() -> productsDAO.delete(TEST_PRODUCT_NUMBER));

        JsonObject result = null;
        try {
            result = productsDAO.search(TEST_PRODUCT_NUMBER);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNull(result);
    }

    @Test
    //@Order(3)
    void update() {
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>();
        fieldsAndValuesConditions.put("product_number", TEST_PRODUCT_NUMBER);

        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>();
        fieldsAndValuesToUpdates.put("name", "Updated Product");

        assertDoesNotThrow(() -> productsDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates));

        JsonObject result = null;
        try {
            result = productsDAO.search(TEST_PRODUCT_NUMBER);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals("Updated Product", result.get("name").getAsString());
    }

    @Test
    //@Order(4)
    void search() {
        JsonObject result = null;
        try {
            result = productsDAO.search(TEST_PRODUCT_NUMBER);
        } catch (SQLException e) {
            fail("Exception thrown during search");
        }

        assertNotNull(result);
        assertEquals(TEST_PRODUCT_NUMBER, result.get("product_number").getAsInt());
    }

}