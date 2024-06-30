package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SystemFacade {
    // Singleton instance
    private static SystemFacade instance;
    private ItemManager item_manager;

    // Private constructor to prevent instantiation
    private SystemFacade() {
        item_manager = ItemManager.getInstance();
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
    public List<String> makeInventoryReport(List<String> categories) {
        // Placeholder for inventory report generation implementation
        return null;
    }

    // Method to make defect report
    public List<String> makeDefectReport() {
        // Placeholder for defect report generation implementation
        return null;
    }

    // Method to add an item
    public void addItem(JsonObject json_item) throws SQLException {
        item_manager.addItem(json_item);
    }

    // Method to remove an item
    public void removeItem(int item_id) {
        item_manager.removeItem(item_id);
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
