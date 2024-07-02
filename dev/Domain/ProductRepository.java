package Domain;

import Data.ProductsDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    // Singleton instance
    private static ProductRepository instance;

    private static ProductsDAO productsDAO;

    // Private constructor to prevent instantiation
    private ProductRepository() {
        // Private constructor to prevent instantiation
        productsDAO = ProductsDAO.getInstance();
    }

    // Method to get the singleton instance
    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    // Method to add a product
    public void addProduct(String product) {
        // Placeholder for adding product implementation
    }

    // Method to update discount
    public void updateDiscount(int discount, Date startDate, Date endDate,
                               List<String> categories, List<Integer> product_numbers) {
        // Placeholder for updating discount implementation
    }

    // Method to search for a product
    public JsonObject search(int catalog_num) throws SQLException {
        // Placeholder for search implementation
        JsonObject json = productsDAO.search(catalog_num);
        return json;
        //remember to return product class json - with object mapper
    }

    public void incrementProductAmount(JsonObject json_item) throws SQLException {
        //search for current amount in item's catalog number
        JsonObject product_json = productsDAO.search(json_item.get("product_number").getAsInt());
        int curr_amount = product_json.get("currentAmount").getAsInt();

        Map<String, Object> fieldsAndValuesConditions = new HashMap<>(){{put("product_number", json_item.get("product_number").getAsInt());}};
        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>(){{put("current_amount", curr_amount+1);}};
        productsDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates);
    }

    public void decrementProductAmount(JsonObject json_item) throws SQLException {
        //search for current amount in item's catalog number
        JsonObject product_json = productsDAO.search(json_item.get("product_number").getAsInt());
        int curr_amount = product_json.get("currentAmount").getAsInt();

        Map<String, Object> fieldsAndValuesConditions = new HashMap<>(){{put("product_number", json_item.get("product_number").getAsInt());}};
        Map<String, Object> fieldsAndValuesToUpdates = new HashMap<>(){{put("current_amount", curr_amount-1);}};
        productsDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates);
    }
}
