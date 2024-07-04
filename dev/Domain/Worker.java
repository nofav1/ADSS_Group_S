package Domain;

import java.util.ArrayList;

public class Worker {
    private String ID;
    private String name;
    private String bankAccount;
    private WorkConditions workConditions;
    private Role role;
    private String password;
    private String branch;
    private boolean isManager;
    private boolean isActive;

    //Constructor
    public Worker(String ID, String name, String bankAccount, WorkConditions workConditions, Role role, String password, String branch) {
        this.ID = ID;
        this.name = name;
        this.bankAccount = bankAccount;
        this.workConditions = workConditions;
        this.role = role;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public WorkConditions getWorkConditions() {
        return workConditions;
    }

    public void setWorkConditions(WorkConditions workConditions) {
        this.workConditions = workConditions;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getID() {
        return ID;
    }
// Other methods...

    // Add New Role To Worker
    public void updateRole(Role role) {
        // Each role only once.
        if (role != null) this.role = role;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Worker: " + name);
        res.append(" Role: " + role);
        return res.toString();
    }

    // Equals by ID
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Worker workerObj)) return false;
        return this.ID.equals(workerObj.ID);
    }
}
