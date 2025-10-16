package ar.edu.unicen.seminario.ddl.data.repositories

import ar.edu.unicen.seminario.ddl.data.DTO.GenresDTO
import ar.edu.unicen.seminario.ddl.data.DTO.PlatformFilterDTO
import ar.edu.unicen.seminario.ddl.data.RawgRemoteDataSource
import ar.edu.unicen.seminario.ddl.models.Game
import javax.inject.Inject

class RawgRepository @Inject constructor(private val remoteDataSource: RawgRemoteDataSource) {

    suspend fun getGames(Key: String,page: Int, pageSize: Int, platforms: String? = null, genres: String? = null, ordering: String? = null): List<Game> {
        return remoteDataSource.getGames(Key, page, pageSize, platforms, genres, ordering)
    }

    suspend fun getGame(id: Int, key: String): Game {
        return remoteDataSource.getGame(id,key)
    }
    suspend fun getPlatforms(key: String): List<PlatformFilterDTO> {
        return remoteDataSource.getPlatforms(key)
    }

    suspend fun getGenres(key: String): List<GenresDTO> {
        return remoteDataSource.getGenres(key)
    }

}

