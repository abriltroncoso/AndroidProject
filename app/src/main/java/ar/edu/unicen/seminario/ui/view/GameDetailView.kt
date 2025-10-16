package ar.edu.unicen.seminario.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.data.repositories.RawgRepository
import ar.edu.unicen.seminario.ddl.models.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailView @Inject constructor(private val rawgRepositorie: RawgRepository) : ViewModel() {
    private val _game : MutableStateFlow<Game?> = MutableStateFlow(null)
    val game : StateFlow<Game?> = _game.asStateFlow()

    private val _loading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading.asStateFlow()

    private val _error : MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    fun getGame(id: Int, Key: String){
        viewModelScope.launch {
            getGameSuspend(id, Key)
        }

    }
    suspend fun getGameSuspend(id: Int, key: String){
        viewModelScope.launch {
            _loading.value = true
            try {
                _game.value = rawgRepositorie.getGame(id,key)

            } catch (e: Exception) {
                _error.value = e.message
            }
            finally {
                _loading.value = false
            }
        }
    }
}