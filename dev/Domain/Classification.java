package Domain;

public class Classification {
    private int catalogNum;
    private String category;
    private String subcategory;
    private int size;
    private double costPrice;
    private int demand; //rate - 1 to 10
    private int supplyTime; //days
    private int minAmountForAlert; //derived from supplyTime and demand
    private String manufacturer;
    private int currentAmount;
    private int supplierDis; //default 0
    private int storeDis; //default 0
    private double salePrice; //default -1

    public Classification() {
        this.currentAmount = 0;
        this.storeDis = 0;
        this.supplierDis = 0;
        this.salePrice = -1;
    }

    public void setCatalogNum(int catalogNum) {
        this.catalogNum = catalogNum;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public void setSupplyTime(int supplyTime) {
        this.supplyTime = supplyTime;
    }

    public void setMinAmountForAlert(int minAmountForAlert) {
        this.minAmountForAlert = minAmountForAlert;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setSupplierDis(int supplierDis) {
        this.supplierDis = supplierDis;
    }

    public void setStoreDis(int storeDis) {
        this.storeDis = storeDis;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getCatalogNum() {
        return catalogNum;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public int getSize() {
        return size;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public int getDemand() {
        return demand;
    }

    public int getSupplyTime() {
        return supplyTime;
    }

    public int getMinAmountForAlert() {
        return minAmountForAlert;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getSupplierDis() {
        return supplierDis;
    }

    public int getStoreDis() {
        return storeDis;
    }

    public double getSalePrice() {
        return salePrice;
    }
}
