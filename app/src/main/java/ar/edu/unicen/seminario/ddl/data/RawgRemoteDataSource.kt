package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.ddl.data.DTO.GenresDTO
import ar.edu.unicen.seminario.ddl.data.DTO.PlatformFilterDTO
import ar.edu.unicen.seminario.ddl.models.Game
import ar.edu.unicen.seminario.ddl.models.GenresResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject



class RawgRemoteDataSource @Inject constructor(
    private val api: RawgApi
) {

    suspend fun getGames(key: String, page: Int, pageSize: Int, platforms: String? = null, genres: String? = null, ordering: String? = null): List<Game> {
        return withContext(Dispatchers.IO) {
            val response = api.getGames(key, page, pageSize, platforms, genres, ordering)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.results.map { it.convertIntoGame() }

            } else {
                throw Exception("Error HTTP: ${response.code()}")

            }
        }
    }

    suspend fun getGame(id: Int, key: String): Game {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getGame(id, key)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.convertIntoGame()
                } else {
                    throw Exception("Error HTTP: ${response.code()}")
                }
            } catch (e: UnknownHostException) {
                throw Exception("Unable to resolve host")
            } catch (e: SocketTimeoutException) {
                throw Exception("Connection timeout")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getPlatforms(key: String): List<PlatformFilterDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPlatforms(key)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.results
                } else {
                    throw Exception("Error HTTP: ${response.code()}")
                }
            } catch (e: UnknownHostException) {
                throw Exception("Unable to resolve host")
            } catch (e: SocketTimeoutException) {
                throw Exception("Connection timeout")
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getGenres(key: String): List<GenresDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getGenres(key)
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.results
                } else {
                    throw Exception("Error HTTP: ${response.code()}")
                }
            } catch (e: UnknownHostException) {
                throw Exception("Unable to resolve host")
            } catch (e: SocketTimeoutException) {
                throw Exception("Connection timeout")
            } catch (e: Exception) {
                throw e
            }
        }
    }






}