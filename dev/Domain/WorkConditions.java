package Domain;

public class WorkConditions {
    String startDate;
    String directManager;
    private String workType;
    private double salary;


    // Constructor
    public WorkConditions( String startDate, String directManager, String workType, double salary) {
        // TODO: CHANGE IT
        this.startDate = startDate;
        this.directManager = directManager;
        this.workType = workType;
        this.salary = salary;
    }

    // Getters & Setters
    public String getDirectManager() {
        return directManager;
    }

    public void setDirectManager(String directManager) {
        this.directManager = directManager;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "WorkConditions{" +
                "startDate=" + startDate +
                ", directManager=" + directManager +
                ", workType='" + workType + '\'' +
                ", salary=" + salary +
                '}';
    }
}
