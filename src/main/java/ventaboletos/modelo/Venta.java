package ventaboletos.modelo;

/**
 * Representa una compra (transacción) realizada por un pasajero.
 */
public class Venta {

    private final Ruta ruta;
    private final String cedula;
    private final String nombre;
    private final int cantidad;
    private final double total;

    public Venta(Ruta ruta, String cedula, String nombre, int cantidad) {
        this.ruta = ruta;
        this.cedula = cedula;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.total = cantidad * ruta.getPrecio();
    }

    public Ruta getRuta() {
        return ruta;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return total;
    }

    /** Detalle que se muestra en el TextArea. */
    public String getDetalle() {
        return String.format("Ruta: %s | Boletos: %d | Pasajero: %s | Total: $%.2f",
                ruta.getNombre(), cantidad, nombre, total);
    }
}
