package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.ddl.data.DTO.GameDTO
import ar.edu.unicen.seminario.ddl.data.DTO.ResponseGameDTO
import ar.edu.unicen.seminario.ddl.models.GenresResponse
import ar.edu.unicen.seminario.ddl.models.PlatformResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApi {

    @GET("games")
    suspend fun getGames(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("platforms") platforms: String? = null,
        @Query("genres") genres: String? = null,
        @Query("ordering") ordering: String? = null
    ) : Response<ResponseGameDTO>


    @GET("games/{id}")
    suspend fun getGame(
        @Path("id") id: Int,
        @Query("key") key: String,
    ) : Response<GameDTO>


    @GET("genres")
    suspend fun getGenres(
        @Query("key") key: String,
    ) : Response<GenresResponse>

    @GET("platforms")
    suspend fun getPlatforms(
        @Query("key") key: String,
    ) : Response<PlatformResponse>







}