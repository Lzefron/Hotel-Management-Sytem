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