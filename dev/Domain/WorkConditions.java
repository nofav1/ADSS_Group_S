package Domain;

import com.google.gson.Gson;

public class WorkConditions {
    String startDate;
    String directManager;
    private String workType;
    private double salary;


    // Constructor
    public WorkConditions(String startDate, String directManager, String workType, double salary) {
        this.startDate = startDate;
        this.directManager = directManager;
        this.workType = workType;
        this.salary = salary;
    }

    public WorkConditions(String workConditions) {
        // Conversion
        Gson gson = new Gson();
        WorkConditions workCondition = gson.fromJson(workConditions, WorkConditions.class);
        // INIT THIS.values
        this.startDate = workCondition.getStartDate();
        this.directManager = workCondition.getDirectManager();
        this.workType = workCondition.getWorkType();
        this.salary = workCondition.getSalary();
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    // Convert to JSON string
    public String conditionsToJSON() {
        String result;

        // Conversion
        Gson gson = new Gson();
        result = gson.toJson(this);

        return result;
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
