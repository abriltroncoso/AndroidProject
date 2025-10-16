package ar.edu.unicen.seminario.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.constants.Constants
import ar.edu.unicen.seminario.ddl.data.RawgApi
import ar.edu.unicen.seminario.ddl.data.repositories.RawgRepository
import ar.edu.unicen.seminario.ddl.models.Game
import ar.edu.unicen.seminario.ddl.models.GamePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
data class GameFilters(
    val platforms: String? = null,
    val genres: String? = null,
    val ordering: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(private val ragwRepository: RawgRepository ) : ViewModel(){

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Filtros actuales
   private val _filters = MutableStateFlow(GameFilters())

    val gamesPagingData: Flow<PagingData<Game>> =
        _filters.flatMapLatest { filters ->
            Pager(
                    config = PagingConfig(pageSize = 20),
                     pagingSourceFactory = {
                     GamePagingSource(ragwRepository, Constants.API_KEY,filters.platforms,filters.genres,filters.ordering)
                     }
                 ).flow
        }
            .cachedIn(viewModelScope)


    fun applyFilters(platforms: String? = null, genres: String? = null, ordering: String? = null){
        _filters.value = GameFilters(
            platforms = platforms,
            genres = genres,
            ordering = ordering
        )

    }


}


