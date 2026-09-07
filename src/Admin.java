public class Admin extends User {

    public Admin(String id, String name, String phone, String username, String password) {
        super(id, name, phone, username, password);
    }

    public boolean addAdmin(String id, String name, String phone, String username, String password) {
        return true;
    }

    public boolean login(String user, String pass) {
        return false;
    }

    public void addRoom() {
    }

    public void viewAllCustomers() {
    }

    public void viewAllBookings() {
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}