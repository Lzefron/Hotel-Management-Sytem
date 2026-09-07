public class Booking {
    private String bookingId;
    private String customerId;
    private int roomNumber;
    private String checkInDate;
    private int durationDays;
    private boolean isCompleted;

    public Booking(String bookingId, String customerId, int roomNumber, String checkInDate, int durationDays, boolean isCompleted) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
        this.isCompleted = isCompleted;
    }

    public double calculateBill() {
        return 0.0;
    }

    public boolean isExpired() {
        return false;
    }
}