package ventaboletos.modelo;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Un bus (furgoneta) de una ruta. Maneja la cola de pasajeros (FIFO)
 * con un ArrayDeque: el primero que compra es el primero que embarca.
 */
public class Bus {

    /** Capacidad de asientos por viaje. Si cambia, solo se modifica aquí. */
    public static final int CAPACIDAD_MAXIMA = 20;

    private final String ruta;
    private final double precio;

    /** Cola de pasajeros que esperan embarcar. */
    private final Queue<Pasajeros> cola = new ArrayDeque<>();

    /** Asientos que quedan libres. Se actualiza con cada venta. */
    private int boletosDisponibles;

    public Bus(String ruta, double precio) {
        this.ruta = ruta;
        this.precio = precio;
        this.boletosDisponibles = CAPACIDAD_MAXIMA;
    }

    /** Precio de un boleto según la ruta. */
    public static double precioDeRuta(String ruta) {
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

    /**
     * Registra al pasajero en la cola al momento de la venta
     * y descuenta los asientos disponibles.
     */
    public void registrar(Pasajeros pasajero) {
        if (pasajero.getNumBoletos() > boletosDisponibles) {
            throw new IllegalArgumentException("Solo quedan " + boletosDisponibles
                    + " asiento(s) en el bus " + ruta + ".");
        }
        cola.offer(pasajero);
        boletosDisponibles -= pasajero.getNumBoletos();
    }

    /** Saca de la cola a la persona que ya subió (la primera). Devuelve null si está vacía. */
    public Pasajeros embarcar() {
        return cola.poll();
    }

    /** Muestra el siguiente de la cola sin sacarlo. Devuelve null si está vacía. */
    public Pasajeros verSiguiente() {
        return cola.peek();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int pasajerosEnCola() {
        return cola.size();
    }

    public String getRuta() {
        return ruta;
    }

    public double getPrecio() {
        return precio;
    }

    public int getBoletosDisponibles() {
        return boletosDisponibles;
    }

    public int getBoletosVendidos() {
        return CAPACIDAD_MAXIMA - boletosDisponibles;
    }
}
