package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.Map;

public class ClassificationDAO extends ADAO{
    // Singleton instance
    private static ClassificationDAO instance;

    // Private constructor to prevent instantiation
    private ClassificationDAO() {
        // Private constructor to prevent instantiation
        this.table_name = "Classification";
    }

    // Method to get the singleton instance
    public static ClassificationDAO getInstance() {
        if (instance == null) {
            synchronized (ClassificationDAO.class) {
                if (instance == null) {
                    instance = new ClassificationDAO();
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
        String query = "INSERT INTO Classification(catalog_num, category, subcategory, size, product_number) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int catalog_num = json.get("catalog_num").getAsInt();
            String category = json.get("category").getAsString();
            String subcategory = json.get("subcategory").getAsString();
            int size = json.get("size").getAsInt();
            int product_number = json.get("product_number").getAsInt();


            // Set parameters for the prepared statement
            preparedStatement.setInt(1, catalog_num);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, subcategory);
            preparedStatement.setInt(4, size);
            preparedStatement.setInt(5, product_number);

            preparedStatement.executeUpdate();

            // TODO: add to cache

        }
        catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Classification WHERE catalog_num = ?";

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
        String query = "SELECT * FROM Classification WHERE catalog_num = ?";
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
                    jsonObject.addProperty("catalog_num", resultSet.getInt("catalog_num"));
                    jsonObject.addProperty("category", resultSet.getString("category"));
                    jsonObject.addProperty("subcategory", resultSet.getString("subcategory"));
                    jsonObject.addProperty("size", resultSet.getInt("size"));
                    //jsonObject.addProperty("product_number", resultSet.getInt("product_number"));

                    return jsonObject;
                }

                return null; // Product not found
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
