package managers;

import exceptions.AssignmentLimitExceededException;
import exceptions.EquipmentNotAvailableException;
import exceptions.InventoryException;
import exceptions.StaffMemberNotFoundException;
import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class InventoryManager {

    private InventoryItem[] items;
    private StaffMember[] staffMembers;
    private int itemCount = 0;
    private int staffCount = 0;

    public InventoryManager(int maxItems, int maxStaff) {
        items = new InventoryItem[maxItems];
        staffMembers = new StaffMember[maxStaff];
    }

    public void addItem(InventoryItem item) {
        if (itemCount < items.length) {
            items[itemCount++] = item;
        }
    }

    public void addStaffMember(StaffMember staff) {
        if (staffCount < staffMembers.length) {
            staffMembers[staffCount++] = staff;
        }
    }

    public StaffMember findStaffById(int staffId) throws StaffMemberNotFoundException {
        for (int i = 0; i < staffCount; i++) {
            if (staffMembers[i].getStaffId() == staffId) {
                return staffMembers[i];
            }
        }
        throw new StaffMemberNotFoundException("Staff member with ID " + staffId + " not found.");
    }

    public Equipment findEquipmentById(String id) throws EquipmentNotAvailableException {
        for (int i = 0; i < itemCount; i++) {
            if (items[i] instanceof Equipment && items[i].getId().equals(id)) {
                return (Equipment) items[i];
            }
        }
        throw new EquipmentNotAvailableException("Equipment with ID " + id + " not found.");
    }

    public void assignEquipment(StaffMember staff, Equipment equipment)
            throws InventoryException {

        validateAssignment(staff, equipment);

        if (!equipment.isAvailable()) {
            throw new EquipmentNotAvailableException("Equipment is not available.");
        }

        if (staff.getAssignedEquipmentCount() >= 5) {
            throw new AssignmentLimitExceededException("Staff has reached assignment limit.");
        }

        equipment.setAvailable(false);
        staff.addAssignedEquipment(equipment);
    }

    public void returnEquipment(StaffMember staff, String assetId)
            throws EquipmentNotAvailableException {

        Equipment[] assigned = staff.getAssignedEquipment();
        boolean found = false;

        for (int i = 0; i < staff.getAssignedEquipmentCount(); i++) {
            if (assigned[i] != null && assigned[i].getId().equals(assetId)) {
                assigned[i].setAvailable(true);
                staff.removeAssignedEquipment(assetId);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new EquipmentNotAvailableException("This staff member does not have that equipment assigned.");
        }
    }

    public double calculateMaintenanceFee(Equipment equipment, int daysOverdue) {
        double baseRate;

        String category = equipment.getCategory();

        switch (category.toLowerCase()) {
            case "computer":
                baseRate = 5.0;
                break;
            case "lab":
                baseRate = 7.5;
                break;
            case "furniture":
                baseRate = 3.0;
                break;
            default:
                baseRate = 2.0;
        }

        if (daysOverdue <= 0) {
            return 0;
        }

        return baseRate * daysOverdue;
    }

    // Overloaded search methods

    public InventoryItem[] searchEquipment(String name) {
        InventoryItem[] results = new InventoryItem[itemCount];
        int count = 0;

        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().equalsIgnoreCase(name)) {
                results[count++] = items[i];
            }
        }

        return trimResults(results, count);
    }

    public InventoryItem[] searchEquipment(String category, boolean availableOnly) {
        InventoryItem[] results = new InventoryItem[itemCount];
        int count = 0;

        for (int i = 0; i < itemCount; i++) {
            if (items[i] instanceof Equipment) {
                Equipment eq = (Equipment) items[i];
                boolean matchesCategory = eq.getCategory().equalsIgnoreCase(category);
                boolean matchesAvailability = !availableOnly || eq.isAvailable();

                if (matchesCategory && matchesAvailability) {
                    results[count++] = eq;
                }
            }
        }

        return trimResults(results, count);
    }

    public InventoryItem[] searchEquipment(int minWarranty, int maxWarranty) {
        InventoryItem[] results = new InventoryItem[itemCount];
        int count = 0;

        for (int i = 0; i < itemCount; i++) {
            if (items[i] instanceof Equipment) {
                Equipment eq = (Equipment) items[i];
                int w = eq.getWarrantyMonths();
                if (w >= minWarranty && w <= maxWarranty) {
                    results[count++] = eq;
                }
            }
        }

        return trimResults(results, count);
    }

    private InventoryItem[] trimResults(InventoryItem[] results, int count) {
        InventoryItem[] trimmed = new InventoryItem[count];
        for (int i = 0; i < count; i++) {
            trimmed[i] = results[i];
        }
        return trimmed;
    }

    public void validateAssignment(StaffMember staff, Equipment equipment)
            throws InventoryException {

        if (staff == null) {
            throw new StaffMemberNotFoundException("Staff member is null.");
        }

        if (equipment == null) {
            throw new EquipmentNotAvailableException("Equipment is null.");
        }

        if (!equipment.isAvailable()) {
            throw new EquipmentNotAvailableException("Equipment is already assigned.");
        }

        if (staff.getAssignedEquipmentCount() >= 5) {
            throw new AssignmentLimitExceededException("Staff has already reached the maximum assignment limit.");
        }
    }

    public InventoryItem[] getAllItems() {
        InventoryItem[] result = new InventoryItem[itemCount];
        for (int i = 0; i < itemCount; i++) {
            result[i] = items[i];
        }
        return result;
    }

    public StaffMember[] getAllStaff() {
        StaffMember[] result = new StaffMember[staffCount];
        for (int i = 0; i < staffCount; i++) {
            result[i] = staffMembers[i];
        }
        return result;
    }
}
