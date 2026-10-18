package Sistema;

/**
 *venta de boletos a un pasajero.
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

    /** precio de un boleto según la ruta. */
    public static double precioPorRuta(String ruta) {
        switch (ruta) {
            case "quito - guayaquil":
                return 10.50;
            case "quito - cuenca":
                return 12.75;
            case "quito - loja":
                return 15.00;
            default:
                return 0;
        }
    }

    /** precio total de la venta */
    public double getTotal() {
        return numBoletos * precioPorRuta(ruta);
    }

    @Override
    public String toString() {
        return String.format("ruta: %s | boletos: %d | pasajero: %s (%s) | total: $%.2f",
                ruta, numBoletos, nombre, cedula, getTotal());
    }
}