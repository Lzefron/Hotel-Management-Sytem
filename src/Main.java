import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelGUI appGUI = new HotelGUI();
            appGUI.launchApp();
        });
    }
}