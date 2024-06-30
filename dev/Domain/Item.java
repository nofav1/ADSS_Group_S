package Domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Date;

public class Item {
    private int item_id;
    private Date expiring_date;
    private char section;
    private Location location;
    private boolean isDefect;
    private int supplier_dis;
    private double costPrice;
    private double purchase_price;
    private Product product;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Date getExpiring_date() {
        return expiring_date;
    }

    public void setExpiring_date(Date expiring_date) {
        this.expiring_date = expiring_date;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isDefect() {
        return isDefect;
    }

    public void setDefect(boolean defect) {
        isDefect = defect;
    }

    public int getSupplir_dis() {
        return supplier_dis;
    }

    public void setSupplier_dis(int supplier_dis) {
        this.supplier_dis = supplier_dis;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(JsonObject product_json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert JsonObject to JsonNode
            JsonNode jsonNode = objectMapper.readTree(product_json.toString());
            // Convert JsonNode to Product
            Product product = objectMapper.treeToValue(jsonNode, Product.class);
            this.product = product;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
