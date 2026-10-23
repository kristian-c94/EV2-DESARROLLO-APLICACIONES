package com.duoc.madera.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.duoc.madera.models.Registro
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {
    private val gson = Gson()

    companion object {
        private val REGISTERED_USERS_KEY = stringPreferencesKey("registered_users_list")
        private val LAST_LOGGED_USER_KEY = stringPreferencesKey("last_logged_user")
    }

    val usuariosFlow: Flow<List<Registro>> = context.dataStore.data.map { preferences ->
        val json = preferences[REGISTERED_USERS_KEY]
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Registro>>() {}.type
            try {
                gson.fromJson<List<Registro>>(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    val lastLoggedUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LAST_LOGGED_USER_KEY]
    }

    suspend fun guardarUsuarios(usuarios: List<Registro>) {
        val json = gson.toJson(usuarios)
        context.dataStore.edit { preferences ->
            preferences[REGISTERED_USERS_KEY] = json
        }
    }

    suspend fun guardarUsuarioConectado(usuario: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_LOGGED_USER_KEY] = usuario
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { preferences ->
            preferences.remove(LAST_LOGGED_USER_KEY)
        }
    }
}
