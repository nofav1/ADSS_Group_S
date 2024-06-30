package Data;

import com.google.gson.JsonObject;

import java.sql.*;
import java.util.Map;

public class ItemsDAO extends ADAO{
    // Singleton instance
    private static ItemsDAO instance;

    // Private constructor to prevent instantiation
    private ItemsDAO() {
        // Private constructor to prevent instantiation
    }

    // Method to get the singleton instance
    public static ItemsDAO getInstance() {
        if (instance == null) {
            synchronized (ItemsDAO.class) {
                if (instance == null) {
                    instance = new ItemsDAO();
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
        String query = "INSERT INTO Item(item_id, expiring_date, section, location, isDefect, supplir_dis, costPrice, purchase_price, product_number) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int item_id = item_json.get("id").getAsInt();
            String expiring_date = item_json.get("expiring_date").getAsString();
            String section = item_json.get("section").getAsString();
            int location = item_json.get("location").getAsInt();
            boolean isDefect = false; //default
            int supplir_dis = item_json.get("supplier_discount").getAsInt();
            double costPrice = item_json.get("cost_price").getAsDouble();
            double purchase_price; //calculate from product table with discount
            int product_number = item_json.get("catalog_number").getAsInt();

            //calculate purchase_price
            purchase_price = calculatePurchasePrice(product_number);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, item_id);
            preparedStatement.setDate(2, Date.valueOf(expiring_date));
            preparedStatement.setString(3, section);
            preparedStatement.setInt(4, location);
            preparedStatement.setBoolean(5, isDefect);
            preparedStatement.setInt(6, supplir_dis);
            preparedStatement.setDouble(7, costPrice);
            preparedStatement.setDouble(8, purchase_price);
            preparedStatement.setInt(9, product_number);

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            throw new SQLException();
        }
    }

    @Override
    public void delete(int id) {
        // Implementation of delete method
    }

    @Override//return null if not exist
             //convert to json
    public JsonObject search(int id) {
        // Implementation of search method
        return null; // Replace with actual implementation
    }

    private double calculatePurchasePrice(int product_number){
        return 0;
    }
}
