package Presentation;

import Service.RoleController;

import java.util.Scanner;

public class RoleScreen {
    RoleController rc;

    public RoleScreen() {
        rc = new RoleController();
        // Show menu
        menu();
    }

    private void menu() {
        int choose = -1;
        String roleName;
        Scanner scanner = new Scanner(System.in);
        while (choose != 3) {
            System.out.println("1)Create Role\n2)Delete Role\n3)Go Back");
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    //
                    System.out.println("Enter Role Name: ");
                    roleName = scanner.next();
                    if (rc.getRole(roleName) == null)
                        // add role to DB.
                        rc.addRole(roleName);
                    else System.out.println("Role already exists");
                    break;
                case 2:
                    // Delete Role
                    System.out.println("Enter Role Name");
                    roleName = scanner.next();
                    if (rc.getRoles().containsKey(roleName))
                        rc.deleteRole(roleName);
                    else System.out.println("Role does not exist");
                    // delete the specific role from db.
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }


}


