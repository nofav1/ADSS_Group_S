package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProductsDAO extends ADAO{
    // Singleton instance
    private static ProductsDAO instance;

    // Private constructor to prevent instantiation
    private ProductsDAO() {
        // Private constructor to prevent instantiation
    }

    // Method to get the singleton instance
    public static ProductsDAO getInstance() {
        if (instance == null) {
            synchronized (ProductsDAO.class) {
                if (instance == null) {
                    instance = new ProductsDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void update(Map<String, Object> fieldsAndValuesConditions, Map<String, Object> fieldsAndValuesToUpdates) {
        // Implementation of update method
    }

    @Override
    public void add(JsonObject item_json) throws SQLException{
        // Implementation of add method
    }

    @Override
    public void delete(int id) {
        // Implementation of delete method
    }

    @Override
    public JsonObject search(int id) throws SQLException {
        String query = "SELECT * FROM Product WHERE product_number = ?";
        ObjectMapper objectMapper = new ObjectMapper();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the product_number parameter
            preparedStatement.setInt(1, id);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                JsonObject jsonObject = new JsonObject();
                // Process the result set
                if (resultSet.next()) {
                    jsonObject.addProperty("product_number", resultSet.getInt("product_number"));
                    jsonObject.addProperty("name", resultSet.getString("name"));
                    jsonObject.addProperty("demand", resultSet.getInt("demand"));
                    jsonObject.addProperty("supply_time", resultSet.getInt("supply_time"));
                    jsonObject.addProperty("min_amount_for_alert", resultSet.getInt("min_amount_for_alert"));
                    jsonObject.addProperty("manufacturer", resultSet.getString("manufacturer"));
                    jsonObject.addProperty("current_amount", resultSet.getInt("current_amount"));
                    jsonObject.addProperty("discount_id", resultSet.getInt("discount_id"));

                    return jsonObject;
                }

                return null; // Product not found
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
