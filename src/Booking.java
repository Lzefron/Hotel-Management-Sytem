import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingId;
    private String username;
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private String checkInDate;
    private int durationDays;

    private static final String FILE_NAME = "BookingLog.txt";

    public Booking(String bookingId, String username, int roomNumber, String roomType, double pricePerNight, String checkInDate, int durationDays) {
        this.bookingId = bookingId;
        this.username = username;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
    }

    private static void verifyOrCreateFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Booking Error: Could not initialize file " + FILE_NAME);
        }
    }

    public static boolean saveBookingRecord(String bookingId, String username, int roomNumber, String roomType, double price, String date, int days) {
        verifyOrCreateFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(bookingId + "," + username + "," + roomNumber + "," + roomType + "," + price + "," + date + "," + days);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to " + FILE_NAME);
            return false;
        }
    }

    public static List<Booking> fetchBookingsByUsername(String username) {
        List<Booking> userBookings = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return userBookings;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 7 && tokens[1].trim().equalsIgnoreCase(username.trim())) {
                    userBookings.add(new Booking(
                            tokens[0].trim(),
                            tokens[1].trim(),
                            Integer.parseInt(tokens[2].trim()),
                            tokens[3].trim(),
                            Double.parseDouble(tokens[4].trim()),
                            tokens[5].trim(),
                            Integer.parseInt(tokens[6].trim())
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading personal bookings.");
        }
        return userBookings;
    }

    public static List<Booking> fetchAllBookings() {
        List<Booking> allBookings = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return allBookings;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 7) {
                    allBookings.add(new Booking(
                            tokens[0].trim(),
                            tokens[1].trim(),
                            Integer.parseInt(tokens[2].trim()),
                            tokens[3].trim(),
                            Double.parseDouble(tokens[4].trim()),
                            tokens[5].trim(),
                            Integer.parseInt(tokens[6].trim())
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading all global bookings.");
        }
        return allBookings;
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