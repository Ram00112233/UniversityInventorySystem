package models;

public class Furniture extends InventoryItem {
    private String roomNumber;
    private String material;

    public Furniture(String id, String name, String roomNumber, String material) {
        super(id, name, true);
        this.roomNumber = roomNumber;
        this.material = material;
    }

    @Override
    public String getItemType() {
        return "Furniture";
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Room: " + roomNumber +
               ", Material: " + material;
    }
}