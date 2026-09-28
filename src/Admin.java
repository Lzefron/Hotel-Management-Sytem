import java.io.*;
import java.util.List;

public class Admin extends User {
    private static final String FILE_NAME = "AdminLog.txt";

    public Admin(String id, String name, String phone, String username, String password) {
        super(id, name, phone, username, password);
    }

    public static boolean verifyAdminCredentials(String username, String password) {
        if ("admin".equalsIgnoreCase(username.trim()) && "admin".equals(password.trim())) {
            return true;
        }

        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    if (tokens[0].trim().equals(username.trim()) && tokens[1].trim().equals(password.trim())) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error verifying admin records.");
        }
        return false;
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
        return Booking.saveBookingRecord(
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
        return Booking.fetchAllBookings();
    }

    public List<Customer> getAllRegisteredCustomers() {
        return Customer.fetchAllCustomers();
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