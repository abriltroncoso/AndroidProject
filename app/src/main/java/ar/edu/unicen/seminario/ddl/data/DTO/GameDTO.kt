package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.models.Game
import com.google.gson.annotations.SerializedName

data class GameDTO(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("released") val released: String? = null,
    @SerializedName("tba") val tba: Boolean = false,
    @SerializedName("background_image") val backgroundImg: String? = null,
    @SerializedName("rating") val rating: Float? = 0f,
    @SerializedName("rating_top") val ratingTop: Int? = 0,
    // ratings: el JSON muestra "ratings": {} -> modelalo como objeto genérico o lista según lo que devuelva tu API
    @SerializedName("ratings") val ratings: Any? = null,
    // ratings_count en el JSON es un entero
    @SerializedName("ratings_count") val ratingsCount: Int? = 0,
    @SerializedName("reviews_text_count") val reviewsTextCount: String? = null,
    @SerializedName("added") val added: Int? = 0,
    // added_by_status puede ser un objeto con claves y valores enteros
    @SerializedName("added_by_status") val addedByStatus: Map<String, Int>? = emptyMap(),
    @SerializedName("metacritic") val metaCritic: Int? = 0,
    @SerializedName("playtime") val playTime: Int? = 0,
    @SerializedName("suggestions_count") val suggestionsCount: Int? = 0,
    @SerializedName("updated") val updated: String? = null,
    @SerializedName("esrb_rating") val esrbRating: esrbDTO? = null,
    // platforms *es una lista* en el JSON
    @SerializedName("platforms") val platforms: List<PlatformInfoDTO>? = emptyList(),
    // description puede no venir en la respuesta list; por eso lo hago nullable
    @SerializedName("description") val description: String? = null
) {
    fun convertIntoGame(): Game {
        return Game(
            id = id,
            slug = slug ?: "",
            name = name ?: "",
            released = released ?: "",
            backgroundImg = backgroundImg ?: "",
            rating = rating ?: 0f,
            ratingTop = ratingTop ?: 0,
            // si tu modelo Game espera un Int para ratingsCount:
            ratingsCount = ratingsCount ?: 0,
            reviewsTextCount = reviewsTextCount ?: "",
            added = added ?: 0,
            playTime = playTime ?: 0,
            metaCritic = metaCritic ?: 0,
            description = description ?: "",
            tba = tba,
            addedByStatus = addedByStatus ?: emptyMap(),
            suggestionsCount = suggestionsCount ?: 0,
            updated = updated ?: "",
            esrbRating = esrbRating?.toEsrbRating(),
            // convierto la lista de platforms a la estructura que tu Game espera
            platform = platforms?.map { it.toPlatformInfo() } ?: emptyList(),
            descriptionRaw = description ?: ""
        )
    }
}



