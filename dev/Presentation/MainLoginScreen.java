package Presentation;

import Domain.Arrangement;
import Domain.Constraint;
import Domain.SystemDate;
import Service.*;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainLoginScreen {
    private final Scanner scan = new Scanner(System.in);
    private final WorkerController workerController;
    private final ArrangementController arrangementController;
    private final ConstraintController constraintController;
    private final RoleController roleController;
    private boolean isLoadedData = false;


    public MainLoginScreen() {
        // DB init
        workerController = new WorkerController();
        arrangementController = new ArrangementController();
        constraintController = new ConstraintController();
        roleController = new RoleController();

        // load data from configuration file into the system.
        getPathFromConfig();

        // tests
        unitTests();

        // The system run
        while (mainLoginScreen() != 0) {
            if (workerController.getCurrWorker() != null) mainScreen();
        }
    }


    private int mainLoginScreen() {
        int choice;
        System.out.println("Welcome to S-Group Super-Market Workers Management System\n-----------------------------------------------------------" +
                "\nTo continue you need to login\nPlease type your id, or type 0 to exit:");
        while (true) {
            choice = this.scan.nextInt();
            if (choice == 0) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            }

            // Find the worker in the worker's list.
            workerController.setCurrWorker(workerController.getWorker(String.valueOf(choice)));
            if (workerController.getCurrWorker() == null) {
                System.out.println("No worker found. Please try again or type 0 to exit.");
            } else {
                // TODO: Add password feature for every USER (if he has one).
                System.out.println("Successfully logged in! Moving to Presentation.Main screen");
                break;
            }
        }
        return choice;
    }

    private void mainScreen() {
        int choose = -1;
        while (choose != 6) {
            System.out.println("Presentation.Main Workers Screen.\nPlease Choose An Option:" +
                    "\n1)Show Weekly Arrangement" +
                    "\n2)Show Today's Shift" +
                    "\n3)Constraints Weekly Submission" +
                    "\n4)Manager Screen" +
                    "\n5)Load Fake Data" +
                    "\n6)Exit");
            choose = scan.nextInt();
            switch (choose) {
                case 1:
                    // There are at least 2 arrangements.
                    // Get the 2 from the end. its current week.
                    System.out.println(arrangementController.getArrangements().values().stream().toList().get(arrangementController.getArrangements().values().size() - 2));
                    break;
                case 2:
                    System.out.println("Feature not developed yet.");
//                    System.out.println(arrangementController.getCurrArrangement().getTodayShift());
                    break;
                case 3:
                    // Only if the day
                    if (ConstraintController.LAST_DAY > SystemDate.getTodayDate())
                        new ConstraintsScreen(arrangementController, workerController, constraintController);
                    else System.out.println("Last day to submit constraints have passed already. contact a manager");
                    break;
                case 4:
                    // Send the user to ManagerLoginScreen only if his Worker is Manager.
                    if (workerController.getCurrWorker().isManager()) {
                        new ManagerLoginScreen(workerController, arrangementController, roleController, constraintController);
                    } else System.out.println("You're not a Manager!");
                    break;
                case 5:
                    // Load only once.
                    if (!isLoadedData) {

                        isLoadedData = true;
                        // Workers & Roles
                        workerController.loadFakeData();
                        roleController.loadFakeData();
                    }
                    break;
                case 6:
                    workerController.setCurrWorker(null);
                    System.out.println("Exiting System.. Good Bye!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Wrong Option!");
                    break;


            }
        }
    }


    public int getPathFromConfig() {
        Yaml yaml = new Yaml();
        int path = -1;
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.yaml")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found! " + "config.yaml");
            } else {
                // Parse the YAML file
                Map<String, Object> config = yaml.load(inputStream);
                // Access the 'path' value
                path = (int) config.get("LAST_DAY");

                ConstraintController.setLastDay(path); // SET last day for submitting constraints.
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;

    }


    // Unit tests for Domain & Database -  CRUD.
    private boolean unitTests() {
        // Login with worker successfully and exit
        // Test 1 - Work DB IS Ready.
        if (workerController.getAllWorkers().isEmpty()) return false;
        roleController.loadFakeData();
        // Test 2 - Roles DB Is Ready.
        if (roleController.getRoles().isEmpty()) return false;
        // Test 3 Arrangement creation into DB
        arrangementController.createGetArrangement();
//        logger.log(Level.ALL, String.valueOf(arrangementController.getArrangements().size()));
        if (arrangementController.getArrangements().isEmpty()) return false;

        // Test 4 - update worker
//        logger.log(Level.ALL,"Currently worker is a manager? : " + workerController.getAllWorkers().get(0).isManager());
        workerController.getAllWorkers().get(0).setManager(true);
        // update worker after changing it.
        workerController.updateWorker(workerController.getAllWorkers().get(0));
        if (!workerController.getAllWorkers().get(0).isManager()) return false;

        // Test 5 - delete role
        roleController.getRoles().remove("4");
        if (!(roleController.getRoles().get("4") == null)) return false;

        // Test 6 - Constraints LAST_DAY Is Updated from yaml
        if (ConstraintController.LAST_DAY == -1) return false;


        // Test 7 - Create constraint and save it in DB.
        constraintController.createConstraint(workerController.getAllWorkers().getFirst(), "Morning", "07/06/2024");
        if (!constraintController.getAllConstraints().isEmpty()) return false;

        // Test 8 - delete constraint from DB.
        constraintController.deleteConstraint(workerController.getAllWorkers().getFirst(), "Morning", "07/06/2024");
        if (!constraintController.getAllConstraints().isEmpty()) return false;

        // Test 9 - arrangements nonull, and is casted properly to the right type.

        if (!(arrangementController.getArrangements() instanceof HashMap<String, Arrangement>)) return false;

        // Test 10 - arrangements nonull, and is casted properly to the right type.

        if (!(constraintController.getAllConstraints() instanceof HashMap<String, Constraint>)) return false;

        return true;
    }
}
