package Presentation;
import Domain.Location;
import Data.*;

import java.sql.SQLException;
import java.util.*;

import Domain.SystemFacade;
import com.google.gson.JsonObject;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class menu {
    public static Scanner scan;
    public static SystemFacade system;

    static {
        try {
            system = SystemFacade.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int choice = 0;
        scan = new Scanner(System.in);

        while (choice != 3) {
            String mainMenu = "Menu:\n1.Generate Reports" +
                    "\n2.Items Functions" +
                    "\n3.Exit";

            System.out.println(mainMenu);
            choice = scan.nextInt();

            if (choice == 1) { // Generate Reports
                int reportChoice = 0;
                while (reportChoice != 3) {
                    String reportMenu = "Generate Reports:\n1.Inventory Report" +
                            "\n2.Defectives Report" +
                            "\n3.Back";
                    System.out.println(reportMenu);
                    reportChoice = scan.nextInt();
                    scan.nextLine(); // consume the newline

                    if (reportChoice == 1) { // Inventory Report
                        try {
                            generateInventoryReport();
                        } catch (Exception e) {
                            System.out.println("Error in generating inventory report");
                            System.out.println(e.getMessage());
                        }
                    } else if (reportChoice == 2) { // Defectives Report
                        try {
                            generateDefectivesReport();
                        } catch (Exception e) {
                            System.out.println("Error in generating defectives report");
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } else if (choice == 2) { // Items Functions
                int itemChoice = 0;
                while (itemChoice != 6) {
                    //TODO: add option to add product and category (and remove)
                    String itemsMenu = "Items Functions:\n1.Add item" +
                            "\n2.Remove item" +
                            "\n3.Mark as defective" +
                            "\n4.Show item" +
                            "\n5.Update store discount" +
                            "\n6.Back";

                    System.out.println(itemsMenu);
                    itemChoice = scan.nextInt();
                    scan.nextLine(); // consume the newline

                    switch (itemChoice) {
                        case 1: // Add item
                            try {
                                addItem();
                            } catch (Exception e) {
                                System.out.println("Error in adding item");
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 2: // Remove item
                            try {
                                removeItem();
                            } catch (Exception e) {
                                System.out.println("Error in removing item");
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 3: // Mark as defective
                            try {
                                markAsDefective();
                            } catch (Exception e) {
                                System.out.println("Error in marking item as defective");
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 4: // Show item
                            try {
                                showItem();
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 5: // Update store discount
                            int discountChoice = 0;
                            while (discountChoice != 3) {
                                String discountMenu = "Update store discount:\n1.Discount by category" +
                                        "\n2.Discount by product" +
                                        "\n3.Back";
                                System.out.println(discountMenu);
                                discountChoice = scan.nextInt();
                                scan.nextLine(); // consume the newline

                                if (discountChoice == 1) { // Discount by category
                                    try {
                                        discountByCategory();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                } else if (discountChoice == 2) { // Discount by product
                                    try {
                                        discountByProduct();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                            break;
                        case 6: // Back
                            break;
                        default: // error - wrong input
                            break;
                    }
                }
            }
        }
        scan.close();
    }

    public static void generateInventoryReport() throws SQLException {
        System.out.println("Which categories?");
        String stringCategories = scan.nextLine(); //assume that the user writes the categories in this format: "Cat1 Cat2 Cat3..."
        List<String> categories = new ArrayList<>(Arrays.asList(stringCategories.split(" ")));

        System.out.println("Inventory Report\n");

        JsonObject categoriesJsonMap = system.makeInventoryReport(categories);
        printInventoryReport(categoriesJsonMap, categories);
    }
    public static void generateDefectivesReport() throws SQLException {
        System.out.println("Defective Report");

        List<JsonObject> defectiveItemsJsonMap = system.makeDefectReport();
        printDefectiveReport(defectiveItemsJsonMap);
    }

    public static void addItem() throws SQLException {
        JsonObject item_json = getItemDetails();
        system.addItem(item_json);
    }
    public static void removeItem() throws SQLException, InterruptedException {
        System.out.println("Enter item ID: ");
        int itemID = scan.nextInt();
        scan.nextLine();
        JsonObject product_json = system.removeItem(itemID);
        if(system.checkForAmountAlert(product_json)) { //check for alert
            System.out.println("ALERT! Please order new supply of " + product_json.get("name"));
        }

    }
    public static void markAsDefective() throws Exception {
        System.out.println("Enter item ID: ");
        int itemID = scan.nextInt();
        scan.nextLine();
        system.markAsDefect(itemID);
    }
    public static void showItem() throws SQLException {
        System.out.println("Enter item ID: ");
        int itemID = scan.nextInt();
        scan.nextLine();
        JsonObject full_item_json = system.showItemDetails(itemID);
        if(full_item_json == null){
            System.out.println("Item not found");
        }
        else {
            String details = getItemDetails(full_item_json);
            System.out.println(details);
        }
    }

    public static void discountByCategory() throws SQLException {
        JsonObject json = new JsonObject();
        System.out.print("Which Category? ");
        String category = scan.nextLine();
        json.addProperty("category", category);

        System.out.print("Discount: ");
        int discount = scan.nextInt();
        scan.nextLine();
        json.addProperty("discount", discount);

        System.out.print("Start date: ");
        String s_date = scan.nextLine();
        json.addProperty("start_date", s_date);

        System.out.print("End date: ");
        String e_date = scan.nextLine();
        json.addProperty("end_date", e_date);

        system.updateDiscountByCategory(json);

    }
    public static void discountByProduct() throws SQLException {
        JsonObject json = new JsonObject();
        System.out.print("Which Catalog number? ");
        String pn = scan.nextLine();
        json.addProperty("product_number", pn);

        System.out.print("Discount: ");
        int discount = scan.nextInt();
        scan.nextLine();
        json.addProperty("discount", discount);

        System.out.print("Start date: ");
        String s_date = scan.nextLine();
        json.addProperty("start_date", s_date);

        System.out.print("End date: ");
        String e_date = scan.nextLine();
        json.addProperty("end_date", e_date);

        system.updateDiscountByCatalogNum(json);
    }

    public static JsonObject getItemDetails(){ //get all product details from user
        JsonObject json = new JsonObject();

        System.out.print("Item ID: ");
        int itemID = scan.nextInt();
        scan.nextLine();
        json.addProperty("id", itemID);

        System.out.print("Item expiring date: ");
        String iExpD = scan.nextLine();
        json.addProperty("expiring_date", iExpD);

        System.out.print("Item section: ");
        char item_section = scan.nextLine().charAt(0);
        json.addProperty("section", item_section);

        System.out.print("Item location:(WareHouse = 0, Interior = 1): ");
        int item_Loc = scan.nextInt();
        scan.nextLine();
        Location loc = Location.WareHouse;
        json.addProperty("location", item_Loc); //int

        System.out.print("Item supplier Discount: ");
        int item_SupplierDis = scan.nextInt();
        scan.nextLine();
        json.addProperty("supplier_discount", item_SupplierDis);

        System.out.print("Item cost price: ");
        double item_costPrice = scan.nextDouble();
        scan.nextLine();
        json.addProperty("cost_price", item_costPrice);

        //product catalog_num
        System.out.print("Catalog number: ");
        String product_number = scan.nextLine();
        json.addProperty("product_number", product_number);

        return json;

    }

    public static String getItemDetails(JsonObject fullItemJson){
        StringBuilder details = new StringBuilder();

        JsonObject itemDetails = fullItemJson.getAsJsonObject("item_details");
        JsonObject productDetails = fullItemJson.getAsJsonObject("product_details");
        JsonObject classificationDetails = fullItemJson.getAsJsonObject("classification_details");
        JsonObject storeDiscountDetails = fullItemJson.getAsJsonObject("store_discount_details");

        details.append("Item Details:\n")
                .append("Item ID: ").append(itemDetails.get("item_id").getAsInt()).append("\n")
                .append("Expiring Date: ").append(itemDetails.get("expiring_date").getAsString()).append("\n")
                .append("Section: ").append(itemDetails.get("section").getAsString()).append("\n")
                .append("Location: ").append(itemDetails.get("location").getAsString()).append("\n")
                .append("Is Defect: ").append(itemDetails.get("isDefect").getAsBoolean()).append("\n")
                .append("Supplier Discount: ").append(itemDetails.get("supplier_dis").getAsInt()).append("\n")
                .append("Cost Price: ").append(itemDetails.get("costPrice").getAsDouble()).append("\n")
                .append("Purchase Price: ").append(itemDetails.get("purchase_price").getAsDouble()).append("\n\n");

        details.append("Product Details:\n")
                .append("Product Number: ").append(productDetails.get("product_number").getAsInt()).append("\n")
                .append("Product Name: ").append(productDetails.get("name").getAsString()).append("\n")
                .append("Demand: ").append(productDetails.get("demand").getAsInt()).append("\n")
                .append("Supply Time: ").append(productDetails.get("supply_time").getAsInt()).append("\n")
                .append("Min Amount for Alert: ").append(productDetails.get("min_amount_for_alert").getAsInt()).append("\n")
                .append("Manufacturer: ").append(productDetails.get("manufacturer").getAsString()).append("\n")
                .append("Current Amount: ").append(productDetails.get("current_amount").getAsInt()).append("\n\n");

        details.append("Classification Details:\n")
                .append("Catalog Number: ").append(classificationDetails.get("catalog_num").getAsInt()).append("\n")
                .append("Category: ").append(classificationDetails.get("category").getAsString()).append("\n")
                .append("Subcategory: ").append(classificationDetails.get("subcategory").getAsString()).append("\n")
                .append("Size: ").append(classificationDetails.get("size").getAsInt()).append("\n\n");

        details.append("Store Discount Details:\n")
                .append("Discount ID: ").append(storeDiscountDetails.get("discount_id").getAsInt()).append("\n")
                .append("Start Date: ").append(storeDiscountDetails.get("start_date").getAsString()).append("\n")
                .append("End Date: ").append(storeDiscountDetails.get("end_date").getAsString()).append("\n")
                .append("Discount Percentage: ").append(storeDiscountDetails.get("discount").getAsInt()).append("\n");


        return details.toString();
    }

    private static void printInventoryReport(JsonObject categoriesJsonMap, List<String> categories) {
        for (String category : categories) {
            if (categoriesJsonMap.has(category)) {
                System.out.println(category + ":");
                JsonObject subcategories = categoriesJsonMap.getAsJsonObject(category);
                for (String subcategory : subcategories.keySet()) {
                    System.out.println("----" + subcategory + ":");
                    JsonObject sizes = subcategories.getAsJsonObject(subcategory);
                    for (String size : sizes.keySet()) {
                        System.out.println("--------size: " + size);
                        JsonObject locations = sizes.getAsJsonObject(size);
                        for (String location : locations.keySet()) {
                            System.out.println("------------" + location + ": " + locations.get(location).getAsInt());
                        }
                    }
                }
            }
        }
    }

    private static void printDefectiveReport(List<JsonObject> defectiveItemsJsonMap) {
        if (defectiveItemsJsonMap == null || defectiveItemsJsonMap.isEmpty()) {
            System.out.println("No defective items found.");
            return;
        }

        int index = 1;
        for (JsonObject jsonObject : defectiveItemsJsonMap) {
            System.out.println(index + ". Item: " + jsonObject.get("item_id").getAsInt() +
                    ", expiring date: " + jsonObject.get("expiring_date").getAsString() +
                    ", section: " + jsonObject.get("section").getAsString() +
                    ", location: " + jsonObject.get("location").getAsString() +
                    ", supplier discount: " + jsonObject.get("supplier_dis").getAsInt() +
                    ", cost price: " + jsonObject.get("costPrice").getAsDouble() +
                    ", purchase price: " + jsonObject.get("purchase_price").getAsDouble() +
                    ", product number: " + jsonObject.get("product_number").getAsInt());
            index++;
        }
    }

}
