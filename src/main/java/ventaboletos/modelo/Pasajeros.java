package ventaboletos.modelo;

/**
 * Datos de un pasajero y de los boletos que compra.
 */
public class Pasajeros {

    private String nombre;
    private String cedula;
    private String ruta;
    private int numBoletos;

    public Pasajeros(String nombre, String cedula, String ruta, int numBoletos) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.ruta = ruta;
        this.numBoletos = numBoletos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getNumBoletos() {
        return numBoletos;
    }

    public void setNumBoletos(int numBoletos) {
        this.numBoletos = numBoletos;
    }

    /** Precio total de los boletos de este pasajero. */
    public double getTotal() {
        return numBoletos * Bus.precioDeRuta(ruta);
    }

    @Override
    public String toString() {
        return String.format("Ruta: %s | Boletos: %d | Pasajero: %s (%s) | Total: $%.2f",
                ruta, numBoletos, nombre, cedula, getTotal());
    }
}
