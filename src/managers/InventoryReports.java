package managers;

import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class InventoryReports {

    private InventoryManager manager;

    public InventoryReports(InventoryManager manager) {
        this.manager = manager;
    }

    // for loop
    public void generateInventoryReport() {
        System.out.println("=== Inventory Report ===");
        InventoryItem[] items = manager.getAllItems();

        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
    }

    // while loop
    public void findExpiredWarranties() {
        System.out.println("=== Expired Warranties ===");
        InventoryItem[] items = manager.getAllItems();
        int i = 0;

        while (i < items.length) {
            if (items[i] instanceof Equipment) {
                Equipment eq = (Equipment) items[i];
                if (eq.getWarrantyMonths() == 0) {
                    System.out.println(eq);
                }
            }
            i++;
        }
    }

    // enhanced for loop
    public void displayAssignmentsByDepartment() {
        System.out.println("=== Assignments by Staff ===");
        StaffMember[] staff = manager.getAllStaff();

        for (StaffMember s : staff) {
            System.out.println("Staff: " + s.getName() + " (ID: " + s.getStaffId() + ")");
            Equipment[] assigned = s.getAssignedEquipment();
            for (Equipment eq : assigned) {
                if (eq != null) {
                    System.out.println("  - " + eq);
                }
            }
        }
    }

    // nested loops
    public void calculateUtilisationRate() {
        System.out.println("=== Utilisation Rate ===");
        InventoryItem[] items = manager.getAllItems();
        StaffMember[] staff = manager.getAllStaff();

        int totalEquipment = 0;
        int assignedCount = 0;

        for (InventoryItem item : items) {
            if (item instanceof Equipment) {
                totalEquipment++;
                for (StaffMember s : staff) {
                    Equipment[] assigned = s.getAssignedEquipment();
                    for (Equipment eq : assigned) {
                        if (eq != null && eq.getId().equals(item.getId())) {
                            assignedCount++;
                        }
                    }
                }
            }
        }

        if (totalEquipment == 0) {
            System.out.println("No equipment in the system.");
            return;
        }

        double utilisationRate = (assignedCount * 100.0) / totalEquipment;
        System.out.println("Total equipment: " + totalEquipment);
        System.out.println("Assigned equipment: " + assignedCount);
        System.out.println("Utilisation rate: " + utilisationRate + "%");
    }

    // do-while loop
    public void generateMaintenanceSchedule() {
        System.out.println("=== Maintenance Schedule ===");
        InventoryItem[] items = manager.getAllItems();

        int i = 0;
        if (items.length == 0) {
            System.out.println("No items to schedule.");
            return;
        }

        do {
            InventoryItem item = items[i];
            System.out.println("Item: " + item.getName() + " (ID: " + item.getId() + ") - Maintenance next month.");
            i++;
        } while (i < items.length);
    }
}
