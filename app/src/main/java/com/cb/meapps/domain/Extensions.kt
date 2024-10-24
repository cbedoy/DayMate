package com.cb.meapps.domain

fun Double.asMoney() = "\$${String.format("%,.0f", this)}"
fun Int.asMoney() = "\$${String.format("%,.0f", this)}"

fun Float.asMoney() = "\$${String.format("%,.0f", this)}"

fun Float.round(decimals: Int) =  String.format("%.${decimals}f", this)

fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}

fun String.toIntOrDefault(default: Int = 0): Int {
    return this.toIntOrNull() ?: default
}

fun String.toIntOrZero(): Int {
    return this.toIntOrNull() ?: 0
}

fun String.toFloatOrZero(): Float {
    return this.toFloatOrNull() ?: 0.0f
}