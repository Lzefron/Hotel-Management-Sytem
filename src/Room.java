import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private String roomType;
    private String bedType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, String bedType, double pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.bedType = bedType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    public static List<Room> getDefaultInventory() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, "Economy", "Single Bed", 50.0, true));
        rooms.add(new Room(102, "Standard", "Double Bed", 90.0, true));
        rooms.add(new Room(201, "Deluxe", "Twin Bed", 140.0, true));
        rooms.add(new Room(301, "Executive VIP Suite", "King Size Bed", 250.0, true));
        rooms.add(new Room(401, "Presidential Penthouse", "Master Suite King Bed", 500.0, true));
        return rooms;
    }

    public double calculateStayPrice(int numberOfNights) {
        if (numberOfNights <= 0) {
            return 0.0;
        }
        return this.pricePerNight * numberOfNights;
    }

    public boolean reserveRoom() {
        if (this.isAvailable) {
            this.isAvailable = false;
            return true;
        }
        return false;
    }

    public void releaseRoom() {
        this.isAvailable = true;
    }

    public String getDisplayLabel() {
        return "Room " + roomNumber + " | " + roomType + " (" + bedType + ") - $" + pricePerNight + "/night";
    }

    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public String getBedType() { return bedType; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }
}