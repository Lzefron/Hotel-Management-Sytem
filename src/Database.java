import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public void createAdminFile() {
        try {
            File file = new File("AdminLog.txt");
            if (file.createNewFile()) {
                System.out.println("Admin log created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error creating Admin file.");
            e.printStackTrace();
        }
    }

    public void createCustomerFile() {
        try {
            File file = new File("CustomerLog.txt");
            if (file.createNewFile()) {
                System.out.println("Customer log created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error creating Customer file.");
            e.printStackTrace();
        }
    }

    public void createBookingFile() {
        try {
            File file = new File("BookingLog.txt");
            if (file.createNewFile()) {
                System.out.println("Booking log created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error creating Booking file.");
            e.printStackTrace();
        }
    }

    public void writeAdmin(String username, String password) {
        createAdminFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("AdminLog.txt", true))) {
            bw.write(username + "," + password);
            bw.newLine();
            System.out.println("Admin saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Admin data.");
            e.printStackTrace();
        }
    }

    public void writeCustomer(String id, String name, String phone, String username, String password, String address) {
        createCustomerFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("CustomerLog.txt", true))) {
            bw.write(id + "," + name + "," + phone + "," + username + "," + password + "," + address);
            bw.newLine();
            System.out.println("Customer registered successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Customer data.");
            e.printStackTrace();
        }
    }

    public void writeBooking(String bookingId, String customerId, int roomNumber, String checkInDate, int durationDays) {
        createBookingFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("BookingLog.txt", true))) {
            bw.write(bookingId + "," + customerId + "," + roomNumber + "," + checkInDate + "," + durationDays);
            bw.newLine();
            System.out.println("Booking recorded successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Booking data.");
            e.printStackTrace();
        }
    }

    public void readAllCustomers() {
        File file = new File("CustomerLog.txt");
        if (!file.exists()) {
            System.out.println("No customer records found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n--- REGISTERED CUSTOMERS ---");
            System.out.println("ID | Name | Phone | Username | Address");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    System.out.println(data[0] + " | " + data[1] + " | " + data[2] + " | " + data[3] + " | " + data[5]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Customer log.");
            e.printStackTrace();
        }
    }

    public void viewAllBookings() {
        File file = new File("BookingLog.txt");
        if (!file.exists()) {
            System.out.println("No booking records found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n--- CURRENT BOOKINGS ---");
            System.out.println("BookingID | CustomerID | RoomNo | CheckInDate | Days");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    System.out.println(data[0] + " | Customer: " + data[1] + " | Room: " + data[2] + " | Date: " + data[3] + " | Duration: " + data[4] + " days");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Bookings log.");
            e.printStackTrace();
        }
    }

    public boolean verifyAdminLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return true;
        }

        File file = new File("AdminLog.txt");
        if (!file.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[0].equals(username) && data[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error verifying Admin login.");
        }
        return false;
    }

    public Customer getCustomerByCredentials(String username, String password) {
        File file = new File("CustomerLog.txt");
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6 && data[3].equals(username) && data[4].equals(password)) {
                    return new Customer(data[0], data[1], data[2], data[3], data[4], data[5]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Customer log.");
        }
        return null;
    }

    public List<Customer> loadCustomersFromFile() {
        List<Customer> list = new ArrayList<>();
        File file = new File("CustomerLog.txt");
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    list.add(new Customer(data[0], data[1], data[2], data[3], data[4], data[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers into memory.");
        }
        return list;
    }
    public String generateNextCustomerId() {
        File file = new File("CustomerLog.txt");
        if (!file.exists()) return "CUST-1001";

        int count = 1000;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error calculating Customer ID.");
        }
        return "CUST-" + (count + 1);
    }

    public void viewBookingsForCustomer(String targetCustomerId) {
        File file = new File("BookingLog.txt");
        if (!file.exists()) {
            System.out.println("No booking records found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            System.out.println("\n--- YOUR BOOKINGS ---");
            System.out.println("BookingID | RoomNo | CheckInDate | Days");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5 && data[1].trim().equals(targetCustomerId.trim())) {
                    System.out.println(data[0] + " | Room: " + data[2] + " | Date: " + data[3] + " | Duration: " + data[4] + " days");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No active bookings found for your ID.");
            }
        } catch (IOException e) {
            System.out.println("Error reading Bookings log.");
        }
    }
}