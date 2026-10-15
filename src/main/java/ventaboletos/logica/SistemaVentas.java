package ventaboletos.logica;

import java.util.ArrayList;
import java.util.List;
import ventaboletos.modelo.Pasajeros;
import ventaboletos.modelo.VentaBoletos;

/**
 * Lógica del negocio: validaciones, cola de pasajeros y totales.
 */
public class SistemaVentas {

    public static final String[] RUTAS = {"QUITO - GUAYAQUIL", "QUITO - CUENCA", "QUITO - LOJA"};
    public static final int ASIENTOS_POR_RUTA = 20;
    public static final int MAX_BOLETOS_POR_CEDULA = 5;

    /** Cola de pasajeros que esperan embarcar. */
    private final Pasajeros pasajeros = new Pasajeros();

    /** Historial de todas las ventas (no se borra al embarcar, sirve para totales y control por cédula). */
    private final List<VentaBoletos> historial = new ArrayList<>();

    public Pasajeros getPasajeros() {
        return pasajeros;
    }

    public List<VentaBoletos> getHistorial() {
        return historial;
    }

    /**
     * Valida los datos, registra la venta y encola al pasajero.
     *
     * @throws IllegalArgumentException con el mensaje de error si algo no es válido
     */
    public VentaBoletos vender(String ruta, String cedula, String nombre, String textoCantidad) {
        if (ruta == null || ruta.isEmpty()) {
            throw new IllegalArgumentException("Seleccione una ruta.");
        }
        cedula = cedula == null ? "" : cedula.trim();
        nombre = nombre == null ? "" : nombre.trim();
        textoCantidad = textoCantidad == null ? "" : textoCantidad.trim();

        // --- Cédula ---
        if (cedula.isEmpty()) {
            throw new IllegalArgumentException("Ingrese la cédula del pasajero.");
        }
        if (!cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cédula debe tener exactamente 10 dígitos numéricos.");
        }

        // --- Nombre ---
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el nombre del pasajero.");
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios.");
        }

        // --- Cantidad entre 1 y 5 ---
        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número de boletos debe ser un número entero.");
        }
        if (cantidad < 1 || cantidad > MAX_BOLETOS_POR_CEDULA) {
            throw new IllegalArgumentException("El número de boletos debe estar entre 1 y "
                    + MAX_BOLETOS_POR_CEDULA + ".");
        }

        // --- Máximo 5 boletos en total por cédula (sumando compras anteriores) ---
        int yaComprados = boletosPorCedula(cedula);
        if (yaComprados + cantidad > MAX_BOLETOS_POR_CEDULA) {
            int restantes = MAX_BOLETOS_POR_CEDULA - yaComprados;
            throw new IllegalArgumentException("La cédula " + cedula + " ya tiene " + yaComprados
                    + " boleto(s). " + (restantes == 0
                            ? "No puede comprar más."
                            : "Solo puede comprar " + restantes + " más."));
        }

        // --- 20 asientos = 20 pasajes por ruta ---
        int disponibles = disponiblesPorRuta(ruta);
        if (cantidad > disponibles) {
            throw new IllegalArgumentException(disponibles == 0
                    ? "La ruta " + ruta + " está llena (20 asientos vendidos)."
                    : "Solo quedan " + disponibles + " asiento(s) en la ruta " + ruta + ".");
        }

        // --- Registrar venta y encolar pasajero ---
        VentaBoletos venta = new VentaBoletos(nombre, cedula, ruta, cantidad);
        historial.add(venta);
        pasajeros.registrar(venta);
        return venta;
    }

    /** Total de boletos comprados por una cédula (en todas las rutas). */
    public int boletosPorCedula(String cedula) {
        int total = 0;
        for (VentaBoletos v : historial) {
            if (v.getCedula().equals(cedula)) {
                total += v.getNumBoletos();
            }
        }
        return total;
    }

    public int vendidosPorRuta(String ruta) {
        int total = 0;
        for (VentaBoletos v : historial) {
            if (v.getRuta().equals(ruta)) {
                total += v.getNumBoletos();
            }
        }
        return total;
    }

    public int disponiblesPorRuta(String ruta) {
        return ASIENTOS_POR_RUTA - vendidosPorRuta(ruta);
    }

    /** Monto total recaudado por todas las ventas. */
    public double getTotalRecaudado() {
        double total = 0;
        for (VentaBoletos v : historial) {
            total += v.getTotal();
        }
        return total;
    }
}
