class AFP(val nombre: String, val tasaDescuento: Double) {
    override fun toString(): String {
        return "$nombre (Tasa: ${tasaDescuento * 100}%)"
    }
}