import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private void verifyOrCreateFile(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Database Error: Could not initialize file " + fileName);
        }
    }

    public boolean writeCustomerRecord(String id, String name, String phone, String username, String password, String address) {
        verifyOrCreateFile("CustomerLog.txt");

        if (isUsernameTaken(username)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerLog.txt", true))) {
            writer.write(id + "," + name + "," + phone + "," + username + "," + password + "," + address);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to CustomerLog.txt");
            return false;
        }
    }

    public boolean writeBookingRecord(String bookingId, String username, int roomNumber, String roomType, double price, String date, int days) {
        verifyOrCreateFile("BookingLog.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("BookingLog.txt", true))) {
            writer.write(bookingId + "," + username + "," + roomNumber + "," + roomType + "," + price + "," + date + "," + days);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to BookingLog.txt");
            return false;
        }
    }

    public boolean verifyAdminCredentials(String username, String password) {
        if ("admin".equalsIgnoreCase(username.trim()) && "admin".equals(password.trim())) {
            return true;
        }

        File file = new File("AdminLog.txt");
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

    public Customer authenticateCustomer(String username, String password) {
        File file = new File("CustomerLog.txt");
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

    public boolean isUsernameTaken(String username) {
        File file = new File("CustomerLog.txt");
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

    public List<Booking> fetchBookingsByUsername(String username) {
        List<Booking> userBookings = new ArrayList<>();
        File file = new File("BookingLog.txt");
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

    public List<Booking> fetchAllBookings() {
        List<Booking> allBookings = new ArrayList<>();
        File file = new File("BookingLog.txt");
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

    public List<Customer> fetchAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        File file = new File("CustomerLog.txt");
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

    public String generateNextCustomerId() {
        File file = new File("CustomerLog.txt");
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
}