package com.esan.diarioespacial.ui.asteroids

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.esan.diarioespacial.DiarioApp
import com.esan.diarioespacial.data.local.AsteroidEntity
import com.esan.diarioespacial.data.remote.RetrofitClient
import com.esan.diarioespacial.data.remote.neows.NeoWsApi
import com.esan.diarioespacial.data.repository.AsteroidRepository
import com.esan.diarioespacial.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AsteroidUiState(
    val isLoading: Boolean = false,
    val items: List<AsteroidEntity> = emptyList(),
    val onlyHazardous: Boolean = false,
    val error: String? = null
)

class AsteroidViewModel(private val repository: AsteroidRepository) : ViewModel() {

    // Últimos 7 días (rango máximo permitido por NeoWs)
    private val start = DateUtils.daysAgo(6)
    private val end = DateUtils.today()

    private val isLoading = MutableStateFlow(false)
    private val error = MutableStateFlow<String?>(null)
    private val onlyHazardous = MutableStateFlow(false)

    val uiState: StateFlow<AsteroidUiState> =
        combine(
            repository.observeRange(start, end),
            onlyHazardous,
            isLoading,
            error
        ) { list, onlyH, loading, err ->
            AsteroidUiState(
                isLoading = loading,
                items = if (onlyH) list.filter { it.isHazardous } else list,
                onlyHazardous = onlyH,
                error = err
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AsteroidUiState(isLoading = true))

    val favorites: StateFlow<List<AsteroidEntity>> =
        repository.observeFavorites()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun observeById(id: String): Flow<AsteroidEntity?> = repository.observeById(id)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                repository.refresh(start, end)
            } catch (e: Exception) {
                error.value = "No se pudo conectar con la NASA. Revisa tu conexión o tu API key."
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setOnlyHazardous(value: Boolean) {
        onlyHazardous.value = value
    }

    fun toggleFavorite(item: AsteroidEntity) {
        viewModelScope.launch { repository.toggleFavorite(item) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DiarioApp
                AsteroidViewModel(
                    AsteroidRepository(
                        api = RetrofitClient.retrofit.create(NeoWsApi::class.java),
                        dao = app.database.asteroidDao()
                    )
                )
            }
        }
    }
}
