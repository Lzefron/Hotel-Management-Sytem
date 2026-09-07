public class Room {
    private int roomNumber;
    private String roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double price, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public void setAvailability(boolean status) {
        this.isAvailable = status;
    }

    public boolean checkAvailability() {
        return isAvailable;
    }
}