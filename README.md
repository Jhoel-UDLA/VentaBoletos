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

**Enunciado y rúbrica**

- **Interfaz:** ComboBox de rutas, campos de cédula, nombre y número de boletos, y botón "comprar".
- **Compra:** el pasajero se agrega a la cola del bus y a la lista de ventas; el TextArea muestra ruta, cantidad de boletos, nombre y precio total.
- **Validaciones:**
  - la cédula debe ser ecuatoriana válida (provincia, tercer dígito y dígito verificador módulo 10);
  - cada compra debe ser de 1 a 5 boletos (no se aceptan negativos, cero ni texto);
  - cada cédula puede tener máximo 5 boletos en total;
  - cada furgoneta tiene 20 asientos, y no se vende más de lo disponible.
- **Labels:** vendidos por ruta, disponibles por ruta y total recaudado.

**Requisitos adicionales del docente**

1. **clase `Pasajeros`:** nombre, cédula, ruta y número de boletos, con constructor, get, set y `toString`.
2. **clase `Bus` (cola con `ArrayDeque`):** `registrar()`, `embarcar()` y `verSiguiente()`.
3. **máximo 5 boletos por persona**, sumando sus compras anteriores.
4. **20 asientos = 20 pasajes por ruta.**
5. **`CAPACIDAD_MAXIMA = 20`:** constante, por si cambia la capacidad.
6. **`boletosDisponibles`:** variable que se actualiza con cada venta.
7. **3 buses, 3 botones:** un botón de embarcar por cada ruta.

## Estructura

```
src/main/java/Sistema/
├── Pasajeros.java
├── Bus.java
├── FrmVentaBoletos.java
└── FrmVentaBoletos.form
```

## Cómo ejecutar

**NetBeans:** `File → Open Project` y seleccionar la carpeta `VentaBoletos`. Ejecutar con **Run Project (F6)**.

**Consola:**

```bash
javac -encoding UTF-8 -d out src/main/java/Sistema/*.java
java -cp out Sistema.FrmVentaBoletos
```
