package Sistema;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * clase bus: cola de pasajeros de una ruta.
 * se usa arraydeque (cola implementada con arreglo), el primero que compra es el primero que sube.
 */
public class Bus {

    // capacidad maxima de la ruta, es constante por si la capacidad cambia
    public static final int CAPACIDAD_MAXIMA = 20;

    private String ruta;
    private int boletosDisponibles; // se actualiza con cada venta
    private Queue<Pasajeros> cola;

    public Bus(String ruta) {
        this.ruta = ruta;
        this.boletosDisponibles = CAPACIDAD_MAXIMA;
        this.cola = new ArrayDeque<>();
    }

    /** registra al pasajero al final de la cola al momento de la venta y descuenta los asientos. */
    public boolean registrar(Pasajeros pasajero) {
        if (pasajero.getNumBoletos() > boletosDisponibles) {
            return false; // no hay asientos suficientes
        }
        cola.add(pasajero);
        boletosDisponibles = boletosDisponibles - pasajero.getNumBoletos();
        return true;
    }

    /** saca de la cola a la persona que ya subió (el primero). devuelve null si está vacía. */
    public Pasajeros embarcar() {
        return cola.poll();
    }

    /** devuelve el siguiente en la cola sin sacarlo. devuelve null si está vacía. */
    public Pasajeros verSiguiente() {
        return cola.peek();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamanio() {
        return cola.size();
    }

    public String getRuta() {
        return ruta;
    }

    public int getBoletosDisponibles() {
        return boletosDisponibles;
    }

    public int getBoletosVendidos() {
        return CAPACIDAD_MAXIMA - boletosDisponibles;
    }

    public Queue<Pasajeros> getCola() {
        return cola;
    }
}
