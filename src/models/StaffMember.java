package models;

public class StaffMember {
    private int staffId;
    private String name;
    private String email;
    private Equipment[] assignedEquipment;
    private int count = 0;

    public StaffMember(int staffId, String name, String email) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.assignedEquipment = new Equipment[5];
    }

    public int getStaffId() { return staffId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public int getAssignedEquipmentCount() {
        return count;
    }

    public Equipment[] getAssignedEquipment() {
        return assignedEquipment;
    }

    public void addAssignedEquipment(Equipment equipment) {
        if (count < 5) {
            assignedEquipment[count] = equipment;
            count++;
        }
    }

    public void removeAssignedEquipment(String assetId) {
        for (int i = 0; i < count; i++) {
            if (assignedEquipment[i].getId().equals(assetId)) {
                assignedEquipment[i] = assignedEquipment[count - 1];
                assignedEquipment[count - 1] = null;
                count--;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Staff ID: " + staffId +
               ", Name: " + name +
               ", Email: " + email +
               ", Assigned Items: " + count;
    }
}
