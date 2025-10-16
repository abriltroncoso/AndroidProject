package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.ddl.data.DTO.GenresDTO
import ar.edu.unicen.seminario.ddl.data.DTO.PlatformFilterDTO
import ar.edu.unicen.seminario.ddl.models.Game
import ar.edu.unicen.seminario.ddl.models.GenresResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



class RawgRemoteDataSource @Inject constructor(
    private val api: RawgApi
) {

    suspend fun getGames(key: String, page: Int, pageSize: Int, platforms: String? = null, genres: String? = null, ordering: String? = null): List<Game> {
        return withContext(Dispatchers.IO) {
            android.util.Log.d("RawgRemoteDataSource", "🌐 Llamando a API con:")
            android.util.Log.d("RawgRemoteDataSource", "  key=$key")
            android.util.Log.d("RawgRemoteDataSource", "  page=$page")
            android.util.Log.d("RawgRemoteDataSource", "  pageSize=$pageSize")
            android.util.Log.d("RawgRemoteDataSource", "  platforms=$platforms")
            android.util.Log.d("RawgRemoteDataSource", "  genres=$genres")
            android.util.Log.d("RawgRemoteDataSource", "  ordering=$ordering")
            val response = api.getGames(key, page, pageSize, platforms, genres, ordering)
            if (response.isSuccessful && response.body() != null) {
                android.util.Log.d("RawgRemoteDataSource", "✅ Recibidos ${response.body()!!.results.size} juegos recibidos de la API")
                response.body()!!.results.map { it.convertIntoGame() }

            } else {
                android.util.Log.e("RawgRemoteDataSource", "❌ Error HTTP: ${response.code()}")
                throw Exception("Error HTTP: ${response.code()}")

            }
        }
    }

    suspend fun getGame(id: Int, key: String): Game {
        return withContext(Dispatchers.IO) {
            val response = api.getGame(id, key)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.convertIntoGame()
            } else {
                throw Exception("Error HTTP: ${response.code()}")
            }
        }
    }

    suspend fun getPlatforms(key: String): List<PlatformFilterDTO> {
        return withContext(Dispatchers.IO) {
            val response = api.getPlatforms(key)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results
            } else {
                throw Exception("Error HTTP: ${response.code()}")
            }
        }
    }

    suspend fun getGenres(key: String): List<GenresDTO> {
        return withContext(Dispatchers.IO) {
            val response = api.getGenres(key)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results
            } else {
                throw Exception("Error HTTP: ${response.code()}")
            }
        }
    }






}