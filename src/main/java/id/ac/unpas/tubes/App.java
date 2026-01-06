package id.ac.unpas.tubes;

import id.ac.unpas.tubes.view.MainFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Menjalankan Swing di Event Dispatch Thread (Best Practice)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}