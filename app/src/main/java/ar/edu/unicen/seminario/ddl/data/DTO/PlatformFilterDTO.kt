package ar.edu.unicen.seminario.ddl.data.DTO

import com.google.gson.annotations.SerializedName

class PlatformFilterDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {


}