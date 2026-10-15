package ventaboletos.modelo;

/**
 * Representa la venta de boletos a un pasajero.
 */
public class VentaBoletos {

    private String nombre;
    private String cedula;
    private String ruta;
    private int numBoletos;

    public VentaBoletos(String nombre, String cedula, String ruta, int numBoletos) {
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

    /** Precio de un boleto según la ruta. */
    public static double precioPorRuta(String ruta) {
        switch (ruta) {
            case "QUITO - GUAYAQUIL":
                return 10.50;
            case "QUITO - CUENCA":
                return 12.75;
            case "QUITO - LOJA":
                return 15.00;
            default:
                return 0;
        }
    }

    /** Precio total de esta venta. */
    public double getTotal() {
        return numBoletos * precioPorRuta(ruta);
    }

    @Override
    public String toString() {
        return String.format("Ruta: %s | Boletos: %d | Pasajero: %s (%s) | Total: $%.2f",
                ruta, numBoletos, nombre, cedula, getTotal());
    }
}
