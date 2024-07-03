package Domain;

import Data.StoreDiscountDAO;
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

    // Method to update discount
    /*public void updateDiscount(JsonObject list_) {
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>(){{put("discount_id", discount_id);}};
        Map<String, Object> fieldsAndValuesToUpdate = new HashMap<>(){{put("discount", true);}};
        storeDiscountDAO.update(fieldsAndValuesConditions, fieldsAndValuesToUpdate);
    }*/

    // Method to search for a discount
    public JsonObject search(int discount_id) throws SQLException {
        return null;
    }
}
