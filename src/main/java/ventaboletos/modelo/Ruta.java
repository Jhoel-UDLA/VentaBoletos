package ventaboletos.modelo;

/**
 * Representa una ruta de la empresa con su precio y capacidad.
 */
public class Ruta {

    public static final int CAPACIDAD_MAXIMA = 20;

    private final String nombre;
    private final double precio;
    private int boletosVendidos;

    public Ruta(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.boletosVendidos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public int getBoletosDisponibles() {
        return CAPACIDAD_MAXIMA - boletosVendidos;
    }

    public void venderBoletos(int cantidad) {
        boletosVendidos += cantidad;
    }

    @Override
    public String toString() {
        // Lo que se muestra en el ComboBox
        return nombre;
    }
}
