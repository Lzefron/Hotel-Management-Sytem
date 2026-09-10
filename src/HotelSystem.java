import java.util.ArrayList;
import java.util.List;

public class HotelSystem {
    private List<Room> roomList;
    private List<Customer> customerList;
    private List<Booking> bookingList;
    private List<Admin> adminList;

    // Creates the empty lists and adds one default admin so the system can be used.
    public HotelSystem() {
        roomList = new ArrayList<Room>();
        customerList = new ArrayList<Customer>();
        bookingList = new ArrayList<Booking>();
        adminList = new ArrayList<Admin>();

        adminList.add(new Admin("A1", "Main Admin", "0000000000", "admin", "admin123"));
    }

    // ---------- Room methods ----------

    // Adds a new room to the list. Returns false if the room number already exists.
    public boolean addRoom(Room room) {
        if (findRoomByNumber(room.getRoomNumber()) != null) {
            return false;
        }
        roomList.add(room);
        return true;
    }

    // Finds a room by its number. Returns null if no such room exists.
    public Room findRoomByNumber(int roomNumber) {
        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Returns a list of all rooms that are currently free.
    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<Room>();
        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);
            if (room.checkAvailability()) {
                available.add(room);
            }
        }
        return available;
    }

    // Returns the full list of rooms.
    public List<Room> getRoomList() {
        return roomList;
    }

    // ---------- Customer methods ----------

    // Adds a new customer. Returns false if the username is already taken.
    public boolean addCustomer(Customer customer) {
        if (findCustomerByUsername(customer.getUsername()) != null) {
            return false;
        }
        customerList.add(customer);
        return true;
    }

    // Finds a customer by username. Returns null if not found.
    public Customer findCustomerByUsername(String username) {
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    // Checks customer username and password. Returns the customer if correct, else null.
    public Customer loginCustomer(String username, String password) {
        Customer customer = findCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

    // Makes a new customer id like C1, C2, C3 based on how many customers exist.
    public String generateCustomerId() {
        return "C" + (customerList.size() + 1);
    }

    // Returns the full list of customers.
    public List<Customer> getCustomerList() {
        return customerList;
    }

    // ---------- Admin methods ----------

    // Adds a new admin. Returns false if the username is already taken.
    public boolean addAdmin(Admin admin) {
        if (findAdminByUsername(admin.getUsername()) != null) {
            return false;
        }
        adminList.add(admin);
        return true;
    }

    // Finds an admin by username. Returns null if not found.
    public Admin findAdminByUsername(String username) {
        for (int i = 0; i < adminList.size(); i++) {
            Admin admin = adminList.get(i);
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        return null;
    }

    // Checks admin username and password. Returns the admin if correct, else null.
    public Admin loginAdmin(String username, String password) {
        Admin admin = findAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    // Makes a new admin id like A1, A2, A3 based on how many admins exist.
    public String generateAdminId() {
        return "A" + (adminList.size() + 1);
    }

    // ---------- Booking methods ----------

    // Books a room for a customer. Returns the new booking, or null if the room is not free.
    public Booking bookRoom(Customer customer, int roomNumber, String checkInDate, int durationDays) {
        Room room = findRoomByNumber(roomNumber);
        if (room == null || !room.checkAvailability()) {
            return null;
        }
        String bookingId = "B" + (bookingList.size() + 1);
        Booking booking = new Booking(bookingId, customer.getId(), roomNumber, checkInDate, durationDays, false);
        bookingList.add(booking);
        room.setAvailability(false);
        return booking;
    }

    // Returns all bookings that belong to the given customer.
    public List<Booking> getBookingsForCustomer(Customer customer) {
        List<Booking> result = new ArrayList<Booking>();
        for (int i = 0; i < bookingList.size(); i++) {
            Booking booking = bookingList.get(i);
            if (booking.getCustomerId().equals(customer.getId())) {
                result.add(booking);
            }
        }
        return result;
    }

    // Returns the full list of bookings.
    public List<Booking> getBookingList() {
        return bookingList;
    }

    // ---------- File methods (to be done by another team member) ----------

    // Loads rooms, customers and bookings from files.
    public void loadDataFromFiles() {
    }

    // Saves rooms, customers and bookings to files.
    public void saveDataToFiles() {
    }
}
