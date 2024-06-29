package Domain;

public class ClassificationManager {
    // Singleton instance
    private static ClassificationManager instance;

    // Private constructor to prevent instantiation
    private ClassificationManager() {
        // Private constructor to prevent instantiation
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
}
