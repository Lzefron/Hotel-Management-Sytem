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
}