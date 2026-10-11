package com.duoc.madera.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
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
import com.duoc.madera.models.Registro
import com.duoc.madera.ui.components.AnimatedWoodButton
import com.duoc.madera.ui.components.AnimatedWoodTextButton
import com.duoc.madera.viewmodels.RegistroViewModel

@Composable
fun RegistroView(
    onRegistroSuccess: (Registro) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegistroViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

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
            value = viewModel.nombre,
            onValueChange = { viewModel.onNombreChanged(it) },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = viewModel.nombreError != null,
            supportingText = {
                viewModel.nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = viewModel.apellido,
            onValueChange = { viewModel.onApellidoChanged(it) },
            label = { Text("Apellido") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = viewModel.apellidoError != null,
            supportingText = {
                viewModel.apellidoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.onCorreoChanged(it) },
            label = { Text("Correo Electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = viewModel.correoError != null,
            supportingText = {
                viewModel.correoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = viewModel.user,
            onValueChange = { viewModel.onUserChanged(it) },
            label = { Text("Nombre de Usuario") },
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            isError = viewModel.userError != null,
            supportingText = {
                viewModel.userError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = viewModel.contrasenia,
            onValueChange = { viewModel.onContraseniaChanged(it) },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.contraseniaError != null,
            supportingText = {
                viewModel.contraseniaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                viewModel.registrar(onRegistroSuccess)
            }),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedWoodButton(
            onClick = {
                focusManager.clearFocus()
                viewModel.registrar(onRegistroSuccess)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedWoodTextButton(onClick = onNavigateToLogin) {
            Text("Volver al Login")
        }
    }
}
