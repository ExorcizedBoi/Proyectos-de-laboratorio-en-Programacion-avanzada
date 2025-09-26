class Direccion(
    val calle: String,
    val numero: String,
    val ciudad: String
) {
    override fun toString(): String {
        return "$calle $numero, $ciudad"
    }
}