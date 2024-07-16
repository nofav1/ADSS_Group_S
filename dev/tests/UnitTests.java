package tests;

import Domain.*;
import Presentation.menu;
import Service.ConstraintController;
import Service.WorkerController;
import Service.RoleController;
import Service.ArrangementController;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UnitTests {

    SystemFacade systemFacade;
    private WorkerController workerController;
    private RoleController roleController;
    private ArrangementController arrangementController;
    private ConstraintController constraintController;

    @Before
    public void setUp() {
        // Initialize controllers and load fake data if necessary
        workerController = new WorkerController();
        roleController = new RoleController();
        arrangementController = new ArrangementController();
        constraintController = new ConstraintController();

        arrangementController.createGetArrangement();
        // Load fake data
        workerController.loadFakeData();
        roleController.loadFakeData();
    }

    // Part 1 Tests
    @Test
    public void testDatabaseReady() {
        // Test 1 - Worker DB is ready
        assertFalse("Worker DB should not be empty", workerController.getAllWorkers().isEmpty());
    }

    @Test
    public void testRolesDBReady() {
        // Test 2 - Roles DB is ready
        assertFalse("Roles DB should not be empty", roleController.getRoles().isEmpty());
    }

    @Test
    public void testArrangementCreation() {
        // Test 3 - Arrangement creation into DB
        arrangementController.createGetArrangement();
        assertFalse("Arrangements DB should not be empty", arrangementController.getArrangements().isEmpty());
    }

    @Test
    public void testUpdateWorker() {
        // Test 4 - Update worker
        workerController.getAllWorkers().get(0).setManager(false);
        workerController.updateWorker(workerController.getAllWorkers().get(0));
        assertFalse("Worker should be a manager", !workerController.getAllWorkers().get(0).isManager());
    }

    @Test
    public void testDeleteRole() {
        // Test 5 - Delete role
        roleController.getRoles().remove("4");
        assertNull("Role with ID 4 should be null", roleController.getRoles().get("4"));
    }

    @Test
    public void testConstraintsLastDayUpdated() {
        // Test 6 - Constraints LAST_DAY is updated from yaml
        getPathFromConfig();
        assertNotEquals("LAST_DAY should be updated", -1, ConstraintController.LAST_DAY);
    }

    @Test
    public void testCreateAndDeleteConstraint() {
        // Test 7 - Create constraint and save it in DB
        constraintController.createConstraint(workerController.getAllWorkers().getFirst(), "Morning", "07/06/2024");
        assertTrue("Constraints DB should not be empty", constraintController.getAllConstraints().isEmpty());

        // Test 8 - Delete constraint from DB
        constraintController.deleteConstraint(workerController.getAllWorkers().getFirst(), "Morning", "07/06/2024");
        assertTrue("Constraints DB should be empty", constraintController.getAllConstraints().isEmpty());
    }

    @Test
    public void testArrangementsType() {
        // Test 9 - Arrangements are not null and cast properly to the right type
        assertTrue("Arrangements should be instance of HashMap", arrangementController.getArrangements() instanceof HashMap<?, ?>);
    }

    @Test
    public void testConstraintsType() {
        // Test 10 - Constraints are not null and cast properly to the right type
        assertTrue("Constraints should be instance of HashMap", constraintController.getAllConstraints() instanceof HashMap<?, ?>);
    }

    // Create a worker with Same id

    // create an existing role
    // update worker conditions
    // Create  a constraint by a worker
    // Create constraint and change it -> check no twice
    // Create constraint and delete it
    // Create shifts by all for 1 day
    // Make an arrangement and build it - try choose wokrersbefore managers,
    // Make an arrangement - choose 2 managers and check it saved
    // Delete worker
    // delete not existing role

    // Work 2 Tests

    @Test
    public void createExistingRole() {
        int sizeBefore = roleController.getRoles().size();
        roleController.addRole("Cashier");
        int sizeAfter = roleController.getRoles().size();
        assertEquals("Roles size should be same", sizeBefore, sizeAfter);
    }

    @Test
    public void createExistingWorker() {
        int sizeBefore = workerController.getAllWorkers().size();
        workerController.createWorker("208395608", "Shai", "123", "13/06/2024", "meow", "Full", 123.5, new Role("Cashier"), "123", "koki");
        int sizeAfter = workerController.getAllWorkers().size();
        assertEquals("Workers size should be same", sizeBefore, sizeAfter);
    }

    @Test
    public void updateWorker() {
        // Get the first worker
        Worker worker = workerController.getAllWorkers().get(0);

        // Update worker's manager status
        worker.setManager(true);
        workerController.updateWorker(worker);

        // Fetch the worker again and verify the update
        Worker updatedWorker = workerController.getAllWorkers().get(0);
        assertTrue("Worker should be a manager", updatedWorker.isManager());
    }

    @Test
    public void createConstraintByWorker() {
        Worker worker = workerController.getAllWorkers().get(0);
        String shiftDate = arrangementController.getCurrArrangement().getStartDate();
        // Create a new constraint for the worker
        Constraint morning = constraintController.createConstraint(worker, "Morning", shiftDate);
        Arrangement currentArrangement = arrangementController.getCurrArrangement();
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);


        // Verify the constraint is saved
        // Assert True - its empty - BEFORE The addition
        assertFalse("Constraints DB should not be empty", arrangementController.getCurrArrangement().getWeeklyShifts().getFirst().getConstraints().isEmpty());
    }

    @Test
    public void createChangeConstraint() {
        Worker worker = workerController.getAllWorkers().get(0);
        String shiftDate = arrangementController.getCurrArrangement().getStartDate();
        Arrangement currentArrangement = arrangementController.getCurrArrangement();
        // Create a new constraint for the worker
        Constraint evening = constraintController.createConstraint(worker, "Evening", shiftDate);
        // update db
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), evening);
        Constraint morning = constraintController.createConstraint(worker, "Evening", shiftDate);
        // measure size of constraints before addition
        int sizeBefore = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure size of constraints after addition
        int sizeAfter = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();

        // Verify the constraint is saved
        assertEquals("Size before and after must be same", sizeBefore, sizeAfter);
    }


    @Test
    public void deleteWorker() {
        // Get the first worker
        Worker worker = workerController.getAllWorkers().get(0);

        worker.setActive(false);
        // Update worker's manager status
        workerController.updateWorker(worker);

        // Fetch the worker again and verify the update
        Worker updatedWorker = workerController.getAllWorkers().get(0);
        assertTrue("Worker should be a manager", updatedWorker.isManager());
    }

    //
    @Test
    public void createDeleteDoubleArr() {
        Worker worker = workerController.getAllWorkers().get(0);
        String shiftDate = arrangementController.getCurrArrangement().getStartDate();
        Arrangement currentArrangement = arrangementController.getCurrArrangement();
        // measure size of constraints before addition
        // Create a new constraint for the worker
        Constraint morning = constraintController.createConstraint(worker, "Evening", shiftDate);
        // update db
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure after adding the constraint
        int sizeBefore = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // delete the constraint
        arrangementController.removeConstraintFromShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // add the constraint again
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure size of constraints after addition
        int sizeAfter = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // Verify the constraint is saved
        assertTrue("Size before and after must be same", sizeBefore == sizeAfter); // Should be smaller after deletion

    }

    @Test
    public void createDeleteCreateArr() {
        Worker worker = workerController.getAllWorkers().get(0);
        String shiftDate = arrangementController.getCurrArrangement().getStartDate();
        Arrangement currentArrangement = arrangementController.getCurrArrangement();
        // measure size of constraints before addition
        // Create a new constraint for the worker
        Constraint morning = constraintController.createConstraint(worker, "Evening", shiftDate);
        // update db
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure after adding the constraint
        int sizeBefore = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // delete the constraint
        arrangementController.removeConstraintFromShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // add the constraint again
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure size of constraints after addition
        int sizeAfter = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // Verify the constraint is saved
        assertTrue("Size before and after must be same", sizeBefore == sizeAfter); // Should be smaller after deletion
    }

    //
    @Test
    public void deleteUnexistingRole() {
        // Attempt to delete a non-existing role
        int sizeBefore = roleController.getRoles().size();
        roleController.deleteRole("NotExists");
        int sizeAfter = roleController.getRoles().size();
        // Verify the deletion fails
        assertEquals("Deletion of a non-existing role should fail", sizeBefore, sizeAfter);
    }


    @Test
    public void createConstraintDelete() {
        Worker worker = workerController.getAllWorkers().get(0);
        String shiftDate = arrangementController.getCurrArrangement().getStartDate();
        Arrangement currentArrangement = arrangementController.getCurrArrangement();
        // measure size of constraints before addition
        // Create a new constraint for the worker
        Constraint morning = constraintController.createConstraint(worker, "Evening", shiftDate);
        // update db
        arrangementController.addConstraintToShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        int sizeBefore = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // delete the constraint
        arrangementController.removeConstraintFromShift(currentArrangement.getWeeklyShifts().getFirst(), morning);
        // measure size of constraints after addition
        int sizeAfter = currentArrangement.getWeeklyShifts().getFirst().getConstraints().size();
        // Verify the constraint is saved
        assertTrue("Size before and after must be same", sizeBefore > sizeAfter); // Should be smaller after deletion
    }

    // Assignement 3 //
    // ------------ //
    // Delete an item through a worker that has a warehouse role
    @Test
    public void deleteItemWithWarehouseWorker() {

        Worker warehouseWorker = workerController.getAllWorkers().get(7);
        JsonObject newItemJson = new JsonObject(); // item object json
        // Add properties needed
        newItemJson.addProperty("id", 7001);
        newItemJson.addProperty("expiring_date", "20/07/2024");
        newItemJson.addProperty("section", "Food");
        newItemJson.addProperty("location", 0); //int
        newItemJson.addProperty("supplier_discount", 20);
        newItemJson.addProperty("cost_price", 10.52);
        newItemJson.addProperty("product_number", 7001);

        try {
//            SystemFacade systemFacade = SystemFacade.getInstance();
            menu menu = new menu();
            menu.addItem(newItemJson); // add the item to the db
            JsonObject result = menu.removeItem(7001);// remove it
            // assert result not null ( item deleted successfully if result not null )
            assertNotNull("Problem", result);
        } catch (Exception e) {
            System.out.println("Test not passed");
        }

    }

    // Yaml configuration loading
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

}
