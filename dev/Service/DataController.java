package Service;
import Domain.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataController {
    //private List<Product> products;
    private HashMap<Integer,Product> products; //saves all current products in store
    private HashMap<Integer,Product> purchaseProducts; //saves all the purchase products

    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> productsAmountMapByCategory; //saves all products amount in format: Map<category, Map<sub-category,Map<size, Map<location, amount>>>>

    //private HashMap<String, HashMap<String, Integer>> productsAmountMapBySubCategory;

    //private HashMap<String, Integer> productsAmountMapBySize;


    public DataController(){
        products = new HashMap<>();
        purchaseProducts = new HashMap<>();
        productsAmountMapByCategory = new HashMap<>();
        //productsAmountMapBySubCategory = new HashMap<>();
        //productsAmountMapBySize = new HashMap<>();
    }
    public void ImportData(){
        // import CSV file path
        String csvFilePath = "C:\\Users\\nofar\\OneDrive - post.bgu.ac.il\\semester D\\analysis and design of software systems\\nitutz_project\\ADSS_Group_S\\dev\\dataBase.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord; //array: int pID, String pName, String pExpD, String pLoc, String pSection, int pCatalogNum,String pCategory, String pSubCategory, int pSize, double pCost, int pDemand, int pSupplyTime,int pMinAmountForAlert, String pManufacturer, int pSupplierDis, int pStoreDis
            while ((nextRecord = reader.readNext()) != null) { //reading record by record
                System.out.println(nextRecord[0]+" , "+nextRecord[1]+" , "+nextRecord[2]+" , "+nextRecord[3]+" , "+nextRecord[4]+" , "+nextRecord[5]+" , "+nextRecord[6]+" , "+nextRecord[7]+" , "+nextRecord[8]+" , "+nextRecord[9]+" , "+nextRecord[10]+" , "+nextRecord[11]+" , "+nextRecord[12]+" , "+nextRecord[13]+" , "+nextRecord[14]+" , "+nextRecord[15]);
                Product product = new Product();
                String inputID = nextRecord[0];
                if (inputID.startsWith("\uFEFF")) { //remove useless chars from csv file
                    inputID = inputID.substring(1);
                }
                inputID = inputID.trim();
                nextRecord[0] = inputID;
                setProductDetails(product, nextRecord); //build product
                products.put(product.getId(), product);

                //add to productsAmountMap
                String category = product.getC().getCategory();
                String subCategory = product.getC().getSubcategory();
                String size = String.valueOf(product.getC().getSize());
                String location = product.getStored(); //interiorStore or wareHouse

                changeProductToCountMap(true, category, subCategory,size, location);
                /*// Check if category exists
                if (!productsAmountMapByCategory.containsKey(category)) {
                    productsAmountMapByCategory.put(category, new HashMap<>());
                }

                // Check if subCategory exists
                HashMap<String, HashMap<String, Integer>> subCategoryMap = productsAmountMapByCategory.get(category);
                if (!subCategoryMap.containsKey(subCategory)) {
                    subCategoryMap.put(subCategory, new HashMap<>());
                }

                // Check if size exists and update value
                HashMap<String, Integer> sizeMap = subCategoryMap.get(subCategory);
                sizeMap.put(size, sizeMap.getOrDefault(size, 0) + 1);*/


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        System.out.println("SHOW PRODUCTS SIZE LIST: ");
        System.out.println(products.size());
    }

    //add = true : increment by 1 in the catalog product location in the hash map
    //add = false : decrement by 1 in the catalog product location in the hash map
    public void changeProductToCountMap(boolean add, String category, String subCategory, String size, String location){
        // Check if category exists
        if (!productsAmountMapByCategory.containsKey(category)) {
            productsAmountMapByCategory.put(category, new HashMap<>());
        }

        // Check if subCategory exists
        HashMap<String, HashMap<String, HashMap<String, Integer>>> subCategoryMap = productsAmountMapByCategory.get(category);
        if (!subCategoryMap.containsKey(subCategory)) {
            subCategoryMap.put(subCategory, new HashMap<>());
        }

        // Check if size exists
        HashMap<String, HashMap<String, Integer>> sizeMap = subCategoryMap.get(subCategory);
        if (!sizeMap.containsKey(size)){
            sizeMap.put(size, new HashMap<>());
        }

        HashMap<String, Integer> locMap = sizeMap.get(size);
        if(add) { //increment
            locMap.put(location, locMap.getOrDefault(location, 0) + 1);
        }
        else{ //decrement
            locMap.put(location, locMap.getOrDefault(location, 0) - 1);
        }
    }
    public void setProductDetails(Product product, String[] details ){ //the array contains: int pID, String pName, String pExpD, String pLoc, String pSection, int pCatalogNum,String pCategory, String pSubCategory, int pSize, double pCost, int pDemand, int pSupplyTime,int pMinAmountForAlert, String pManufacturer, int pSupplierDis, int pStoreDis
        product.setId(Integer.parseInt(details[0]));
        product.setName(details[1]);
        product.setExpiring_date(details[2]);
        product.setStored(details[3]);
        product.setSection(details[4]);

        Classification classification = new Classification();
        classification.setCatalogNum(Integer.parseInt(details[5]));
        classification.setCategory(details[6]);
        classification.setSubcategory(details[7]);
        classification.setSize(Integer.parseInt(details[8]));
        classification.setCostPrice(Double.parseDouble(details[9]));
        classification.setDemand(Integer.parseInt(details[10]));
        classification.setSupplyTime(Integer.parseInt(details[11]));
        classification.setMinAmountForAlert(Integer.parseInt(details[12]));
        classification.setManufacturer(details[13]);
        classification.setSupplierDis(Integer.parseInt(details[14]));
        classification.setStoreDis(Integer.parseInt(details[15]));

        product.setC(classification);
    }
    public void addProductController(String productDetails){
        Product product = new Product();
        String[] detailsArray = productDetails.split(",");
        setProductDetails(product, detailsArray);
        products.put(product.getId(), product);

        changeProductToCountMap(true, product.getC().getCategory(), product.getC().getSubcategory(), String.valueOf(product.getC().getSize()), product.getStored());

        System.out.println(products.size()); //dont forget to delete
    }

    public void markDefect(int productID){
        products.get(productID).setDefect(true);
    }

    public void handlePurchaseProduct(int productID, int salePrice){
        Product p = products.get(productID);
        purchaseProducts.put(productID, p);
        purchaseProducts.get(productID).getC().setSalePrice(salePrice);
        products.remove(productID);
        changeProductToCountMap(false,p.getC().getCategory(), p.getC().getSubcategory(), String.valueOf(p.getC().getSize()), p.getStored()); //decrement by 1 in categories map
    }

    public void handleDefectProduct(int productID){
        Product p = products.get(productID);
        products.remove(productID);
        changeProductToCountMap(false,p.getC().getCategory(), p.getC().getSubcategory(), String.valueOf(p.getC().getSize()), p.getStored()); //decrement by 1 in categories map
    }

    public void updatePriceController(int productID, int newPrice){
        products.get(productID).getC().setCostPrice(newPrice);
    }

    public String defectReportControl(){
        String defectReport = "";
        int counter = 1;
        for (Product p : products.values()){
            if(p.isDefect()) {
                defectReport += counter + ". " + "Product ID: "+ p.getId() + ", Product Name: "+ p.getName() + ", Stored: " + p.getStored() + ", Section:" + p.getSection() + " , Category: " + p.getC().getCategory() + ", Sub-Category: " + p.getC().getSubcategory() + ", Size: " + p.getC().getSize() +"\n";
                counter++;
            }
        }
        if(counter == 1){defectReport = "No defective products";}
        return defectReport;
    }

    /*//returns the total products amount. (with no categories independent)
    //amounts[0] - All products in store
    //amounts[1] - All products in the inner store
    //amounts[2] - All products in the warehouse
    public int[] getTotalProductsAmount(){
        int[] amounts = new int[3];
        amounts[0] = this.products.size(); //total amount
        int[] amountResults = helperGetTotalProductsAmount(null);
        amounts[1] = amountResults[0];
        amounts[2] = amountResults[1];
        return amounts;
    }*/

    public String inventoryReportController(String[] categories){
        String report = "";
        // Iterate over the keys of the top-level map (categories)
        for (String category : categories) {
            System.out.println(category + ": " + productsAmountMapByCategory.get(category).size());
            for (String subCategory : productsAmountMapByCategory.get(category).keySet()){ //Iterate over sub-categories
                System.out.println("---" + subCategory + ": " + productsAmountMapByCategory.get(category).get(subCategory).size());
                for(String size : productsAmountMapByCategory.get(category).get(subCategory).keySet()){ //Iterate over sizes
                    System.out.println("------Size(" + size + "):" + " Store - " + productsAmountMapByCategory.get(category).get(subCategory).get(size).get("interiorStore")+
                            ", WareHouse - " + productsAmountMapByCategory.get(category).get(subCategory).get(size).get("wareHouse"));
                }
                System.out.println();
            }
            System.out.println("----------------------------------------------------------");
        }

        return report;
    }
}
