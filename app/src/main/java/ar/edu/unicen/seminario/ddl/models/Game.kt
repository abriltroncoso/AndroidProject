package ar.edu.unicen.seminario.ddl.models


data class Game(
    val id: Int? = null,
    val slug: String? = null,
    val name: String? = null,
    val released: String? = null,
    val backgroundImg: String? = null,
    val rating: Float? = null,
    val ratingTop: Int? = null,
    val ratingsCount: Int? = null,
    val reviewsTextCount: String? = null,
    val added: Int? = null,
    val playTime: Int? = null,
    val metaCritic: Int? = null,
    val description: String? = null,
    val descriptionRaw: String? = null,
    val platform: List<PlatformInfo>? = null,
    val tba: Boolean? = null,
    val addedByStatus: Map<String, Any>? = null,
    val suggestionsCount: Int? = null,
    val updated: String? = null,
    val esrbRating: EsrbRating? = null
)
val emptyGame = Game()