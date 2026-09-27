import java.util.List;

public class Admin extends User {
    private Database database;

    public Admin(String id, String name, String phone, String username, String password) {
        super(id, name, phone, username, password);
        this.database = new Database();
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public boolean createWalkInReservation(String targetUsername, Room room, String checkInDate, int durationDays) {
        if (!isLoggedIn) {
            return false;
        }

        if (targetUsername == null || targetUsername.trim().isEmpty() || room == null || durationDays <= 0) {
            return false;
        }

        String bookingId = "BK-" + (1000 + (int)(Math.random() * 9000));
        return database.writeBookingRecord(
                bookingId,
                targetUsername.trim(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                checkInDate,
                durationDays
        );
    }

    public List<Booking> getAllGlobalBookings() {
        return database.fetchAllBookings();
    }

    public List<Customer> getAllRegisteredCustomers() {
        return database.fetchAllCustomers();
    }

    public double calculateTotalRevenue() {
        List<Booking> allBookings = getAllGlobalBookings();
        double revenue = 0.0;
        for (Booking b : allBookings) {
            revenue += b.calculateTotalBill();
        }
        return revenue;
    }
}