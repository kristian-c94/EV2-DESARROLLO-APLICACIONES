package com.duoc.madera.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.madera.models.Registro
import com.duoc.madera.repository.UserRepository
import android.graphics.Bitmap


class RegistroViewModel : ViewModel() {
    var nombre by mutableStateOf("")
        private set

    var apellido by mutableStateOf("")
        private set

    var correo by mutableStateOf("")
        private set

    var user by mutableStateOf("")
        private set

    var contrasenia by mutableStateOf("")
        private set

    var nombreError by mutableStateOf<String?>(null)
        private set

    var apellidoError by mutableStateOf<String?>(null)
        private set

    var correoError by mutableStateOf<String?>(null)
        private set

    var userError by mutableStateOf<String?>(null)
        private set

    var contraseniaError by mutableStateOf<String?>(null)
        private set

    var generalError by mutableStateOf<String?>(null)
        private set

    var foto by mutableStateOf<Bitmap?>(null)
        private set

    fun onNombreChanged(value: String) {
        nombre = value
        if (nombreError != null) nombreError = null
        if (generalError != null) generalError = null
    }

    fun onApellidoChanged(value: String) {
        apellido = value
        if (apellidoError != null) apellidoError = null
        if (generalError != null) generalError = null
    }

    fun onCorreoChanged(value: String) {
        correo = value
        if (correoError != null) correoError = null
        if (generalError != null) generalError = null
    }

    fun onUserChanged(value: String) {
        user = value
        if (userError != null) userError = null
        if (generalError != null) generalError = null
    }

    fun onContraseniaChanged(value: String) {
        contrasenia = value
        if (contraseniaError != null) contraseniaError = null
        if (generalError != null) generalError = null
    }

    fun onFotoCapturada(imagen: Bitmap?) {
        if (imagen != null) foto = imagen
    }
    fun eliminarFoto() {
        foto = null
    }
    fun registrar(onSuccess: (Registro) -> Unit): Boolean {
        var isValid = true

        val trimmedNombre = nombre.trim()
        val trimmedApellido = apellido.trim()
        val trimmedCorreo = correo.trim()
        val trimmedUser = user.trim()
        val trimmedPass = contrasenia.trim()

        if (trimmedNombre.isEmpty()) {
            nombreError = "El nombre es obligatorio"
            isValid = false
        } else if (trimmedNombre.length < 2) {
            nombreError = "El nombre debe tener al menos 2 caracteres"
            isValid = false
        } else {
            nombreError = null
        }

        if (trimmedApellido.isEmpty()) {
            apellidoError = "El apellido es obligatorio"
            isValid = false
        } else if (trimmedApellido.length < 2) {
            apellidoError = "El apellido debe tener al menos 2 caracteres"
            isValid = false
        } else {
            apellidoError = null
        }

        if (trimmedCorreo.isEmpty()) {
            correoError = "El correo electrónico es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(trimmedCorreo).matches()) {
            correoError = "Formato de correo inválido (ej. usuario@dominio.com)"
            isValid = false
        } else {
            correoError = null
        }

        if (trimmedUser.isEmpty()) {
            userError = "El nombre de usuario es obligatorio"
            isValid = false
        } else if (trimmedUser.contains(" ")) {
            userError = "El usuario no debe contener espacios"
            isValid = false
        } else if (trimmedUser.length < 3) {
            userError = "El usuario debe tener al menos 3 caracteres"
            isValid = false
        } else if (UserRepository.existeUsuario(trimmedUser)) {
            userError = "El usuario ya está registrado"
            isValid = false
        } else {
            userError = null
        }

        if (trimmedPass.isEmpty()) {
            contraseniaError = "La contraseña es obligatoria"
            isValid = false
        } else if (trimmedPass.length < 6) {
            contraseniaError = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            contraseniaError = null
        }

        if (!isValid) {
            generalError = "Por favor corrige los campos con errores"
            return false
        }

        val nuevoRegistro = Registro(
            nombre = trimmedNombre,
            apellido = trimmedApellido,
            correo = trimmedCorreo,
            contrasenia = trimmedPass,
            user = trimmedUser,
            foto = foto

        )

        val agregado = UserRepository.agregarUsuario(nuevoRegistro)
        if (!agregado) {
            userError = "El usuario ya existe"
            generalError = "No se pudo completar el registro"
            return false
        }

        generalError = null
        onSuccess(nuevoRegistro)
        return true
    }
}
