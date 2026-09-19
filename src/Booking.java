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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getBookingDetails() {
        String status = isCompleted ? "Completed" : "Active";
        return "Booking ID: " + bookingId + " | Customer ID: " + customerId + " | Room: " + roomNumber +
                " | Check-In: " + checkInDate + " | Duration: " + durationDays + " days | Status: " + status;
    }
}