package Presentation;

import Service.*;

import java.util.Calendar;
import java.util.Scanner;

public class CreateWorkerScreen {
    WorkerController workerController;
    RoleController roleController;

    public CreateWorkerScreen() {
        workerController = new WorkerController();
        roleController = new RoleController();
        createProcess();
    }


    private void createProcess() {
        Scanner scanner = new Scanner(System.in);
        String id;
        System.out.println("Creating Worker Screen\n--------------------" +
                "\nEnter new worker ID(NUMBERS ONLY):");
        // If not numbers only, exit screen.
        try {
            id = String.valueOf(scanner.nextInt());
            // Exit if it is already existing worker.
            if (workerController.getWorker(id) != null) {
                System.out.println("Workers already exist");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid Worker ID");
            return;
        }

        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Bank Account:");
        String bankAccount = scanner.next();
        String role = null;
        while (roleController.getRole(role) == null) {
            System.out.println("Role:");
            role = scanner.next();
            if (roleController.getRole(role) == null) System.out.println("Role not found. Try again.");
        }
        String password = "";
        System.out.println("dev.Presentation.Main branch:");
        String branch = scanner.next();


        // Work Condition
        System.out.println("Work Conditions:\n-----------------");
        String today = Calendar.getInstance().getTime().toString();
        System.out.println("Worker's direct manager name:");
        String director = scanner.next();
        String workType = "";
        while (!workType.equals("Full") && !workType.equals("Half")) {
            System.out.println("Worker's work type(Full/Half):");
            workType = scanner.next();
        }
        System.out.println("Salary:");
        double salary = scanner.nextDouble();
        workerController.createWorker(id, name, bankAccount, today, director, workType, salary, roleController.getRole(role), password, branch);

    }


}
