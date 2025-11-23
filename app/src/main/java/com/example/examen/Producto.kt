package com.example.examen

data class Producto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val precio: Double,
    val imagenResId: Int,
    val categoria: String,
    val cantidad: Int = 1
)