package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.Map;

public class StoreDiscountDAO extends ADAO{
    // Singleton instance
    private static StoreDiscountDAO instance;

    // Private constructor to prevent instantiation
    private StoreDiscountDAO() {
        // Private constructor to prevent instantiation
        this.table_name = "StoreDiscount";
    }

    // Method to get the singleton instance
    public static StoreDiscountDAO getInstance() {
        if (instance == null) {
            synchronized (StoreDiscountDAO.class) {
                if (instance == null) {
                    instance = new StoreDiscountDAO();
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
    public void add(JsonObject json) throws SQLException {
        String query = "INSERT INTO StoreDiscount(discount_id, start_date, end_date, discount) VALUES(?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int discount_id = json.get("discount_id").getAsInt();
            String start_date = json.get("start_date").getAsString();
            String end_date = json.get("end_date").getAsString();
            int discount = json.get("discount").getAsInt();


            // Set parameters for the prepared statement
            preparedStatement.setInt(1, discount_id);
            preparedStatement.setString(2, start_date);
            preparedStatement.setString(3, end_date);
            preparedStatement.setInt(4, discount);

            preparedStatement.executeUpdate();

            // TODO: add to cache

        }
        catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM StoreDiscount WHERE discount_id = ?";

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
        String query = "SELECT * FROM StoreDiscount WHERE discount_id = ?";
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
                    jsonObject.addProperty("discount_id", resultSet.getInt("discount_id"));
                    jsonObject.addProperty("start_date", resultSet.getString("start_date"));
                    jsonObject.addProperty("end_date", resultSet.getString("end_date"));
                    jsonObject.addProperty("discount", resultSet.getDouble("discount"));

                    return jsonObject;
                }

                return null; // Product not found
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
