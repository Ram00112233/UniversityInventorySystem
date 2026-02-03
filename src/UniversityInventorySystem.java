import exceptions.InventoryException;
import managers.InventoryManager;
import managers.InventoryReports;
import models.Equipment;
import models.Furniture;
import models.LabEquipment;
import models.StaffMember;

import java.util.Scanner;

public class UniversityInventorySystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        InventoryManager manager = new InventoryManager(100, 50);
        InventoryReports reports = new InventoryReports(manager);

        boolean running = true;

        while (running) {
            System.out.println("\n=== University Inventory Management System ===");
            System.out.println("1. Add new equipment");
            System.out.println("2. Register new staff member");
            System.out.println("3. Assign equipment to staff");
            System.out.println("4. Return equipment");
            System.out.println("5. Search inventory");
            System.out.println("6. Generate reports");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    addEquipmentMenu(scanner, manager);
                    break;
                case 2:
                    registerStaffMenu(scanner, manager);
                    break;
                case 3:
                    assignEquipmentMenu(scanner, manager);
                    break;
                case 4:
                    returnEquipmentMenu(scanner, manager);
                    break;
                case 5:
                    searchMenu(scanner, manager);
                    break;
                case 6:
                    reportsMenu(scanner, reports);
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting system. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addEquipmentMenu(Scanner scanner, InventoryManager manager) {
        System.out.println("1. Equipment");
        System.out.println("2. Furniture");
        System.out.println("3. Lab Equipment");
        System.out.print("Choose type: ");
        int type = readInt(scanner);
        scanner.nextLine(); // consume newline

        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        switch (type) {
            case 1:
                System.out.print("Enter brand: ");
                String brand = scanner.nextLine();
                System.out.print("Enter warranty months: ");
                int warranty = readInt(scanner);
                scanner.nextLine();
                System.out.print("Enter category (e.g., computer, lab, furniture): ");
                String category = scanner.nextLine();
                Equipment eq = new Equipment(id, name, brand, warranty, category);
                manager.addItem(eq);
                System.out.println("Equipment added.");
                break;
            case 2:
                System.out.print("Enter room number: ");
                String room = scanner.nextLine();
                System.out.print("Enter material: ");
                String material = scanner.nextLine();
                Furniture furniture = new Furniture(id, name, room, material);
                manager.addItem(furniture);
                System.out.println("Furniture added.");
                break;
            case 3:
                System.out.print("Enter lab name: ");
                String lab = scanner.nextLine();
                System.out.print("Enter calibration date: ");
                String date = scanner.nextLine();
                LabEquipment labEq = new LabEquipment(id, name, lab, date);
                manager.addItem(labEq);
                System.out.println("Lab equipment added.");
                break;
            default:
                System.out.println("Invalid type.");
        }
    }

    private static void registerStaffMenu(Scanner scanner, InventoryManager manager) {
        System.out.print("Enter staff ID: ");
        int staffId = readInt(scanner);
        scanner.nextLine();
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter staff email: ");
        String email = scanner.nextLine();

        StaffMember staff = new StaffMember(staffId, name, email);
        manager.addStaffMember(staff);
        System.out.println("Staff member registered.");
    }

    private static void assignEquipmentMenu(Scanner scanner, InventoryManager manager) {
        try {
            System.out.print("Enter staff ID: ");
            int staffId = readInt(scanner);
            scanner.nextLine();
            StaffMember staff = manager.findStaffById(staffId);

            System.out.print("Enter equipment ID: ");
            String eqId = scanner.nextLine();
            Equipment eq = manager.findEquipmentById(eqId);

            manager.assignEquipment(staff, eq);
            System.out.println("Equipment assigned successfully.");
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnEquipmentMenu(Scanner scanner, InventoryManager manager) {
        try {
            System.out.print("Enter staff ID: ");
            int staffId = readInt(scanner);
            scanner.nextLine();
            StaffMember staff = manager.findStaffById(staffId);

            System.out.print("Enter equipment ID to return: ");
            String eqId = scanner.nextLine();

            manager.returnEquipment(staff, eqId);
            System.out.println("Equipment returned successfully.");
        } catch (InventoryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchMenu(Scanner scanner, InventoryManager manager) {
        System.out.println("1. Search by name");
        System.out.println("2. Search by category and availability");
        System.out.println("3. Search by warranty range");
        System.out.print("Choose option: ");
        int option = readInt(scanner);
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                var resultsByName = manager.searchEquipment(name);
                printSearchResults(resultsByName);
                break;
            case 2:
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                System.out.print("Available only? (true/false): ");
                boolean availableOnly = Boolean.parseBoolean(scanner.nextLine());
                var resultsByCategory = manager.searchEquipment(category, availableOnly);
                printSearchResults(resultsByCategory);
                break;
            case 3:
                System.out.print("Enter min warranty: ");
                int min = readInt(scanner);
                System.out.print("Enter max warranty: ");
                int max = readInt(scanner);
                scanner.nextLine();
                var resultsByWarranty = manager.searchEquipment(min, max);
                printSearchResults(resultsByWarranty);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void printSearchResults(models.InventoryItem[] results) {
        if (results.length == 0) {
            System.out.println("No items found.");
        } else {
            System.out.println("Search results:");
            for (models.InventoryItem item : results) {
                System.out.println(item);
            }
        }
    }

    private static void reportsMenu(Scanner scanner, InventoryReports reports) {
        System.out.println("1. Inventory report");
        System.out.println("2. Expired warranties");
        System.out.println("3. Assignments by staff");
        System.out.println("4. Utilisation rate");
        System.out.println("5. Maintenance schedule");
        System.out.print("Choose option: ");
        int option = readInt(scanner);

        switch (option) {
            case 1:
                reports.generateInventoryReport();
                break;
            case 2:
                reports.findExpiredWarranties();
                break;
            case 3:
                reports.displayAssignmentsByDepartment();
                break;
            case 4:
                reports.calculateUtilisationRate();
                break;
            case 5:
                reports.generateMaintenanceSchedule();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}