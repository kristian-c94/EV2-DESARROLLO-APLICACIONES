package com.duoc.madera.repository

import com.duoc.madera.models.Registro

object UserRepository {
    private val usuarios = mutableListOf<Registro>()

    fun getUsuarios(): List<Registro> = usuarios.toList()

    fun agregarUsuario(registro: Registro): Boolean {
        if (usuarios.any { it.user.equals(registro.user, ignoreCase = true) }) {
            return false
        }
        usuarios.add(registro)
        return true
    }

    fun existeUsuario(user: String): Boolean {
        return usuarios.any { it.user.equals(user, ignoreCase = true) }
    }

    fun buscarUsuario(user: String): Registro? {
        return usuarios.find { it.user.equals(user, ignoreCase = true) }
    }

    fun estaVacio(): Boolean = usuarios.isEmpty()
}
