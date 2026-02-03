package models;

public class LabEquipment extends InventoryItem {
    private String labName;
    private String calibrationDate;

    public LabEquipment(String id, String name, String labName, String calibrationDate) {
        super(id, name, true);
        this.labName = labName;
        this.calibrationDate = calibrationDate;
    }

    @Override
    public String getItemType() {
        return "LabEquipment";
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Lab: " + labName +
               ", Calibration: " + calibrationDate;
    }
}