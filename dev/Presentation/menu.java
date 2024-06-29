package Presentation;
import Data.*;

import java.util.Scanner;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class menu {
    public static Scanner scan;
    public static void main(String[] args) {
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

        }

    public static String getProductDetails(){ //get all product details from user
        System.out.print("Product ID: ");
        int pID = scan.nextInt();
        scan.nextLine();

        System.out.print("Product name: ");
        String pName = scan.nextLine();

        System.out.print("Product expiring date: ");
        String pExpD = scan.nextLine();

        System.out.print("Product location: ");
        String pLoc = scan.nextLine();

        System.out.print("Product section: ");
        String pSection = scan.nextLine();

        System.out.print("Product catalogNum: ");
        int pCatalogNum = scan.nextInt();
        scan.nextLine();
        System.out.print("Product category: ");
        String pCategory = scan.nextLine();

        System.out.print("Product sub-category: ");
        String pSubCategory = scan.nextLine();

        System.out.print("Product size: ");
        int pSize = scan.nextInt();

        System.out.print("Product cost: ");
        double pCost = scan.nextDouble();

        System.out.print("Product demand: ");
        int pDemand = scan.nextInt();

        System.out.print("Product supply Time: ");
        int pSupplyTime = scan.nextInt();

        scan.nextLine();
        System.out.print("Product manufacturer: ");
        String pManufacturer = scan.nextLine();

        System.out.print("Product supplier Discount: ");
        int pSupplierDis = scan.nextInt();

        System.out.print("Product store Discount: ");
        int pStoreDis = scan.nextInt();

        return pID + "," + pName + "," + pExpD + "," + pLoc + "," + pSection + ","
                + pCatalogNum + "," + pCategory + "," + pSubCategory + "," + pSize + ","
                + pCost + "," + pDemand + "," + pSupplyTime + ","
                + pManufacturer + "," + pSupplierDis + "," + pStoreDis;

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
