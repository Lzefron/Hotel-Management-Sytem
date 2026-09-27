public class Booking {
    private String bookingId;
    private String username;
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private String checkInDate;
    private int durationDays;

    public Booking(String bookingId, String username, int roomNumber, String roomType, double pricePerNight, String checkInDate, int durationDays) {
        this.bookingId = bookingId;
        this.username = username;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
    }

    public double calculateTotalBill() {
        if (durationDays <= 0) return 0.0;
        return pricePerNight * durationDays;
    }

    public boolean isValidBooking() {
        return bookingId != null && !bookingId.trim().isEmpty()
                && username != null && !username.trim().isEmpty()
                && roomNumber > 0 && durationDays > 0;
    }

    public String getBookingId() { return bookingId; }
    public String getUsername() { return username; }
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public String getCheckInDate() { return checkInDate; }
    public int getDurationDays() { return durationDays; }
}