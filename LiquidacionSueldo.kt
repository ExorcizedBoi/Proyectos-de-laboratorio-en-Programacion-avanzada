class LiquidacionSueldo(
    val empleado: Empleado,
    val sueldoBruto: Double,
    val descuentoAFP: Double,
    val descuentoSalud: Double,
    val seguroCesantia: Double
) {
    val sueldoLiquido: Double
        get() = sueldoBruto - descuentoAFP - descuentoSalud - seguroCesantia

    override fun toString(): String {
        return """
        |=== Liquidación para ${empleado.nombre} ===
        |Sueldo Bruto: $$sueldoBruto
        |Descuento AFP: $${"%.2f".format(descuentoAFP)}
        |Descuento Salud (7%): $${"%.2f".format(descuentoSalud)}
        |Seguro Cesantía (0.6%): $${"%.2f".format(seguroCesantia)}
        |Sueldo Líquido: $${"%.2f".format(sueldoLiquido)}
        """.trimMargin()
    }
}