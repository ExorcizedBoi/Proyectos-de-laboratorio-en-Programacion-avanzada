fun main() {
    val repositorio = Repositorio()

    // Creacion de las AFP más conocidas de la actualizdad
    val afp1 = AFP("Capital", 0.12)
    val afp2 = AFP("Habitat", 0.11)
    val afp3 = AFP("Modelo", 0.13)

    repositorio.agregarAFP(afp1)
    repositorio.agregarAFP(afp2)
    repositorio.agregarAFP(afp3)
    // Opciones para el menu
    var opcion: Int

    do {
        println("\n=== SISTEMA DE LIQUIDACIÓN DE SUELDOS ===")
        println("1. Listar empleados")
        println("2. Agregar empleado")
        println("3. Generar liquidación por RUT")
        println("4. Listar liquidaciones")
        println("5. Filtrar empleados por AFP")
        println("6. Eliminar empleado")
        println("7. Salir")
        print("Seleccione una opción: ")

        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> listarEmpleados(repositorio)
            2 -> agregarEmpleado(repositorio)
            3 -> generarLiquidacion(repositorio)
            4 -> listarLiquidaciones(repositorio)
            5 -> filtrarEmpleadosPorAFP(repositorio)
            6 -> eliminarEmpleado(repositorio)
            7 -> println("Saliendo del sistema...")
            else -> println("Opción no válida")
        }

    } while (opcion != 7)
}

// Función para convertir texto con puntos a número
fun convertirSueldo(texto: String): Double? {
    return try {
        // Eliminar todos los puntos y luego convertir a Double
        texto.replace(".", "").replace(",", ".").toDouble()
    } catch (e: Exception) {
        null
    }
}

fun listarEmpleados(repositorio: Repositorio) {
    println("\n=== LISTA DE EMPLEADOS ===")
    if (repositorio.listaEmpleados.isEmpty()) {
        println("No hay empleados registrados")
    } else {
        repositorio.listaEmpleados.forEachIndexed { index, empleado ->
            println("${index + 1}. $empleado")
        }
    }
}

fun agregarEmpleado(repositorio: Repositorio) {
    println("\n=== AGREGAR EMPLEADO ===")

    print("Ingrese RUT: ")
    val rut = readLine() ?: ""

    // Verifica si es que el RUT ya existe
    if (repositorio.listaEmpleados.any { it.rut == rut }) {
        println("Error: Ya existe un empleado con ese RUT")
        return
    }

    print("Ingrese nombre: ")
    val nombre = readLine() ?: ""

    print("Ingrese sueldo bruto: ")
    val sueldoTexto = readLine() ?: ""
    val sueldo = convertirSueldo(sueldoTexto)

    if (sueldo == null || sueldo <= 0) {
        println("Error el Sueldo debe ser un número mayor a 0")
        println("Ejemplos válidos: $$$$$$, $.$$$.$$$, $$$$$$")
        return
    }

    println("Seleccione AFP:")
    repositorio.listaAFPs.forEachIndexed { index, afp ->
        println("${index + 1}. $afp")
    }
    val afpIndex = readLine()?.toIntOrNull() ?: 1
    val afpSeleccionada = repositorio.listaAFPs.getOrNull(afpIndex - 1) ?: repositorio.listaAFPs.first()

    print("== Ahora ingrese su dirección ==")
    print("Ingrese calle: ")
    val calle = readLine() ?: ""

    print("Ingrese número: ")
    val numero = readLine() ?: ""

    print("Ingrese ciudad: ")
    val ciudad = readLine() ?: ""

    val direccion = Direccion(calle, numero, ciudad)
    val nuevoEmpleado = Empleado(rut, nombre, sueldo, afpSeleccionada, direccion)

    repositorio.agregarEmpleado(nuevoEmpleado)
    println("Empleado agregado exitosamente!")
    println("Datos: $nuevoEmpleado")
}

fun generarLiquidacion(repositorio: Repositorio) {
    println("\n=== GENERAR LIQUIDACIÓN ===")

    if (repositorio.listaEmpleados.isEmpty()) {
        println("No hay empleados registrados para generar liquidación")
        return
    }

    print("Ingrese RUT del empleado: ")
    val rut = readLine() ?: ""

    val empleado = repositorio.listaEmpleados.find { it.rut == rut }

    if (empleado != null) {
        println("Generando liquidación para: ${empleado.nombre}")
        println("Sueldo bruto registrado: $${empleado.sueldoBruto}")

        val descuentoAFP = empleado.sueldoBruto * empleado.afp.tasaDescuento
        val descuentoSalud = empleado.sueldoBruto * 0.07
        val seguroCesantia = empleado.sueldoBruto * 0.006

        val liquidacion = LiquidacionSueldo(
            empleado,
            empleado.sueldoBruto,
            descuentoAFP,
            descuentoSalud,
            seguroCesantia
        )

        repositorio.agregarLiquidacion(liquidacion)
        println(liquidacion)
        println("Liquidación generada de manera exitosamente")
    } else {
        println("No se encontró empleado con RUT: $rut")
    }
}

fun listarLiquidaciones(repositorio: Repositorio) {
    println("\n=== LISTA DE LIQUIDACIONES ===")
    if (repositorio.listaLiquidaciones.isEmpty()) {
        println("No hay liquidaciones generadas")
    } else {
        repositorio.listaLiquidaciones.forEachIndexed { index, liquidacion ->
            println("${index + 1}. $liquidacion")
        }

        // Calcular total de descuentos
        val totalDescuentos = repositorio.listaLiquidaciones.sumOf {
            it.descuentoAFP + it.descuentoSalud + it.seguroCesantia
        }
        println("\nTotal de descuentos en nómina: $${"%.2f".format(totalDescuentos)}")
    }
}

fun filtrarEmpleadosPorAFP(repositorio: Repositorio) {
    println("\n=== FILTRAR EMPLEADOS POR AFP ===")

    println("Seleccione AFP:")
    repositorio.listaAFPs.forEachIndexed { index, afp ->
        println("${index + 1}. $afp")
    }
    val afpIndex = readLine()?.toIntOrNull() ?: 1
    val afpSeleccionada = repositorio.listaAFPs.getOrNull(afpIndex - 1)

    if (afpSeleccionada != null) {
        val empleadosFiltrados = repositorio.listaEmpleados
            .filter { it.afp.nombre == afpSeleccionada.nombre }
            .sortedByDescending { it.sueldoBruto }

        println("\nEmpleados de AFP ${afpSeleccionada.nombre} (ordenados por sueldo):")
        if (empleadosFiltrados.isEmpty()) {
            println("No hay empleados en esta AFP")
        } else {
            empleadosFiltrados.forEachIndexed { index, empleado ->
                println("${index + 1}. $empleado")
            }
        }
    } else {
        println("AFP no válida")
    }
}

fun eliminarEmpleado(repositorio: Repositorio) {
    println("\n=== ELIMINAR EMPLEADO ===")

    print("Ingrese RUT del empleado para eliminarlo: ")
    val rut = readLine() ?: ""

    val empleado = repositorio.listaEmpleados.find { it.rut == rut }

    if (empleado != null) {
        repositorio.listaEmpleados.remove(empleado)
        // También eliminar sus liquidaciones
        repositorio.listaLiquidaciones.removeAll { it.empleado.rut == rut }
        println("Empleado eliminado de manera exitosa")
    } else {
        println("No se encontró empleado con ese RUT: $rut")
    }
}
