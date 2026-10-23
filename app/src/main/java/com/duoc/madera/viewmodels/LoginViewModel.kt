package com.duoc.madera.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.madera.models.Login
import com.duoc.madera.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var user by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var userError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var generalError by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    fun onUserChanged(newUser: String) {
        user = newUser
        if (userError != null) userError = null
        if (generalError != null) generalError = null
        if (successMessage != null) successMessage = null
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        if (passwordError != null) passwordError = null
        if (generalError != null) generalError = null
        if (successMessage != null) successMessage = null
    }

    fun setMensajeExito(mensaje: String?) {
        successMessage = mensaje
    }

    fun login(onSuccess: (Login) -> Unit) {
        var isValid = true
        val trimmedUser = user.trim()
        val trimmedPass = password.trim()

        if (trimmedUser.isEmpty()) {
            userError = "El usuario no puede estar vacío"
            isValid = false
        } else if (trimmedUser.length < 3) {
            userError = "El usuario debe tener al menos 3 caracteres"
            isValid = false
        } else {
            userError = null
        }

        if (trimmedPass.isEmpty()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        } else if (trimmedPass.length < 4) {
            passwordError = "La contraseña debe tener al menos 4 caracteres"
            isValid = false
        } else {
            passwordError = null
        }

        if (!isValid) {
            generalError = "Por favor corrige los campos indicados"
            return
        }

        if (UserRepository.estaVacio()) {
            generalError = "No hay usuarios registrados. Por favor regístrate primero."
            return
        }

        val usuarioEncontrado = UserRepository.buscarUsuario(trimmedUser)

        if (usuarioEncontrado == null) {
            userError = "El usuario no existe"
            generalError = "El usuario no está registrado. Regístrate para ingresar."
        } else if (usuarioEncontrado.contrasenia != trimmedPass) {
            passwordError = "Contraseña incorrecta"
            generalError = "La contraseña ingresada es incorrecta"
        } else {
            generalError = null
            viewModelScope.launch {
                UserRepository.guardarSesion(usuarioEncontrado.user)
                onSuccess(Login(user = usuarioEncontrado.user, password = trimmedPass))
            }
        }
    }
}
