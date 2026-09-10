public abstract class User {
    private String id;
    private String name;
    private String phone;
    private String username;
    private String password;

    public User(String id, String name, String phone, String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public void logout() {
    }

    public abstract String getRole();

    // Returns the user's id.
    public String getId() {
        return id;
    }

    // Returns the user's name.
    public String getName() {
        return name;
    }

    // Returns the user's phone number.
    public String getPhone() {
        return phone;
    }

    // Returns the user's username.
    public String getUsername() {
        return username;
    }

    // Returns the user's password. Used to check login.
    public String getPassword() {
        return password;
    }
}
