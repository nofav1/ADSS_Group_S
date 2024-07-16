package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassificationManager {
    // Singleton instance
    private static ClassificationManager instance;
    private static ClassificationRepository classificationRepository;

    // Private constructor to prevent instantiation
    private ClassificationManager() {
        classificationRepository = ClassificationRepository.getInstance();
    }

    // Method to get the singleton instance
    public static ClassificationManager getInstance() {
        if (instance == null) {
            synchronized (ClassificationManager.class) {
                if (instance == null) {
                    instance = new ClassificationManager();
                }
            }
        }
        return instance;
    }

    // Method to add classification
    public void addClassification(String classification) {
        //TODO:: implement
    }

    // Method to generate inventory report
    public JsonObject makeInventoryReport(List<String> categories) throws SQLException {
        JsonObject json = classificationRepository.makeInventoryReport(categories);
        return json;
    }

    public List<JsonObject> findProductInCategory(String category){
        return classificationRepository.findProductInCategory(category);
    }

    // Method to search for a classification
    public JsonObject search(int catalog_num) throws SQLException {
        return classificationRepository.search(catalog_num);
    }
}
