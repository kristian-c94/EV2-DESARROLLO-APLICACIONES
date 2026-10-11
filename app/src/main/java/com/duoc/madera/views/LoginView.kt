package com.duoc.madera.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.madera.models.Login
import com.duoc.madera.ui.components.AnimatedWoodButton
import com.duoc.madera.ui.components.AnimatedWoodTextButton
import com.duoc.madera.viewmodels.LoginViewModel

@Composable
fun LoginView(
    mensajeExitoInicial: String? = null,
    onLoginSuccess: (Login) -> Unit,
    onNavigateToRegistro: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    LaunchedEffect(mensajeExitoInicial) {
        if (mensajeExitoInicial != null) {
            viewModel.setMensajeExito(mensajeExitoInicial)
        }
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enciclopedia de Maderas",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = viewModel.successMessage != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            viewModel.successMessage?.let { msg ->
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Éxito",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = viewModel.generalError != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            viewModel.generalError?.let { msg ->
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = viewModel.user,
            onValueChange = { viewModel.onUserChanged(it) },
            label = { Text("Usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Usuario") },
            isError = viewModel.userError != null,
            supportingText = {
                viewModel.userError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.passwordError != null,
            supportingText = {
                viewModel.passwordError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                viewModel.login(onLoginSuccess)
            }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedWoodButton(
            onClick = {
                focusManager.clearFocus()
                viewModel.login(onLoginSuccess)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedWoodTextButton(onClick = onNavigateToRegistro) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}
