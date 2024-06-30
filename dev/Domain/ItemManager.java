package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.List;

public class ItemManager {
    // Singleton instance
    private static ItemManager instance;
    private ItemRepository item_repo;

    // Private constructor to prevent instantiation
    private ItemManager() {
        item_repo = ItemRepository.getInstance();
    }

    // Method to get the singleton instance
    public static ItemManager getInstance() {
        if (instance == null) {
            synchronized (ItemManager.class) {
                if (instance == null) {
                    instance = new ItemManager();
                }
            }
        }
        return instance;
    }

    // Method to add an item
    public void addItem(JsonObject item_json) throws SQLException {
        item_repo.addItem(item_json);
    }

    // Method to remove an item
    public void removeItem(int item_id) {
        // Placeholder for removing item implementation
    }

    // Method to mark an item as defective
    public void markAsDefect(int item_id) {
        // Placeholder for marking item as defect implementation
    }

    // Method to show item details
    public String showItemDetails(int item_id) {
        // Placeholder for showing item details implementation
        return null;
    }

    // Method to generate defect report
    public List<String> makeDefectReport() {
        // Placeholder for defect report generation implementation
        return null;
    }
}
