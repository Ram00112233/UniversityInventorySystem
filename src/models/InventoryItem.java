package models;

public abstract class InventoryItem {
    protected String id;
    protected String name;
    protected boolean isAvailable;

    public InventoryItem(String id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public abstract String getItemType();

    @Override
    public String toString() {
        return "[" + getItemType() + "] ID: " + id +
               ", Name: " + name +
               ", Available: " + isAvailable;
    }
}