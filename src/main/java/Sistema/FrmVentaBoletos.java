package Sistema;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * formulario para la venta de boletos de las 3 rutas.
 * el diseño esta en FrmVentaBoletos.form (gui designer de intellij).
 *
 * integrantes:
 * - iván alejandro montero bravo
 * - jhoel núñez pillajo
 * - jhandry orozco
 * - mateo gaibor balseca
 */
public class FrmVentaBoletos extends JFrame {

    // componentes del formulario (enlazados con el .form)
    private JPanel panelPrincipal;
    private JPanel pnlCompra;
    private JPanel pnlResumen;
    private JPanel pnlBuses;
    private JComboBox<String> cmbRuta;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtBoletos;
    private JButton btnComprar;
    private JLabel lblVendidosGuayaquil;
    private JLabel lblVendidosCuenca;
    private JLabel lblVendidosLoja;
    private JLabel lblDisponiblesGuayaquil;
    private JLabel lblDisponiblesCuenca;
    private JLabel lblDisponiblesLoja;
    private JLabel lblTotal;
    private JButton btnEmbarcarGuayaquil;
    private JButton btnEmbarcarCuenca;
    private JButton btnEmbarcarLoja;
    private JLabel lblSiguienteGuayaquil;
    private JLabel lblSiguienteCuenca;
    private JLabel lblSiguienteLoja;
    private JLabel lblColaGuayaquil;
    private JLabel lblColaCuenca;
    private JLabel lblColaLoja;
    private JTextArea txtAreaVentas;

    // maximo de boletos que puede tener una persona (por cedula)
    private final int MAX_BOLETOS = 5;

    // 3 buses diferentes, uno por ruta
    private Bus busGuayaquil = new Bus("quito - guayaquil");
    private Bus busCuenca = new Bus("quito - cuenca");
    private Bus busLoja = new Bus("quito - loja");

    // lista con todas las ventas (sirve para controlar los boletos por cedula,
    // porque cuando el pasajero embarca sale de la cola del bus)
    private ArrayList<Pasajeros> ventas = new ArrayList<>();

    private double totalRecaudado = 0;

    public FrmVentaBoletos() {
        setTitle("venta de boletos");
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // eventos de los botones
        btnComprar.addActionListener(e -> comprar());
        btnEmbarcarGuayaquil.addActionListener(e -> embarcar(busGuayaquil));
        btnEmbarcarCuenca.addActionListener(e -> embarcar(busCuenca));
        btnEmbarcarLoja.addActionListener(e -> embarcar(busLoja));

        actualizarLabels();
    }

    // accion del boton comprar
    private void comprar() {
        String ruta = cmbRuta.getSelectedItem().toString();
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim().toLowerCase();
        String textoBoletos = txtBoletos.getText().trim();

        // validar campos vacios
        if (cedula.isEmpty() || nombre.isEmpty() || textoBoletos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "llene todos los campos");
            return;
        }

        // validar cedula: 10 numeros y que sea una cedula ecuatoriana valida
        if (cedula.length() != 10 || !esNumero(cedula)) {
            JOptionPane.showMessageDialog(this, "la cédula debe tener 10 números");
            return;
        }
        if (!cedulaValida(cedula)) {
            JOptionPane.showMessageDialog(this, "la cédula ingresada no es una cédula ecuatoriana válida");
            return;
        }

