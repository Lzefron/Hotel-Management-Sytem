import java.util.List;
import java.util.Scanner;

public class Main {
    private static HotelSystem hotel = new HotelSystem();
    private static Scanner input = new Scanner(System.in);

    // Starting point of the program. Shows the main menu until the user exits.
    public static void main(String[] args) {
        hotel.loadDataFromFiles();
        addSampleRooms();

        boolean running = true;
        while (running) {
            System.out.println("\n===== Hotel Management System =====");
            System.out.println("1. Customer Login");
            System.out.println("2. Customer Register");
            System.out.println("3. Admin Login");
            System.out.println("0. Exit");
            int choice = readInt("Enter choice: ");

            if (choice == 1) {
                customerLogin();
            } else if (choice == 2) {
                customerRegister();
            } else if (choice == 3) {
                adminLogin();
            } else if (choice == 0) {
                running = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        hotel.saveDataToFiles();
        System.out.println("Goodbye!");
    }

    // Adds a few rooms so the program has data to show when it starts.
    private static void addSampleRooms() {
        hotel.addRoom(new Room(101, "Single", 50.0, true));
        hotel.addRoom(new Room(102, "Double", 80.0, true));
        hotel.addRoom(new Room(201, "Suite", 150.0, true));
    }

    // ---------- Customer part ----------

    // Asks for username and password, then opens the customer menu if correct.
    private static void customerLogin() {
        String username = readText("Username: ");
        String password = readText("Password: ");
        Customer customer = hotel.loginCustomer(username, password);
        if (customer == null) {
            System.out.println("Wrong username or password.");
        } else {
            System.out.println("Welcome, " + customer.getName() + "!");
            customerMenu(customer);
        }
    }

    // Asks for customer details and creates a new customer account.
    private static void customerRegister() {
        String name = readText("Name: ");
        String phone = readText("Phone: ");
        String address = readText("Address: ");
        String username = readText("Username: ");
        String password = readText("Password: ");

        String id = hotel.generateCustomerId();
        Customer customer = new Customer(id, name, phone, username, password, address);
        if (hotel.addCustomer(customer)) {
            System.out.println("Registered successfully. Your id is " + id);
        } else {
            System.out.println("Username already taken.");
        }
    }

    // Shows the customer menu and runs the chosen option until logout.
    private static void customerMenu(Customer customer) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- Customer Menu -----");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View My Bookings");
            System.out.println("0. Logout");
            int choice = readInt("Enter choice: ");

            if (choice == 1) {
                showAvailableRooms();
            } else if (choice == 2) {
                bookRoom(customer);
            } else if (choice == 3) {
                showMyBookings(customer);
            } else if (choice == 0) {
                customer.logout();
                loggedIn = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Prints all rooms that are free right now.
    private static void showAvailableRooms() {
        List<Room> rooms = hotel.getAvailableRooms();
        if (rooms.size() == 0) {
            System.out.println("No rooms available.");
            return;
        }
        System.out.println("Room No | Type   | Price");
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            System.out.println(room.getRoomNumber() + "     | " + room.getRoomType() + " | " + room.getPrice());
        }
    }

    // Asks for room number and dates, then books the room for the customer.
    private static void bookRoom(Customer customer) {
        showAvailableRooms();
        int roomNumber = readInt("Room number: ");
        String checkIn = readText("Check-in date (dd/mm/yyyy): ");
        int days = readInt("Number of days: ");

        Booking booking = hotel.bookRoom(customer, roomNumber, checkIn, days);
        if (booking == null) {
            System.out.println("Room is not available.");
        } else {
            System.out.println("Booked! Your booking id is " + booking.getBookingId());
        }
    }

    // Prints all bookings made by the logged-in customer.
    private static void showMyBookings(Customer customer) {
        List<Booking> bookings = hotel.getBookingsForCustomer(customer);
        if (bookings.size() == 0) {
            System.out.println("You have no bookings.");
            return;
        }
        for (int i = 0; i < bookings.size(); i++) {
            printBooking(bookings.get(i));
        }
    }

    // ---------- Admin part ----------

    // Asks for username and password, then opens the admin menu if correct.
    private static void adminLogin() {
        String username = readText("Username: ");
        String password = readText("Password: ");
        Admin admin = hotel.loginAdmin(username, password);
        if (admin == null) {
            System.out.println("Wrong username or password.");
        } else {
            System.out.println("Welcome, " + admin.getName() + "!");
            adminMenu(admin);
        }
    }

    // Shows the admin menu and runs the chosen option until logout.
    private static void adminMenu(Admin admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n----- Admin Menu -----");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. View All Customers");
            System.out.println("4. View All Bookings");
            System.out.println("5. Add Admin");
            System.out.println("0. Logout");
            int choice = readInt("Enter choice: ");

            if (choice == 1) {
                addRoom();
            } else if (choice == 2) {
                showAllRooms();
            } else if (choice == 3) {
                showAllCustomers();
            } else if (choice == 4) {
                showAllBookings();
            } else if (choice == 5) {
                addAdmin();
            } else if (choice == 0) {
                admin.logout();
                loggedIn = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Asks for room details and adds the room to the hotel.
    private static void addRoom() {
        int roomNumber = readInt("Room number: ");
        String type = readText("Room type (Single/Double/Suite): ");
        double price = readDouble("Price per night: ");

        if (hotel.addRoom(new Room(roomNumber, type, price, true))) {
            System.out.println("Room added.");
        } else {
            System.out.println("Room number already exists.");
        }
    }

    // Prints every room with its availability.
    private static void showAllRooms() {
        List<Room> rooms = hotel.getRoomList();
        if (rooms.size() == 0) {
            System.out.println("No rooms in the system.");
            return;
        }
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            String status = room.checkAvailability() ? "Available" : "Booked";
            System.out.println(room.getRoomNumber() + " | " + room.getRoomType() + " | " + room.getPrice() + " | " + status);
        }
    }

    // Prints every registered customer.
    private static void showAllCustomers() {
        List<Customer> customers = hotel.getCustomerList();
        if (customers.size() == 0) {
            System.out.println("No customers registered.");
            return;
        }
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.println(c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getUsername());
        }
    }

    // Prints every booking in the system.
    private static void showAllBookings() {
        List<Booking> bookings = hotel.getBookingList();
        if (bookings.size() == 0) {
            System.out.println("No bookings yet.");
            return;
        }
        for (int i = 0; i < bookings.size(); i++) {
            printBooking(bookings.get(i));
        }
    }

    // Asks for admin details and creates a new admin account.
    private static void addAdmin() {
        String name = readText("Name: ");
        String phone = readText("Phone: ");
        String username = readText("Username: ");
        String password = readText("Password: ");

        String id = hotel.generateAdminId();
        if (hotel.addAdmin(new Admin(id, name, phone, username, password))) {
            System.out.println("Admin added with id " + id);
        } else {
            System.out.println("Username already taken.");
        }
    }

    // ---------- Helper methods ----------

    // Prints one booking on a single line.
    private static void printBooking(Booking b) {
        String status = b.isCompleted() ? "Completed" : "Active";
        System.out.println(b.getBookingId() + " | Customer " + b.getCustomerId() + " | Room " + b.getRoomNumber()
                + " | " + b.getCheckInDate() + " | " + b.getDurationDays() + " days | " + status);
    }

    // Shows a message and reads one line of text from the user.
    private static String readText(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    // Shows a message and reads a whole number. Asks again if the input is not a number.
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Shows a message and reads a decimal number. Asks again if the input is not a number.
    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String line = input.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
