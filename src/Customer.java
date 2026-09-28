import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private static final String FILE_NAME = "CustomerLog.txt";

    public Customer(String id, String name, String phone, String username, String password, String address) {
        super(id, name, phone, username, password);
        this.address = address;
    }

    private static void verifyOrCreateFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Customer File Error: Could not initialize " + FILE_NAME);
        }
    }

    public static boolean registerCustomerRecord(String id, String name, String phone, String username, String password, String address) {
        verifyOrCreateFile();

        if (isUsernameTaken(username)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(id + "," + name + "," + phone + "," + username + "," + password + "," + address);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to " + FILE_NAME);
            return false;
        }
    }

    public static Customer authenticateCustomer(String username, String password) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    String storedUname = tokens[3].trim();
                    String storedPass = tokens[4].trim();
                    if (storedUname.equals(username.trim()) && storedPass.equals(password.trim())) {
                        return new Customer(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(), storedUname, storedPass, tokens[5].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer credentials.");
        }
        return null;
    }

    public static boolean isUsernameTaken(String username) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 4 && tokens[3].trim().equalsIgnoreCase(username.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking username uniqueness.");
        }
        return false;
    }

    public static List<Customer> fetchAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return customerList;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    customerList.add(new Customer(
                            tokens[0].trim(),
                            tokens[1].trim(),
                            tokens[2].trim(),
                            tokens[3].trim(),
                            tokens[4].trim(),
                            tokens[5].trim()
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customer database.");
        }
        return customerList;
    }

    public static String generateNextCustomerId() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return "CUST-1001";

        int totalRecords = 1000;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                totalRecords++;
            }
        } catch (IOException e) {
            System.err.println("Error generating ID.");
        }
        return "CUST-" + (totalRecords + 1);
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
        boolean success = Booking.saveBookingRecord(
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
        return Booking.fetchBookingsByUsername(getUsername());
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