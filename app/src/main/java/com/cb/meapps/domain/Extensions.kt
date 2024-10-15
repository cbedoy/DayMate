package com.cb.meapps.domain

fun Double.asMoney() = "\$${String.format("%,.0f", this)}"
fun Int.asMoney() = "\$${String.format("%,.0f", this)}"

fun Float.asMoney() = "\$${String.format("%,.0f", this)}"

fun Float.round(decimals: Int) =  String.format("%.${decimals}f", this)