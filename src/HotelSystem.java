import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelSystem {
    private List<Room> rooms;
    private List<Customer> customers;
    private Database database;

    public HotelSystem() {
        this.rooms = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.database = new Database();
        initializeRooms();
        loadExistingData();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single Standard", 80.0, true));
        rooms.add(new Room(102, "Single Deluxe", 100.0, true));
        rooms.add(new Room(201, "Double Deluxe", 150.0, true));
        rooms.add(new Room(202, "Double Executive", 180.0, true));
        rooms.add(new Room(301, "Penthouse Suite", 350.0, true));
    }

    private void loadExistingData() {
        this.customers = database.loadCustomersFromFile();
    }

    public void displayAllAvailableRooms() {
        System.out.println("\n--- AVAILABLE HOTEL ROOMS ---");
        boolean found = false;
        for (Room room : rooms) {
            if (room.checkAvailability()) {
                System.out.println(room.getRoomInfo());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms currently available.");
        }
    }

    public Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void startSystem() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("   GRAND HOTEL MANAGEMENT SYSTEM ");
            System.out.println("=================================");
            System.out.println("1. Admin Access");
            System.out.println("2. Customer Access");
            System.out.println("3. View Available Rooms");
            System.out.println("4. Exit");
            System.out.print("Select role: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Admin Username: ");
                    String aName = scanner.nextLine();
                    System.out.print("Enter Admin Password: ");
                    String aPass = scanner.nextLine();

                    if (database.verifyAdminLogin(aName, aPass)) {
                        Admin admin = new Admin("ADM-01", "System Admin", "000-000", aName, aPass);
                        admin.login(aName, aPass);
                        admin.adminMenu();
                    } else {
                        System.out.println("Invalid Admin Credentials.");
                    }
                    break;

                case 2:
                    System.out.println("\n1. Login");
                    System.out.println("2. Register");
                    System.out.print("Choose: ");
                    int custOpt = scanner.nextInt();
                    scanner.nextLine();

                    if (custOpt == 1) {
                        System.out.print("Enter Username: ");
                        String cName = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String cPass = scanner.nextLine();

                        Customer customer = database.getCustomerByCredentials(cName, cPass);
                        if (customer != null) {
                            customer.login(cName, cPass);
                            customer.customerMenu();
                        } else {
                            System.out.println("Invalid customer username or password.");
                        }
                    } else if (custOpt == 2) {
                        System.out.print("Enter ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter Username: ");
                        String uname = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String pass = scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String addr = scanner.nextLine();

                        database.writeCustomer(id, name, phone, uname, pass, addr);
                        System.out.println("Registration complete! You can now log in.");
                    }
                    break;

                case 3:
                    displayAllAvailableRooms();
                    break;

                case 4:
                    running = false;
                    System.out.println("System shutting down. Have a great day!");
                    break;

                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
    }
}