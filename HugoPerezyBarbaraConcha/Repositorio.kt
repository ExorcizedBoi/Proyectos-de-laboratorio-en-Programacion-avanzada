class Repositorio {
    val listaAFPs = mutableListOf<AFP>()
    val listaEmpleados = mutableListOf<Empleado>()
    val listaLiquidaciones = mutableListOf<LiquidacionSueldo>()

    fun agregarAFP(afp: AFP) {
        listaAFPs.add(afp)
    }

    fun agregarEmpleado(empleado: Empleado) {
        listaEmpleados.add(empleado)
    }

    fun agregarLiquidacion(liquidacion: LiquidacionSueldo) {
        listaLiquidaciones.add(liquidacion)
    }
}