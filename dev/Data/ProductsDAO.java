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
    private static StoreDiscountDAO storeDiscountDAO;

    // Private constructor to prevent instantiation
    private ProductsDAO() {
        // Private constructor to prevent instantiation
        storeDiscountDAO = StoreDiscountDAO.getInstance();
        this.table_name = "Product";
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
        super.update(fieldsAndValuesConditions, fieldsAndValuesToUpdates); //update in dataBase

        //TODO: update in cache
    }

    @Override
    public void add(JsonObject json) throws SQLException{
        String query = "INSERT INTO Product(product_number, name, demand, supply_time, min_amount_for_alert, manufacturer, current_amount, discount_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int product_number = json.get("product_number").getAsInt();
            String name = json.get("name").getAsString();
            int demand = json.get("demand").getAsInt();
            int supply_time = json.get("supply_time").getAsInt();
            int min_amount_for_alert = json.get("min_amount_for_alert").getAsInt();
            String manufacturer = json.get("manufacturer").getAsString();
            int current_amount = json.get("current_amount").getAsInt();
            int discount_id = json.get("discount_id").getAsInt();

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, product_number);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, demand);
            preparedStatement.setInt(4, supply_time);
            preparedStatement.setInt(5, min_amount_for_alert);
            preparedStatement.setString(6, manufacturer);
            preparedStatement.setInt(7, current_amount);
            preparedStatement.setInt(8, discount_id);

            preparedStatement.executeUpdate();

            // TODO: add to cache

        }
        catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Product WHERE product_number = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameter for the prepared statement
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            //TODO: remove from cache

        } catch (SQLException e) {

        }
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
                    jsonObject.addProperty("supplyTime", resultSet.getInt("supply_time"));
                    jsonObject.addProperty("minAmountForAlert", resultSet.getInt("min_amount_for_alert"));
                    jsonObject.addProperty("manufacturer", resultSet.getString("manufacturer"));
                    jsonObject.addProperty("currentAmount", resultSet.getInt("current_amount"));
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
