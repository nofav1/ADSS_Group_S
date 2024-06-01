package Domain;

public class Product {
    private int id;
    private String name;
    private String expiring_date;
    private String stored;
    private String section;
    private boolean isDefect;
    private Classification c;

    public Product(){
        isDefect = false;
    }

    public String getStored() {
        return stored;
    }

    public void setStored(String stored) {
        this.stored = stored;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiring_date(String expiring_date) {
        this.expiring_date = expiring_date;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setC(Classification c) {
        this.c = c;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExpiring_date() {
        return expiring_date;
    }

    public String getSection() {
        return section;
    }

    public Classification getC() {
        return c;
    }

    public void setDefect(boolean defect) {
        isDefect = defect;
    }

    public boolean isDefect() {
        return isDefect;
    }
}
