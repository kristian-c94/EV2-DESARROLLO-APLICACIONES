package com.duoc.madera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.duoc.madera.ui.theme.EnciclopediaMaderasTheme
import com.duoc.madera.views.LoginView
import com.duoc.madera.views.MainView
import com.duoc.madera.views.RegistroView

// Definición de las pantallas de la aplicación
sealed class Pantalla {
    data class Login(val mensajeExito: String? = null) : Pantalla()
    object Registro : Pantalla()
    data class Main(val usuario: String) : Pantalla()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnciclopediaMaderasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var pantallaActual by remember { mutableStateOf<Pantalla>(Pantalla.Login()) }

                    AnimatedContent(
                        targetState = pantallaActual,
                        transitionSpec = {
                            if (targetState is Pantalla.Registro) {
                                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                    slideOutHorizontally { width -> -width } + fadeOut()
                                )
                            } else {
                                (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                    slideOutHorizontally { width -> width } + fadeOut()
                                )
                            }
                        },
                        label = "PantallaTransition"
                    ) { targetPantalla ->
                        when (targetPantalla) {
                            is Pantalla.Login -> {
                                LoginView(
                                    mensajeExitoInicial = targetPantalla.mensajeExito,
                                    onLoginSuccess = { loginData ->
                                        pantallaActual = Pantalla.Main(loginData.user)
                                    },
                                    onNavigateToRegistro = {
                                        pantallaActual = Pantalla.Registro
                                    }
                                )
                            }
                            is Pantalla.Registro -> {
                                RegistroView(
                                    onRegistroSuccess = { registroData ->
                                        pantallaActual = Pantalla.Login(
                                            mensajeExito = "¡Usuario registrado con éxito! Ya puedes iniciar sesión."
                                        )
                                    },
                                    onNavigateToLogin = {
                                        pantallaActual = Pantalla.Login()
                                    }
                                )
                            }
                            is Pantalla.Main -> {
                                MainView(
                                    usuarioConectado = targetPantalla.usuario,
                                    onCerrarSesion = {
                                        pantallaActual = Pantalla.Login()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
