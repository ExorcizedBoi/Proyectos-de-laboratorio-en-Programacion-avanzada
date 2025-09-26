class Empleado(
    val rut: String,
    val nombre: String,
    val sueldoBruto: Double,
    val afp: AFP,
    val direccion: Direccion
) {
    override fun toString(): String {
        return "$nombre (RUT: $rut) - Sueldo: $$sueldoBruto - AFP: ${afp.nombre}"
    }
}