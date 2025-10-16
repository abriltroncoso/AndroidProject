package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.models.GameResponse
import com.google.gson.annotations.SerializedName


data class ResponseGameDTO(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    // ojo: "results" con l — debe coincidir con el JSON
    @SerializedName("results") val results: List<GameDTO> = emptyList()
) {
    fun toResponseGame(): GameResponse {
        return GameResponse(
            count = count,
            next = next ?: "",
            previous = previous ?: "",
            results = results.map { it.convertIntoGame() }
        )
    }
}