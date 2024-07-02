package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProductManager {
    // Singleton instance
    private static ProductManager instance;
    private static ProductRepository productRepository;

    // Private constructor to prevent instantiation
    private ProductManager() {
        productRepository = ProductRepository.getInstance();
    }

    // Method to get the singleton instance
    public static ProductManager getInstance() {
        if (instance == null) {
            synchronized (ProductManager.class) {
                if (instance == null) {
                    instance = new ProductManager();
                }
            }
        }
        return instance;
    }

    // Method to add a product
    public void addProduct(String product) {
        // Placeholder for adding product implementation
    }

    // Method to remove a product
    public void removeProduct(int product_id) {
        // Placeholder for removing product implementation
    }

    // Method to update discount
    public void updateDiscount(int discount, Date startDate, Date endDate,
                               List<String> categories, List<Integer> product_numbers) {
        // Placeholder for updating discount implementation
    }

    // Method to search for a product
    public boolean search(int catalog_num) {
        // Placeholder for search implementation
        return false;
    }

    public void incrementProductAmount(JsonObject json_item) throws SQLException {
        productRepository.incrementProductAmount(json_item);
    }

    public void decrementProductAmount(JsonObject json_item) throws SQLException {
        productRepository.decrementProductAmount(json_item);
    }
}
