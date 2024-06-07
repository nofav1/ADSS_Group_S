package Presentation;

import Service.*;

import java.util.Scanner;

public class EditWorkerScreen {
    WorkerController workerController;
    RoleController roleController;

    public EditWorkerScreen() {
        workerController = new WorkerController();
        roleController = new RoleController();
        menu();
    }

    private void menu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.println("Edit Worker Screen\nChoose a worker:");
        // Choose worker to edit
        workersMenu(scanner);
        while (choice != 9) {
            System.out.println("What would you like to change?" +
                    "\n1)Name" +
                    "\n2)Bank Account" +
                    "\n3)Role" +
                    "\n4)Password" +
                    "\n5)dev.Presentation.Main branch" +
                    "\n6)Work Type" +
                    "\n7)Direct Manager" +
                    "\n8)Salary" +
                    "\n9)Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.next();
                    workerController.getSelectedWorker().setName(name);
                    break;
                case 2:
                    System.out.println("Enter Bank Account:");
                    String bankAccount = scanner.next();
                    workerController.getSelectedWorker().setBankAccount(bankAccount);
                    break;
                case 3:
                    String role="";
                    while(roleController.getRole(role)==null){
                        System.out.println("Enter Role:");
                        role = scanner.next();
                        if (roleController.getRole(role)==null) System.out.println("No role found. Please try again");
                    }
                    if (roleController.getRole(role) != null) ;
                    // Add the new role to the worker
                    workerController.getSelectedWorker().getRoles().add(roleController.getRole(role));
                    break;
                case 4:
                    System.out.println("Enter Password:");
                    String password = scanner.next();
                    workerController.getSelectedWorker().setPassword(password);
                    break;
                case 5:
                    System.out.println("Enter dev.Presentation.Main branch:");
                    String branch = scanner.next();
                    workerController.getSelectedWorker().setBranch(branch);
                    break;
                case 6:
                    System.out.println("Enter Work Type:");
                    String workType = scanner.next();
                    workerController.getSelectedWorker().getWorkConditions().setWorkType(workType);
                    break;
                case 7:
                    System.out.println("Enter Direct Manager:");
                    String direct = scanner.next();
                    workerController.getSelectedWorker().getWorkConditions().setDirectManager(direct);
                    break;
                case 8:
                    System.out.println("Enter Salary:");
                    double salary = scanner.nextDouble();
                    workerController.getSelectedWorker().getWorkConditions().setSalary(salary);
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void workersMenu(Scanner scanner) {
        for (int i = 0; i < workerController.getAllWorkers().size(); i++) {
            System.out.println(i + 1 + ")" + "ID: " + workerController.getAllWorkers().get(i).getID() + "Name: " + workerController.getAllWorkers().get(i).getName());
        }
        int i = scanner.nextInt();
        workerController.selectWorker(workerController.getAllWorkers().get(i - 1));

    }

}

