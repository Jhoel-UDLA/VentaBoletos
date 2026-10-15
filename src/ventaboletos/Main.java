package ventaboletos;

import javax.swing.SwingUtilities;
import ventaboletos.vista.VentanaVentas;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaVentas().setVisible(true));
    }
}
