package ventaboletos.logica;

import java.util.ArrayList;
import java.util.List;
import ventaboletos.modelo.Ruta;
import ventaboletos.modelo.Venta;

/**
 * Lógica del negocio: rutas, lista de ventas y validaciones.
 */
public class SistemaVentas {

    public static final int MAX_BOLETOS_POR_COMPRA = 5;
    public static final int MAX_BOLETOS_POR_CEDULA = 5;

    private final List<Ruta> rutas = new ArrayList<>();
    private final List<Venta> ventas = new ArrayList<>();

    public SistemaVentas() {
        rutas.add(new Ruta("QUITO - GUAYAQUIL", 10.50));
        rutas.add(new Ruta("QUITO - CUENCA", 12.75));
        rutas.add(new Ruta("QUITO - LOJA", 15.00));
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    /**
     * Valida los datos y registra la compra.
     *
     * @return la venta registrada
     * @throws IllegalArgumentException con el mensaje de error si algo no es válido
     */
    public Venta comprar(Ruta ruta, String cedula, String nombre, String textoCantidad) {
        if (ruta == null) {
            throw new IllegalArgumentException("Seleccione una ruta.");
        }

        cedula = cedula == null ? "" : cedula.trim();
        nombre = nombre == null ? "" : nombre.trim();
        textoCantidad = textoCantidad == null ? "" : textoCantidad.trim();

        // --- Validación de cédula ---
        if (cedula.isEmpty()) {
            throw new IllegalArgumentException("Ingrese la cédula del pasajero.");
        }
        if (!cedula.matches("\\d{10}")) {
            throw new IllegalArgumentException("La cédula debe tener exactamente 10 dígitos numéricos.");
        }

        // --- Validación de nombre ---
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el nombre del pasajero.");
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios.");
        }

        // --- Validación de cantidad (1 a 5) ---
        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número de boletos debe ser un número entero.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("El número de boletos debe ser mayor a 0 (no se permiten negativos ni cero).");
        }
        if (cantidad > MAX_BOLETOS_POR_COMPRA) {
            throw new IllegalArgumentException("Máximo " + MAX_BOLETOS_POR_COMPRA + " boletos por transacción.");
        }

        // --- Validación de máximo 5 boletos por cédula ---
        int yaComprados = boletosPorCedula(cedula);
        if (yaComprados + cantidad > MAX_BOLETOS_POR_CEDULA) {
            int restantes = MAX_BOLETOS_POR_CEDULA - yaComprados;
            throw new IllegalArgumentException("La cédula " + cedula + " ya tiene " + yaComprados
                    + " boleto(s). Solo puede comprar " + restantes + " más.");
        }

        // --- Validación de capacidad de la furgoneta ---
        if (cantidad > ruta.getBoletosDisponibles()) {
            throw new IllegalArgumentException("No hay suficientes asientos en la ruta " + ruta.getNombre()
                    + ". Disponibles: " + ruta.getBoletosDisponibles());
        }

        // --- Registrar la compra ---
        Venta venta = new Venta(ruta, cedula, nombre, cantidad);
        ventas.add(venta);
        ruta.venderBoletos(cantidad);
        return venta;
    }

    /** Total de boletos comprados por una cédula (en todas las rutas). */
    public int boletosPorCedula(String cedula) {
        int total = 0;
        for (Venta v : ventas) {
            if (v.getCedula().equals(cedula)) {
                total += v.getCantidad();
            }
        }
        return total;
    }

    /** Monto total recaudado por todas las ventas. */
    public double getTotalRecaudado() {
        double total = 0;
        for (Venta v : ventas) {
            total += v.getTotal();
        }
        return total;
    }
}
