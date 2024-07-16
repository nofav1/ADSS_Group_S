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
        inflateWorkersMenu(scanner);
        while (choice != 11) {
            System.out.println("What would you like to change?" +
                    "\n1)Name" +
                    "\n2)Bank Account" +
                    "\n3)Role" +
                    "\n4)Password" +
                    "\n5)branch" +
                    "\n6)Work Type" +
                    "\n7)Direct Manager" +
                    "\n8)Salary" +
                    "\n9)Fire/Unfire" +
                    "\n10)Make/Remove manager" +
                    "\n11)Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.next();
                    workerController.getSelectedWorker().setName(name); // set new name
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB
                    break;
                case 2:
                    System.out.println("Enter Bank Account:");
                    String bankAccount = scanner.next();
                    workerController.getSelectedWorker().setBankAccount(bankAccount);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB
                    break;
                case 3:
                    String role = "";
                    while (roleController.getRoles().get(role) == null) {
                        System.out.println("Enter Role:");
                        role = scanner.next();
                        if (roleController.getRoles().get(role) == null)
                            System.out.println("No role found. Please try again");
                    }
                    if (roleController.getRoles().get(role) != null) ;
                    // Add the new role to the worker
                    workerController.getSelectedWorker().updateRole(roleController.getRole(role));
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB

                    break;
                case 4:
                    System.out.println("Enter Password:");
                    String password = scanner.next();
                    workerController.getSelectedWorker().setPassword(password);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB

                    break;
                case 5:
                    System.out.println("Enter branch:");
                    String branch = scanner.next();
                    workerController.getSelectedWorker().setBranch(branch);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB

                    break;
                case 6:
                    System.out.println("Enter Work Type:");
                    String workType = scanner.next();
                    workerController.getSelectedWorker().getWorkConditions().setWorkType(workType);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB
                    break;
                case 7:
                    System.out.println("Enter Direct Manager:");
                    String direct = scanner.next();
                    workerController.getSelectedWorker().getWorkConditions().setDirectManager(direct);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB
                    break;
                case 8:
                    System.out.println("Enter Salary:");
                    double salary = scanner.nextDouble();
                    workerController.getSelectedWorker().getWorkConditions().setSalary(salary);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB

                    break;
                case 9:
                    boolean active = workerController.getSelectedWorker().isActive();

                    workerController.getSelectedWorker().setActive(!active);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB

                    break;
                case 10:
                    boolean isManager = workerController.getSelectedWorker().isManager();

                    // Choose password for manager - MUST!
                    System.out.println("Choose password:\n");
                    String pass = scanner.next();

                    // change worker fields
                    workerController.getSelectedWorker().setPassword(pass);
                    workerController.getSelectedWorker().setManager(!isManager);
                    workerController.updateWorker(workerController.getSelectedWorker()); // change DB
                    break;
                case 11:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void inflateWorkersMenu(Scanner scanner) {
        for (int i = 0; i < workerController.getAllWorkers().size(); i++) {
            System.out.println(i + 1 + ")" + "ID: " + workerController.getAllWorkers().get(i).getID() + "Name: " + workerController.getAllWorkers().get(i).getName());
        }
        int i = scanner.nextInt();
        workerController.selectWorker(workerController.getAllWorkers().get(i - 1));

    }

}

