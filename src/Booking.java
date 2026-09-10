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

    // Returns the booking id.
    public String getBookingId() {
        return bookingId;
    }

    // Returns the id of the customer who made this booking.
    public String getCustomerId() {
        return customerId;
    }

    // Returns the booked room number.
    public int getRoomNumber() {
        return roomNumber;
    }

    // Returns the check-in date as text.
    public String getCheckInDate() {
        return checkInDate;
    }

    // Returns how many days the booking is for.
    public int getDurationDays() {
        return durationDays;
    }

    // Returns true if the customer has already checked out.
    public boolean isCompleted() {
        return isCompleted;
    }
}
