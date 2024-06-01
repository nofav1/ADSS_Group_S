package Domain;

import java.util.ArrayList;

public class Worker {
    private int ID;
    private String name;
    private int bankAccount;
    private WorkConditions workConditions;
    private ArrayList<Role> roles;
    private String password;
    private String branch;
    private boolean isManager;
    private boolean isActive;

    //Constructor
    public Worker(int ID, String name, int bankAccount, WorkConditions workConditions, ArrayList<Role> roles, String password, String branch) {
        this.ID = ID;
        this.name = name;
        this.bankAccount = bankAccount;
        this.workConditions = workConditions;
        this.roles = roles;
        this.password = password;
        this.branch = branch;

        // New Worker always is not manager, and yes active.
        this.isManager = false;
        this.isActive = true;
    }


    /// Getters & Setters ///
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public WorkConditions getWorkConditions() {
        return workConditions;
    }

    public void setWorkConditions(WorkConditions workConditions) {
        this.workConditions = workConditions;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Other methods...

    // Add New Role To Worker
    public void addRole(Role role) {
        // Each role only once.
        if (role != null && !roles.contains(role)) this.roles.add(role);
    }
    // Removes Existing Role To Worker
    public void removeRole(Role role) {
        if (role != null && roles.contains(role)) {
            this.roles.remove(role);
        }
    }


}
