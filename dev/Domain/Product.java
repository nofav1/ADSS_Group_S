package Domain;

import com.google.gson.Gson;

public class Product {
    private int pn; // Product number
    private String name;
    private int demand;
    private int supplyTime;
    private int minAmountForAlert;
    private String manufacturer;
    private int currentAmount;
    private Classification classification;
    private StoreDiscount storeDiscount;

    public Product(int productId) {
        pn = productId;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public int getSupplyTime() {
        return supplyTime;
    }

    public void setSupplyTime(int supplyTime) {
        this.supplyTime = supplyTime;
    }

    public int getMinAmountForAlert() {
        return minAmountForAlert;
    }

    public void setMinAmountForAlert(int minAmountForAlert) {
        this.minAmountForAlert = minAmountForAlert;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public StoreDiscount getStoreDiscount() {
        return storeDiscount;
    }

    public void setStoreDiscount(StoreDiscount storeDiscount) {
        this.storeDiscount = storeDiscount;
    }
}
