public class Customer extends User {
    private String address;

    public Customer(String id, String name, String phone, String username, String password, String address) {
        super(id, name, phone, username, password);
        this.address = address;
    }

    public boolean register(String id, String name, String phone, String username, String password, String address) {
        return true;
    }

    public boolean login(String username, String password) {
        return true;
    }

    public void searchAvailableRooms() {
    }

    public void bookRoom() {
    }

    public void viewMyBookings() {
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    // Returns the customer's address.
    public String getAddress() {
        return address;
    }
}
