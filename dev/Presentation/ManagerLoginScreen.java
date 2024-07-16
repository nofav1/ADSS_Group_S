package Presentation;

import Service.*;

import java.util.Scanner;

public class ManagerLoginScreen {
    WorkerController workerController;
    private final ArrangementController arrangementController;
    private final RoleController roleController;
    private final ConstraintController constraintController;
    String password;

    public ManagerLoginScreen(WorkerController workerController, ArrangementController arrangementController, RoleController roleController, ConstraintController constraintController) {
        this.workerController = workerController;
        this.arrangementController = arrangementController;
        this.roleController = roleController;
        this.constraintController = constraintController;
        askForPassword();
    }

    public void askForPassword() {
        Scanner in = new Scanner(System.in);
        // Safety null check
        if (workerController.getCurrWorker() == null) return;

        System.out.println("Hello " + workerController.getCurrWorker().getName() + "!\nEnter password:");
        try {
            password = String.valueOf(in.nextInt());
        } catch (Exception e) {
            return;
        }
        // same password as currWorker password?
        if (workerController.getCurrWorker().getPassword().equals(password)) {
            new ManagerScreen(workerController,arrangementController,roleController,constraintController );
        } else {
            System.out.println("Wrong password. Try again later.");
        }
    }
}
