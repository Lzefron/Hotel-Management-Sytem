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
        if (inputUsername != null && inputPassword != null
                && this.username.equals(inputUsername.trim())
                && this.password.equals(inputPassword.trim())) {
            this.isLoggedIn = true;
            return true;
        }
        this.isLoggedIn = false;
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}