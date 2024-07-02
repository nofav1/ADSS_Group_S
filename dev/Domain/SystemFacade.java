package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SystemFacade {
    // Singleton instance
    private static SystemFacade instance;
    private ItemManager item_manager;
    private ProductManager product_manager;
    private ClassificationManager classification_manager;

    // Private constructor to prevent instantiation
    private SystemFacade() {
        item_manager = ItemManager.getInstance();
        product_manager = ProductManager.getInstance();
        classification_manager = ClassificationManager.getInstance();
    }

    // Method to get the singleton instance
    public static SystemFacade getInstance() {
        if (instance == null) {
            synchronized (SystemFacade.class) {
                if (instance == null) {
                    instance = new SystemFacade();
                }
            }
        }
        return instance;
    }

    // Method to make inventory report
    public JsonObject makeInventoryReport(List<String> categories) throws SQLException {
        JsonObject json = classification_manager.makeInventoryReport(categories);
        return json;
    }

    // Method to make defect report
    public List<String> makeDefectReport() {
        // Placeholder for defect report generation implementation
        return null;
    }

    // Method to add an item
    public void addItem(JsonObject json_item) throws SQLException {
        item_manager.addItem(json_item); //add item in itemDAO + cache
        product_manager.incrementProductAmount(json_item); //increment by 1
    }

    // Method to remove an item
    public void removeItem(int item_id) throws SQLException {
        JsonObject json_item = item_manager.showItemDetails(item_id);
        item_manager.removeItem(item_id);
        product_manager.decrementProductAmount(json_item); //decrement by 1
    }

    // Method to mark an item as defective
    public void markAsDefect(int item_id) {
        item_manager.markAsDefect(item_id);
    }

    // Method to show item details
    public JsonObject showItemDetails(int item_id) throws SQLException {
        return item_manager.showItemDetails(item_id);
    }

    // Method to update discount
    public void updateDiscount(int discount, Date startDate, Date endDate,
                               List<String> categories, List<Integer> product_numbers) {
        // Placeholder for updating discount implementation
    }
}
