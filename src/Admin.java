import java.util.Scanner;

public class Admin extends User {

    private Database db = new Database();

    public Admin(String id, String name, String phone, String username, String password) {
        super(id, name, phone, username, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void adminMenu() {
        if (!isLoggedIn) {
            System.out.println("Access Denied: Admin must be logged in.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean inAdminSession = true;

        while (inAdminSession && isLoggedIn) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Add Another Admin");
            System.out.println("2. View All Registered Customers");
            System.out.println("3. View All Hotel Bookings");
            System.out.println("4. Book Room for Customer (Phone/Walk-in)");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter New Admin ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter Username: ");
                    String uname = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = scanner.nextLine();
                    addAdmin(id, name, phone, uname, pass);
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    viewAllBookings();
                    break;
                case 4:
                    bookRoomForCustomer(scanner);
                    break;
                case 5:
                    logout();
                    inAdminSession = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void bookRoomForCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();
        System.out.print("Enter Room Number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Duration (days): ");
        int days = scanner.nextInt();
        scanner.nextLine();

        String bookingId = "BK-" + (1000 + (int) (Math.random() * 9000));
        db.writeBooking(bookingId, custId, roomNum, date, days);
        System.out.println("Booking successfully created by Admin. Booking ID: " + bookingId);
    }

    public boolean addAdmin(String id, String name, String phone, String username, String password) {
        if (!isLoggedIn) return false;
        db.writeAdmin(username, password);
        return true;
    }

    public void viewAllCustomers() {
        if (isLoggedIn) db.readAllCustomers();
    }

    public void viewAllBookings() {
        if (isLoggedIn) db.viewAllBookings();
    }
}