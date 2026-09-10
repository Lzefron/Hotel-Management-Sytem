import java.util.Scanner;

public class Customer extends User{

    Scanner s = new Scanner(System.in);

    private String address;
    public int roomNumber;
    public String bookRoomID;
    public String customerCode;
    public String arrivalDate;
    public int stayTime;

    public Customer(String id, String name, String phone, String username, String password, String address) {
        super(id, name, phone, username, password);
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public boolean register(){
        System.out.println("The customer name as " + getName());
        System.out.println("His/Her ID: " + getId());
        System.out.println("His/Her phone: " + getPhone());
        System.out.println("His/Her username: " + getUsername());
        System.out.println("has been registered in the hotel");

        return true;
    }

    public boolean login(String inputUsername, String inputPassword) {
        if (getUsername().equals(inputUsername) && getPassword().equals(inputPassword)) {
            System.out.println("The user " + getUsername() + " is logged in now.");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    @Override
    public void logout() {
        System.out.println("User " + getUsername() + " logged out now.");
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public boolean searchAvailableRooms(){
        System.out.print("Enter your desired room number = ");
        roomNumber = s.nextInt();
        s.nextLine();

        if(roomNumber<0 || roomNumber>900){
            System.out.println("Invalid room Number");
            return false;
        }

        else if(roomNumber>210 && roomNumber<220){
            System.out.println("Room is available");
            return true;
        }

        else{
            System.out.println("Room is not available");
            return false;
        }
    }

    public boolean bookRoom() {
        boolean isAvailable = searchAvailableRooms();

        if (isAvailable) {
            System.out.println("Do you want to book this room?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice (1 or 2): ");
            int choice = s.nextInt();
            s.nextLine();

            if (choice == 1) {
                System.out.println("Room " + roomNumber + " has been successfully booked!");
                return true;
            }

            else if (choice == 2) {
                System.out.println("Booking canceled. Returning to main menu.");
                return false;
            }

            else {
                System.out.println("Invalid choice. Booking aborted.");
                return false;
            }
        }
        return false;
    }

    public void viewMyBookings(String bookRoomID, String customerCode, String arrivalDate, int stayTime) {
        boolean isbooked = bookRoom();

        if (isbooked) {
            this.bookRoomID = bookRoomID;
            this.customerCode = customerCode;
            this.arrivalDate = arrivalDate;
            this.stayTime = stayTime;

            System.out.println("Customer's booking ID: " + this.bookRoomID);
            System.out.println("His/ Her customer ID: " + this.customerCode);
            System.out.println("His/ Her checkInDate: " + this.arrivalDate);
            System.out.println("His/ Her durationDays: " + this.stayTime);
            System.out.println("has been booked room number: " + this.roomNumber);
        }
        else {
            System.out.println("Booking is cancelled");
        }
    }
}
