package com.duoc.madera.viewmodels

import androidx.lifecycle.ViewModel
import com.duoc.madera.models.MaderaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _maderas = MutableStateFlow<List<MaderaItem>>(emptyList())
    val maderas: StateFlow<List<MaderaItem>> = _maderas.asStateFlow()

    init {
        cargarMaderas()
    }

    private fun cargarMaderas() {
        _maderas.value = listOf(
            MaderaItem("Nogal Americano", "Madera Dura", "Excelente durabilidad y acabado elegante"),
            MaderaItem("Pino Silvestre", "Madera Blanda", "Fácil trabajo, ideal para carpintería general"),
            MaderaItem("Roble Blanco", "Madera Dura", "Resistente a la humedad y de gran densidad"),
            MaderaItem("Cerezo", "Madera Semidura", "Tono rojizo único y pulido suave")
        )
    }
}
