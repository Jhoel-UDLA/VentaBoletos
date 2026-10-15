package ventaboletos.modelo;

import java.util.LinkedList;

/**
 * Estructura de cola (FIFO) de pasajeros que esperan embarcar.
 * El primero en comprar es el primero en subir.
 */
public class Pasajeros {

    private final LinkedList<VentaBoletos> cola = new LinkedList<>();

    /** Registra (encola) al pasajero al final de la cola al momento de la venta. */
    public void registrar(VentaBoletos venta) {
        cola.addLast(venta);
    }

    /** Saca de la cola a la persona que ya subió (el primero). Devuelve null si está vacía. */
    public VentaBoletos embarcar() {
        return cola.pollFirst();
    }

    /** Devuelve el siguiente en la cola sin sacarlo. Devuelve null si está vacía. */
    public VentaBoletos verSiguiente() {
        return cola.peekFirst();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamanio() {
        return cola.size();
    }

    public LinkedList<VentaBoletos> getCola() {
        return cola;
    }
}
