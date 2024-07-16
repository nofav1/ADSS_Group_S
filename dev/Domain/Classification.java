package Domain;

public class Classification {
    private int catalog_num;
    private String category;
    private String subcategory;
    private int size;

    public int getCatalog_num() {
        return catalog_num;
    }

    public void setCatalog_num(int catalog_num) {
        this.catalog_num = catalog_num;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
