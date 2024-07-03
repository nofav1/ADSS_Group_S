package Domain;

import Data.ClassificationDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassificationRepository {
    // Singleton instance
    private static ClassificationRepository instance;
    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> itemAmountMapByCategory; //saves all items amount in format: Map<category, Map<sub-category,Map<size, Map<location, amount>>>> (location- wareHouse(0), interiorStore(1))
    private ClassificationDAO classificationDAO;

    // Private constructor to prevent instantiation
    private ClassificationRepository() {
        classificationDAO = ClassificationDAO.getInstance();
        itemAmountMapByCategory = new HashMap<>();
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

    // Method to generate inventory report
    public JsonObject makeInventoryReport(List<String> categories) throws SQLException {
        List<JsonObject> categories_json_list = classificationDAO.searchByCategories(categories);
        updateCategoryMapAmounts(categories);

        // Convert the map to JSON and return it
        Gson gson = new Gson();
        return gson.toJsonTree(itemAmountMapByCategory).getAsJsonObject();
    }

    //updates items' amount by category - in itemAmountMapByCategory hash map (in the givin categories)
    public void updateCategoryMapAmounts(List<String> categories) throws SQLException {
        // Fetch the categories from the database
        List<JsonObject> categories_json_list = classificationDAO.searchByCategories(categories);

        // Initialize amounts to 0 for the given categories
        for (String category : categories) {
            if (itemAmountMapByCategory.containsKey(category)) {
                HashMap<String, HashMap<String, HashMap<String, Integer>>> subcategories = itemAmountMapByCategory.get(category);
                for (HashMap<String, HashMap<String, Integer>> sizes : subcategories.values()) {
                    for (Map<String, Integer> locations : sizes.values()) {
                        for (String location : locations.keySet()) {
                            locations.put(location, 0);
                        }
                    }
                }
            }
        }

        // Update the itemAmountMapByCategory with the new counts
        for (JsonObject jsonObject : categories_json_list) {
            String category = jsonObject.get("category").getAsString();
            String subcategory = jsonObject.get("subcategory").getAsString();
            String size = jsonObject.get("size").getAsString();
            String location = jsonObject.get("location").getAsString();

            itemAmountMapByCategory
                    .computeIfAbsent(category, k -> new HashMap<>())
                    .computeIfAbsent(subcategory, k -> new HashMap<>())
                    .computeIfAbsent(size, k -> new HashMap<>())
                    .merge(location, 1, Integer::sum);
        }
    }

    //return a json list that contains all product that belongs to the given category
    public List<JsonObject> findProductInCategory(String category){
        Map<String, Object> fieldsAndValuesConditions = new HashMap<>(){{put("category", category);}};
        return classificationDAO.genericSearch(fieldsAndValuesConditions);
    }
}
