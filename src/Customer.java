import java.util.Scanner;

public class Customer extends User {

    private String address;
    private int roomNumber;
    private String bookRoomID;
    private String arrivalDate;
    private int stayTime;

    private Scanner scanner = new Scanner(System.in);
    private Database db = new Database();

    public Customer(String id, String name, String phone, String username, String password, String address) {
        super(id, name, phone, username, password);
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void displayWelcomePortal() {
        System.out.println("==================================================");
        System.out.println("       WELCOME TO THE GRAND HOTEL SYSTEM          ");
        System.out.println("==================================================");
        System.out.println("Location: Downtown Central | Contact: +880-1700000000");
        System.out.println("Amenities: Free Wi-Fi, 24/7 Room Service, Swimming Pool");
        System.out.println("--------------------------------------------------");
    }

    public void customerMenu() {
        displayWelcomePortal();

        boolean running = true;
        while (running) {
            if (!isLoggedIn) {
                System.out.println("\n1. Create Account (Register)");
                System.out.println("2. Login");
                System.out.println("3. Exit Portal");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerCustomerAccount();
                        break;
                    case 2:
                        System.out.print("Enter Username: ");
                        String uname = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String pass = scanner.nextLine();
                        login(uname, pass);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Thank you for visiting!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } else {
                System.out.println("\n===== CUSTOMER DASHBOARD =====");
                System.out.println("1. Search Available Rooms");
                System.out.println("2. Book a Room");
                System.out.println("3. View My Active Bookings");
                System.out.println("4. View Bill & Stay Details");
                System.out.println("5. Logout");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        searchAvailableRooms();
                        break;
                    case 2:
                        bookRoom();
                        break;
                    case 3:
                        viewMyBookings();
                        break;
                    case 4:
                        viewStayDetailsAndBill();
                        break;
                    case 5:
                        logout();
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    public void registerCustomerAccount() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Username: ");
        String uname = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter Address: ");
        String addr = scanner.nextLine();

        db.writeCustomer(id, name, phone, uname, pass, addr);
        System.out.println("Registration complete! You can now log in.");
    }

    public boolean searchAvailableRooms() {
        if (!isLoggedIn) {
            System.out.println("Access denied. Please log in first.");
            return false;
        }

        System.out.print("Enter room number to search (101, 102, 201, etc.): ");
        roomNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Room " + roomNumber + " is available for reservation.");
        return true;
    }

    public boolean bookRoom() {
        if (!isLoggedIn) {
            System.out.println("Access denied. Please log in first.");
            return false;
        }

        boolean isAvailable = searchAvailableRooms();
        if (isAvailable) {
            System.out.println("Confirm booking for Room " + roomNumber + "? (1. Yes / 2. No): ");
            int confirm = scanner.nextInt();
            scanner.nextLine();

            if (confirm == 1) {
                System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
                arrivalDate = scanner.nextLine();
                System.out.print("Enter Stay Duration (days): ");
                stayTime = scanner.nextInt();
                scanner.nextLine();

                bookRoomID = "BK-" + (1000 + (int) (Math.random() * 9000));
                db.writeBooking(bookRoomID, getId(), roomNumber, arrivalDate, stayTime);

                System.out.println("Booking successful! Assigned Booking ID: " + bookRoomID);
                return true;
            }
        }
        return false;
    }

    public void viewMyBookings() {
        if (!isLoggedIn) {
            System.out.println("Access denied. Please log in first.");
            return;
        }

        if (bookRoomID != null) {
            System.out.println("\n--- ACTIVE BOOKING ---");
            System.out.println("Booking Ref : " + bookRoomID);
            System.out.println("Guest Name  : " + getName());
            System.out.println("Room Number : " + roomNumber);
            System.out.println("Check-in    : " + arrivalDate);
            System.out.println("Duration    : " + stayTime + " days");
        } else {
            db.viewAllBookings();
        }
    }

    public void viewStayDetailsAndBill() {
        if (!isLoggedIn) {
            System.out.println("Access denied. Please log in first.");
            return;
        }

        if (stayTime > 0) {
            double rate = 100.0;
            double total = stayTime * rate;

            System.out.println("\n--- STAY & BILL SUMMARY ---");
            System.out.println("Guest Name : " + getName());
            System.out.println("Room Number: " + roomNumber);
            System.out.println("Nights     : " + stayTime);
            System.out.println("Nightly Rate: $" + rate);
            System.out.println("Total Due  : $" + total);
        } else {
            System.out.println("No active stay found for billing calculation.");
        }
    }
}