        // validar que el numero de boletos sea un numero
        int boletos;
        try {
            boletos = Integer.parseInt(textoBoletos);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "el número de boletos debe ser un número entero");
            return;
        }

        // validar que sea entre 1 y 5 (no negativos, no cero, no mayor a 5)
        if (boletos < 1 || boletos > MAX_BOLETOS) {
            JOptionPane.showMessageDialog(this, "solo se puede comprar de 1 a 5 boletos");
            return;
        }

        // validar maximo 5 boletos por cedula, sumando lo que ya compro antes
        int yaTiene = boletosPorCedula(cedula);
        if (yaTiene + boletos > MAX_BOLETOS) {
            if (yaTiene >= MAX_BOLETOS) {
                JOptionPane.showMessageDialog(this, "la cédula " + cedula + " ya tiene 5 boletos, no puede comprar más");
            } else {
                JOptionPane.showMessageDialog(this, "la cédula " + cedula + " ya tiene " + yaTiene
                        + " boletos, solo puede comprar " + (MAX_BOLETOS - yaTiene) + " más");
            }
            return;
        }

        // validar asientos de la ruta (capacidad maxima del bus)
        Bus bus = busSeleccionado();
        if (bus.getBoletosDisponibles() == 0) {
            JOptionPane.showMessageDialog(this, "el bus " + ruta + " ya está lleno");
            return;
        }
        if (boletos > bus.getBoletosDisponibles()) {
            JOptionPane.showMessageDialog(this, "solo quedan " + bus.getBoletosDisponibles()
                    + " asientos en el bus " + ruta);
            return;
        }

        // registrar la venta: el pasajero entra a la cola del bus
        Pasajeros pasajero = new Pasajeros(nombre, cedula, ruta, boletos);
        bus.registrar(pasajero);
        ventas.add(pasajero);
        totalRecaudado = totalRecaudado + pasajero.getTotal();

        // mostrar la compra en el textarea: ruta, cantidad, nombre y precio total
        txtAreaVentas.append("venta -> " + pasajero.toString() + "\n");
        actualizarLabels();
        limpiar();
        JOptionPane.showMessageDialog(this, String.format("compra realizada%ntotal a pagar: $%.2f", pasajero.getTotal()));
    }

    // saca de la cola al primer pasajero del bus (el que ya subio)
    private void embarcar(Bus bus) {
        Pasajeros pasajero = bus.embarcar();
        if (pasajero == null) {
            JOptionPane.showMessageDialog(this, "no hay pasajeros en la cola del bus " + bus.getRuta());
            return;
        }
        txtAreaVentas.append("embarcó -> " + pasajero.getNombre() + " (" + pasajero.getCedula()
                + ") en el bus " + bus.getRuta() + "\n");
        actualizarLabels();
    }

    // devuelve el bus segun la ruta escogida en el combobox
    private Bus busSeleccionado() {
        switch (cmbRuta.getSelectedIndex()) {
            case 0:
                return busGuayaquil;
            case 1:
                return busCuenca;
            default:
                return busLoja;
        }
    }

    // suma todos los boletos que ha comprado una cedula en cualquier ruta
    private int boletosPorCedula(String cedula) {
        int total = 0;
        for (Pasajeros p : ventas) {
            if (p.getCedula().equals(cedula)) {
                total = total + p.getNumBoletos();
            }
        }
        return total;
    }

    // revisa que el texto solo tenga numeros
    private boolean esNumero(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // valida la cedula ecuatoriana con el algoritmo modulo 10
    private boolean cedulaValida(String cedula) {
        // los 2 primeros digitos son la provincia (01 a 24, o 30 para ecuatorianos en el exterior)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if ((provincia < 1 || provincia > 24) && provincia != 30) {
            return false;
        }

        // el tercer digito debe ser menor a 6 (persona natural)
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito >= 6) {
            return false;
        }

        // coeficientes 2,1,2,1,2,1,2,1,2 para los 9 primeros digitos
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            if (valor > 9) {
                valor = valor - 9;
            }
            suma = suma + valor;
        }

        // el digito verificador es lo que falta para llegar a la siguiente decena
        int verificador = (10 - (suma % 10)) % 10;
        int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
        return verificador == ultimoDigito;
    }

    // actualiza los labels de vendidos, disponibles, total recaudado y la cola de cada bus
    private void actualizarLabels() {
        // boletos vendidos por cada ruta
        lblVendidosGuayaquil.setText(String.valueOf(busGuayaquil.getBoletosVendidos()));
        lblVendidosCuenca.setText(String.valueOf(busCuenca.getBoletosVendidos()));
        lblVendidosLoja.setText(String.valueOf(busLoja.getBoletosVendidos()));

        // boletos disponibles restantes por cada ruta
        lblDisponiblesGuayaquil.setText(String.valueOf(busGuayaquil.getBoletosDisponibles()));
        lblDisponiblesCuenca.setText(String.valueOf(busCuenca.getBoletosDisponibles()));
        lblDisponiblesLoja.setText(String.valueOf(busLoja.getBoletosDisponibles()));

        // monto total recaudado por todas las ventas
        lblTotal.setText(String.format("total recaudado: $%.2f", totalRecaudado));

        // siguiente pasajero y cantidad en cola de cada bus
        lblSiguienteGuayaquil.setText("siguiente: " + nombreSiguiente(busGuayaquil));
        lblSiguienteCuenca.setText("siguiente: " + nombreSiguiente(busCuenca));
        lblSiguienteLoja.setText("siguiente: " + nombreSiguiente(busLoja));

        lblColaGuayaquil.setText("en cola: " + busGuayaquil.tamanio());
        lblColaCuenca.setText("en cola: " + busCuenca.tamanio());
        lblColaLoja.setText("en cola: " + busLoja.tamanio());
    }

    // usa verSiguiente() para mostrar quien es el siguiente en subir
    private String nombreSiguiente(Bus bus) {
        Pasajeros siguiente = bus.verSiguiente();
        if (siguiente == null) {
            return "nadie";
        }
        return siguiente.getNombre();
    }

    private void limpiar() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtBoletos.setText("");
        txtCedula.requestFocus();
    }

    public static void main(String[] args) {
        FrmVentaBoletos ventana = new FrmVentaBoletos();
        ventana.setVisible(true);
    }
}
