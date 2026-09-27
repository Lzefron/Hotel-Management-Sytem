import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HotelGUI {

    private static final Font TITLE_FONT    = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font BUTTON_FONT   = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font LABEL_FONT    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font INPUT_FONT    = new Font("Segoe UI", Font.PLAIN, 12);

    private Database database;
    private List<Room> roomInventory;

    public HotelGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }

        this.database = new Database();
        this.roomInventory = buildDefaultRooms();
    }

    private List<Room> buildDefaultRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(101, "Economy", "Single Bed", 50.0, true));
        rooms.add(new Room(102, "Standard", "Double Bed", 90.0, true));
        rooms.add(new Room(201, "Deluxe", "Twin Bed", 140.0, true));
        rooms.add(new Room(301, "Executive VIP Suite", "King Size Bed", 250.0, true));
        rooms.add(new Room(401, "Presidential Penthouse", "Master Suite King Bed", 500.0, true));
        return rooms;
    }

    public void launchApp() {
        SwingUtilities.invokeLater(this::showWelcomeWindow);
    }


    private void showWelcomeWindow() {
        JFrame frame = new JFrame("Grand Hotel Management System");
        frame.setSize(440, 360);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));


        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        JLabel lblTitle = new JLabel("WELCOME TO GRAND HOTEL", SwingConstants.CENTER);
        lblTitle.setFont(TITLE_FONT);

        JLabel lblSubtitle = new JLabel("Select your portal to continue", SwingConstants.CENTER);
        lblSubtitle.setFont(SUBTITLE_FONT);
        lblSubtitle.setForeground(Color.DARK_GRAY);

        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);


        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnCustomerPortal = createStandardButton("Customer Portal (Login)");
        JButton btnRegisterCustomer = createStandardButton("Register New Customer Account");
        JButton btnAdminPortal = createStandardButton("Administrator Portal");
        JButton btnExit = createStandardButton("Exit System");

        buttonPanel.add(btnCustomerPortal);
        buttonPanel.add(btnRegisterCustomer);
        buttonPanel.add(btnAdminPortal);
        buttonPanel.add(btnExit);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);


        btnCustomerPortal.addActionListener(e -> {
            frame.dispose();
            showCustomerLoginWindow();
        });

        btnRegisterCustomer.addActionListener(e -> showRegistrationDialog(frame));

        btnAdminPortal.addActionListener(e -> {
            frame.dispose();
            showAdminLoginWindow();
        });

        btnExit.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }


    private void showCustomerLoginWindow() {
        JFrame frame = new JFrame("Customer Portal - Authentication");
        frame.setSize(380, 240);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("CUSTOMER LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(TITLE_FONT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(LABEL_FONT);
        panel.add(lblUser, gbc);

        JTextField txtUsername = new JTextField(15);
        txtUsername.setFont(INPUT_FONT);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(LABEL_FONT);
        panel.add(lblPass, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(INPUT_FONT);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        JButton btnLogin = createStandardButton("Login");
        JButton btnBack = createStandardButton("Back");

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        btnPanel.add(btnLogin);
        btnPanel.add(btnBack);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        frame.add(panel);

        btnLogin.addActionListener(e -> {
            String uname = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            Customer customer = database.authenticateCustomer(uname, pass);
            if (customer != null) {
                customer.login(uname, pass);
                frame.dispose();
                showCustomerDashboard(customer);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Customer username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> {
            frame.dispose();
            showWelcomeWindow();
        });

        frame.setVisible(true);
    }


    private void showAdminLoginWindow() {
        JFrame frame = new JFrame("Admin Portal - Authentication");
        frame.setSize(380, 240);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("ADMINISTRATOR LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(TITLE_FONT);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblUser = new JLabel("Admin Username:");
        lblUser.setFont(LABEL_FONT);
        panel.add(lblUser, gbc);

        JTextField txtUsername = new JTextField(15);
        txtUsername.setFont(INPUT_FONT);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPass = new JLabel("Admin Password:");
        lblPass.setFont(LABEL_FONT);
        panel.add(lblPass, gbc);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(INPUT_FONT);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtPassword, gbc);

        JButton btnLogin = createStandardButton("Authenticate");
        JButton btnBack = createStandardButton("Back");

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        btnPanel.add(btnLogin);
        btnPanel.add(btnBack);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        frame.add(panel);

        btnLogin.addActionListener(e -> {
            String uname = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            if (database.verifyAdminCredentials(uname, pass)) {
                Admin admin = new Admin("ADM-01", "System Admin", "000-000", uname, pass);
                admin.login(uname, pass);
                frame.dispose();
                showAdminDashboard(admin);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Admin Credentials.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> {
            frame.dispose();
            showWelcomeWindow();
        });

        frame.setVisible(true);
    }


    private void showRegistrationDialog(Component parentComponent) {
        JTextField txtName = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtRegUname = new JTextField();
        JPasswordField txtRegPass = new JPasswordField();
        JPasswordField txtRegPassConfirm = new JPasswordField();
        JTextField txtAddr = new JTextField();

        String autoId = database.generateNextCustomerId();

        Object[] formFields = {
                "System ID: " + autoId,
                "Full Name:", txtName,
                "Phone Number:", txtPhone,
                "Username:", txtRegUname,
                "Password:", txtRegPass,
                "Re-enter Password:", txtRegPassConfirm,
                "Address:", txtAddr
        };

        int option = JOptionPane.showConfirmDialog(parentComponent, formFields, "New Customer Registration", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String uname = txtRegUname.getText().trim();
            String pass1 = new String(txtRegPass.getPassword()).trim();
            String pass2 = new String(txtRegPassConfirm.getPassword()).trim();
            String addr = txtAddr.getText().trim();

            if (name.isEmpty() || uname.isEmpty() || pass1.isEmpty()) {
                JOptionPane.showMessageDialog(parentComponent, "All required fields must be completed.", "Form Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(parentComponent, "Passwords do not match! Check and try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean registered = database.writeCustomerRecord(autoId, name, phone, uname, pass1, addr);
            if (registered) {
                JOptionPane.showMessageDialog(parentComponent, "Registration successful! You can now log in via Customer Portal.");
            } else {
                JOptionPane.showMessageDialog(parentComponent, "Username already registered. Try another username.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showCustomerDashboard(Customer customer) {
        JFrame frame = new JFrame("Customer Portal - Welcome, " + customer.getName());
        frame.setSize(800, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);


        JPanel bookPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        bookPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> roomCombo = new JComboBox<>();
        roomCombo.setFont(INPUT_FONT);
        for (Room r : roomInventory) {
            roomCombo.addItem(r.getDisplayLabel());
        }

        JTextField txtDate = new JTextField("2026-10-01");
        txtDate.setFont(INPUT_FONT);
        JTextField txtDays = new JTextField("3");
        txtDays.setFont(INPUT_FONT);

        JButton btnSubmitBooking = createStandardButton("Confirm Reservation");

        bookPanel.add(createStandardLabel("Select Room & Bed Tier:"));
        bookPanel.add(roomCombo);
        bookPanel.add(createStandardLabel("Check-in Date (YYYY-MM-DD):"));
        bookPanel.add(txtDate);
        bookPanel.add(createStandardLabel("Duration (Days):"));
        bookPanel.add(txtDays);
        bookPanel.add(new JLabel(""));
        bookPanel.add(btnSubmitBooking);


        JPanel historyPanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Booking ID", "Username", "Room No", "Room Type", "Price/Night", "Check-In", "Days", "Total Cost"}, 0
        );
        JTable table = new JTable(tableModel);
        table.setFont(INPUT_FONT);
        historyPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblSpent = createStandardLabel("Total Spent: $0.0");
        JButton btnRefresh = createStandardButton("Refresh Records");
        bottomPanel.add(lblSpent);
        bottomPanel.add(btnRefresh);
        historyPanel.add(bottomPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Reserve Room", bookPanel);
        tabbedPane.addTab("My Personal Bookings", historyPanel);


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblUser = createStandardLabel("Logged in Username: " + customer.getUsername() + " (" + customer.getId() + ")");
        lblUser.setFont(TITLE_FONT);
        topPanel.add(lblUser, BorderLayout.WEST);

        JButton btnLogout = createStandardButton("Logout");
        topPanel.add(btnLogout, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        Runnable refreshData = () -> {
            tableModel.setRowCount(0);
            List<Booking> list = customer.getMyBookings();
            for (Booking b : list) {
                tableModel.addRow(new Object[]{
                        b.getBookingId(), b.getUsername(), b.getRoomNumber(),
                        b.getRoomType(), "$" + b.getPricePerNight(), b.getCheckInDate(),
                        b.getDurationDays(), "$" + b.calculateTotalBill()
                });
            }
            lblSpent.setText("Total Account Expenditure: $" + customer.calculateTotalSpent());
        };

        btnSubmitBooking.addActionListener(e -> {
            try {
                int selectedIndex = roomCombo.getSelectedIndex();
                Room room = roomInventory.get(selectedIndex);
                String date = txtDate.getText().trim();
                int days = Integer.parseInt(txtDays.getText().trim());

                boolean success = customer.createReservation(room, date, days);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Booking Confirmed for Room #" + room.getRoomNumber());
                    refreshData.run();
                } else {
                    JOptionPane.showMessageDialog(frame, "Booking Failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid integer for stay duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener(e -> refreshData.run());

        btnLogout.addActionListener(e -> {
            customer.logout();
            frame.dispose();
            showWelcomeWindow();
        });

        refreshData.run();
        frame.setVisible(true);
    }


    private void showAdminDashboard(Admin admin) {
        JFrame frame = new JFrame("Admin Control Console - Logged in as: " + admin.getUsername());
        frame.setSize(880, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);


        JPanel globalBookingsPanel = new JPanel(new BorderLayout());
        DefaultTableModel bookingsModel = new DefaultTableModel(
                new String[]{"Booking ID", "Username", "Room No", "Room Type", "Price/Night", "Date", "Days", "Total"}, 0
        );
        JTable bookingsTable = new JTable(bookingsModel);
        bookingsTable.setFont(INPUT_FONT);
        globalBookingsPanel.add(new JScrollPane(bookingsTable), BorderLayout.CENTER);

        JPanel bookingsFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblRevenue = createStandardLabel("Total Revenue: $0.0");
        JButton btnRefreshBookings = createStandardButton("Refresh Global Logs");
        bookingsFooter.add(lblRevenue);
        bookingsFooter.add(btnRefreshBookings);
        globalBookingsPanel.add(bookingsFooter, BorderLayout.SOUTH);


        JPanel customersPanel = new JPanel(new BorderLayout());
        DefaultTableModel customersModel = new DefaultTableModel(
                new String[]{"Customer ID", "Name", "Phone", "Username", "Address"}, 0
        );
        JTable customersTable = new JTable(customersModel);
        customersTable.setFont(INPUT_FONT);
        customersPanel.add(new JScrollPane(customersTable), BorderLayout.CENTER);
        JButton btnRefreshCustomers = createStandardButton("Refresh Directory");
        customersPanel.add(btnRefreshCustomers, BorderLayout.SOUTH);


        JPanel walkInPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        walkInPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtTargetUsername = new JTextField();
        txtTargetUsername.setFont(INPUT_FONT);
        JComboBox<String> roomCombo = new JComboBox<>();
        roomCombo.setFont(INPUT_FONT);
        for (Room r : roomInventory) {
            roomCombo.addItem(r.getDisplayLabel());
        }

        JTextField txtDate = new JTextField("2026-10-01");
        txtDate.setFont(INPUT_FONT);
        JTextField txtDays = new JTextField("2");
        txtDays.setFont(INPUT_FONT);
        JButton btnBookForCustomer = createStandardButton("Process Reservation");

        walkInPanel.add(createStandardLabel("Target Customer Username:"));
        walkInPanel.add(txtTargetUsername);
        walkInPanel.add(createStandardLabel("Select Room Tier:"));
        walkInPanel.add(roomCombo);
        walkInPanel.add(createStandardLabel("Check-in Date (YYYY-MM-DD):"));
        walkInPanel.add(txtDate);
        walkInPanel.add(createStandardLabel("Duration (Days):"));
        walkInPanel.add(txtDays);
        walkInPanel.add(new JLabel(""));
        walkInPanel.add(btnBookForCustomer);

        tabbedPane.addTab("Global Booking Logs", globalBookingsPanel);
        tabbedPane.addTab("Customer Directory", customersPanel);
        tabbedPane.addTab("Walk-in Reservation", walkInPanel);

        // Header Navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblHeader = createStandardLabel("ADMINISTRATION CONTROL CONSOLE");
        lblHeader.setFont(TITLE_FONT);
        topPanel.add(lblHeader, BorderLayout.WEST);

        JButton btnLogout = createStandardButton("Logout");
        topPanel.add(btnLogout, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        Runnable refreshBookings = () -> {
            bookingsModel.setRowCount(0);
            List<Booking> list = admin.getAllGlobalBookings();
            for (Booking b : list) {
                bookingsModel.addRow(new Object[]{
                        b.getBookingId(), b.getUsername(), b.getRoomNumber(),
                        b.getRoomType(), "$" + b.getPricePerNight(), b.getCheckInDate(),
                        b.getDurationDays(), "$" + b.calculateTotalBill()
                });
            }
            lblRevenue.setText("Total Revenue Generated: $" + admin.calculateTotalRevenue());
        };

        Runnable refreshCustomers = () -> {
            customersModel.setRowCount(0);
            List<Customer> list = admin.getAllRegisteredCustomers();
            for (Customer c : list) {
                customersModel.addRow(new Object[]{
                        c.getId(), c.getName(), c.getPhone(), c.getUsername(), c.getAddress()
                });
            }
        };

        btnRefreshBookings.addActionListener(e -> refreshBookings.run());
        btnRefreshCustomers.addActionListener(e -> refreshCustomers.run());

        btnBookForCustomer.addActionListener(e -> {
            try {
                String targetUser = txtTargetUsername.getText().trim();
                if (targetUser.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Target Username cannot be blank.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Room room = roomInventory.get(roomCombo.getSelectedIndex());
                String date = txtDate.getText().trim();
                int days = Integer.parseInt(txtDays.getText().trim());

                boolean success = admin.createWalkInReservation(targetUser, room, date, days);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Walk-in reservation written for user: " + targetUser);
                    refreshBookings.run();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to write record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers for duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLogout.addActionListener(e -> {
            admin.logout();
            frame.dispose();
            showWelcomeWindow();
        });

        refreshBookings.run();
        refreshCustomers.run();
        frame.setVisible(true);
    }


    private JButton createStandardButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel createStandardLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(LABEL_FONT);
        return lbl;
    }
}