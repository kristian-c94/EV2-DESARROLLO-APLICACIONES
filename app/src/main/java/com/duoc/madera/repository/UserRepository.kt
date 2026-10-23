package com.duoc.madera.repository

import android.content.Context
import com.duoc.madera.models.Registro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

object UserRepository {
    private var preferencesRepository: UserPreferencesRepository? = null
    private val usuarios = mutableListOf<Registro>()

    fun init(context: Context) {
        if (preferencesRepository == null) {
            preferencesRepository = UserPreferencesRepository(context.applicationContext)
        }
    }

    suspend fun cargarUsuarios(): List<Registro> {
        val prefRepo = preferencesRepository ?: return usuarios.toList()
        val listaGuardada = prefRepo.usuariosFlow.first()
        usuarios.clear()
        usuarios.addAll(listaGuardada)
        return usuarios.toList()
    }

    fun getUsuarios(): List<Registro> = usuarios.toList()

    suspend fun agregarUsuario(registro: Registro): Boolean {
        if (usuarios.any { it.user.equals(registro.user, ignoreCase = true) }) {
            return false
        }
        usuarios.add(registro)
        preferencesRepository?.guardarUsuarios(usuarios)
        return true
    }

    fun existeUsuario(user: String): Boolean {
        return usuarios.any { it.user.equals(user, ignoreCase = true) }
    }

    fun buscarUsuario(user: String): Registro? {
        return usuarios.find { it.user.equals(user, ignoreCase = true) }
    }

    fun estaVacio(): Boolean = usuarios.isEmpty()

    suspend fun guardarSesion(usuario: String) {
        preferencesRepository?.guardarUsuarioConectado(usuario)
    }

    suspend fun cerrarSesion() {
        preferencesRepository?.cerrarSesion()
    }

    fun getUltimoUsuarioFlow(): Flow<String?>? {
        return preferencesRepository?.lastLoggedUserFlow
    }
}
