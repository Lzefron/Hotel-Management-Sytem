public abstract class User {
    private String id;
    private String name;
    private String phone;
    private String username;
    private String password;
    protected boolean isLoggedIn;

    public User(String id, String name, String phone, String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
    }

    public abstract String getRole();

    public boolean login(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            this.isLoggedIn = true;
            System.out.println(getRole() + " " + name + " logged in successfully.");
            return true;
        } else {
            this.isLoggedIn = false;
            System.out.println("Invalid credentials for " + inputUsername);
            return false;
        }
    }

    public void logout() {
        this.isLoggedIn = false;
        System.out.println(getRole() + " " + username + " logged out.");
    }

    public boolean isUserLoggedIn() {
        return isLoggedIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}