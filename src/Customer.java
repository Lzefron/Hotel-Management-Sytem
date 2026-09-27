import java.util.List;

public class Customer extends User {
    private String address;
    private Database database;

    public Customer(String id, String name, String phone, String username, String password, String address) {
        super(id, name, phone, username, password);
        this.address = address;
        this.database = new Database();
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public boolean createReservation(Room room, String checkInDate, int durationDays) {
        if (!isLoggedIn) {
            return false;
        }

        if (room == null || !room.isAvailable() || durationDays <= 0) {
            return false;
        }

        String bookingId = "BK-" + (1000 + (int)(Math.random() * 9000));
        boolean success = database.writeBookingRecord(
                bookingId,
                getUsername(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                checkInDate,
                durationDays
        );

        if (success) {
            room.reserveRoom();
        }
        return success;
    }

    public List<Booking> getMyBookings() {
        return database.fetchBookingsByUsername(getUsername());
    }

    public double calculateTotalSpent() {
        List<Booking> bookings = getMyBookings();
        double total = 0.0;
        for (Booking b : bookings) {
            total += b.calculateTotalBill();
        }
        return total;
    }

    public String getAddress() { return address; }
}