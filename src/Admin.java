public class Admin extends User {

    private String adminLevel;
    private String department;

    public Admin(String id, String name, String phone, String username, String password) {
        super(id, name, phone, username, password);
        this.department = "General";
        this.adminLevel = "Standard";
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public boolean login(String username, String password) {
        // Check if credentials match (using getters from User class)
        if (getUsername().equals(username) && getPassword().equals(password)) {
            System.out.println("Admin " + getName() + " logged in successfully.");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    @Override
    public void logout() {
        System.out.println("Admin " + getUsername() + " logged out successfully.");
    }


    public void addRoom() {
        // Just a simple method - actual adding is done by HotelSystem
        System.out.println("Admin " + getName() + " is adding a new room.");
        System.out.println("Room addition process initiated.");
    }

    public void viewAllCustomers() {
        System.out.println("Admin " + getName() + " is viewing all customers.");
    }

    public void viewAllBookings() {
        System.out.println("Admin " + getName() + " is viewing all bookings.");
    }

    public boolean addAdmin(Admin newAdmin) {
        System.out.println("Admin " + getName() + " is adding new admin: " + newAdmin.getName());
        return true;
    }
}
