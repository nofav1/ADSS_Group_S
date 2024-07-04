package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        // TODO: update in cache
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

    //search record by primery key (catalog_num)
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

                return null; // Classification not found
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<JsonObject> searchByCategories(List<String> categories) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT c.catalog_num, c.category, c.subcategory, c.size, i.location " +
                "FROM Classification c " +
                "JOIN Product p ON c.product_number = p.product_number " +
                "JOIN Item i ON p.product_number = i.product_number " +
                "WHERE ");

        int index = 1;
        int fieldCount = categories.size();

        for (String category : categories) {
            query.append("c.category = '").append(category).append("'");
            if (index < fieldCount) {
                query.append(" OR ");
            }
            index++;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            List<JsonObject> categories_json_array = new ArrayList<>();
            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("catalog_num", resultSet.getInt("catalog_num"));
                    jsonObject.addProperty("category", resultSet.getString("category"));
                    jsonObject.addProperty("subcategory", resultSet.getString("subcategory"));
                    jsonObject.addProperty("size", resultSet.getInt("size"));
                    jsonObject.addProperty("location", resultSet.getString("location"));

                    categories_json_array.add(jsonObject);
                }
                return categories_json_array;

            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
