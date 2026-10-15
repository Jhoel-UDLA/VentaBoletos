# Venta de Boletos – Rutas desde Quito

Programa de escritorio en Java (Swing) para gestionar la venta de boletos de tres rutas:

| Ruta | Precio por boleto |
|---|---|
| QUITO - GUAYAQUIL | $10.50 |
| QUITO - CUENCA | $12.75 |
| QUITO - LOJA | $15.00 |

Cada furgoneta tiene 20 asientos por viaje.

## Integrantes

- Iván Alejandro Montero Bravo
- Jhoel Núñez Pillajo
- Jhandry Orozco
- Mateo Gaibor Balseca

**Asignatura:** Programación III – Universidad de Las Américas (UDLA)

## Funcionalidades

- **Interfaz (JFrame Form de NetBeans):** ComboBox de rutas, campos de cédula, nombre y número de boletos, botón *Comprar*, y un panel por cada bus con su botón *Embarcar* (3 buses = 3 botones).
- **Clase `Pasajeros`:** nombre, cédula, ruta y número de boletos, con constructor, get, set y `toString`.
- **Clase `Bus` (cola con `ArrayDeque`):**
  - `CAPACIDAD_MAXIMA = 20`: constante, por si cambia la capacidad de la ruta.
  - `boletosDisponibles`: variable que se actualiza con cada venta.
  - `registrar(pasajero)`: al vender, el pasajero entra al final de la cola del bus.
  - `embarcar()`: saca de la cola al primero (el que ya subió).
  - `verSiguiente()`: muestra quién es el siguiente sin sacarlo.
- **Validaciones:**
  - Cédula obligatoria de 10 dígitos numéricos; nombre obligatorio (solo letras).
  - Boletos entre 1 y 5 por compra (no negativos, no cero, no texto).
  - Máximo 5 boletos **en total** por cédula, sumando compras anteriores.
  - 20 asientos = 20 pasajes por ruta; no se vende más de lo disponible.
- **TextArea:** muestra cada venta (ruta, boletos, pasajero, total) y cada embarque.
- **Labels:** vendidos y disponibles por ruta, total recaudado, y siguiente/en cola de cada bus.

## Estructura (proyecto Maven de NetBeans)

```
src/main/java/ventaboletos/
├── modelo/
│   ├── Pasajeros.java         # nombre, cédula, ruta, número de boletos (+ get/set/toString)
│   └── Bus.java               # Cola ArrayDeque: registrar, embarcar, verSiguiente, CAPACIDAD_MAXIMA
├── logica/
│   └── SistemaVentas.java     # 3 buses, validaciones, historial y total recaudado
└── vista/
    ├── VentanaVentas.form     # Diseño del JFrame Form (pestaña Design de NetBeans)
    └── VentanaVentas.java     # JFrame Form: initComponents, eventos y main
```

## Cómo ejecutar

**NetBeans:** `File → Open Project`, seleccionar la carpeta `VentaBoletos` (se reconoce como proyecto Maven por el `pom.xml`).
Abrir `VentanaVentas.java` → pestaña **Design** para ver/editar el formulario. Ejecutar con **Run Project (F6)**.

**Consola (sin Maven):**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out ventaboletos.vista.VentanaVentas
```
