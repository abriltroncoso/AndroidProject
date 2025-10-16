package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.models.EsrbRating
import com.google.gson.annotations.SerializedName

class esrbDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String,

    ) {

    fun toEsrbRating(): EsrbRating {
        return EsrbRating(
            id = id,
            slug = slug,
            name = name,
        )
    }
}