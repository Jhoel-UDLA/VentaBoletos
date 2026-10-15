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

- **Interfaz gráfica:** ComboBox de rutas, campos de cédula, nombre y número de boletos, botón *Comprar*.
- **Registro de compras:** cada venta se guarda en un `ArrayList<Venta>` y se muestra en el TextArea con ruta, cantidad, pasajero y total.
- **Validaciones:**
  - Cédula obligatoria de 10 dígitos numéricos.
  - Nombre obligatorio (solo letras y espacios).
  - Boletos entre 1 y 5 por transacción (no negativos, no cero, no texto).
  - Máximo 5 boletos acumulados por cédula (sumando todas las compras).
  - No se puede vender más que los asientos disponibles de la ruta (capacidad 20).
- **Labels de resumen:** boletos vendidos y disponibles por ruta, y total recaudado.

## Estructura (proyecto Maven de NetBeans)

```
src/main/java/ventaboletos/
├── modelo/
│   ├── Ruta.java              # Ruta: nombre, precio, vendidos/disponibles
│   └── Venta.java             # Una compra: ruta, cédula, nombre, cantidad, total
├── logica/
│   └── SistemaVentas.java     # Lista de ventas (ArrayList) + validaciones
└── vista/
    ├── VentanaVentas.form     # Diseño del JFrame Form (pestaña Design de NetBeans)
    └── VentanaVentas.java     # JFrame Form: initComponents, evento Comprar y main
```

## Cómo ejecutar

**NetBeans:** `File → Open Project`, seleccionar la carpeta `VentaBoletos` (se reconoce como proyecto Maven por el `pom.xml`).
Abrir `VentanaVentas.java` → pestaña **Design** para ver/editar el formulario. Ejecutar con **Run Project (F6)**.

**Consola (sin Maven):**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out ventaboletos.vista.VentanaVentas
```
