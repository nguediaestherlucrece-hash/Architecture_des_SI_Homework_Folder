import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Lance l'interface graphique Swing sur le thread dédié (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
