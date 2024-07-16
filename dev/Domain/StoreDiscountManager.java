package Domain;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StoreDiscountManager {
    // Singleton instance
    private static StoreDiscountManager instance;
    private static StoreDiscountRepository storeDiscountRepository;

    // Private constructor to prevent instantiation
    private StoreDiscountManager() throws SQLException {
        storeDiscountRepository = StoreDiscountRepository.getInstance();
    }

    // Method to get the singleton instance
    public static StoreDiscountManager getInstance() throws SQLException {
        if (instance == null) {
            synchronized (StoreDiscountManager.class) {
                if (instance == null) {
                    instance = new StoreDiscountManager();
                }
            }
        }
        return instance;
    }

    // Method to add a Discount
    public int addDiscount(JsonObject discount_json) throws SQLException { //discount_json: "category", "discount", "start_date", "end_date"
        return storeDiscountRepository.addDiscount(discount_json);
    }

    // Method to search for a Discount
    public JsonObject search(int discount_id) throws SQLException {
        return storeDiscountRepository.search(discount_id);
    }
}
