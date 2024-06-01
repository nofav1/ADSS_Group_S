package Presentation;
import Service.*;

import java.util.Scanner;

public class menu {
    public static Scanner scan;
    public static void main(String[] args) {
        DataController dataController = new DataController(); //service
        dataController.ImportData();
        int choice = 0;
        scan = new Scanner(System.in);
        while (choice != 9) {
            String menu = "Menu:\n1.Show all products in stock" +
                    "\n2.Generate an inventory report by category" +
                    "\n3.Generate a defective products report" +
                    "\n4.Product catalog number details" +
                    "\n5.Update Price" +
                    "\n6.Add Product" +
                    "\n7.Remove Product" +
                    "\n8.Mark product as defect" +
                    "\n9.Show Product Details" +
                    "\n10.Exit";

            System.out.println(menu);

            choice = scan.nextInt();
            if(choice!=9){scan.nextLine();}

            int productID;

            switch (choice) {
                case 1:
                    //ShowAllProducts();
                    break;
                case 2: //Generate an inventory report by category
                    System.out.println("Which categories?");
                    String stringCategories = scan.nextLine(); //assume that the user writes the categories in this format: "Cat1 Cat2 Cat3..."
                    String[] categories = stringCategories.split(" ");

                    //dataController.getTotalProductsAmount();

                    System.out.println("Inventory Report\n");
                    /*System.out.println("Total Products In Store Amount: ");
                    //לשלוף את הכמות
                    System.out.println("Inner Amount - ");
                    //כמות*/

                    System.out.println(dataController.inventoryReportController(categories));

                    break;
                case 3: //Generate a defective products report
                    System.out.println("Defective Report:");
                    System.out.println(dataController.defectReportControl());
                    break;
                case 4:
                    System.out.println("You chose Cherry");
                    break;
                case 5: //Update Price
                    System.out.println("Enter product ID: ");
                    productID = scan.nextInt();
                    scan.nextLine();
                    System.out.println("New price: ");
                    int newPrice = scan.nextInt();
                    scan.nextLine();
                    dataController.updatePriceController(productID, newPrice);
                    break;
                case 6: //Add product
                    String productDetails = getProductDetails(); //from user
                    dataController.addProductController(productDetails);
                    break;
                case 7: //remove product
                    System.out.println("Enter product ID: ");
                    productID = scan.nextInt();
                    scan.nextLine();
                    while(choice != 1 && choice != 2){
                        System.out.println("(1)Purchase\n(2)Defect");
                        choice = scan.nextInt();
                        scan.nextLine();
                        if (choice == 1) { //Purchase
                            System.out.println("Sale price: ");
                            int price = scan.nextInt();
                            scan.nextLine();
                            dataController.handlePurchaseProduct(productID, price);
                        } else if (choice == 2) { //Defect
                            dataController.handleDefectProduct(productID);
                        } else {
                            System.out.println("Invalid choice. Please enter 1 or 2");
                        }
                    }
                    break;
                case 8: //Mark product as defect
                    System.out.println("Enter product ID: ");
                    productID = scan.nextInt();
                    scan.nextLine();
                    dataController.markDefect(productID);
                    break;
                case 9:
                    break;
                case 10: //exit
                    break;
                default:

                    break;
            }
        }

        }

    public static String getProductDetails(){ //get all product details from user
        //Scanner scan = new Scanner(System.in);

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

        System.out.print("Product min Amount For Alert: ");
        int pMinAmountForAlert = scan.nextInt();
        scan.nextLine();
        System.out.print("Product manufacturer: ");
        String pManufacturer = scan.nextLine();

        System.out.print("Product supplier Discount: ");
        int pSupplierDis = scan.nextInt();

        System.out.print("Product store Discount: ");
        int pStoreDis = scan.nextInt();

        return pID + "," + pName + "," + pExpD + "," + pLoc + "," + pSection + ","
                + pCatalogNum + "," + pCategory + "," + pSubCategory + "," + pSize + ","
                + pCost + "," + pDemand + "," + pSupplyTime + "," + pMinAmountForAlert + ","
                + pManufacturer + "," + pSupplierDis + "," + pStoreDis;

    }

}
