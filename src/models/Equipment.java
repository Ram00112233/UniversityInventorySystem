package models;

public class Equipment extends InventoryItem {
    private String brand;
    private int warrantyMonths;
    private String category;

    public Equipment(String assetId, String name, String brand, int warrantyMonths, String category) {
        super(assetId, name, true);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
        this.category = category;
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }
    public String getCategory() { return category; }

    @Override
    public String getItemType() {
        return "Equipment";
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Brand: " + brand +
               ", Warranty: " + warrantyMonths + " months" +
               ", Category: " + category;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Equipment) {
            Equipment other = (Equipment) obj;
            return this.id.equals(other.id);
        }
        return false;
    }
}