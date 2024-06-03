package Presentation;

import Domain.Role;
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
        System.out.println("1)Create Role\n2)Delete Role\n3)Go Back\n");
        while (choose != 3) {
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    System.out.println("Enter Role Name: ");
                    roleName = scanner.next();
                    // add role to DB.
                    rc.addRole(roleName);
                    break;
                case 2:
                    // get the index.
                    roleName = scanner.next();
                    // delete the specific role from db.
                    rc.deleteRole(roleName);
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


