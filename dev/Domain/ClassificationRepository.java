package Domain;

public class ClassificationRepository {
    // Singleton instance
    private static ClassificationRepository instance;

    // Private constructor to prevent instantiation
    private ClassificationRepository() {
        // Private constructor to prevent instantiation
    }

    // Method to get the singleton instance
    public static ClassificationRepository getInstance() {
        if (instance == null) {
            synchronized (ClassificationRepository.class) {
                if (instance == null) {
                    instance = new ClassificationRepository();
                }
            }
        }
        return instance;
    }

    // Method to add classification
    public void addClassification(String classification) {
        // Simulating adding classification to repository
        System.out.println("Adding classification: " + classification);
        // Actual implementation to store or process the classification data
    }
}
