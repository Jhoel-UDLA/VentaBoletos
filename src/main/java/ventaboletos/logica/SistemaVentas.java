package ventaboletos.logica;

import java.util.ArrayList;
import java.util.List;
import ventaboletos.modelo.Bus;
import ventaboletos.modelo.Pasajeros;

/**
 * Lógica del negocio: los 3 buses, las validaciones y los totales.
 */
public class SistemaVentas {

    public static final int MAX_BOLETOS_POR_CEDULA = 5;

    /** Un bus por ruta (mismo orden que el ComboBox). */
    private final Bus[] buses = {
        new Bus("QUITO - GUAYAQUIL", 10.50),
        new Bus("QUITO - CUENCA", 12.75),
        new Bus("QUITO - LOJA", 15.00)
    };

    /** Historial de todas las ventas (no se borra al embarcar: sirve para totales y control por cédula). */
    private final List<Pasajeros> historial = new ArrayList<>();

    public Bus[] getBuses() {
        return buses;
    }

    public Bus getBus(int indice) {
        return buses[indice];
    }

    public List<Pasajeros> getHistorial() {
        return historial;
    }

    /**
     * Valida los datos, registra la venta y encola al pasajero en el bus.
     *
     * @throws IllegalArgumentException con el mensaje de error si algo no es válido
     */
    public Pasajeros vender(int indiceBus, String cedula, String nombre, String textoCantidad) {
        if (indiceBus < 0 || indiceBus >= buses.length) {
            throw new IllegalArgumentException("Seleccione una ruta.");
        }
        Bus bus = buses[indiceBus];
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

        // --- Capacidad del bus (CAPACIDAD_MAXIMA asientos = pasajes por ruta) ---
        if (bus.getBoletosDisponibles() == 0) {
            throw new IllegalArgumentException("El bus " + bus.getRuta() + " está lleno ("
                    + Bus.CAPACIDAD_MAXIMA + " asientos vendidos).");
        }

        // --- Registrar venta: el bus valida asientos y encola al pasajero ---
        Pasajeros pasajero = new Pasajeros(nombre, cedula, bus.getRuta(), cantidad);
        bus.registrar(pasajero);
        historial.add(pasajero);
        return pasajero;
    }

    /** Total de boletos comprados por una cédula (en todas las rutas). */
    public int boletosPorCedula(String cedula) {
        int total = 0;
        for (Pasajeros p : historial) {
            if (p.getCedula().equals(cedula)) {
                total += p.getNumBoletos();
            }
        }
        return total;
    }

    /** Monto total recaudado por todas las ventas. */
    public double getTotalRecaudado() {
        double total = 0;
        for (Pasajeros p : historial) {
            total += p.getTotal();
        }
        return total;
    }
}
