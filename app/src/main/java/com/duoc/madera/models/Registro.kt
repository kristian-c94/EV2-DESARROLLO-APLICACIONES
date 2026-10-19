package com.duoc.madera.models

import android.graphics.Bitmap

data class Registro(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasenia: String,
    val user: String,
    val foto: Bitmap? = null
)