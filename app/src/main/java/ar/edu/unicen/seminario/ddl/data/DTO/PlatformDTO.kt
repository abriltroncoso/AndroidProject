package ar.edu.unicen.seminario.ddl.data.DTO

import ar.edu.unicen.seminario.ddl.models.Platform
import com.google.gson.annotations.SerializedName

class PlatformDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String,

    ) {

    fun toPlatform(): Platform {
        return Platform(
            id = id,
            slug = slug,
            name = name
        )
    }
}