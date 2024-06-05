package Service;
import Domain.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataController {
    private HashMap<Integer,Product> products; //saves all current products in store. Key: product ID, Value: object Product
    private HashMap<Integer,Product> purchaseProducts; //saves all the purchase products
    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> productsAmountMapByCategory; //saves all products amount in format: Map<category, Map<sub-category,Map<size, Map<location, amount>>>> (location- wareHouse, interiorStore)
    private Set<Integer> catalogNumSet; //saves all the catalogs number we have in store

    public DataController(){
        products = new HashMap<>();
        purchaseProducts = new HashMap<>();
        productsAmountMapByCategory = new HashMap<>();
        catalogNumSet = new HashSet<>();
    }
    public void ImportData(String path){
        // import CSV file path

        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextRecord; //array: int pID, String pName, String pExpD, String pLoc, String pSection, int pCatalogNum,String pCategory, String pSubCategory, int pSize, double pCost, int pDemand, int pSupplyTime,int pMinAmountForAlert, String pManufacturer, int pSupplierDis, int pStoreDis
            while ((nextRecord = reader.readNext()) != null) { //reading record by record
                Product product = new Product();
                String inputID = nextRecord[0];
                if (inputID.startsWith("\uFEFF")) { //remove useless chars from csv file
                    inputID = inputID.substring(1);
                }
                inputID = inputID.trim();
                nextRecord[0] = inputID;
                setProductDetails(product, nextRecord); //build product
                catalogNumSet.add(product.getC().getCatalogNum()); //add catalog number to catalog set
                products.put(product.getId(), product);

                //add to productsAmountMap
                String category = product.getC().getCategory();
                String subCategory = product.getC().getSubcategory();
                String size = String.valueOf(product.getC().getSize());
                String location = product.getStored(); //interiorStore or wareHouse

                changeProductToCountMap(true, category, subCategory,size, location);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

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
            if(location.equals("wareHouse")){
                locMap.put("interiorStore",0); //initiate interior store with 0
            }
            else{
                locMap.put("wareHouse",0); //initiate wareHouse with 0
            }
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
        classification.setMinAmountForAlert((int)(0.5*classification.getDemand() + 0.5*classification.getSupplyTime()));
        classification.setManufacturer(details[12]);
        classification.setSupplierDis(Integer.parseInt(details[13]));
        classification.setStoreDis(Integer.parseInt(details[14]));

        product.setC(classification);
    }

    //return true - when product is valid and added to dataBase.
    //return false - the product's catalog number is wrong
    public boolean addProductController(String productDetails) {
        Product product = new Product();
        String[] detailsArray = productDetails.split(",");
        setProductDetails(product, detailsArray);
        //checks that catalog number is valid
        if (productsAmountMapByCategory.containsKey(product.getC().getCategory()) &&
            productsAmountMapByCategory.get(product.getC().getCategory()).containsKey(product.getC().getSubcategory()) &&
                productsAmountMapByCategory.get(product.getC().getCategory()).get(product.getC().getSubcategory()).containsKey(String.valueOf(product.getC().getSize()))) {

            if (catalogNumSet.contains(product.getC().getCatalogNum())) { //checks if catalog number is exist
                products.put(product.getId(), product);
                changeProductToCountMap(true, product.getC().getCategory(), product.getC().getSubcategory(), String.valueOf(product.getC().getSize()), product.getStored());
                return true;
            }
            else{ //catalog number is wrong
                return false;
            }
        }
        else{ //new kind of product
            if(!catalogNumSet.contains(product.getC().getCatalogNum())){ //if catalog number is not exist in set, add product
                products.put(product.getId(), product);
                changeProductToCountMap(true, product.getC().getCategory(), product.getC().getSubcategory(), String.valueOf(product.getC().getSize()), product.getStored());
                return true;
            }
            else{
                return false;
            }
        }
    }

    public void markDefect(int productID){
        products.get(productID).setDefect(true);
    }

    public void handlePurchaseProduct(int productID, double salePrice){
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

    public void updatePriceController(int productID, double newPrice){
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

    public String productsIDdetails(int productID){
        String details = "";
        Product p = products.get(productID);
        details = "Product ID: " + p.getId() + ", Name: " + p.getName() + "\n" + "Expiring Date: " + p.getExpiring_date() + "\nLocation: "
                + p.getStored() + ", Section: " + p.getSection() + "\nCatalog Number: "
                + p.getC().getCatalogNum() + ", Category: " + p.getC().getCategory() + ", Sub-Category: " + p.getC().getSubcategory() +
                ", Size: " + p.getC().getSize() + "\nCost: "
                + p.getC().getCostPrice() + "\nDemand: " + p.getC().getDemand() + "\nSupplyTime: " + p.getC().getSupplyTime() +
                "\nMinimun Time For Alert: " + p.getC().getMinAmountForAlert() + "\nManufacturer: "
                + p.getC().getManufacturer() + "\nSupplier Discount: " + p.getC().getSupplierDis() + "\nStore Discount: " + p.getC().getStoreDis() + "\n";

        return details;
    }

    public boolean checkForAlert(int productID){
        Product p = products.get(productID);
        int wAmount = productsAmountMapByCategory.get(p.getC().getCategory()).get(p.getC().getSubcategory()).get(String.valueOf(p.getC().getSize())).get("wareHouse");
        int interiorAmount = productsAmountMapByCategory.get(p.getC().getCategory()).get(p.getC().getSubcategory()).get(String.valueOf(p.getC().getSize())).get("interiorStore");
        int currentAmount = wAmount + interiorAmount - 1; //remove current product from amount
        if(currentAmount <= p.getC().getMinAmountForAlert()){
            return true;
        }
        return false;
    }

    public String getProductName(int productID){
        return products.get(productID).getName();
    }

    public void setDiscountForCategory(String category, int discount){
        for(Product p : products.values()){
            if(p.getC().getCategory().equals(category)){
                p.getC().setStoreDis(discount);
            }
        }
    }

    public void setDiscountForSubCategory(String sub_category, int discount){
        for(Product p : products.values()){
            if(p.getC().getSubcategory().equals(sub_category)){
                p.getC().setStoreDis(discount);
            }
        }
    }

    public void setDiscountForCatalogNum(int cNum, int discount){
        for(Product p : products.values()){
            if(p.getC().getCatalogNum() == cNum){
                p.getC().setStoreDis(discount);
            }
        }
    }

    public double getProductPurchasePrice(int productID){
        Product p = purchaseProducts.get(productID);
        return purchaseProducts.get(productID).getC().getSalePrice();
    }
}
