package Presentation;
import Domain.Location;
import Data.*;

import java.sql.SQLException;
import java.util.Scanner;

import Domain.SystemFacade;
import com.google.gson.JsonObject;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class menu {
    public static Scanner scan;
    public static SystemFacade system = SystemFacade.getInstance();

    public static void main(String[] args) {


        int choice = 0;
        scan = new Scanner(System.in);
        while (choice != 3) {
            String menu = "Menu:\n1.Generate Reports" +
                    "\n2.Items Functions" +
                    "\n3.Exit";

            System.out.println(menu);

            choice = scan.nextInt();
            if (choice != 3) {
                scan.nextLine();
            } else {
                break; //exit
            }

            if (choice == 1) { //Generate Reports
                menu = "Generate Reports:\n1.Inventory Report" +
                        "\n2.Defectives Report" +
                        "\n3.Back";

                System.out.println(menu);

                choice = scan.nextInt();
                if (choice != 3) {
                    scan.nextLine();
                }
                if (choice == 1) { //Inventory Report
                    generateInventoryReport();
                } else if (choice == 2) { //Defectives Report
                    generateDefectivesReport();
                } else if (choice == 3) { //Back
                    choice = 0;
                }
            } else if (choice == 2) { //Items Functions
                menu = "Items Functions:\n1.Add item" +
                        "\n2.Remove item" +
                        "\n3.Mark as defective" +
                        "\n4.Show item" +
                        "\n5.Update store discount" +
                        "\n6.Back";

                System.out.println(menu);

                choice = scan.nextInt();
                if (choice != 6) {
                    scan.nextLine();
                } else {
                    break; //exit
                }

                switch (choice) {
                    case 1: //Add item
                        try{
                            addItem();
                        }catch (Exception e){
                            System.out.println("Error in adding item");
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2: //Remove item
                        removeItem();
                        break;
                    case 3: //Mark as defective
                        markAsDefective();
                        break;
                    case 4: //Show item
                        showItem();
                        break;
                    case 5: //Update store discount
                        menu = "Update store discount:\n1.Discount by category" +
                                "\n2.Discount by product" +
                                "\n3.Back";
                        System.out.println(menu);

                        choice = scan.nextInt();
                        if (choice != 3) {
                            scan.nextLine();
                        } else {
                            choice = 0;
                            break; //Back
                        }

                        switch (choice) {
                            case 1: //Discount by category
                                discountByCategory();
                                break;
                            case 2: //Discount by product
                                discountByProduct();
                                break;
                        }
                        break;
                    case 6: //Back
                        break;
                    default: //error - wrong input
                        break;
                }
            }
        }


        /*
        DataController dataController = new DataController(); //service
        String path = getPathFromConfig();
        dataController.ImportData(path);
        int choice = 0;
        scan = new Scanner(System.in);
        while (choice != 10) {
            String menu = "Menu:\n1.Update Discount" +
                    "\n2.Generate an inventory report by category" +
                    "\n3.Generate a defective products report" +
                    "\n4.Show Product Purchase Price" +
                    "\n5.Update Price" +
                    "\n6.Add Product" +
                    "\n7.Remove Product" +
                    "\n8.Mark product as defect" +
                    "\n9.Show Product Details" +
                    "\n10.Exit";

            System.out.println(menu);

            choice = scan.nextInt();
            if(choice!=10){scan.nextLine();}

            int productID;

            switch (choice) {
                case 1: //Update Discount
                    System.out.println("Discount on:\n(1)Category\n(2)Sub-Category\n(3)Catalog Number");
                    choice = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Enter Discount(%): ");
                    int discount = scan.nextInt();
                    scan.nextLine();
                    boolean cont = true;
                    while(cont) {
                        if (choice == 1) {
                            System.out.println("Enter Category: ");
                            String category = scan.nextLine();
                            dataController.setDiscountForCategory(category, discount);
                            cont = false;
                        } else if (choice == 2) {
                            System.out.println("Enter Sub-Category: ");
                            String sub_category = scan.nextLine();
                            dataController.setDiscountForSubCategory(sub_category, discount);
                            cont = false;
                        } else if (choice == 3) {
                            System.out.println("Enter Catalog Number: ");
                            int cNum = scan.nextInt();
                            scan.nextLine();
                            dataController.setDiscountForCatalogNum(cNum, discount);
                            cont = false;
                        } else {
                            System.out.println("Invalid choice. Please enter 1, 2 or 3");
                        }
                    }
                    break;
                case 2: //Generate an inventory report by category
                    System.out.println("Which categories?");
                    String stringCategories = scan.nextLine(); //assume that the user writes the categories in this format: "Cat1 Cat2 Cat3..."
                    String[] categories = stringCategories.split(" ");

                    System.out.println("Inventory Report\n");

                    System.out.println(dataController.inventoryReportController(categories));

                    break;
                case 3: //Generate a defective products report
                    System.out.println("Defective Report:");
                    System.out.println(dataController.defectReportControl());
                    break;
                case 4: //Show Product Purchase Price
                    try{
                        System.out.println("Enter product ID: ");
                    productID = scan.nextInt();
                    scan.nextLine();
                    double salePrice = dataController.getProductPurchasePrice(productID);
                    System.out.println("Product " + productID + " was sold for " + salePrice);
                    }
                    catch (NullPointerException e){
                        System.out.println("This Product ID was not purchased before.");
                    }
                    break;
                case 5: //Update Price
                    try {
                        System.out.println("Enter product ID: ");
                        productID = scan.nextInt();
                        scan.nextLine();
                        System.out.println("New price: ");
                        double newPrice = scan.nextDouble();
                        scan.nextLine();
                        dataController.updatePriceController(productID, newPrice);
                    }
                    catch (NullPointerException e){
                        System.out.println("This Product ID not in stock.");
                    }
                    break;
                case 6: //Add product
                    boolean validCatalogNum = false;
                    while(!validCatalogNum) {
                        String productDetails = getProductDetails(); //from user
                        validCatalogNum = dataController.addProductController(productDetails);
                        if(!validCatalogNum){
                            System.out.println("Invalid Catalog Number. Insert product details again.");
                        }
                    }
                    break;
                case 7: //remove product
                    try {
                        System.out.println("Enter product ID: ");
                        productID = scan.nextInt();
                        scan.nextLine();
                        while (choice != 1 && choice != 2) {
                            System.out.println("(1)Purchase\n(2)Defect");
                            choice = scan.nextInt();
                            scan.nextLine();
                            if (choice == 1) { //Purchase
                                System.out.println("Sale price: ");
                                double price = scan.nextDouble();
                                scan.nextLine();
                                boolean alert = dataController.checkForAlert(productID);
                                //check if need alert
                                if (alert) {
                                    System.out.println("ALERT!!! " + dataController.getProductName(productID) + " has reached critical amount. Please order new supply.");
                                }
                                dataController.handlePurchaseProduct(productID, price);
                            } else if (choice == 2) { //Defect
                                //check if need alert
                                boolean alert = dataController.checkForAlert(productID);
                                if (alert) {
                                    System.out.println("ALERT!!! " + dataController.getProductName(productID) + " has reached critical amount. Please order new supply");
                                }
                                dataController.handleDefectProduct(productID);
                            } else {
                                System.out.println("Invalid choice. Please enter 1 or 2");
                            }
                        }
                    }
                    catch (NullPointerException e){
                        System.out.println("This Product ID not in stock.");
                    }
                    break;
                case 8: //Mark product as defect
                    try {
                        System.out.println("Enter product ID: ");
                        productID = scan.nextInt();
                        scan.nextLine();
                        dataController.markDefect(productID);
                        break;
                    }
                    catch (NullPointerException e){
                        System.out.println("This Product ID not in stock.");
                    }
                case 9: //Show Product Details
                    try {
                        System.out.println("Enter product ID: ");
                        productID = scan.nextInt();
                        scan.nextLine();
                        String details = dataController.productsIDdetails(productID);
                        System.out.println(details);
                        break;
                    }
                    catch (NullPointerException e){
                    System.out.println("This Product ID not in stock.");
                }
                case 10: //exit
                    break;
                default:
                    break;
            }
        }
         */

    }

    public static void generateInventoryReport(){

    }
    public static void generateDefectivesReport(){

    }

    public static void addItem() throws SQLException {
        JsonObject item_json = getItemDetails();
        system.addItem(item_json);
    }
    public static void removeItem(){


    }
    public static void markAsDefective(){

    }
    public static void showItem(){

    }

    public static void discountByCategory(){

    }
    public static void discountByProduct(){

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

        System.out.print("Item location:(WareHouse = 0, Interior = 1)");
        int item_Loc = scan.nextInt();
        scan.nextLine();
        Location loc = Location.WareHouse;
        /*if(item_Loc == 0){
            loc = Location.WareHouse;
        } else if (item_Loc == 1){
            loc = Location.Interior;
        }
        else {
            System.out.println("Error");
        }*/
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
        String catalog_number = scan.nextLine();
        json.addProperty("catalog_number", catalog_number);

        return json;

    }

    public static String getPathFromConfig(){
        Yaml yaml = new Yaml();
        String path = "";
        try (InputStream inputStream = menu.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found! " + "config.yaml");
            } else {
                // Parse the YAML file
                Map<String, Object> config = yaml.load(inputStream);
                // Access the 'path' value
                path = (String) config.get("path");
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;

    }

}
