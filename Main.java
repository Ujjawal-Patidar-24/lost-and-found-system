import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // ── 1. Initialise database (creates lost_and_found.db if absent) ──────
        DataStore.init();

        // ── 2. Launch Swing UI on the Event Dispatch Thread ───────────────────
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new HomeScreen().setVisible(true);
        });
    }
}
