package Data;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public class ClassificationDAO extends ADAO{
    // Singleton instance
    private static ClassificationDAO instance;

    // Private constructor to prevent instantiation
    private ClassificationDAO() {
        // Private constructor to prevent instantiation
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
    public JsonObject search(int id) {
        // Implementation of search method
        return null; // Replace with actual implementation
    }
}
