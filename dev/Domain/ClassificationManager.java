package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.List;

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
        // Simulating adding classification to manager
        System.out.println("Adding classification to manager: " + classification);
        // Actual implementation to manage or process the classification data
    }

    // Method to generate inventory report
    public JsonObject makeInventoryReport(List<String> categories) throws SQLException {
        JsonObject json = classificationRepository.makeInventoryReport(categories);
        return json;
    }
}
