package Domain;

public class WorkConditions {
    SystemDate startDate;
    Worker directManager;
    private java.lang.String workType;
    private java.lang.String department;
    private int salary;
    private int globalSalary; // TODO : NEEDED?


    // Constructor
    public WorkConditions() {
       // TODO: CHANGE IT
    /*    this.startDate = startDate;
        this.directManager = directManager;
        this.workType = workType;
        this.department = department;
        this.salary = salary;
        this.globalSalary = globalSalary;*/
    }

    // Getters & Setters
    public Worker getDirectManager() {
        return directManager;
    }

    public void setDirectManager(Worker directManager) {
        this.directManager = directManager;
    }

    public java.lang.String getWorkType() {
        return workType;
    }

    public void setWorkType(java.lang.String workType) {
        this.workType = workType;
    }

    public java.lang.String getDepartment() {
        return department;
    }

    public void setDepartment(java.lang.String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getGlobalSalary() {
        return globalSalary;
    }

    public void setGlobalSalary(int globalSalary) {
        this.globalSalary = globalSalary;
    }

    @Override
    public java.lang.String toString() {
        return "WorkConditions{" +
                "startDate=" + startDate +
                ", directManager=" + directManager +
                ", workType='" + workType + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", globalSalary=" + globalSalary +
                '}';
    }
}
