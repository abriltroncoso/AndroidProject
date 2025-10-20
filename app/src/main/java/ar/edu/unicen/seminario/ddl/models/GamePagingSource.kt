package ar.edu.unicen.seminario.ddl.models

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.seminario.ddl.data.RawgApi
import ar.edu.unicen.seminario.ddl.data.repositories.RawgRepository

class GamePagingSource(
    private val rawgRepository: RawgRepository,
    private val apiKey: String,
    private val platforms: String? = null,
    private val genres: String? = null,
    private val ordering: String? = null,
) : PagingSource<Int, Game>() {

    init {
        android.util.Log.d("GamePagingSource", "🎮 Creado con:")
        android.util.Log.d("GamePagingSource", "  platforms=$platforms")
        android.util.Log.d("GamePagingSource", "  genres=$genres")
        android.util.Log.d("GamePagingSource", "  ordering=$ordering")
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val currentPage = params.key ?: 1
            val games = rawgRepository.getGames(
                apiKey,
                currentPage,
                params.loadSize,
                platforms,
                genres,
                ordering,
            )
            LoadResult.Page(
                data = games,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (games.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}
