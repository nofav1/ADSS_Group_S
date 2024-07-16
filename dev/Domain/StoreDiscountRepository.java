package Domain;

import DataAccess.StoreDiscountDAO;
import com.google.gson.JsonObject;

import java.sql.SQLException;

public class StoreDiscountRepository {
    // Singleton instance
    private static StoreDiscountRepository instance;
    private StoreDiscountDAO storeDiscountDAO;
    private static int nextDiscountId;

    // Private constructor to prevent instantiation
    private StoreDiscountRepository() throws SQLException {
        // Private constructor to prevent instantiation
        storeDiscountDAO = StoreDiscountDAO.getInstance();
        nextDiscountId = storeDiscountDAO.getNextDiscountId();
    }

    // Method to get the singleton instance
    public static StoreDiscountRepository getInstance() throws SQLException {
        if (instance == null) {
            synchronized (StoreDiscountRepository.class) {
                if (instance == null) {
                    instance = new StoreDiscountRepository();
                }
            }
        }
        return instance;
    }

    // Method to add a discount
    //return discount_id
    public int addDiscount(JsonObject discount_json) throws SQLException { //discount_json: "category", "discount", "start_date", "end_date"
        discount_json.addProperty("discount_id", nextDiscountId);
        storeDiscountDAO.add(discount_json);
        nextDiscountId++; //increment nextDiscountId by 1
        return nextDiscountId-1;
    }

    // Method to search for a discount
    public JsonObject search(int discount_id) throws SQLException {
        return storeDiscountDAO.search(discount_id);
    }
}
