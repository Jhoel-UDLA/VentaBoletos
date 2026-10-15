package ventaboletos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ventaboletos.logica.SistemaVentas;
import ventaboletos.modelo.Ruta;
import ventaboletos.modelo.Venta;

/**
 * Interfaz gráfica para la venta de boletos.
 */
public class VentanaVentas extends JFrame {

    private final SistemaVentas sistema = new SistemaVentas();

    private JComboBox<Ruta> cmbRuta;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JButton btnComprar;
    private JTextArea txtAreaVentas;

    // Labels de estadísticas (uno por ruta)
    private JLabel[] lblVendidos;
    private JLabel[] lblDisponibles;
    private JLabel lblTotalRecaudado;

    public VentanaVentas() {
        super("Venta de Boletos - Cooperativa de Transporte");
        initComponentes();
        actualizarLabels();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 600);
        setLocationRelativeTo(null);
    }

    private void initComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- Formulario de compra ----------
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la compra"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        cmbRuta = new JComboBox<>();
        for (Ruta r : sistema.getRutas()) {
            cmbRuta.addItem(r);
        }
        txtCedula = new JTextField(15);
        txtNombre = new JTextField(15);
        txtCantidad = new JTextField(5);
        btnComprar = new JButton("Comprar");

        agregarFila(panelFormulario, g, 0, "Ruta:", cmbRuta);
        agregarFila(panelFormulario, g, 1, "Cédula:", txtCedula);
        agregarFila(panelFormulario, g, 2, "Nombre:", txtNombre);
        agregarFila(panelFormulario, g, 3, "N° de boletos (1-5):", txtCantidad);

        g.gridx = 1;
        g.gridy = 4;
        panelFormulario.add(btnComprar, g);

        // ---------- Estadísticas (Labels) ----------
        int n = sistema.getRutas().size();
        lblVendidos = new JLabel[n];
        lblDisponibles = new JLabel[n];

        JPanel panelEstadisticas = new JPanel(new GridLayout(n + 2, 3, 5, 5));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Resumen"));
        panelEstadisticas.add(negrita(new JLabel("Ruta")));
        panelEstadisticas.add(negrita(new JLabel("Vendidos")));
        panelEstadisticas.add(negrita(new JLabel("Disponibles")));
        for (int i = 0; i < n; i++) {
            panelEstadisticas.add(new JLabel(sistema.getRutas().get(i).getNombre()));
            lblVendidos[i] = new JLabel();
            lblDisponibles[i] = new JLabel();
            panelEstadisticas.add(lblVendidos[i]);
            panelEstadisticas.add(lblDisponibles[i]);
        }
        panelEstadisticas.add(negrita(new JLabel("Total recaudado:")));
        lblTotalRecaudado = negrita(new JLabel());
        lblTotalRecaudado.setForeground(new Color(0, 120, 0));
        panelEstadisticas.add(lblTotalRecaudado);
        panelEstadisticas.add(new JLabel());

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelSuperior.add(panelFormulario);
        panelSuperior.add(panelEstadisticas);

        // ---------- Detalle de compras (TextArea) ----------
        txtAreaVentas = new JTextArea();
        txtAreaVentas.setEditable(false);
        txtAreaVentas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(txtAreaVentas);
        scroll.setBorder(BorderFactory.createTitledBorder("Compras realizadas"));

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        setContentPane(panelPrincipal);

        // ---------- Eventos ----------
        btnComprar.addActionListener(e -> comprar());
        txtCantidad.addActionListener(e -> comprar()); // Enter en el campo de boletos
    }

    private void agregarFila(JPanel panel, GridBagConstraints g, int fila, String texto,
            java.awt.Component campo) {
        g.gridx = 0;
        g.gridy = fila;
        g.weightx = 0;
        panel.add(new JLabel(texto), g);
        g.gridx = 1;
        g.weightx = 1;
        panel.add(campo, g);
    }

    private JLabel negrita(JLabel lbl) {
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
        return lbl;
    }

    /** Acción del botón "Comprar". */
    private void comprar() {
        try {
            Venta venta = sistema.comprar(
                    (Ruta) cmbRuta.getSelectedItem(),
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtCantidad.getText());

            txtAreaVentas.append(sistema.getVentas().size() + ". " + venta.getDetalle() + "\n");
            actualizarLabels();
            limpiarCampos();
            JOptionPane.showMessageDialog(this,
                    String.format("Compra registrada.%nTotal a pagar: $%.2f", venta.getTotal()),
                    "Compra exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Actualiza los labels de vendidos, disponibles y total recaudado. */
    private void actualizarLabels() {
        for (int i = 0; i < sistema.getRutas().size(); i++) {
            Ruta r = sistema.getRutas().get(i);
            lblVendidos[i].setText(String.valueOf(r.getBoletosVendidos()));
            lblDisponibles[i].setText(String.valueOf(r.getBoletosDisponibles()));
            lblDisponibles[i].setForeground(r.getBoletosDisponibles() == 0 ? Color.RED : Color.BLACK);
        }
        lblTotalRecaudado.setText(String.format("$%.2f", sistema.getTotalRecaudado()));
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtCedula.requestFocus();
    }
}
