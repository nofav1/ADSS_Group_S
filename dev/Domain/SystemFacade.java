package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemFacade {
    // Singleton instance
    private static SystemFacade instance;
    private ItemManager item_manager;
    private ProductManager product_manager;
    private ClassificationManager classification_manager;
    private StoreDiscountManager storeDiscount_manager;

    // Private constructor to prevent instantiation
    private SystemFacade() throws SQLException {
        item_manager = ItemManager.getInstance();
        product_manager = ProductManager.getInstance();
        classification_manager = ClassificationManager.getInstance();
        storeDiscount_manager = StoreDiscountManager.getInstance();
    }

    // Method to get the singleton instance
    public static SystemFacade getInstance() throws SQLException {
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
        return classification_manager.makeInventoryReport(categories);
    }

    // Method to make defect report
    public List<JsonObject> makeDefectReport() throws SQLException {
        return item_manager.makeDefectReport();
    }

    // Method to add an item
    public void addItem(JsonObject json_item) throws SQLException {
        JsonObject product_json = product_manager.search(json_item.get("product_number").getAsInt());
        item_manager.addItem(json_item); //add item in itemDAO + cache
        product_manager.incrementProductAmount(json_item); //increment product amount by 1
    }

    // Method to remove an item
    //return product json that the item belongs to
    public JsonObject removeItem(int item_id) throws SQLException {
        JsonObject json_item = item_manager.showItemDetails(item_id);
        JsonObject product_json = product_manager.search(json_item.get("product_number").getAsInt());
        item_manager.removeItem(item_id);
        product_manager.decrementProductAmount(json_item); //decrement by 1
        return product_json;
    }

    public boolean checkForAmountAlert(JsonObject product_json) throws SQLException {
        return product_manager.checkForAmountAlert(product_json);
    }

    // Method to mark an item as defective
    public void markAsDefect(int item_id) throws Exception {
        item_manager.markAsDefect(item_id);
    }

    // Method to show item details
    public JsonObject showItemDetails(int item_id) throws SQLException {
        JsonObject item_json = item_manager.showItemDetails(item_id);
        JsonObject product_json = product_manager.search(item_json.get("product_number").getAsInt());
        JsonObject classification_json = classification_manager.search(product_json.get("product_number").getAsInt());
        JsonObject store_json = storeDiscount_manager.search(product_json.get("discount_id").getAsInt());

        // Combine all the JSON objects into one
        JsonObject combined_json = new JsonObject();
        combined_json.add("item_details", item_json);
        combined_json.add("product_details", product_json);
        combined_json.add("classification_details", classification_json);
        combined_json.add("store_discount_details", store_json);

        return combined_json;
    }

    /**
     * Method to update discount by category
     * add a discount record in discount table - the new given discount
     * finds all relevent products that belonges to the given category
     * updates discount in the relevent products
     * update purcase price in all relevent items
     **/
    public void updateDiscountByCategory(JsonObject json) throws SQLException {
        int discount = json.get("discount").getAsInt();
        int discount_id = storeDiscount_manager.addDiscount(json);
        List<JsonObject> product_list_in_category = classification_manager.findProductInCategory(json.get("category").getAsString());
        product_manager.updateDiscount(product_list_in_category, discount_id);
        item_manager.updatePurchasePrice(product_list_in_category, discount);
    }

    /**
     * Method to update discount by catalog number
     * add a discount record in discount table - the new given discount
     * updates discount in the relevent product
     * update purcase price in all relevent items
     **/
    public void updateDiscountByCatalogNum(JsonObject json) throws SQLException {
        int discount = json.get("discount").getAsInt();
        int discount_id = storeDiscount_manager.addDiscount(json);
        JsonObject product_json = product_manager.search(json.get("product_number").getAsInt());
        List<JsonObject> product_json_list = new ArrayList<>(Arrays.asList(product_json));
        product_manager.updateDiscount(product_json_list, discount_id);
        item_manager.updatePurchasePrice(product_json_list, discount);
    }

}
