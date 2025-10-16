package ar.edu.unicen.seminario.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.constants.Constants
import ar.edu.unicen.seminario.ddl.data.DTO.GenresDTO
import ar.edu.unicen.seminario.ddl.data.DTO.PlatformFilterDTO
import ar.edu.unicen.seminario.ddl.data.repositories.RawgRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(private val rawgRepository: RawgRepository) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _platforms = MutableStateFlow<List< PlatformFilterDTO>>(emptyList())
    val platforms = _platforms.asStateFlow()

    private val _genres = MutableStateFlow<List< GenresDTO>>(emptyList())
    val genres = _genres.asStateFlow()

    fun loadFiltersData(){
        viewModelScope.launch {
            _loading.value = true

            try {
                val platformsDeferred = launch{
                    _platforms.value = rawgRepository.getPlatforms(Constants.API_KEY)
                }
                val genresDeferred = launch {
                    _genres.value = rawgRepository.getGenres(Constants.API_KEY)
                }
                platformsDeferred.join()
                genresDeferred.join()
            }catch (e: Exception){
                _error.value = e.message
            }finally {
                _loading.value = false
            }

        }
    }
}