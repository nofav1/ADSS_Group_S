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
        //TODO:: implement
    }

    // Method to remove a product
    public void removeProduct(int product_id) {
        //TODO:: implement
    }

    // Method to update discount
    public void updateDiscount(List<JsonObject> products_json_list, int discount_id) {
        productRepository.updateDiscount(products_json_list, discount_id);
    }

    // Method to search for a product
    public JsonObject search(int catalog_num) throws SQLException {
        return productRepository.search(catalog_num);
    }

    public void incrementProductAmount(JsonObject json_item) throws SQLException {
        productRepository.incrementProductAmount(json_item);
    }

    public void decrementProductAmount(JsonObject json_item) throws SQLException {
        productRepository.decrementProductAmount(json_item);
    }

    public boolean checkForAmountAlert(JsonObject product_json) throws SQLException {
        return productRepository.checkForAmountAlert(product_json);
    }
}
